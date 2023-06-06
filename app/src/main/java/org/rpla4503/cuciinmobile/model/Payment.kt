package org.rpla4503.cuciinmobile.model

data class Payment(
    var id: String = "",
    var userId: String = "",
    var bookingId: String = "",
    var statusPayment: String = "",
    var typePayment: String = "",
    var methodPayment: String = "",
    var totalPayment: String = ""

)
