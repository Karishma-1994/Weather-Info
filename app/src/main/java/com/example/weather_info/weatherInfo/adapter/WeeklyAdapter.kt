package com.example.weather_info.weatherInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.weather_info.databinding.WeeklyViewItemBinding

import com.example.weather_info.model.WeeklyModel
import com.example.weather_info.util.loadWeatherIcon

class WeeklyAdapter : RecyclerView.Adapter<WeeklyAdapter.WeeklyModelViewHolder>() {

    private var weekly: List<WeeklyModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyModelViewHolder {
        val binding: WeeklyViewItemBinding =
            WeeklyViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeeklyModelViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weekly.size
    }

    override fun onBindViewHolder(holder: WeeklyModelViewHolder, position: Int) {
        holder.bind(weekly[position])
    }

    fun setWeekly(weeklyModelList: List<WeeklyModel>) {
        this.weekly = weeklyModelList
        notifyDataSetChanged()
    }

    class WeeklyModelViewHolder(private var binding: WeeklyViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weeklyModel: WeeklyModel) {
            binding.weeklyData = weeklyModel
            binding.executePendingBindings()
            binding.weatherIconImageView.loadWeatherIcon(weeklyModel.icon!!)
        }
    }
}
