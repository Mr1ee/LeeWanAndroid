package com.lee.wanandroid.compressor.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import com.lee.wanandroid.compressor.Config;

import java.io.*;

public final class FileUtils {

    private static final String TAG = "FileUtils";

    private FileUtils() {
        throw new UnsupportedOperationException("u can't initialize me");
    }

    private static boolean isStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Get the default cache directory.
     *
     * @param context   the context to get cache directory.
     * @param cacheName cache directory name.
     * @return the cache directory file.
     */
    public static File getDefaultCacheDir(Context context, String cacheName) {
        if (!isStorageWritable()) {
            return null;
        }

        File cacheDir = context.getExternalCacheDir();
        if (cacheDir != null) {
            File result = new File(cacheDir, cacheName);
            if (!result.mkdirs() && (!result.exists() || !result.isDirectory())) {
                // File wasn't able to create a directory, or the result exists but not a directory
                return null;
            }
            return result;
        }
        if (Log.isLoggable(TAG, Log.ERROR)) {
            Log.e(TAG, "default disk cache dir is null");
        }
        return null;
    }

    public static File uriToFile(Context context, Uri uri) throws IOException {
        File dir = FileUtils.getDefaultCacheDir(context, Config.DEFAULT_ALBUM_FILE_CACHE_DIR);
        String fileName = FileUtils.getFileName(context, uri);
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        File outFile = null;
        if (dir != null) {
            String cacheBuilder = dir.getAbsolutePath() + fileName;
            outFile = new File(cacheBuilder);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(outFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (inputStream != null) {
                copyFile(inputStream, out);
                inputStream.close();
            }

            if (out != null) {
                out.close();
            }
        }
        return outFile;
    }

    private static String getFileName(Context context, Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf(File.separator);
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    /**
     * Copy file from source to destination.
     *
     * @param source      the source file.
     * @param destination the destination to copy to.
     * @return is copy succeed.
     */
    public static boolean copyFile(File source, File destination) {
        try {
            return copyFile(new FileInputStream(source), new FileOutputStream(destination));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error copying file : " + e);
            return false;
        }
    }

    /**
     * Copy from the original to destination, based on the input and output stream.
     *
     * @param is the input stream.
     * @param os the output stream.
     * @return is copy succeed.
     */
    public static boolean copyFile(InputStream is, OutputStream os) {
        boolean res = false;
        byte[] data = new byte[1024];
        int len;
        try {
            while ((len = is.read(data)) > 0) {
                os.write(data, 0, len);
            }
            is.close();
            os.close();
            res = true;
        } catch (IOException e) {
            Log.e(TAG, "Error copying file : " + e);
        }
        return res;
    }
}
