package top.sunhy.image

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.tttell.xmx.basic.glide.http.OkHttpUrlLoader
import top.sunhy.image.progress.ProgressManager
import java.io.InputStream

@GlideModule(glideName = "ShyGlideModule")
class GlideModule : AppGlideModule() {

    companion object {
        /**
         * 图片缓存目录
         */
        const val IMAGE_DISK_CACHE_NAME = "app-image-cache"
        /**
         * 图片缓存文件最大值为100Mb
         */
        const val IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, IMAGE_DISK_CACHE_NAME, IMAGE_DISK_CACHE_MAX_SIZE.toLong()))
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize
        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(
            ProgressManager.okHttpClient))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}