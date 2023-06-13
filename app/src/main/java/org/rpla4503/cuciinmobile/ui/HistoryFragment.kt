package org.rpla4503.cuciinmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.rpla4503.cuciinmobile.BookingAdapter
import org.rpla4503.cuciinmobile.databinding.FragmentHistoryBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.session.SessionManager

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var bookingAdapter: BookingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        // Inisialisasi adapter dan tetapkan ke RecyclerView
        bookingAdapter = BookingAdapter()
        binding.rvHistory.adapter = bookingAdapter

        // Tetapkan LinearLayoutManager ke RecyclerView
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil data laporan dari DatabaseHandler
        sessionManager = SessionManager(requireContext())
        databaseHandler = DatabaseHandler(requireContext())

        val userId = sessionManager.getUserId()
            .toString() // Ubah sesuai dengan cara Anda mendapatkan email pengguna

        if (userId != null) {
            databaseHandler.getAllBookingsByUserId(userId) { bookings ->
                bookingAdapter.setBookings(bookings)
            }
        }
    }
}