package com.anderdadev.lavaautosaguazul.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.databinding.ItemUnscheduledTaskYardBinding
import com.anderdadev.lavaautosaguazul.resposes.UnscheduledTaskYard

class ShowUnscheduledTaskYardViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var mBinding = ItemUnscheduledTaskYardBinding.bind(view)

    fun bind(task: UnscheduledTaskYard){
        mBinding.tvDate.text = task.date
        mBinding.tvStartHour.text = task.hour
        mBinding.tvService.text = task.service
        mBinding.tvPrice.text = task.price
        mBinding.tvPlate.text = task.plate
        (task.name+" "+task.last_name).also { mBinding.tvEmployee.text = it }
        mBinding.tvTypeVehicle.text = task.type
    }

}