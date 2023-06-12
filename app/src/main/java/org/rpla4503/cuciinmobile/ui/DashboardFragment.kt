package org.rpla4503.cuciinmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.FragmentDashboardBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.session.SessionManager

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        databaseHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHandler.getUser(sessionManager.getUserId()!!) { user ->
            if (user != null) {
                binding.fullname.text = user.fullname
            }
        }

        databaseHandler.checkPendingBookings(
            sessionManager.getUserId().toString()
        ) { hasPendingBookings ->
            if (hasPendingBookings) {
                // Ada booking yang belum selesai
                // Lakukan tindakan yang diperlukan di sini
                binding.button.text = "See Booking"
            } else {
                // Tidak ada booking yang belum selesai
                // Lakukan tindakan yang diperlukan di sini
                binding.button.text = "Make A Reservation"
            }
        }
        binding.button.setOnClickListener(View.OnClickListener {
            databaseHandler.checkPendingBookings(
                sessionManager.getUserId().toString()
            ) { hasPendingBookings ->
                if (hasPendingBookings) {
                    // Ada booking yang belum selesai
                    // Lakukan tindakan yang diperlukan di sini
                    val descBookingFragment = DescBookingFragment()
                    val fragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragmentContainer, descBookingFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                } else {
                    // Tidak ada booking yang belum selesai
                    // Lakukan tindakan yang diperlukan di sini
                    val formBookingFragment = FormBookingFragment()
                    val fragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragmentContainer, formBookingFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        })
    }
}