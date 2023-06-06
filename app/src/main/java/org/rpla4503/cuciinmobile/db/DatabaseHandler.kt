package org.rpla4503.cuciinmobile.db

import android.content.Context
import com.google.firebase.database.*
import org.rpla4503.cuciinmobile.model.Booking
import org.rpla4503.cuciinmobile.model.Payment
import org.rpla4503.cuciinmobile.model.Rating
import org.rpla4503.cuciinmobile.model.User

class DatabaseHandler(context: Context) {
    private val BASE_URL = "https://cuciinmobile-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val database: DatabaseReference = FirebaseDatabase.getInstance(BASE_URL).reference

    // User related operations

    fun addUser(user: User, callback: (Boolean) -> Unit) {
        val userId = database.child("users").push().key
        user.id = userId ?: ""
        database.child("users").child(user.id).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Pengiriman data berhasil
                    callback(true)
                } else {
                    // Pengiriman data gagal
                    callback(false)
                }
            }
    }


    fun getUser(userId: String, callback: (User?) -> Unit) {
        val query = database.child("users").orderByChild("id").equalTo(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var user: User? = null
                for (childSnapshot in snapshot.children) {
                    user = childSnapshot.getValue(User::class.java)
                    break
                }
                callback(user)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun getUserSignIn(username: String, callback: (User?) -> Unit) {
        val query = database.child("users").orderByChild("username").equalTo(username)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var user: User? = null
                for (childSnapshot in snapshot.children) {
                    user = childSnapshot.getValue(User::class.java)
                    break
                }
                callback(user)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }


    fun deleteUser(user: User) {
        val userId = user.id
        if (userId.isNotEmpty()) {
            database.child("users").child(userId).removeValue()
        }
    }

    // Update user account details (email, full name, phone number, address)
    fun updateUserAccount(user: User, callback: (Boolean) -> Unit) {
        val userId = user.id
        if (userId.isNotEmpty()) {
            val updatedUser = HashMap<String, Any>()
            updatedUser["fullname"] = user.fullname
            updatedUser["email"] = user.email
            updatedUser["password"] = user.password

            database.child("users").child(userId).updateChildren(updatedUser)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Update berhasil
                        callback(true)
                    } else {
                        // Update gagal
                        callback(false)
                    }
                }
        } else {
            // ID pengguna kosong
            callback(false)
        }
    }

    fun getAllUsers(callback: (List<User>) -> Unit) {
        val userList = mutableListOf<User>()
        database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let { userList.add(it) }
                }
                callback(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    fun addBooking(booking: Booking, callback: (Boolean) -> Unit) {
        val bookingId = database.child("bookings").push().key
        booking.id = bookingId ?: ""
        database.child("bookings").child(booking.id).setValue(booking)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Penambahan data booking berhasil
                    callback(true)
                } else {
                    // Penambahan data booking gagal
                    callback(false)
                }
            }
    }

    fun getBooking(bookingId: String, callback: (Booking?) -> Unit) {
        val query = database.child("bookings").orderByChild("id").equalTo(bookingId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var booking: Booking? = null
                for (childSnapshot in snapshot.children) {
                    booking = childSnapshot.getValue(Booking::class.java)
                    break
                }
                callback(booking)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun getLatestBooking(callback: (Booking?) -> Unit) {
        val query = database.child("bookings").orderByKey().limitToLast(1)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var booking: Booking? = null
                for (childSnapshot in snapshot.children) {
                    booking = childSnapshot.getValue(Booking::class.java)
                    break
                }
                callback(booking)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun deleteBooking(booking: Booking) {
        val bookingId = booking.id
        if (bookingId.isNotEmpty()) {
            database.child("bookings").child(bookingId).removeValue()
        }
    }

    fun updateBookingStatus(bookingId: String, newStatus: String, callback: (Boolean) -> Unit) {
        val updatedBookingStatus = HashMap<String, Any>()
        updatedBookingStatus["status"] = newStatus

        database.child("bookings").child(bookingId).updateChildren(updatedBookingStatus)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Update status booking berhasil
                    callback(true)
                } else {
                    // Update status booking gagal
                    callback(false)
                }
            }
    }


    fun getAllBookings(callback: (List<Booking>) -> Unit) {
        val bookingList = mutableListOf<Booking>()
        database.child("bookings").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (bookingSnapshot in snapshot.children) {
                    val booking = bookingSnapshot.getValue(Booking::class.java)
                    booking?.let { bookingList.add(it) }
                }
                callback(bookingList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    fun addPayment(payment: Payment, callback: (Boolean) -> Unit) {
        val paymentId = database.child("payments").push().key
        payment.id = paymentId ?: ""
        database.child("payments").child(payment.id).setValue(payment)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Penambahan data Payment berhasil
                    callback(true)
                } else {
                    // Penambahan data Payment gagal
                    callback(false)
                }
            }
    }

    fun getPayment(paymentId: String, callback: (Payment?) -> Unit) {
        val query = database.child("payments").orderByChild("id").equalTo(paymentId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var payment: Payment? = null
                for (childSnapshot in snapshot.children) {
                    payment = childSnapshot.getValue(Payment::class.java)
                    break
                }
                callback(payment)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun deletePayment(payment: Payment) {
        val paymentId = payment.id
        if (paymentId.isNotEmpty()) {
            database.child("payments").child(paymentId).removeValue()
        }
    }

    fun updatePaymentStatus(payment: Payment, newStatus: String, callback: (Boolean) -> Unit) {
        val paymentId = payment.id
        if (paymentId.isNotEmpty()) {
            val updatedPaymentStatus = HashMap<String, Any>()
            updatedPaymentStatus["statusPayment"] = newStatus

            database.child("payments").child(paymentId).updateChildren(updatedPaymentStatus)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Update status pembayaran berhasil
                        callback(true)
                    } else {
                        // Update status pembayaran gagal
                        callback(false)
                    }
                }
        } else {
            // ID pembayaran kosong
            callback(false)
        }
    }

    fun updateTypePayment(payment: Payment, newType: String, callback: (Boolean) -> Unit) {
        val paymentId = payment.id
        if (paymentId.isNotEmpty()) {
            val updatedTypePayment = HashMap<String, Any>()
            updatedTypePayment["typePayment"] = newType

            database.child("payments").child(paymentId).updateChildren(updatedTypePayment)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Update jenis pembayaran berhasil
                        callback(true)
                    } else {
                        // Update jenis pembayaran gagal
                        callback(false)
                    }
                }
        } else {
            // ID pembayaran kosong
            callback(false)
        }
    }


    fun getAllPayments(callback: (List<Payment>) -> Unit) {
        val paymentList = mutableListOf<Payment>()
        database.child("payments").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (paymentSnapshot in snapshot.children) {
                    val payment = paymentSnapshot.getValue(Payment::class.java)
                    payment?.let { paymentList.add(it) }
                }
                callback(paymentList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    fun checkPendingBookings(userId: String, callback: (Boolean) -> Unit) {
        val query = database.child("bookings").orderByChild("userId").equalTo(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var hasPendingBookings = false
                for (childSnapshot in snapshot.children) {
                    val booking = childSnapshot.getValue(Booking::class.java)
                    if (booking?.status != "selesai" && booking?.status != "rating") {
                        hasPendingBookings = true
                        break
                    }
                }
                callback(hasPendingBookings)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }


    fun checkRatingBookings(callback: (Boolean) -> Unit) {
        val query = database.child("bookings").orderByChild("status")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var hasRatingBookings = false
                for (childSnapshot in snapshot.children) {
                    val booking = childSnapshot.getValue(Booking::class.java)
                    if (booking?.status == "rating") {
                        hasRatingBookings = true
                        break
                    }
                }
                callback(hasRatingBookings)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }

    fun addRating(rating: Rating, callback: (Boolean) -> Unit) {
        val ratingId = database.child("ratings").push().key
        rating.id = ratingId ?: ""
        database.child("ratings").child(rating.id).setValue(rating)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Penambahan data rating berhasil
                    callback(true)
                } else {
                    // Penambahan data rating gagal
                    callback(false)
                }
            }
    }

    fun getRating(ratingId: String, callback: (Rating?) -> Unit) {
        val query = database.child("ratings").orderByChild("id").equalTo(ratingId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var rating: Rating? = null
                for (childSnapshot in snapshot.children) {
                    rating = childSnapshot.getValue(Rating::class.java)
                    break
                }
                callback(rating)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun deleteRating(rating: Rating) {
        val ratingId = rating.id
        if (ratingId.isNotEmpty()) {
            database.child("ratings").child(ratingId).removeValue()
        }
    }

    fun getAllRatings(callback: (List<Rating>) -> Unit) {
        val ratingList = mutableListOf<Rating>()
        database.child("ratings").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ratingSnapshot in snapshot.children) {
                    val rating = ratingSnapshot.getValue(Rating::class.java)
                    rating?.let { ratingList.add(it) }
                }
                callback(ratingList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

}
