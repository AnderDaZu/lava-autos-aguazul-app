package com.anderdadev.lavaautosaguazul.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.resposes.CheckVehicle
import com.anderdadev.lavaautosaguazul.viewHolder.ShowVehicleViewHolder

class ShowVehicleAdapter(private  val vehicles: List<CheckVehicle>): RecyclerView.Adapter<ShowVehicleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowVehicleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return  ShowVehicleViewHolder(layoutInflater.inflate(R.layout.item_show_vehicle, parent, false))
    }

    override fun onBindViewHolder(holder: ShowVehicleViewHolder, position: Int) {
        val item = vehicles[position]
        holder.bind(item)
    }

    override fun getItemCount() = vehicles.size
}