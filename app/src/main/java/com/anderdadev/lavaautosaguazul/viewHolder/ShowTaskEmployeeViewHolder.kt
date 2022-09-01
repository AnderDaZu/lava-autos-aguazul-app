package com.anderdadev.lavaautosaguazul.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.databinding.ItemTaskEmployeeBinding
import com.anderdadev.lavaautosaguazul.resposes.TaskEmployee

class ShowTaskEmployeeViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var mBinding = ItemTaskEmployeeBinding.bind(view)

    fun bind(task: TaskEmployee){
        mBinding.tvDate.text = task.date
        mBinding.tvStartHour.text = task.hour
        mBinding.tvService.text = task.service
        mBinding.tvPlate.text = task.plate
        (task.mark+", "+task.model).also { mBinding.tvVehicle.text = it }
    }
}