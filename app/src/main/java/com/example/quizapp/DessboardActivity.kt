package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityDessboardBinding
import com.google.firebase.firestore.FirebaseFirestore

class DessboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDessboardBinding
    private lateinit var questionList: ArrayList<QuestionModel>
    private var currentQuestionIndex = 0
    private var score = 0
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDessboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Questions
        questionList = ArrayList()
        // Fetch questions from Firestore
        fetchQuestions()


        // Set click listeners for options
        binding.option1.setOnClickListener { checkAnswer(binding.option1.text.toString()) }
        binding.option2.setOnClickListener { checkAnswer(binding.option2.text.toString()) }
        binding.option3.setOnClickListener { checkAnswer(binding.option3.text.toString()) }
        binding.option4.setOnClickListener { checkAnswer(binding.option4.text.toString()) }
    }

    private fun fetchQuestions() {
        firestore.collection("quize")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val question = document.toObject(QuestionModel::class.java)
                    questionList.add(question)
                }
                setQuestion()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error fetching questions: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setQuestion() {
        if (currentQuestionIndex < questionList.size) {
            val question = questionList[currentQuestionIndex]
            binding.question.text = question.question
            binding.option1.text = question.option1
            binding.option2.text = question.option2
            binding.option3.text = question.option3
            binding.option4.text = question.option4
        } else {
            // Quiz is finished
            Toast.makeText(this, "Your score: $score", Toast.LENGTH_SHORT).show()
            val intent=Intent(this,ScoreActivity::class.java)
            intent.putExtra("SCORE",score)
            startActivity(intent)
            finish()
        }
    }

    private fun checkAnswer(selectedAnswer: String) {
        val correctAnswer = questionList[currentQuestionIndex].answer
        if (selectedAnswer.equals(correctAnswer, ignoreCase = true)) {
            score++
        }
        currentQuestionIndex++
        setQuestion()
    }
}
