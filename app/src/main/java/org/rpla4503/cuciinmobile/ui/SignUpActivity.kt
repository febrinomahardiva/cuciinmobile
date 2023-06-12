package org.rpla4503.cuciinmobile.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import org.rpla4503.cuciinmobile.databinding.SignupPageBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.model.User

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: SignupPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toSignin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.confirmSignup.setOnClickListener {
            val usernameInputLayout = binding.usernameSignup
            val fullnameInputLayout = binding.fullnameSignup
            val emailInputLayout = binding.emailSignup
            val passwordInputLayout = binding.passwordSignup
            val confirmPasswordInputLayout = binding.confirmPasswordSignup

            val username = binding.usernameInputSignup.text.toString().trim()
            val fullname = binding.fullnameInputSignup.text.toString().trim()
            val email = binding.emailInputSignup.text.toString().trim()
            val password = binding.passwordInputSignup.text.toString().trim()
            val confirmPassword = binding.confirmPasswordInputSignup.text.toString().trim()

            if (isInputValid(username, fullname, email, password, confirmPassword)) {
                // Lakukan tindakan setelah validasi berhasil
                val user = User(
                    username = username,
                    fullname = fullname,
                    email = email,
                    password = password
                )
                DatabaseHandler(this).addUser(user) { isSuccess ->
                    if (isSuccess) {
                        // Registrasi berhasil
                        showToast("Registrasi berhasil")
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Registrasi gagal
                        showToast("Registrasi gagal")
                    }
                }
            } else {
                // Tampilkan pesan kesalahan jika validasi gagal
                if (username.isEmpty()) {
                    showError(usernameInputLayout, "Username harus diisi")
                } else {
                    clearError(usernameInputLayout)
                }

                if (fullname.isEmpty()) {
                    showError(fullnameInputLayout, "Fullname harus diisi")
                } else {
                    clearError(fullnameInputLayout)
                }

                if (email.isEmpty()) {
                    showError(emailInputLayout, "Email harus diisi")
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showError(emailInputLayout, "Email tidak valid")
                } else {
                    clearError(emailInputLayout)
                }

                if (password.isEmpty()) {
                    showError(passwordInputLayout, "Password harus diisi")
                } else {
                    clearError(passwordInputLayout)
                }

                if (confirmPassword.isEmpty()) {
                    showError(confirmPasswordInputLayout, "Konfirmasi password harus diisi")
                } else if (password != confirmPassword) {
                    showError(confirmPasswordInputLayout, "Konfirmasi password tidak sesuai")
                } else {
                    clearError(confirmPasswordInputLayout)
                }
            }
        }
    }

    private fun isInputValid(
        username: String,
        fullname: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return username.isNotEmpty() && fullname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showError(textInputLayout: TextInputLayout, errorMessage: String) {
        textInputLayout.error = errorMessage
    }

    private fun clearError(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}