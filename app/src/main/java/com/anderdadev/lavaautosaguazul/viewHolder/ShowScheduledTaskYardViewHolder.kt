package com.anderdadev.lavaautosaguazul.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.databinding.ItemScheduledTaskYardBinding
import com.anderdadev.lavaautosaguazul.resposes.ScheduledTaskYard

class ShowScheduledTaskYardViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var mBinding = ItemScheduledTaskYardBinding.bind(view)

    fun bind(task: ScheduledTaskYard){
        mBinding.tvDate.text = task.finished
        mBinding.tvService.text = task.service
        mBinding.tvEmployee.text = task.employee
        mBinding.tvPrice.text = task.price
        mBinding.tvPlate.text = task.plate
        (task.mark+", "+task.model).also { mBinding.tvVehicle.text = it }
    }
}