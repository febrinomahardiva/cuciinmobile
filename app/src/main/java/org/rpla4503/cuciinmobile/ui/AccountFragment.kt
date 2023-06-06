package org.rpla4503.cuciinmobile.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.rpla4503.cuciinmobile.databinding.FragmentAccountBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.model.User
import org.rpla4503.cuciinmobile.session.SessionManager


class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    private var getPassword: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())
        val id = sessionManager.getUserId().toString() // Ganti dengan email user yang sesuai
        databaseHandler.getUser(sessionManager.getUserId()!! ) { user ->
            user?.let {
                // Update the UI with the user data
                getPassword = it.password
                binding.fullnameText.setText(it.fullname)
                binding.emailText.setText(it.email)
                binding.currentPasswordText.setText(it.password)
                binding.newPasswordText.setText(it.password)
                binding.confirmNewPasswordText.setText(it.password)

                // Set a click listener for the update button
                binding.btUpdate.setOnClickListener { _ ->
                    updateUserAccount(it)
                }
            }
        }
    }

    private fun updateUserAccount(user: User) {
        // Reset error messages
        binding.fullnameText.error = null
        binding.emailText.error = null
        binding.currentPasswordText.error = null
        binding.newPasswordText.error = null
        binding.confirmNewPasswordText.error = null

        // Get the input values
        val fullname = binding.fullnameText.text.toString().trim()
        val email = binding.emailText.text.toString().trim()
        val currentPassword = binding.currentPasswordText.text.toString().trim()
        val newPassword = binding.newPasswordText.text.toString().trim()
        val confirmNewPassword = binding.confirmNewPasswordText.text.toString().trim()

        // Validate input
        var isValid = true


        if (fullname.isEmpty()) {
            binding.fullnameText.error = "Masukkan nama lengap anda"
            isValid = false
        }
        if (fullname.matches(Regex(".*\\d.*"))) {
            binding.fullnameText.error = "Masukkan nama dengan format yang benar"
            isValid = false
        }
        if(fullname.matches(Regex(".*[^\\p{L}\\s].*"))){
            binding.fullnameText.error = "Masukkan nama dengan format yang benar"
            isValid = false

        }

        if (email.isEmpty()) {
            binding.emailText.error = "Email tidak boleh kosong"
            isValid = false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailText.error = "Masukkan email yang valid"
            isValid = false
        }

        if (currentPassword.isEmpty()) {
            binding.currentPasswordText.error = "Masukkan password lama"
            isValid = false
        }
        if (currentPassword != getPassword) {
            binding.currentPasswordText.error = "Password lama tidak valid"
            isValid = false
        }
        if (newPassword.isEmpty()) {
            binding.newPasswordText.error = "Masukkan password baru"
            isValid = false
        }

        if (confirmNewPassword.isEmpty()) {
            binding.confirmNewPasswordText.error = "Konfirmasi password baru"
            isValid = false
        }
        if (confirmNewPassword != newPassword) {
            binding.confirmNewPasswordText.error = "Konfirmasi password salah"
            isValid = false
        }

        if (currentPassword == null && newPassword == null && confirmNewPassword == null) {
            binding.currentPasswordText.setText(getPassword)
            binding.newPasswordText.setText(getPassword)
            binding.confirmNewPasswordText.setText(getPassword)
        }

        if (!isValid) {
            return
        }

        val updatedUser = User(
            user.id,
            user.username,
            fullname,
            email,
            newPassword,
//            password // Assuming the password is not editable in this screen
        )

        updateAccountUser(updatedUser)
    }
    private fun updateAccountUser(user: User) {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Perbaharui informasi akun ?")
            .setMessage("Anda akan memperbaharui profile anda")
            .setPositiveButton("OK") { _, _ ->
                databaseHandler.updateUserAccount(user) { success ->
                    if (success) {
                        // Update successful
                        showSuccessDialog()
                    } else {
                        // Update failed
                        showErrorDialog()
                    }
                }
            }.setNegativeButton("No") { _, _ ->
            }.show()
    }
    private fun showSuccessDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Update berhasil")
            .setMessage("Update berhasil erhasil diperbaharui")
            .setPositiveButton("OK") { _, _ ->
                // Tambahkan pemanggilan recreate() di dalam tindakan positif tombol OK
                requireActivity().recreate()
            }.show()
    }
    private fun showErrorDialog() {
        Toast.makeText(requireContext(), "Update gagal", Toast.LENGTH_SHORT).show()
    }
}


