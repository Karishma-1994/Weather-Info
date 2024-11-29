package com.example.weather_info.weatherInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_info.databinding.TodayViewItemBinding
import com.example.weather_info.model.TodayModel

class TodayAdapter : RecyclerView.Adapter<TodayModelViewHolder>() {

    private var todayList: List<TodayModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayModelViewHolder {
        val binding: TodayViewItemBinding =
            TodayViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodayModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodayModelViewHolder, position: Int) {
        holder.bind(todayList[position])
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    fun setToday(todayList: List<TodayModel>) {
        this.todayList = todayList
        notifyDataSetChanged()
    }
}

class TodayModelViewHolder(private var binding: TodayViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(todayModel: TodayModel) {
        binding.todayData = todayModel
        binding.executePendingBindings()
    }
}
