package org.rpla4503.cuciinmobile.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.rpla4503.cuciinmobile.databinding.SigninPageBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.session.SessionManager

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: SigninPageBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHandler = DatabaseHandler(this)

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.toSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.confirmSignin.setOnClickListener {
            val username = binding.usernameInput.text.toString().trim()
            val password = binding.passwordInp.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Snackbar.make(
                    binding.root,
                    "Username and password cannot be empty",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        databaseHandler.getUserSignIn(username) { user ->
            if (user != null && user.password == password) {
                // Login berhasil
                sessionManager.createLoginSession(user.id)
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
                finish()
            } else {
                // Login gagal
                Snackbar.make(binding.root, "Invalid username or password", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}