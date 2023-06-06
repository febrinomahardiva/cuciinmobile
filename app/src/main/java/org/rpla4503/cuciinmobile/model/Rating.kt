package org.rpla4503.cuciinmobile.model

data class Rating(
    var id: String = "",
    val userId: String = "",
    val bookingId: String = "",
    val rating: String = "",
    val comment: String = "",
)
