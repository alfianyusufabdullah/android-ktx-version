package alfianyusufabdullah.androidxversion.data.remote

import alfianyusufabdullah.androidxversion.data.entity.ModuleEntity
import android.util.Log
import org.jsoup.Jsoup
import kotlin.math.log

class RemoteDataSource {

    fun getModules(module: String, url: String): List<ModuleEntity> {
        val modules = mutableListOf<ModuleEntity>()
        var packages = ""
        try {
            val request = Jsoup.connect(url).execute()
            val elements = request.parse().allElements.filter {
                it.tagName().contains("-ktx")
            }

            elements.forEach {
                packages = it.tagName()
                val versions = it.attr("versions")
                val latestRelease = versions.split(",").last()
                val latestStableRelease = versions.split(",").filter { release ->
                    !release.contains("beta") && !release.contains("alpha") && !release.contains("rc")
                }

                val ktxModule = ModuleEntity(
                    module = module,
                    packages = packages,
                    latestRelease = latestRelease,
                    latestStableRelease = if (latestStableRelease.isNotEmpty()) latestStableRelease.last() else ""
                )

                modules.add(ktxModule)
            }
        } catch (e: Exception) {
            Log.d("ERR-$packages", e.localizedMessage)
        }

        return modules
    }
}