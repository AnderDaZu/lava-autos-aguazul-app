package com.anderdadev.lavaautosaguazul.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.databinding.ItemCheckVehicleBinding
import com.anderdadev.lavaautosaguazul.resposes.CheckVehicle

class CheckVehiclesViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private var mBinding = ItemCheckVehicleBinding.bind(view)

    fun bind(vehicle: CheckVehicle, position: Int, selectedPosition: Int){
        (vehicle.plate +" - "+ vehicle.mark +"  "+ vehicle.model +",  "+ vehicle.color).also { mBinding.radioButton.text = it }
        vehicle.id.also { mBinding.radioButton.id = it }

        if (selectedPosition == -1 && position == 0){
            mBinding.radioButton.isChecked
        }else{
            if(selectedPosition == position){
                mBinding.radioButton.isChecked
            }else{
                mBinding.radioButton.isChecked = false

                mBinding.radioButton.setOnClickListener {
//                    mSelectedItem = adapterPosition
//                    notifyDataSetChanged()
                }
            }
        }
    }

}