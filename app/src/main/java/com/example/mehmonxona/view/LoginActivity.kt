package com.example.mehmonxona.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mehmonxona.R
import com.example.mehmonxona.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        load()

        firebaseAuth= FirebaseAuth.getInstance()

        binding.apply {
            textviewGotoregistr.setOnClickListener{
                val intent = Intent(this@LoginActivity,RegistrActivity::class.java)
                startActivity(intent)
            }

            btnLogin.setOnClickListener {
                progressbarLogin.visibility = View.VISIBLE
                firebaseAuth.signInWithEmailAndPassword(edittextEmail.text.toString(),edittextPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        var uid = firebaseAuth.currentUser!!.uid
                        progressbarLogin.visibility = View.GONE
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        intent.putExtra("uid",uid)
                        startActivity(intent)
                        save(uid)
                    }else{
                        Toast.makeText(this@LoginActivity,"'Kirish' da xato bor", Toast.LENGTH_LONG).show()
                        progressbarLogin.visibility = View.GONE
                    }
                }
            }
        }
    }

    fun save(text: String?) {
        val editor = getSharedPreferences("EJ", MODE_PRIVATE).edit() as SharedPreferences.Editor
        editor.putString("ej", text)
        editor.commit()
    }

    fun load() {
        val sharedPreferences = getSharedPreferences("EJ", MODE_PRIVATE)
        val savedtext = sharedPreferences.getString("ej", null)
        if (savedtext!=null){
            val intent=Intent(this@LoginActivity,MainActivity::class.java)
            intent.putExtra("uid",savedtext)
            startActivity(intent)
        }


    }
}