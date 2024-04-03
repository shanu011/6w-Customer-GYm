package com.example.userside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.userside.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    var auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etEmail.setText("testing@gmail.com")
        binding.etPassword.setText("123456")
        binding.btnLogin.setOnClickListener {
            if(binding.etEmail.text.toString().isEmpty()){
                binding.etEmail.error = "Enter Your Email"
            }else if(binding.etPassword.text.toString().isEmpty()){
                binding.etPassword.error = "Enter Your Password"
            }else{
                auth.signInWithEmailAndPassword(binding.etEmail.text.toString(),binding.etPassword.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        var intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        this.finish()
                        Toast.makeText(this,"Login Successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Your Credential is wrong", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    println("Error: ${it.message}")
                }
            }
        }
    }
}