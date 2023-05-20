package com.example.ecoearth_xin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import com.example.ecoearth_xin.databinding.ActivityLoginpageBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
@SuppressLint( "CheckResult")

class Loginpage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginpageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginpageBinding.inflate (layoutInflater)
        setContentView(binding.root)

        //Auth
        auth = FirebaseAuth.getInstance()

        // Email Validation
        val emailStream = RxTextView.textChanges(binding.resEmail)
            .skipInitialValue()
            .map { email ->
                email.isEmpty()
            }
        emailStream.subscribe {
            showTextMinimalAlert(it, "Email")
        }
        // Password Validation
        val passwordStream = RxTextView.textChanges (binding.resPassword)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }
        passwordStream.subscribe {
            showTextMinimalAlert(it, "Password")
        }
        //click
        binding.loginButton.setOnClickListener {
            val email = binding.resEmail.text.toString().trim()
            val password = binding.resPassword.text.toString().trim()
            loginUser(email, password)
            startActivity (Intent(this, Homepage::class.java))
        }
        binding.noAccount.setOnClickListener {
            startActivity (Intent (this, SignUppage::class.java))
        }
    }
    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
        if (text == "Email") {
            binding.resEmail.error = if (isNotValid) "$text cannot be empty!" else null
        }
        else if (text == "Password") {
            binding.resPassword.error = if (isNotValid) "$text cannot be empty!" else null
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { login ->
                if (login.isSuccessful) {
                    Intent(this, Homepage::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, login.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}