package com.anderdadev.lavaautosaguazul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.resposes.AppointmentUser
import com.anderdadev.lavaautosaguazul.viewHolder.AppointmentViewHolder

class AppointmentAdapter(private val appointments: List<AppointmentUser>): RecyclerView.Adapter<AppointmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return  AppointmentViewHolder(layoutInflater.inflate(R.layout.item_appointment, parent, false))
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val item = appointments[position]
        holder.bind(item)
    }

    override fun getItemCount() = appointments.size
}