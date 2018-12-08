package alfianyusufabdullah.androidxversion.presentation

import alfianyusufabdullah.androidxversion.R
import alfianyusufabdullah.androidxversion.data.entity.ModuleEntity
import alfianyusufabdullah.androidxversion.data.remote.RemoteDataSource
import alfianyusufabdullah.androidxversion.data.repository.AndroidKTXRepository
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0f

        mainPresenter = MainPresenter(AndroidKTXRepository(RemoteDataSource()))
        mainPresenter.attachView(this)

        rvModule.apply {
            hasFixedSize()
            layoutManager = GridLayoutManager(context , 1)
            adapter = MainAdapter(mutableListOf())
        }

        swipeRefresh.setOnRefreshListener { mainPresenter.getModules() }
        mainPresenter.getModules()

    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }

    override fun onStartToLoad() {
        swipeRefresh.isRefreshing = true
        tvLoadStat.visibility = View.VISIBLE

        (rvModule.adapter as MainAdapter).clear()
    }

    override fun onLoad(module: String) {
        tvLoadStat.text = module
    }

    override fun onNext(module: ModuleEntity) {
        (rvModule.adapter as MainAdapter).add(module)
    }

    override fun onCompleted(error: Boolean) {
        tvLoadStat.text = if (error) "Some module fail to load" else "Done"
        tvLoadStat.postDelayed({
            tvLoadStat.visibility = View.GONE
            swipeRefresh.isRefreshing = false
        }, 1000)
    }
}
