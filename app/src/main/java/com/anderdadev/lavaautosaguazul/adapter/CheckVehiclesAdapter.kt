package com.anderdadev.lavaautosaguazul.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.R
import com.anderdadev.lavaautosaguazul.adapter.CheckVehiclesAdapter.CheckVehiclesViewHolder
import com.anderdadev.lavaautosaguazul.databinding.ItemCheckVehicleBinding
import com.anderdadev.lavaautosaguazul.resposes.CheckVehicle
//import com.anderdadev.lavaautosaguazul.viewHolder.CheckVehiclesViewHolder

class CheckVehiclesAdapter(private val vehicles: List<CheckVehicle>) :
    RecyclerView.Adapter<CheckVehiclesViewHolder>() {

    private var mSelectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckVehiclesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CheckVehiclesViewHolder(
            layoutInflater.inflate(
                R.layout.item_check_vehicle,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CheckVehiclesViewHolder, position: Int) {
        val item = vehicles[position]
        holder.bind(item, position, mSelectedItem)
    }

    override fun getItemCount() = vehicles.size

    inner class CheckVehiclesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var mBinding = ItemCheckVehicleBinding.bind(view)

        fun bind(vehicle: CheckVehicle, position: Int, selectedPosition: Int) {
            (vehicle.plate + " - " + vehicle.mark + "  " + vehicle.model + ",  " + vehicle.color).also {
                mBinding.radioButton.text = it
            }
            vehicle.id.also { mBinding.radioButton.id = it }

//            if (selectedPosition == -1 && position == 0){
//                mBinding.radioButton.isChecked
//            }else{
//                if(selectedPosition == position){
//                    mBinding.radioButton.isChecked
//                }else{
//                    mBinding.radioButton.isChecked = false
//
//                    mBinding.radioButton.setOnClickListener {
//                        mSelectedItem = adapterPosition
//                        notifyDataSetChanged()
//                    }
//                }
//            }
        }

    }

}