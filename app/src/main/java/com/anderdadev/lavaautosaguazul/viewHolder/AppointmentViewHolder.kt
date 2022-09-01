package com.anderdadev.lavaautosaguazul.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anderdadev.lavaautosaguazul.databinding.ItemAppointmentBinding
import com.anderdadev.lavaautosaguazul.resposes.AppointmentUser
import java.text.SimpleDateFormat
import java.util.*

class AppointmentViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private  var mBinding = ItemAppointmentBinding.bind(view)

    fun bind(appointment: AppointmentUser){
        mBinding.tvPlate.text = appointment.plate
        mBinding.tvDate.text = appointment.date
        mBinding.tvStartHour.text = appointment.start_hour
        mBinding.tvService.text = appointment.service
        mBinding.tvPrice.text = appointment.price
        (appointment.name+" "+appointment.last_name).also { mBinding.tvEmployee.text = it }
        mBinding.tvPlate.text = appointment.plate
        (appointment.mark+" "+appointment.model).also { mBinding.tvVehicle.text = it }

        val current = SimpleDateFormat("yyyy/mm/dd")
        val date = current.format(Date())


        if (appointment.date == date && appointment.state == "Inactivo"){
            "Sin iniciar".also { mBinding.tvState.text = it }
        }else if(appointment.date == date && appointment.state == "Activo"){
            "Iniciada".also { mBinding.tvState.text = it }
        }else if(appointment.date < date && appointment.state == "Inactivo"){
            "No fue iniciada".also { mBinding.tvState.text = it }
        }else if(appointment.date < date && appointment.state == "Activo"){
            "Fue iniciada".also { mBinding.tvState.text = it }
        }

        if (appointment.stock == null){
            "No tiene observaciones".also { mBinding.tvStock.text = it }
        }else if (appointment.stock.isNotEmpty()){
            mBinding.tvStock.text = appointment.stock
        }
    }
}