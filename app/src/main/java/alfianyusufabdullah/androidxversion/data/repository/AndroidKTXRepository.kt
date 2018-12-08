package alfianyusufabdullah.androidxversion.data.repository

import alfianyusufabdullah.androidxversion.data.entity.UrlEntity
import alfianyusufabdullah.androidxversion.data.remote.RemoteDataSource
import kotlinx.coroutines.*

class AndroidKTXRepository(private val dataSource: RemoteDataSource) {

    private val urls = listOf(
        UrlEntity("core", "https://dl.google.com/dl/android/maven2/androidx/core/group-index.xml"),
        UrlEntity("fragment", "https://dl.google.com/dl/android/maven2/androidx/fragment/group-index.xml"),
        UrlEntity("palette", "https://dl.google.com/dl/android/maven2/androidx/palette/group-index.xml"),
        UrlEntity("sqlite", "https://dl.google.com/dl/android/maven2/androidx/sqlite/group-index.xml"),
        UrlEntity("collection", "https://dl.google.com/dl/android/maven2/androidx/collection/group-index.xml"),
        UrlEntity("lifecycle", "https://dl.google.com/dl/android/maven2/androidx/lifecycle/group-index.xml"),
        UrlEntity("Arch navigation", "https://dl.google.com/dl/android/maven2/android/arch/navigation/group-index.xml"),
        UrlEntity("Arch work", "https://dl.google.com/dl/android/maven2/android/arch/work/group-index.xml")
    )

    fun getModule(callback: AndroidKTXRepositoryCallback) = GlobalScope.launch(Dispatchers.Main) {
        callback.onStartToLoad()
        var counter = 0
        urls.forEach { url ->
            callback.onLoad(url.module)
            val module = withContext(Dispatchers.IO) { dataSource.getModules(url.module, url.url) }

            if (module.isEmpty()) {
                counter++
            } else {
                synchronized(this) {
                    module.forEach {
                        callback.onNext(it)
                    }
                }
            }
        }

        if (counter > 0) {
            callback.onCompleted(true)
        } else {
            callback.onCompleted(false)
        }
    }
}