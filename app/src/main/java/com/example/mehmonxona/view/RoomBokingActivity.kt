package com.example.mehmonxona.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mehmonxona.R
import com.example.mehmonxona.databinding.ActivityRoomBokingBinding
import com.example.mehmonxona.model.BookingModel
import com.example.mehmonxona.utilits.Constants.HISTORY
import com.example.mehmonxona.view.adapters.RoomBookingAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RoomBokingActivity : AppCompatActivity() {
    lateinit var binding:ActivityRoomBokingBinding
    lateinit var databaseReference: DatabaseReference
    val arrayList = ArrayList<BookingModel>()
    lateinit var roomBookingAdapter: RoomBookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBokingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val phone = intent.getStringExtra("phone")

        databaseReference= FirebaseDatabase.getInstance().getReference().child(HISTORY).child(phone!!)

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    var bookingModel=datasnapshot.getValue(BookingModel::class.java)
                    arrayList.add(bookingModel!!)
                }
                binding.recyclerviewForHistory.layoutManager = LinearLayoutManager(this@RoomBokingActivity)
                var roomBookingAdapter = RoomBookingAdapter(this@RoomBokingActivity,arrayList)
                binding.recyclerviewForHistory.adapter=roomBookingAdapter
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }
}