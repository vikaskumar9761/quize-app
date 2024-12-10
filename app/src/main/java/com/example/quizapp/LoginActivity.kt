package com.example.quizapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val email=binding.email.editText?.text.toString()
            val password=binding.password.editText?.text.toString()

            Firebase.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task->
                if (task.isSuccessful){
                    Toast.makeText(this, "user created", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "error ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}