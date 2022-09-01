package com.anderdadev.lavaautosaguazul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.resposes.TaskUnscheduledEmployee
import com.anderdadev.lavaautosaguazul.viewHolder.ShowUnscheduledTaskEmployeeViewHolder

class ShowUnscheduledTaskEmployeeAdapter(private val tasks: List<TaskUnscheduledEmployee>):
    RecyclerView.Adapter<ShowUnscheduledTaskEmployeeViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowUnscheduledTaskEmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ShowUnscheduledTaskEmployeeViewHolder(layoutInflater.inflate(R.layout.item_unscheduled_task_employee,
            parent, false))
    }

    override fun onBindViewHolder(holder: ShowUnscheduledTaskEmployeeViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item)
    }

    override fun getItemCount() = tasks.size
}