package com.lee.wanandroid.compressor.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class ImageUtils {

    private ImageUtils() {
        throw new UnsupportedOperationException("u can't initialize me");
    }

    /**
     * Get angle from image attribute.
     *
     * @param path the image file
     * @return the angle of image
     */
    public static int getImageAngle(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * Need the image compress according to the file size and the least compress size.
     *
     * @param filePath          file path
     * @param leastCompressSize least compress size
     * @return true if need to compress
     */
    public static boolean needCompress(String filePath, int leastCompressSize) {
        if (leastCompressSize > 0) {
            File source = new File(filePath);
            return source.exists() && source.length() > (leastCompressSize << 10);
        }
        return true;
    }

    public static String extSuffix(InputStream is) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            return options.outMimeType.replace("image/", ".");
        } catch (Exception e) {
            return ".jpg";
        }
    }

    /**
     * Rotate given bitmap and return the result.
     *
     * @param srcBitmap the source bitmap
     * @param angle     the angle to rotate
     * @return the rotated bitmap
     */
    public static Bitmap rotateBitmap(Bitmap srcBitmap, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
    }

}
