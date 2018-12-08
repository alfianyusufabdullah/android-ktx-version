package alfianyusufabdullah.androidxversion.presentation

import alfianyusufabdullah.androidxversion.data.entity.ModuleEntity
import alfianyusufabdullah.androidxversion.data.repository.AndroidKTXRepository
import alfianyusufabdullah.androidxversion.data.repository.AndroidKTXRepositoryCallback

class MainPresenter(private val androidKTXRepository: AndroidKTXRepository) {

    private var mView: MainView? = null

    fun attachView(view: MainView) {
        mView = view
    }

    fun detachView() {
        mView = null
    }

    fun getModules() {
        androidKTXRepository.getModule(object : AndroidKTXRepositoryCallback {
            override fun onStartToLoad() {
                mView?.onStartToLoad()
            }
            override fun onLoad(module: String) {
                mView?.onLoad(module)
            }

            override fun onNext(module: ModuleEntity) {
                mView?.onNext(module)
            }

            override fun onCompleted(error: Boolean) {
                mView?.onCompleted(error)
            }
        })
    }
}