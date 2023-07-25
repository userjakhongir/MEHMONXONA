package com.example.mehmonxona.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mehmonxona.R
import com.example.mehmonxona.databinding.ActivityRegistrBinding
import com.example.mehmonxona.model.UserModel
import com.example.mehmonxona.utilits.Constants.USER_INFORMATION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    var cheking:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference().child(USER_INFORMATION)

        binding.apply {
            btnRegister.setOnClickListener {
                chekked(edittextName.text.toString(),edittextSurname.text.toString(),edittextPhonenumber.text.toString())
                if (cheking){
                    firebaseAuth.createUserWithEmailAndPassword(edittextEmail.text.toString(),edittextPassword.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful){
                            var uid = firebaseAuth.currentUser!!.uid
                            val userModel=UserModel(
                                edittextName.text.toString(),
                                edittextSurname.text.toString(),
                                edittextPhonenumber.text.toString(),
                                edittextEmail.text.toString(),
                                edittextPassword.text.toString(),
                                uid
                            )
                            databaseReference.child(uid).setValue(userModel).addOnCompleteListener {
                                if (it.isSuccessful){
                                    Toast.makeText(this@RegistrActivity,"Ro'yxatdan o'tish muovfaqqiyatli!",Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                }else{
                    Toast.makeText(this@RegistrActivity,"Ro'yxatdan o'tishda xatolik bor",Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    fun chekked(name:String, surname:String, phone:String):Boolean{
        if (name.isEmpty() || surname.isEmpty() || phone.isEmpty()) {
            binding.apply {
                cheking=false
                edittextName.setError("Empty")
                edittextSurname.setError("Empty")
                edittextPhonenumber.setError("Empty")
            }
        }else{
            cheking=true
        }
    return cheking
    }
}