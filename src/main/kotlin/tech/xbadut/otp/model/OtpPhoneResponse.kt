package tech.xbadut.otp.model

data class OtpPhoneResponse(
    val success: Boolean,
    val message: String,
    val phone: String,
)