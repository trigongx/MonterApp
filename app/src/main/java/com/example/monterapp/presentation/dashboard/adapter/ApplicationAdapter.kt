package com.example.monterapp.presentation.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.monterapp.data.models.Application
import com.example.monterapp.databinding.ItemApplicationBinding

class ApplicationAdapter(
    val onClickItem: (application:Application) -> Unit
): RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>() {

    private val list = mutableListOf<Application>()

    fun addApplication(applications: List<Application>){
        list.clear()
        list.addAll(applications)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        return ApplicationViewHolder(
            ItemApplicationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class ApplicationViewHolder(private val binding:ItemApplicationBinding):ViewHolder(binding.root){
        fun onBind(application: Application){
            binding.tvApplicationType.text = application.reason
            binding.tvApplicationDate.text = application.date.toString()
            itemView.setOnClickListener { onClickItem(application) }
        }
    }
}