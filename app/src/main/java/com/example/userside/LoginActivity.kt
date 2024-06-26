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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvAccount.setOnClickListener {
            var intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            if(binding.etEmail.text.toString().isEmpty()){
                binding.etEmail.error = "Enter Your Email"
            }else if(!isEmailValid(binding.etEmail.text.toString())){
                binding.etEmail.error = "Enter Valid Email"
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
        binding.tvForget.setOnClickListener {
            if(binding.etEmail.text.toString().isEmpty()){
                binding.etEmail.error = "Enter Your Email"
            }
            else if(!isEmailValid(binding.etEmail.text.toString())){
                binding.etEmail.error = "Enter Valid Email"
            } else{
                auth.sendPasswordResetEmail(binding.etEmail.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this,"Mail Sent", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"NetWork Issue", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        return emailRegex.matches(email)
    }
}