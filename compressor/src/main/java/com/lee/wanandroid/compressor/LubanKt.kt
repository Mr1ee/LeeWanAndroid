package com.lee.wanandroid.compressor

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.util.Size
import androidx.annotation.IntRange
import com.lee.wanandroid.compressor.utils.FileUtils
import com.lee.wanandroid.compressor.utils.ImageUtils
import io.reactivex.Observable
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

/**
 *
 * @Description:    LubanKt
 * @Author:         lihuayong
 * @CreateDate:     2019-05-17 22:56
 * @UpdateUser:
 * @UpdateDate:     2019-05-17 22:56
 * @UpdateRemark:
 * @Version:        1.0
 */
class LubanKt private constructor(builder: Builder<*>) {

    companion object {
        @JvmStatic
        fun asFile(context: Context): Builder<File> {
            return Builder(context, false)
        }

        @JvmStatic
        fun asBitmap(context: Context): Builder<Bitmap> {
            return Builder(context, true)
        }
    }

    private var mTargetDir: String? = null
    private val focusAlpha: Boolean
    private val mQuality: Int
    private val mSize: Size?
    private val mLeastCompressSize: Int
    @ScaleMode.Mode
    private val scaleMode: Int
    private val mRenameListener: OnRenameListener?
    private val mCompressionPredicate: CompressionPredicate?
    private val mStreamProviders: MutableList<InputStreamProvider>

    init {
        this.mTargetDir = builder.mTargetDir
        this.focusAlpha = builder.focusAlpha
        this.mQuality = builder.mQuality
        this.mSize = builder.mSize
        this.scaleMode = builder.scaleMode
        this.mRenameListener = builder.mRenameListener
        this.mStreamProviders = builder.mStreamProviders
        this.mLeastCompressSize = builder.mLeastCompressSize
        this.mCompressionPredicate = builder.mCompressionPredicate
    }

    /**
     * Returns a file with a cache image name in the private cache directory.
     *
     * @param context A context.
     */
    private fun getImageCacheFile(context: Context, suffix: String): File {
        if (TextUtils.isEmpty(mTargetDir)) {
            mTargetDir = getImageCacheDir(context)?.absolutePath
        }

        val cacheBuilder = mTargetDir + "/" +
                System.currentTimeMillis() +
                (Math.random() * 1000).toInt() +
                if (TextUtils.isEmpty(suffix)) ".jpg" else suffix

        return File(cacheBuilder)
    }

    private fun getImageCustomFile(context: Context, filename: String): File {
        if (TextUtils.isEmpty(mTargetDir)) {
            mTargetDir = getImageCacheDir(context)?.absolutePath
        }

        val cacheBuilder = "$mTargetDir/$filename"

        return File(cacheBuilder)
    }

    /**
     * Returns a directory with a default name in the private cache directory of the application to
     * use to store retrieved audio.
     *
     * @param context A context.
     * @see [FileUtils].getImageCacheDir
     */
    private fun getImageCacheDir(context: Context): File? {
        return FileUtils.getDefaultCacheDir(context, Config.DEFAULT_DISK_CACHE_DIR)
    }

    /**
     * start compressToFile and return the file
     */
    @Throws(IOException::class)
    private fun getFile(input: InputStreamProvider, context: Context): File {
        try {
            return Engine(
                input,
                getImageCacheFile(context, ImageUtils.extSuffix(input.open())),
                focusAlpha,
                mQuality,
                mSize,
                scaleMode
            ).compressToFile()
        } finally {
            input.close()
        }
    }

    /**
     * start compressToFile and return the file
     */
    @Throws(IOException::class)
    private fun getBitmap(input: InputStreamProvider, context: Context): Bitmap {
        try {
            return Engine(input, null, focusAlpha, mQuality, mSize, scaleMode).compressToBitmap()
        } finally {
            input.close()
        }
    }

    @Throws(IOException::class)
    private fun getFile(context: Context): List<File> {
        val results = ArrayList<File>()
        val iterator = mStreamProviders.iterator()

        while (iterator.hasNext()) {
            results.add(compressToFile(context, iterator.next()))
            iterator.remove()
        }

        return results
    }

    @Throws(IOException::class)
    private fun getBitmap(): List<Bitmap> {
        val results = ArrayList<Bitmap>()
        val iterator = mStreamProviders.iterator()

        while (iterator.hasNext()) {
            results.add(compressToBitmap(iterator.next()))
            iterator.remove()
        }

        return results
    }

    @Throws(IOException::class)
    private fun compressToFile(context: Context, path: InputStreamProvider): File {
        try {
            return compressReal(context, path)
        } finally {
            path.close()
        }
    }

    @Throws(IOException::class)
    private fun compressToBitmap(path: InputStreamProvider): Bitmap {
        try {
            return Engine(path, null, focusAlpha, mQuality, mSize, scaleMode).compressToBitmap()
        } finally {
            path.close()
        }
    }

    @Throws(IOException::class)
    private fun compressReal(context: Context, path: InputStreamProvider): File {

        var outFile = getImageCacheFile(context, ImageUtils.extSuffix(path.open()))

        mRenameListener?.let {
            val filename = it.rename(path.path)
            outFile = getImageCustomFile(context, filename)
        }

        return mCompressionPredicate?.let {
            if (it.apply(path.path) && ImageUtils.needCompress(path.path, mLeastCompressSize)) {
                Engine(path, outFile, focusAlpha, mQuality, mSize, scaleMode).compressToFile()
            } else {
                File(path.path)
            }
        } ?: if (ImageUtils.needCompress(path.path, mLeastCompressSize)) {
            Engine(path, outFile, focusAlpha, mQuality, mSize, scaleMode).compressToFile()
        } else {
            File(path.path)
        }
    }

