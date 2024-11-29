package com.example.weather_info.weatherInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_info.databinding.WeeklyViewItemBinding
import com.example.weather_info.model.WeeklyModel

class WeeklyAdapter : RecyclerView.Adapter<WeeklyModelViewHolder>() {

    private var weeklyList: List<WeeklyModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyModelViewHolder {
        val binding: WeeklyViewItemBinding =
            WeeklyViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeeklyModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeeklyModelViewHolder, position: Int) {
        holder.bind(weeklyList[position])
    }

    override fun getItemCount(): Int {
        return weeklyList.size
    }

    fun setToday(weeklyList: List<WeeklyModel>) {
        this.weeklyList = weeklyList
        notifyDataSetChanged()
    }
}

class WeeklyModelViewHolder(private var binding: WeeklyViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(weeklyModel: WeeklyModel) {
        binding.weeklyData = weeklyModel
        binding.executePendingBindings()
    }
}
