package com.example.mehmonxona.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.LoginFilter.UsernameFilterGMail
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mehmonxona.R
import com.example.mehmonxona.databinding.ActivityRoomViewBinding
import com.example.mehmonxona.model.RoomsModel
import com.example.mehmonxona.utilits.Constants.PHONE
import com.example.mehmonxona.utilits.Constants.SURNAME
import com.example.mehmonxona.utilits.Constants.USERNAME
import com.example.mehmonxona.viewmodel.RoomsViewmodel
import java.util.Calendar

class RoomViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoomViewBinding
    lateinit var roomsViewmodel: RoomsViewmodel

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRoomViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent = intent
        val roomname: String? =intent.getStringExtra("roomname")

        roomsViewmodel = ViewModelProvider(this@RoomViewActivity).get(RoomsViewmodel::class.java)

        binding.apply {
            var room:RoomsModel = intent.getSerializableExtra("room") as RoomsModel
            Glide.with(this@RoomViewActivity).load(room!!.roomimageurl).into(imageviewRoomimage)
            textviewRoomname.text = "${room!!.roomname}"
            textviewRoomdescription.text = "${room.roomdescription}"

            btnRoombooking.setOnClickListener {
                val calendar= Calendar.getInstance()
                var day=calendar.get(Calendar.DAY_OF_MONTH)
                var month=calendar.get(Calendar.MONTH)+1
                var year=calendar.get(Calendar.YEAR)
                var hour = calendar.get(Calendar.HOUR)
                var minute = calendar.get(Calendar.MINUTE)
                var datatime="$day/$month/$year   $hour:$minute"

                    roomsViewmodel.booking(roomname!!,USERNAME,SURNAME, PHONE,datatime)
            }

            roomsViewmodel.bookingsucces().observe(this@RoomViewActivity,{
                if (it){
                    Toast.makeText(this@RoomViewActivity,"Xona Bron qilindi!!",Toast.LENGTH_LONG).show()
                }
            })

        }
    }
}