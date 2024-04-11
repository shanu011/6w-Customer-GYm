package com.example.userside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.userside.databinding.ActivityLoginBinding
import com.example.userside.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignUpBinding
    var auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.alleradyAccount.setOnClickListener {
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            if(binding.etEmail.text.toString().isEmpty()){
                binding.etEmail.error = "Enter Your Email"
            }else  if(!isEmailValid(binding.etEmail.text.toString())){
                binding.etEmail.error = "Enter Valid Email"
            }else if(binding.etPassword.text.toString().isEmpty()){
                binding.etPassword.error = "Enter Your Password"
            }else{
                auth.createUserWithEmailAndPassword(binding.etEmail.text.toString(),binding.etPassword.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        var intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        this.finish()
                        Toast.makeText(this,"SignUp Successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Internet Issue", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    println("Error: ${it.message}")
                }
            }
        }
    }
    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        return emailRegex.matches(email)
    }
}