package com.example.mehmonxona.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mehmonxona.R
import com.example.mehmonxona.databinding.ActivityMainBinding
import com.example.mehmonxona.utilits.Constants.PHONE
import com.example.mehmonxona.utilits.Constants.SURNAME
import com.example.mehmonxona.utilits.Constants.USERNAME
import com.example.mehmonxona.utilits.Constants.USER_INFORMATION
import com.example.mehmonxona.view.adapters.RoomsAdapter
import com.example.mehmonxona.viewmodel.RoomsViewmodel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var roomsViewmodel: RoomsViewmodel
    lateinit var roomsAdapter: RoomsAdapter
    var useruid:String?= null
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        roomsViewmodel = ViewModelProvider(this@MainActivity).get(RoomsViewmodel::class.java)
        useruid = intent.getStringExtra("uid")


        if (useruid == "KebjfSvuabgUr6DU6MQisUFWzjz1"){
            Toast.makeText(this@MainActivity,"Admin kirdi",Toast.LENGTH_LONG).show()
            binding.imgForadminadd.visibility = View.VISIBLE
            binding.imgForadminorders.visibility = View.VISIBLE
        }

        databaseReference=FirebaseDatabase.getInstance().getReference().child(USER_INFORMATION).child(useruid!!)

        val view = binding.navigationciew1.getHeaderView(0)
        val textviewSurname: TextView? = findViewById(R.id.textview_navigation_name)

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var name = snapshot.child("name").getValue().toString()
                var surname = snapshot.child("surname").getValue().toString()
                var phone = snapshot.child("phone").getValue().toString()
                var email = snapshot.child("email").getValue().toString()
                var password = snapshot.child("password").getValue().toString()
                USERNAME = name
                SURNAME = surname
                PHONE = phone

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        binding.apply {
            imvForheadline.setOnClickListener {
                drawerlayout1.openDrawer(Gravity.LEFT)
            }
            imgForadminadd.setOnClickListener {
                val intent = Intent(this@MainActivity, AddForAdminActivity2::class.java)
                startActivity(intent)
            }
            imgForadminorders.setOnClickListener {
                val intent = Intent(this@MainActivity,ForAdminBookiActivity::class.java)
                startActivity(intent)
            }
            navigationciew1.setNavigationItemSelectedListener {
                when(it.itemId){
                   R.id.room_booking->{
                       var intent = Intent(this@MainActivity,RoomBokingActivity::class.java)
                       intent.putExtra("phone", PHONE)
                       startActivity(intent)
                   }
                    R.id.logout->{
                        save(null)
                        finish()
                    }
                }
                return@setNavigationItemSelectedListener true
            }

            recyclerviewRooms.layoutManager = GridLayoutManager(this@MainActivity,2)
            roomsViewmodel.readallrooms().observe(this@MainActivity,{
                roomsAdapter = RoomsAdapter(this@MainActivity,it)
                recyclerviewRooms.adapter =roomsAdapter
            })
        }
    }
    fun save(text: String?) {
        val editor = getSharedPreferences("EJ", MODE_PRIVATE).edit() as SharedPreferences.Editor
        editor.putString("ej", text)
        editor.commit()
    }
}