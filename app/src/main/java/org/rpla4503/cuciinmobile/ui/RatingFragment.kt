package org.rpla4503.cuciinmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import org.rpla4503.cuciinmobile.R
import org.rpla4503.cuciinmobile.databinding.FragmentRatingBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.model.Rating
import org.rpla4503.cuciinmobile.session.SessionManager

class RatingFragment : Fragment() {
    private lateinit var binding: FragmentRatingBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    private var idBooking: String = ""

    private var getRating: String = ""

    private var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatingBinding.inflate(inflater, container, false)
        databaseHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookingId = arguments?.getString("bookingId")

        // Gunakan bookingId sesuai kebutuhan Anda, misalnya untuk menampilkan ID booking di UI
        if (bookingId != null) {
            // Lakukan operasi lain dengan bookingId
            // ...
            idBooking = bookingId

            Toast.makeText(requireContext(), bookingId, Toast.LENGTH_SHORT).show()
        }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            val selectedRating = rating.toInt()
            getRating = "$selectedRating"
            setAllStarsSelected(selectedRating)
        }

        binding.btRating.setOnClickListener {
            val rating = Rating(
                userId = sessionManager.getUserId().toString(),
                bookingId = idBooking,
                rating = getRating,
                comment = binding.multilaneRatingText.text.toString()

            )

            databaseHandler.addRating(rating){ success ->
                if (success){
                    databaseHandler.updateBookingStatus(idBooking, "selesai"){ success ->
                        if (success){
                            Toast.makeText(requireContext(), "Rating Berhasil", Toast.LENGTH_SHORT).show()
                            val dashboardFragment = DashboardFragment()
                            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                            transaction.replace(R.id.fragmentContainer, dashboardFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        } else {
                            Toast.makeText(requireContext(), "Rating Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Rating Gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setAllStarsSelected(selectedRating: Int) {
        binding.ratingBar.rating = selectedRating.toFloat()
    }
}