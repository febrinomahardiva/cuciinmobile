package org.rpla4503.cuciinmobile.db

import android.content.Context
import com.google.firebase.database.*
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
            updatedUser["username"] = user.username
            updatedUser["fullname"] = user.fullname
            updatedUser["email"] = user.email

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

    // Update user password
    fun updatePassword(user: User, callback: (Boolean) -> Unit) {
        val userId = user.id
        if (userId.isNotEmpty()) {
            val updatedUserPassword = HashMap<String, Any>()
            updatedUserPassword["password"] = user.password

            database.child("users").child(userId).updateChildren(updatedUserPassword)
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
}