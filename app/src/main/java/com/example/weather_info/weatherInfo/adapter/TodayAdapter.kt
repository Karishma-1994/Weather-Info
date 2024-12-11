package com.example.weather_info.weatherInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.weather_info.databinding.TodayViewItemBinding
import com.example.weather_info.model.TodayModel
import com.example.weather_info.util.loadWeatherIcon

class TodayAdapter : RecyclerView.Adapter<TodayAdapter.TodayModelViewHolder>() {

    private var todays: List<TodayModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayModelViewHolder {
        val binding: TodayViewItemBinding =
            TodayViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodayModelViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todays.size
    }

    override fun onBindViewHolder(holder: TodayModelViewHolder, position: Int) {
        holder.bind(todays[position])
    }

    fun setTodays(todayModelList: List<TodayModel>) {
        this.todays = todayModelList
        notifyDataSetChanged()
    }

    class TodayModelViewHolder(private var binding: TodayViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todayModel: TodayModel) {
            binding.todayData = todayModel
            binding.executePendingBindings()
            binding.weatherIconImageView2.loadWeatherIcon(todayModel.icon)
        }
    }
}