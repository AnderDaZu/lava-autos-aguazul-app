package com.anderdadev.lavaautosaguazul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.resposes.UnscheduledTaskYard
import com.anderdadev.lavaautosaguazul.viewHolder.ShowUnscheduledTaskYardViewHolder

class ShowUnscheduledTaskYardAdapter(private val tasks: List<UnscheduledTaskYard>): RecyclerView.Adapter<ShowUnscheduledTaskYardViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowUnscheduledTaskYardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ShowUnscheduledTaskYardViewHolder(layoutInflater.inflate(R.layout.item_unscheduled_task_yard,
            parent, false))
    }

    override fun onBindViewHolder(holder: ShowUnscheduledTaskYardViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item)
    }

    override fun getItemCount() = tasks.size

}