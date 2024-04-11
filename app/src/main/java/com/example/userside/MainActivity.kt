package com.example.userside

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.userside.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    var db = Firebase.firestore
    var auth = Firebase.auth
    var exerciseModel = ExerciseModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment)
        setSupportActionBar(binding.toolBar)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.categoryFragment)
                }

                R.id.favouriteFragment -> {
                    navController.navigate(R.id.favoruiteFragment)
                }
            }
            return@setOnItemSelectedListener true
        }

    }


    override fun onResume() {
        super.onResume()
        binding.toolBar.title = "YOGA"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {

                var dialog = AlertDialog.Builder(this)
                dialog.setCancelable(false)
                dialog.setTitle("Logout")
                dialog.setMessage("Do You Want To Logout")
                dialog.setPositiveButton("Yes") { _, _ ->
                    auth.signOut()
                    startActivity(Intent(this,LoginActivity::class.java))
                    this.finish()
                }
                dialog.setNegativeButton("No") { _, _ ->

                }
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}