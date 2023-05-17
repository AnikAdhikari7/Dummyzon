package com.example.dummyzon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.dummyzon.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("Users")
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            /*
            // How to use BCrypt to hash a password
            String password = "1234";
            String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            // $2a$12$US00g/uMhoSBm.HiuieBjeMtoN69SN.GE25fCpldebzkryUyopws6
            ...
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
            // result.verified == true
            */

            val bcryptHashPass: String = BCrypt.withDefaults().hashToString(12, password.toCharArray())
            val user = User(name, phone, email, bcryptHashPass)

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all the boxes!", Toast.LENGTH_SHORT).show()
//                if (binding.etName.inputType.)
//                binding.lName.boxStrokeErrorColor
            } else if (password == confirmPassword) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    database.child(phone).setValue(user).addOnSuccessListener {
                        binding.etName.text?.clear()
                        binding.etPhone.text?.clear()
                        binding.etEmail.text?.clear()
                        binding.etPassword.text?.clear()
                        binding.etConfirmPassword.text?.clear()

                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                        Toast.makeText(this, "Successfully Registered! Logging in...", Toast.LENGTH_SHORT)
                            .show()
                    }.addOnCanceledListener {
                        Toast.makeText(this, "Failed! Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }.addOnCanceledListener {
                    Toast.makeText(this, "Failed! Please try again.", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.etPassword.error = "Password doesn't match!"
                binding.etConfirmPassword.error = "Password doesn't match!"
            }
        }

        binding.llSignIn.setOnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}