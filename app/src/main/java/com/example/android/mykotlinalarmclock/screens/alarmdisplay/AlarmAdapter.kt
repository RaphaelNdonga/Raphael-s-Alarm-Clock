package com.example.android.mykotlinalarmclock.screens.alarmdisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mykotlinalarmclock.data.Alarm
import com.example.android.mykotlinalarmclock.databinding.AlarmListItemBinding

class AlarmAdapter:ListAdapter<Alarm, AlarmAdapter.ViewHolder>(DiffUtilCallback()) {
    class ViewHolder(private val binding:AlarmListItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: Alarm?) {
            binding.executePendingBindings()
            binding.alarm = alarm
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AlarmListItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarm = getItem(position)
        holder.bind(alarm)
    }
}

class DiffUtilCallback: DiffUtil.ItemCallback<Alarm>(){
    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem.alarmId == newItem.alarmId
    }

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem == newItem
    }

}