package com.lee.wanandroid.compressor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Size;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lee.wanandroid.compressor.utils.ImageUtils;

/**
 * Responsible for starting compressToFile & compressToBitmap and managing active and cached resources.
 */
@SuppressWarnings("unused")
class Engine {
    private InputStreamProvider srcImg;
    private File tagImg;
    private int srcWidth;
    private int srcHeight;
    private boolean focusAlpha;
    private int reqQuality;
    @ScaleMode.Mode
    private int scaleMode;
    private String mimeType;
    private Size reqSize;

    public Engine(InputStreamProvider srcImg, File tagImg, boolean focusAlpha, int quality, Size size, @ScaleMode.Mode int mode) throws IOException {
        this.tagImg = tagImg;
        this.srcImg = srcImg;
        this.focusAlpha = focusAlpha;
        this.reqQuality = quality;
        this.reqSize = size;
        this.scaleMode = mode;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeStream(srcImg.open(), null, options);
        this.srcWidth = options.outWidth;
        this.srcHeight = options.outHeight;
        this.mimeType = options.outMimeType;
    }

    /**
     * Luban's inSampleSize calculate algorithm
     */
    private int computeSize() {
        srcWidth = srcWidth % 2 == 1 ? srcWidth + 1 : srcWidth;
        srcHeight = srcHeight % 2 == 1 ? srcHeight + 1 : srcHeight;

        int longSide = Math.max(srcWidth, srcHeight);
        int shortSide = Math.min(srcWidth, srcHeight);

        float scale = ((float) shortSide / longSide);
        if (scale <= 1 && scale > 0.5625) {
            if (longSide < 1664) {
                return 1;
            } else if (longSide < 4990) {
                return 2;
            } else if (longSide > 4990 && longSide < 10240) {
                return 4;
            } else {
                return longSide / 1280;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            return longSide / 1280 == 0 ? 1 : longSide / 1280;
        } else {
            return (int) Math.ceil(longSide / (1280.0 / scale));
        }
    }

    private boolean isNear(float src, float req) {
        return src / req < 1.4;
    }

    /**
     * 使图片不至于缩放太厉害
     */
    private boolean isNear(float srcHeight, float reqHeight, float srcWidth, float reqWidth) {
        return isNear(srcHeight, reqHeight) || isNear(srcWidth, reqWidth);
    }

    /**
     * Compressor's inSampleSize calculate algorithm
     */
    private int computeInSampleSize(int srcWidth, int srcHeight, int reqWidth, int reqHeight) {
        float imgRatio = (float) srcWidth / (float) srcHeight;
        float maxRatio = (float) reqWidth / (float) reqHeight;
        int inSampleSize = 1;

        if (srcHeight < reqHeight || srcWidth < reqWidth) {
            return inSampleSize;
        }

        if (srcHeight > reqHeight || srcWidth > reqWidth) {
            switch (scaleMode) {
                case ScaleMode.SCALE_LARGER: {
                    //If Height is greater
                    if (imgRatio < maxRatio) {
                        reqWidth = (int) (reqHeight * imgRatio);
                    }  //If Width is greater
                    else if (imgRatio > maxRatio) {
                        reqHeight = (int) (reqWidth * (float) srcHeight / (float) srcWidth);
                    }
                    break;
                }
                case ScaleMode.SCALE_SMALLER: {
                    //If Height is greater
                    if (imgRatio < maxRatio) {
                        reqHeight = (int) (reqWidth * (float) srcHeight / (float) srcWidth);
                    }  //If Width is greater
                    else if (imgRatio > maxRatio) {
                        reqWidth = (int) (reqHeight * imgRatio);
                    }
                    break;
                }
                case ScaleMode.SCALE_WIDTH: {
                    reqHeight = (int) (reqWidth * (float) srcHeight / (float) srcWidth);
                    break;
                }
                case ScaleMode.SCALE_HEIGHT: {
                    reqWidth = (int) (reqHeight * imgRatio);
                    break;
                }
                default: {
                    //If Height is greater
                    if (imgRatio < maxRatio) {
                        reqWidth = (int) (reqHeight * imgRatio);
                    }  //If Width is greater
                    else if (imgRatio > maxRatio) {
                        reqHeight = (int) (reqWidth * (float) srcHeight / (float) srcWidth);
                    }
                    break;
                }
            }

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((srcHeight / inSampleSize) >= reqHeight && (srcWidth / inSampleSize) >= reqWidth
                    && !isNear((float) (srcHeight / inSampleSize), (float) reqHeight, (float) (srcWidth / inSampleSize), (float) reqWidth)) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    File compressToFile() throws IOException {
        Bitmap tagBitmap = compressToBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (tagBitmap != null) {
            tagBitmap.compress(focusAlpha ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, reqQuality, stream);
            tagBitmap.recycle();
        }

        FileOutputStream fos = new FileOutputStream(tagImg);
        fos.write(stream.toByteArray());
        fos.flush();
        fos.close();
        stream.close();

        return tagImg;
    }

    Bitmap compressToBitmap() throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (reqSize == null || reqSize.getWidth() <= 1 || reqSize.getHeight() <= 1) {
            options.inSampleSize = computeSize();
        } else {
            options.inSampleSize = computeInSampleSize(srcWidth, srcHeight, reqSize.getWidth(), reqSize.getHeight());
        }

        Bitmap tagBitmap = BitmapFactory.decodeStream(srcImg.open(), null, options);

        if (isJPG(mimeType)) {
            tagBitmap = ImageUtils.rotateBitmap(tagBitmap, ImageUtils.getImageAngle(srcImg.getPath()));
        }
        return tagBitmap;
    }

    private boolean isJPG(String mimeType) {
        if (TextUtils.isEmpty(mimeType)) {
            return false;
        }
        return Config.JPG.equals(mimeType.replace("image/", "."));
    }
}