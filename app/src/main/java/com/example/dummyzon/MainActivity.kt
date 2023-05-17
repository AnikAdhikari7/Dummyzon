package com.example.dummyzon

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.dummyzon.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ibLogout.setOnClickListener {
            firebaseAuth.signOut()
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Successfully Logged Out!", Toast.LENGTH_SHORT).show()
        }


        replaceWithFragment(HomeNavFragment())
        
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottomNavHome -> replaceWithFragment(HomeNavFragment())
                R.id.bottomNavSearch -> replaceWithFragment(SearchNavFragment())
                R.id.bottomNavCategories -> replaceWithFragment(CategoriesNavFragment())
                R.id.bottomNavCart -> replaceWithFragment(CartNavFragment())
                R.id.bottomNavProfile -> replaceWithFragment(ProfileNavFragment())
                else -> {
                    replaceWithFragment(HomeNavFragment())
                }
            }
            true
        }
    }

    private fun replaceWithFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }

}