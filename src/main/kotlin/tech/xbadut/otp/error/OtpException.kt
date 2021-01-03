package tech.xbadut.otp.error

class OtpException(m: String, p: String) : Exception() {
    val error = m
    val phone = p

}