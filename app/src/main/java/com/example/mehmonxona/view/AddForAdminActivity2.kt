package com.example.mehmonxona.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.mehmonxona.databinding.ActivityAddForAdmin2Binding
import com.example.mehmonxona.viewmodel.RoomsViewmodel

class AddForAdminActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityAddForAdmin2Binding
    var imageuri: Uri? = null
    lateinit var roomsViewmodel: RoomsViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddForAdmin2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        roomsViewmodel = ViewModelProvider(this@AddForAdminActivity2).get(RoomsViewmodel::class.java)

        binding.apply {
            imageviewOpengalery.setOnClickListener {
                openFileChooser()
            }
            btnAddroom.setOnClickListener {
                if (imageuri != null){
                    roomsViewmodel.uploadnewrooms(edittextRoomname.text.toString(),edittextRoomdescription.text.toString(),imageuri!!)
                }else{
                    Toast.makeText(this@AddForAdminActivity2,"Rasim tanlanmagan!!", Toast.LENGTH_LONG).show()
                }
            }
        }

    //progressbarshowhide
        roomsViewmodel.uploadsucces().observe(this@AddForAdminActivity2,{
            if (it){
                showprogressbar()
            }else{
                hideprogressbar()
            }
        })
        roomsViewmodel.uploadprogress().observe(this@AddForAdminActivity2,{
            binding.apply {
                progressBar.progress=it.toInt()
                textviewForprogress.text = "${it.toInt()} %"
            }
        })
    //progressbarshowhide


    }

    // Show progressbar hide progressbar
    fun showprogressbar(){
        binding.progressBar.visibility = View.VISIBLE
        binding.textviewForprogress.visibility = View.VISIBLE
    }
    fun hideprogressbar(){
        binding.progressBar.visibility = View.GONE
        binding.textviewForprogress.visibility = View.GONE
    }
    // Show progressbar hide progressbar

    //Open Gallery
    fun openFileChooser() {
        getContent.launch("image/*")
    }
    //Open Gallery and Set image to imageview
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
        binding.imageviewOpengalery.setImageURI(uri)
        if (uri!=null){
            imageuri=uri
            binding.btnAddroom.isEnabled=true
        }
    }
}