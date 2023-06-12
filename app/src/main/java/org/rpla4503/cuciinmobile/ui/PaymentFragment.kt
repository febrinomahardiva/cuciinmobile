package org.rpla4503.cuciinmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.PaymentPageBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.model.Payment
import org.rpla4503.cuciinmobile.session.SessionManager

class PaymentFragment : Fragment() {
    private lateinit var binding: PaymentPageBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager
    private var idPayment: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PaymentPageBinding.inflate(inflater, container, false)
        databaseHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())

        val paymentId = arguments?.getString("paymentId")

        if (paymentId != null) {
            idPayment = paymentId
            Toast.makeText(requireContext(), paymentId, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHandler.getPayment(idPayment) { payment ->
            if (payment != null) {
                binding.price.text = payment.totalPayment.toString()
            }
        }

        binding.btnPay.setOnClickListener {
            val selectedRadioButtonId = binding.paymentRadioGroup.checkedRadioButtonId
            val selectedRadioButton =
                binding.paymentRadioGroup.findViewById<RadioButton>(selectedRadioButtonId)
            val newPaymentType = selectedRadioButton?.text.toString()

            val updatePayment = Payment()
            updatePayment.id = idPayment

            databaseHandler.updateTypePayment(updatePayment, newPaymentType) { success ->
                if (success) {
                    val descBooking = DescBookingFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, descBooking)
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
