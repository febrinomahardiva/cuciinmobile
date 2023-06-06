package org.rpla4503.cuciinmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.DeskripsiPaymentPageBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.model.Payment
import org.rpla4503.cuciinmobile.session.SessionManager

class DescPaymentFragment : Fragment() {
    private lateinit var binding: DeskripsiPaymentPageBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager
    private var idBooking: String = ""
    private var typePayment: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DeskripsiPaymentPageBinding.inflate(inflater, container, false)
        databaseHandler = DatabaseHandler(requireContext())

        sessionManager = SessionManager(requireContext())

        // Mendapatkan argumen yang dikirimkan dari fragment sebelumnya
        val bookingId = arguments?.getString("bookingId")

        // Gunakan bookingId sesuai kebutuhan Anda, misalnya untuk menampilkan ID booking di UI
        if (bookingId != null) {
            // Lakukan operasi lain dengan bookingId
            // ...
            idBooking = bookingId

            Toast.makeText(requireContext(), bookingId, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var total = 0
        databaseHandler.getBooking(idBooking) { booking ->
            if (booking != null) {
                if (booking?.vehicleType == "Car") {
                    total = 75000
                    if (booking?.servicesType == "Reguler Washing") {
                        total = total
                    } else if (booking?.servicesType == "Fogging") {
                        total += 10000
                    } else if (booking?.servicesType == "Interior Cleaning") {
                        total += 25000
                    } else if (booking?.servicesType == "Detailing") {
                        total += 75000
                    }
                } else if (booking?.vehicleType == "Motorcycle") {
                    total = 25000
                    if (booking?.servicesType == "Reguler Washing") {
                        total = total
                    } else if (booking?.servicesType == "Fogging") {
                        total += 10000
                    } else if (booking?.servicesType == "Interior Cleaning") {
                        total += 25000
                    } else if (booking?.servicesType == "Detailing") {
                        total += 75000
                    }
                }
            }

            binding.btCash.setOnClickListener() {
                typePayment = "Cash"
            }

            binding.btCashless.setOnClickListener() {
                typePayment = "Cashless"
            }

            binding.btDesc.setOnClickListener(View.OnClickListener {
                val payment = Payment(
                    userId = sessionManager.getUserId().toString(),
                    bookingId = idBooking,
                    statusPayment = "Belum Bayar",
                    typePayment = typePayment,
                    methodPayment = typePayment,
                    totalPayment = "Rp. $total"
                )

                databaseHandler.addPayment(payment) { success ->
                    if (success) {
                        if (payment.typePayment == "Cash") {
                            val descBooking = DescBookingFragment()
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, descBooking)
                                .commit()
                        } else if (payment.typePayment == "Cashless") {
                            val paymentBooking = PaymentFragment()
                            val bundle = Bundle()
                            bundle.putString("paymentId", payment.id)
                            paymentBooking.arguments = bundle
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, paymentBooking)
                                .commit()
                        }
                    } else Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}