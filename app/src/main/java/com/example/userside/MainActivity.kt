package com.example.userside

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.userside.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var binding : ActivityMainBinding
    var db = Firebase.firestore
    var auth = Firebase.auth
    var exerciseModel = ExerciseModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment)

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment->{navController.navigate(R.id.exerciseLevelFragment)}
                    R.id.favouriteFragment->{navController.navigate(R.id.favoruiteFragment)}
            }
            return@setOnItemSelectedListener true
        }

    }


    override fun onResume() {
        super.onResume()
        binding.toolBar.title = "GYM Exercise"
    }
}