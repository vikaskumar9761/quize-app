package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizapp.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            if(Firebase.auth.currentUser!=null) {
                startActivity(Intent(this, DessboardActivity::class.java))
                finish()
            }else{
                val intent=Intent(this,LoginActivity::class.java)
                intent.putExtra("MODE","SIGNUP")
                startActivity(intent)
            }
        },3000)
    }
}