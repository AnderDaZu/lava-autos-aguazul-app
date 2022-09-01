package com.anderdadev.lavaautosaguazul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.resposes.ScheduledTaskYard
import com.anderdadev.lavaautosaguazul.viewHolder.ShowScheduledTaskYardViewHolder

class ShowScheduledTaskYardAdapter(private val tasks: List<ScheduledTaskYard>):
    RecyclerView.Adapter<ShowScheduledTaskYardViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowScheduledTaskYardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ShowScheduledTaskYardViewHolder(layoutInflater.inflate(R.layout.item_scheduled_task_yard,
            parent, false))
    }

    override fun onBindViewHolder(holder: ShowScheduledTaskYardViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item)
    }

    override fun getItemCount() = tasks.size
}