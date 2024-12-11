package com.example.weather_info.weatherInfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_info.databinding.TodayViewItemBinding
import com.example.weather_info.model.CurrentModel

class TodayAdapter : RecyclerView.Adapter<TodayAdapter.TodayModelViewHolder>() {

    private var todays: List<CurrentModel> = mutableListOf()

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

    fun setTodays(currentModelList: List<CurrentModel>) {
        this.todays = currentModelList
        notifyDataSetChanged()
    }

    class TodayModelViewHolder(private var binding: TodayViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentModel: CurrentModel) {
            binding.todayData = currentModel
            binding.executePendingBindings()
        }
    }
}