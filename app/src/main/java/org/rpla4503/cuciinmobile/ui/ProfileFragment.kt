package org.rpla4503.cuciinmobile.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.FragmentProfileBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.session.SessionManager

class ProfileFragment: Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        databaseHandler = DatabaseHandler(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btAccount.setOnClickListener {
            val accountFragment = AccountFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, accountFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.btAboutus.setOnClickListener {
            val aboutUsFragment = AboutUsFragment()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, aboutUsFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.btLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Keluar aplikasi!")
            .setMessage("Anda akan keluar aplikasi?")
            .setPositiveButton("OK") { _, _ ->
                sessionManager.logout()
                val message = "Anda telah keluar"
                val intent = Intent(activity, SignInActivity::class.java)
                intent.putExtra("toast_message", message)
                startActivity(intent)
                activity?.finish()
            }
            .setNegativeButton("No", null)
            .show()
    }
}