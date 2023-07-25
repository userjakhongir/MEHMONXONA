package com.example.mehmonxona.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mehmonxona.databinding.RecycleritemforhistoryBinding
import com.example.mehmonxona.model.BookingModel

class RoomBookingAdapter constructor(
    val context: Context,
    var arrayList: ArrayList<BookingModel>
):RecyclerView.Adapter<RoomBookingAdapter.RoomBookingViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomBookingViewHolder {
       val view = RecycleritemforhistoryBinding.inflate(LayoutInflater.from(context))
        return RoomBookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomBookingViewHolder, position: Int) {
        holder.binding.apply {
            textviewBookingroomname.text = "Bron qilingan xona: ${arrayList.get(position).roomname}"
            textviewBookingusername.text = "Bron qiluvchining ism familiyasi: ${arrayList.get(position).username} ${arrayList.get(position).surname}"
            textviewBookingusernumber.text = "Bron qiluvchining tel raqami: ${arrayList.get(position).phone}"
            textviewBookingdatatime.text = "Bron qilingan sana: ${arrayList.get(position).datatime}"
        }
    }
    override fun getItemCount(): Int {
        return arrayList.size
    }
    class RoomBookingViewHolder(val binding: RecycleritemforhistoryBinding):RecyclerView.ViewHolder(binding.root)
}