package com.example.mehmonxona.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mehmonxona.databinding.ActivityForAdminBookiBinding
import com.example.mehmonxona.model.BookingModel
import com.example.mehmonxona.utilits.Constants.ADMINBOOKING
import com.example.mehmonxona.view.adapters.OrderAdapterAdmin
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ForAdminBookiActivity : AppCompatActivity() {
    lateinit var binding:ActivityForAdminBookiBinding
    lateinit var databaseReference: DatabaseReference
    val arrayList=ArrayList<BookingModel>()

    lateinit var orderAdapterAdminViewHolder: OrderAdapterAdmin.OrderAdapterAdminViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForAdminBookiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference().child(ADMINBOOKING)

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val bookingModel=datasnapshot.getValue(BookingModel::class.java)
                    arrayList.add(bookingModel!!)
                }

                binding.recyclerviewForOrders.layoutManager=LinearLayoutManager(this@ForAdminBookiActivity)
             val    orderAdapterAdmin = OrderAdapterAdmin(this@ForAdminBookiActivity,arrayList)
                binding.recyclerviewForOrders.adapter=orderAdapterAdmin

            }


            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}