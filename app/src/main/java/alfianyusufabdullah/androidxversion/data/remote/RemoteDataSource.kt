package alfianyusufabdullah.androidxversion.data.remote

import alfianyusufabdullah.androidxversion.data.entity.ModuleEntity
import org.jsoup.Jsoup

class RemoteDataSource {

    fun getModules(module: String, url: String): List<ModuleEntity> {
        val request = Jsoup.connect(url).execute()
        val elements = request.parse().allElements
        val modules = mutableListOf<ModuleEntity>()

        elements.forEach {
            val tag = it.tagName()
            if (tag.contains("-ktx")) {
                val versions = it.attr("versions")
                val lastVersion = versions.split(",").last()

                modules.add(ModuleEntity(module , tag , lastVersion))
            }
        }

        return modules
    }
}