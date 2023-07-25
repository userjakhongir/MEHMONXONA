package com.example.mehmonxona.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mehmonxona.databinding.RecycleritemforhordersBinding
import com.example.mehmonxona.model.BookingModel

class OrderAdapterAdmin constructor(
    val context: Context,
    var arrayList: ArrayList<BookingModel>
):RecyclerView.Adapter<OrderAdapterAdmin.OrderAdapterAdminViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapterAdminViewHolder {
        val view = RecycleritemforhordersBinding.inflate(LayoutInflater.from(context))
        return OrderAdapterAdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderAdapterAdminViewHolder, position: Int) {
       holder.binding.apply {
           textviewBookingroomname.text = "Bron qilingan xona: ${arrayList.get(position).roomname}"
           textviewBookingusername.text = "Bron qiluvchining ism familiyasi: ${arrayList.get(position).username} ${arrayList.get(position).surname}"
           textviewBookingusernumber.text = "Bron qiluvchining tel raqami: ${arrayList.get(position).phone}"
           textviewBookingdatatime.text = "Bron qilingan sana: ${arrayList.get(position).datatime}"
       }
    }
    override fun getItemCount(): Int  = arrayList.size

    class OrderAdapterAdminViewHolder(val binding: RecycleritemforhordersBinding):RecyclerView.ViewHolder(binding.root)

}