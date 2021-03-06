package com.example.android.mykotlinalarmclock.screens.alarmdisplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mykotlinalarmclock.data.Alarm
import com.example.android.mykotlinalarmclock.databinding.AlarmListItemBinding
import com.example.android.mykotlinalarmclock.utils.OnToggleAlarmListener
import com.example.android.mykotlinalarmclock.utils.getRecurringDaysText
import timber.log.Timber

class AlarmAdapter(private val switchListener: OnToggleAlarmListener) :
    ListAdapter<Alarm, AlarmAdapter.ViewHolder>(DiffUtilCallback()) {
    class ViewHolder(
        val binding: AlarmListItemBinding,
        private val switchListener: OnToggleAlarmListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: Alarm?) {
            alarm?.let {
                val alarmTimeText = "${alarm.hour}:${alarm.minute}"
                binding.alarmTime.text = alarmTimeText
                binding.alarmTitle.text = alarm.title
                binding.alarmDays.text = getRecurringDaysText(alarm)
                binding.alarmSwitch.isChecked = alarm.started
                binding.alarmSwitch.setOnCheckedChangeListener { _, _ ->
                    Timber.i("The switch has changed")
                    switchListener.onToggle(alarm)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AlarmListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, switchListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarm = getItem(position)
        holder.bind(alarm)
        Timber.i("The id is ${alarm.alarmId}")
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.alarmSwitch.setOnCheckedChangeListener(null)
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Alarm>() {
    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem.alarmId == newItem.alarmId
    }

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
        return oldItem == newItem
    }

}
