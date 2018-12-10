package alfianyusufabdullah.androidxversion.data.remote

import alfianyusufabdullah.androidxversion.data.entity.ModuleEntity
import android.util.Log
import org.jsoup.Jsoup

class RemoteDataSource {

    fun getModules(module: String, url: String): List<ModuleEntity> {
        val modules = mutableListOf<ModuleEntity>()
        try {
            val request = Jsoup.connect(url).execute()
            val elements = request.parse().allElements

            elements.forEach {
                val tag = it.tagName()
                if (tag.contains("-ktx")) {
                    val versions = it.attr("versions")
                    val lastVersion = versions.split(",").last()

                    modules.add(ModuleEntity(module, tag, lastVersion))
                }
            }
        } catch (e: Exception){
            Log.d("ERR" , e.localizedMessage)
        }

        return modules
    }
}