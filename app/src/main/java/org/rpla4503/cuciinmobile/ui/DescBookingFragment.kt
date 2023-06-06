package org.rpla4503.cuciinmobile.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.DeskripsiBookingPageBinding
import org.rpla4503.cuciinmobile.databinding.DeskripsiPaymentPageBinding
import org.rpla4503.cuciinmobile.databinding.FragmentRatingBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.session.SessionManager

class DescBookingFragment : Fragment() {
    private lateinit var binding: DeskripsiBookingPageBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    private var getId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DeskripsiBookingPageBinding.inflate(inflater, container, false)
        databaseHandler = DatabaseHandler(requireContext())

        sessionManager = SessionManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHandler.getLatestBooking() { booking ->
            if (booking != null) {
                binding.noBooking.text = "No Booking : " + booking.id
                binding.status.text = "Status : " + booking.status
                binding.fullnameDesc.text =
                    "Fullname" + databaseHandler.getUser(sessionManager.getUserId()!!) { user ->
                        if (user != null) {
                            binding.fullnameDesc.text = "Fullname : " + user.fullname
                        }

                    }
                binding.phoneNumbDesc.text = "Phone Number : " + booking.numberPhone
                binding.vehicleTypeDesc.text = "Vehicle Type : " + booking.vehicleType
                binding.dateDesc.text = "Date : " + booking.date
                binding.timeDesc.text = "Time : " + booking.time
                binding.numberPlateDesc.text = "Number Plate : " + booking.plateNumber

                getId = booking.id
            }
        }
        binding.btDesc.setOnClickListener {
            databaseHandler.checkRatingBookings { hasRatingBookings ->
                if (hasRatingBookings) {
                    val ratingFragment = RatingFragment()
                    val bundle = Bundle()
                    bundle.putString("bookingId", getId)
                    ratingFragment.arguments = bundle
                    val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                    transaction.replace(R.id.fragmentContainer, ratingFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                } else {
                    val intent = Intent(context, MainActivity::class.java)

                    startActivity(intent).also {
                        activity?.finish()
                    }
                }
            }
        }
    }
}