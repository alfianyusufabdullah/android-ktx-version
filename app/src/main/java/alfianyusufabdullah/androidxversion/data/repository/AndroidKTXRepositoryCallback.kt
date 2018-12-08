package alfianyusufabdullah.androidxversion.data.repository

import alfianyusufabdullah.androidxversion.data.entity.ModuleEntity

interface AndroidKTXRepositoryCallback {
    fun onStartToLoad()
    fun onLoad(module: String)
    fun onNext(module: ModuleEntity)
    fun onCompleted(error: Boolean)
}