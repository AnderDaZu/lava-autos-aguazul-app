package com.anderdadev.lavaautosaguazul.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.databinding.ItemScheduledTaskEmployeeBinding
import com.anderdadev.lavaautosaguazul.resposes.TaskScheduledEmployee

class ShowScheduledTaskEmployeeViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var mBinding = ItemScheduledTaskEmployeeBinding.bind(view)

    fun bind(task: TaskScheduledEmployee){
        mBinding.tvDate.text = task.finished
        mBinding.tvService.text = task.service
        mBinding.tvPlate.text = task.plate
        (task.mark+", "+task.model).also { mBinding.tvVehicle.text = it }
    }

}