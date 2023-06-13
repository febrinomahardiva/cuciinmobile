package org.rpla4503.cuciinmobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rpla4503.cuciinmobile.databinding.ListHistoryBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler
import org.rpla4503.cuciinmobile.model.Booking

class BookingAdapter : RecyclerView.Adapter<BookingAdapter.ViewHolder>() {
    private var bookings: List<Booking> = emptyList()
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookings[position])
    }

    override fun getItemCount(): Int {
        return bookings.size
    }

    fun setBookings(bookings: List<Booking>) {

        this.bookings = bookings
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            val context = binding.root.context // Get the context from the root view
            databaseHandler = DatabaseHandler(context) // Initialize with the appropriate context Or initialize it using the appropriate constructor
        }

        fun bind(booking: Booking) {
            // Mengikat data laporan ke tampilan item di sini
            // Contoh:

            binding.tvDate.text = booking.date
            binding.tvTime.text = booking.time
            binding.tvVehicleType.text = booking.vehicleType
            binding.tvServicesType.text = booking.servicesType
        }
    }
}
