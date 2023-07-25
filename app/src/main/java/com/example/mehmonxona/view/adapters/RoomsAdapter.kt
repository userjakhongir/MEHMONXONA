package com.example.mehmonxona.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mehmonxona.databinding.RecyclerviewItemRoomBinding
import com.example.mehmonxona.model.RoomsModel
import com.example.mehmonxona.view.RoomViewActivity

class RoomsAdapter constructor(
    val context: Context,
    val arrayList: ArrayList<RoomsModel>
):RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val view = RecyclerviewItemRoomBinding.inflate(LayoutInflater.from(context))
        return RoomsViewHolder(view )
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        holder.binding.apply {
            textviewItemName.text = arrayList.get(position).roomname
            textviewItemDescription.text = arrayList.get(position).roomdescription
            Glide.with(context).load(arrayList.get(position).roomimageurl).into(imagviewItem)

            linearlayoutItem.setOnClickListener {
                val intent = Intent(context,RoomViewActivity::class.java)
                intent.putExtra("room",arrayList.get(position))
                intent.putExtra("roomname",arrayList.get(position).roomname)
                context.startActivity(intent)
            }
        }
    }
    override fun getItemCount(): Int  = arrayList.size

    class RoomsViewHolder(val binding:RecyclerviewItemRoomBinding):RecyclerView.ViewHolder(binding.root)

}