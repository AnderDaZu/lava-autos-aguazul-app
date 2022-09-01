package com.anderdadev.lavaautosaguazul.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.databinding.ItemShowVehicleBinding
import com.anderdadev.lavaautosaguazul.resposes.CheckVehicle

class ShowVehicleViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var mBinding = ItemShowVehicleBinding.bind(view)

    fun bind(vehicle: CheckVehicle){
        mBinding.tvPlate.text = vehicle.plate
        mBinding.tvLine.text = vehicle.model
        mBinding.tvMark.text = vehicle.mark
        mBinding.tvColor.text = vehicle.color
    }

}