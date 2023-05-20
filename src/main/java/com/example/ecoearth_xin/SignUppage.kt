package com.example.ecoearth_xin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Observable
import android.util.Patterns
import android.widget.Toast
import com.example.ecoearth_xin.databinding.ActivitySignUppageBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView

@SuppressLint("CheckResult")
class SignUppage : AppCompatActivity() {
    private lateinit var binding: ActivitySignUppageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUppageBinding.inflate (layoutInflater)
        setContentView(binding.root)

        //Auth
        auth = FirebaseAuth.getInstance()

        //Fullname Validation
        val nameStream = RxTextView.textChanges (binding.Name)
            .skipInitialValue().map { name ->
                name.isEmpty()
            }
        nameStream.subscribe {
            showNameExistAlert(it)
        }
        //Email Validation
        val emailStream = RxTextView.textChanges(binding.resEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher (email).matches ()
            }
        emailStream.subscribe {
            showEmailValidAlert(it)
        }
        // Password Validation
        val passwordStream = RxTextView.textChanges (binding.resPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
            showTextMinimalAlert(it,"Password")
        }
        //Confirm Password Validation
        val passwordComfirmStream = io.reactivex.Observable.merge(
            RxTextView.textChanges(binding.resPassword)
                .skipInitialValue()
                .map { password ->
                    password.toString() != binding.checkPsswd.text.toString()
                },
            RxTextView.textChanges(binding.checkPsswd)
                .skipInitialValue()
                .map { password ->
                    password.toString() != binding.resPassword.text.toString()
                })
        passwordComfirmStream.subscribe {
            showPasswordConfirmAlert(it)
        }
        // Click
        binding.signupButton.setOnClickListener {
            val email = binding.resEmail.text.toString().trim()
            val password = binding.resPassword.text.toString().trim()
            resgisterUser(email,password)
        }
        binding.noAccount.setOnClickListener {
            startActivity (Intent( this, Loginpage::class.java))
        }
    }
    private fun showNameExistAlert(isNotValid: Boolean) {
        binding.Name.error = if (isNotValid) "Nama cannot be empty!" else null
    }

    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
        if (text == "Password")
            binding.resPassword.error = if (isNotValid) "$text need to be more than 8 characters!" else null
    }
    private fun showEmailValidAlert (isNotValid: Boolean) {
        binding.resEmail.error = if (isNotValid) "Email not valid!" else null
    }
    private fun showPasswordConfirmAlert(isNotValid: Boolean) {
        binding.checkPsswd.error = if (isNotValid) "Password not same!" else null
    }
    private fun resgisterUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    startActivity (Intent( this, Loginpage::class.java))
                    Toast.makeText(this, "Sign Up successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}