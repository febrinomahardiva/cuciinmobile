package org.rpla4503.cuciinmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.FragmentFormBookingBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.model.Booking
import org.rpla4503.cuciinmobile.session.SessionManager

class FormBookingFragment : Fragment() {
    private lateinit var binding: FragmentFormBookingBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager
    private var time: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormBookingBinding.inflate(inflater, container, false)

        databaseHandler = DatabaseHandler(requireContext())

        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.jam9.setOnClickListener {
            binding.jam9.setImageResource(R.drawable.jam_9_on)
            time = "09.00"
        }

        binding.jam10.setOnClickListener {
            binding.jam10.setImageResource(R.drawable.jam_10_on)
            time = "10.00"
        }

        binding.jam11.setOnClickListener {
            binding.jam11.setImageResource(R.drawable.jam_11_on)
            time = "11.00"
        }

        binding.jam12.setOnClickListener {
            binding.jam12.setImageResource(R.drawable.jam_12_on)
            time = "12.00"
        }

        binding.jam13.setOnClickListener {
            binding.jam13.setImageResource(R.drawable.jam_13_on)
            time = "13.00"
        }

        binding.jam14.setOnClickListener {
            binding.jam14.setImageResource(R.drawable.jam_14_on)
            time = "14.00"
        }

        binding.jam15.setOnClickListener {
            binding.jam15.setImageResource(R.drawable.jam_15_on)
            time = "15.00"
        }

        binding.jam16.setOnClickListener {
            binding.jam16.setImageResource(R.drawable.jam_16_on)
            time = "16.00"
        }

        binding.bookingButton.setOnClickListener {
            val date = binding.dateInput.text.toString()
            val vehicleTypeId = binding.vehicleTypeRadioGroup.checkedRadioButtonId
            val serviceTypeId = binding.servicesTypeRadioGroup.checkedRadioButtonId
            val vehicleType = view.findViewById<RadioButton>(vehicleTypeId)?.text.toString()
            val serviceType = view.findViewById<RadioButton>(serviceTypeId)?.text.toString()
            val phoneNumber = binding.phoneNumberInput.text.toString()
            val plateNumber = binding.plateNumberInput.text.toString()

            val booking = Booking(
                userId = sessionManager.getUserId().toString(),
                status = "pending",
                date = date,
                time = time,
                vehicleType = vehicleType,
                servicesType = serviceType,
                numberPhone = phoneNumber,
                plateNumber = plateNumber
            )

            databaseHandler.addBooking(booking) { success ->
                val message = if (success) {
                    val descPaymentFragment = DescPaymentFragment()
                    val bundle = Bundle()
                    bundle.putString("bookingId", booking.id)
                    descPaymentFragment.arguments = bundle

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, descPaymentFragment)
                        .commit()

                    null
                } else {
                    "Gagal menambahkan booking"
                }
            }
        }
    }
}