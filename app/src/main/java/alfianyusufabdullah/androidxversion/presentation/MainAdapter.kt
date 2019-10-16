package alfianyusufabdullah.androidxversion.presentation

import alfianyusufabdullah.androidxversion.R
import alfianyusufabdullah.androidxversion.data.entity.ModuleEntity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_module.view.*

class MainAdapter(private val modules: MutableList<ModuleEntity>) :
    RecyclerView.Adapter<MainAdapter.MainHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_module, p0, false)
        return MainHolder(view)
    }

    override fun getItemCount() = modules.size

    override fun onBindViewHolder(p0: MainHolder, p1: Int) {
        p0.bind(modules[p1])
    }

    fun clear() {
        modules.clear()
        notifyDataSetChanged()
    }

    fun add(module: ModuleEntity) {
        modules.add(module)
        notifyItemInserted(modules.size - 1)
    }

    class MainHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(module: ModuleEntity) {
            view.itemModule.text = module.module
            view.itemPackage.text = module.packages
            view.itemLatestRelease.text = String.format("Latest: %1s", module.latestRelease)

            view.itemStableRelease.text =
                String.format("Stable: %1s", module.latestStableRelease)
        }
    }
}