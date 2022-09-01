package com.anderdadev.lavaautosaguazul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.resposes.TaskScheduledEmployee
import com.anderdadev.lavaautosaguazul.viewHolder.ShowScheduledTaskEmployeeViewHolder

class ShowScheduledTaskEmployeeAdapter(private val tasks: List<TaskScheduledEmployee>):
    RecyclerView.Adapter<ShowScheduledTaskEmployeeViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowScheduledTaskEmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ShowScheduledTaskEmployeeViewHolder(layoutInflater.inflate(R.layout.item_scheduled_task_employee,
        parent, false))
    }

    override fun onBindViewHolder(holder: ShowScheduledTaskEmployeeViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item)
    }

    override fun getItemCount() = tasks.size
}