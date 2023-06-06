package org.rpla4503.cuciinmobile.model

data class Booking(
    var id: String = "",
    var userId: String = "",
    var status: String = "",
    var date: String = "",
    var time: String = "",
    var vehicleType: String = "",
    var servicesType: String = "",
    var numberPhone: String = "",
    var plateNumber: String = ""
)