    class Builder<V> internal constructor(private val context: Context, private val retBitmap: Boolean = false) {
        internal var mTargetDir: String? = null
        internal var focusAlpha: Boolean = Config.DEFAULT_FOCUS_ALPHA
        internal var mQuality: Int = Config.DEFAULT_COMPRESS_QUALITY
        internal var mSize: Size? = null
        internal var mLeastCompressSize = Config.DEFAULT_IGNORE_SIZE
        @ScaleMode.Mode
        internal var scaleMode = Config.COMPRESSOR_DEFAULT_SCALE_MODE
        internal var mRenameListener: OnRenameListener? = null
        internal var mCompressionPredicate: CompressionPredicate? = null
        internal val mStreamProviders: MutableList<InputStreamProvider>

        init {
            this.mStreamProviders = ArrayList()
        }

        private fun build(): LubanKt {
            return LubanKt(this)
        }

        /**
         * begin compressToFile image with synchronize
         *
         * @return the thumb image file list
         */
        @Throws(IOException::class)
        private fun getFile(): List<File> = build().getFile(context)

        /**
         * begin compressToBitmap image with synchronize
         *
         * @return the thumb image file list
         */
        @Throws(IOException::class)
        private fun getBitmap(): List<Bitmap> = build().getBitmap()


        fun load(inputStreamProvider: InputStreamProvider): Builder<V> {
            mStreamProviders.add(inputStreamProvider)
            return this
        }

        fun <T> load(file: T): Builder<V> {
            return when (file) {
                is String -> load(file as String)
                is File -> load(file as File)
                is Uri -> load(file as Uri)
                else -> throw IllegalArgumentException("Incoming data type exception, it must be String, File, Uri or Bitmap")
            }
        }

        fun load(file: File): Builder<V> {
            mStreamProviders.add(object : InputStreamAdapter() {
                @Throws(IOException::class)
                override fun openInternal(): InputStream {
                    return FileInputStream(file)
                }

                override fun getPath(): String {
                    return file.absolutePath
                }
            })
            return this
        }

        fun load(string: String): Builder<V> {
            mStreamProviders.add(object : InputStreamAdapter() {
                @Throws(IOException::class)
                override fun openInternal(): InputStream {
                    return FileInputStream(string)
                }

                override fun getPath(): String {
                    return string
                }
            })
            return this
        }

        /**
         * 从相机拿过来的Uri并不能拿到File
         */
        fun load(uri: Uri): Builder<V> {
            val file = FileUtils.uriToFile(context, uri)
            return load(file)
        }

        fun <T> load(list: List<T>): Builder<V> {
            for (src in list) {
                load(src)
            }
            return this
        }

        fun setRenameListener(listener: OnRenameListener): Builder<V> {
            this.mRenameListener = listener
            return this
        }

        /**
         * set the compressed file store directory
         */
        fun setTargetDir(targetDir: String): Builder<V> {
            this.mTargetDir = targetDir
            return this
        }

        /**
         * Do I need to keep the image's alpha channel
         *
         * @param focusAlpha
         *
         * true - compress to PNG, to keep alpha channel, the compressToFile speed will be slow.
         *
         * false - compress to JPG, don't keep alpha channel, it might have a black background.
         */
        fun setFocusAlpha(focusAlpha: Boolean): Builder<V> {
            this.focusAlpha = focusAlpha
            return this
        }

        /**
         * set compress quality
         *
         * @param quality compress quality [1,100]
         */
        fun setQuality(@IntRange(from = 1, to = 100) quality: Int): Builder<V> {
            this.mQuality = quality
            return this
        }

        /**
         * if set size, use Compressor inSampleSize calculate algorithm
         * if not set, use Luban's inSampleSize calculate algorithm
         *
         * @param size request compressToFile size
         */
        fun setCompressSize(size: Size): Builder<V> {
            this.mSize = size
            return this
        }

        /**
         * do not compressToFile when the origin image file size less than one value
         *
         * @param size the value of file size, unit KB, default 100K
         */
        fun ignoreBy(size: Int): Builder<V> {
            this.mLeastCompressSize = size
            return this
        }

        /**
         * do compressToFile image when return value was true, otherwise, do not compressToFile the image file
         *
         * @param compressionPredicate A predicate callback that returns true or false for the given input path should be compressed.
         */
        fun filter(compressionPredicate: CompressionPredicate): Builder<V> {
            this.mCompressionPredicate = compressionPredicate
            return this
        }

        /**
         * set compress scale mode, only apply with [setCompressSize]
         * @param mode scale mode [ScaleMode]
         */
        fun setScaleMode(@ScaleMode.Mode mode: Int): Builder<V> {
            this.scaleMode = mode
            return this
        }

        fun get(): List<V> {
            return if (retBitmap) {
                getBitmap() as List<V>
            } else {
                getFile() as List<V>
            }
        }

        fun asObservable(): Observable<List<V>> {
            return Observable.create { emitter ->
                emitter.onNext(get())
                emitter.onComplete()
            }
        }
    }
}