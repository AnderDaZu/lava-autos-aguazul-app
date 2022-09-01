package com.anderdadev.lavaautosaguazul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.resposes.TaskEmployee
import com.anderdadev.lavaautosaguazul.viewHolder.ShowTaskEmployeeViewHolder

class ShowTaskEmployeeAdapter(private val tasks: List<TaskEmployee>):
    RecyclerView.Adapter<ShowTaskEmployeeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowTaskEmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ShowTaskEmployeeViewHolder(layoutInflater.inflate(R.layout.item_task_employee,
            parent, false))
    }

    override fun onBindViewHolder(holder: ShowTaskEmployeeViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item)
    }

    override fun getItemCount() = tasks.size

}