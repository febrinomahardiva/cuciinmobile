package org.rpla4503.cuciinmobile.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.FragmentProfileBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.session.SessionManager

class ProfileFragment : Fragment() {
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
        sessionManager = SessionManager(requireContext())
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
            MaterialAlertDialogBuilder(requireContext()).setTitle("Logout ?")
                .setMessage("Iya")
                .setPositiveButton("OK") { _, _ ->
                    sessionManager.logout()
                    val intent = Intent(requireContext(), SignInActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }.setNegativeButton("No") { _, _ ->
                }.show()
            true
        }
    }
}