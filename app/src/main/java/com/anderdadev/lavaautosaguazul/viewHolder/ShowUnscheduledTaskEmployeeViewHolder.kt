package com.anderdadev.lavaautosaguazul.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.databinding.ItemUnscheduledTaskEmployeeBinding
import com.anderdadev.lavaautosaguazul.resposes.TaskUnscheduledEmployee

class ShowUnscheduledTaskEmployeeViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var mBinding = ItemUnscheduledTaskEmployeeBinding.bind(view)

    fun bind(task: TaskUnscheduledEmployee){
        mBinding.tvDate.text = task.date
        mBinding.tvService.text = task.service
        mBinding.tvPlate.text = task.plate
        mBinding.tvTypeVehicle.text = task.type
    }

}