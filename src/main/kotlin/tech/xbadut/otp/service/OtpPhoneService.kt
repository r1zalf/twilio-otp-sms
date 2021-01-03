package tech.xbadut.otp.service

import tech.xbadut.otp.model.OtpPhoneResponse

interface OtpPhoneService {
    fun requestCodeOtp(phone: String): OtpPhoneResponse
    fun validationCodeOtp(phone: String, code: Int): OtpPhoneResponse
}