package com.lee.wanandroid.compressor;

import android.graphics.Bitmap;

/**
 * @Description: Config
 * @Author: lihuayong
 * @CreateDate: 2019-05-18 17:30
 * @UpdateUser:
 * @UpdateDate: 2019-05-18 17:30
 * @UpdateRemark:
 * @Version: 1.0
 */
public final class Config {

    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;

    public static final int DEFAULT_COMPRESS_QUALITY = 75;

    public static final String DEFAULT_DISK_CACHE_DIR = "luban_disk_cache";

    public static final String DEFAULT_ALBUM_FILE_CACHE_DIR = "album_file_cache";

    public static final int DEFAULT_IGNORE_SIZE = 100;

    public static final boolean DEFAULT_FOCUS_ALPHA = false;

    public static final String JPG = ".jpg";

    @ScaleMode.Mode
    public static final int COMPRESSOR_DEFAULT_SCALE_MODE = ScaleMode.SCALE_LARGER;

}