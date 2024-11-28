package com.example.weather_info.weatherInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_info.databinding.TodayViewItemBinding
import com.example.weather_info.model.TodayModel

class TodayAdapter: RecyclerView.Adapter<TodayModelViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayModelViewHolder {
       val binding: TodayViewItemBinding=
           TodayViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodayModelViewHolder (binding)
    }

    override fun onBindViewHolder(holder: TodayModelViewHolder, position: Int) {

        holder.bind(todayModel = TodayModel())
    }


    override fun getItemCount(): Int {

        TODO("Not yet implemented")
    }

}
class TodayModelViewHolder(private var binding: TodayViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(todayModel: TodayModel) {
        binding.todayData = todayModel
        binding.executePendingBindings()
    }
}
