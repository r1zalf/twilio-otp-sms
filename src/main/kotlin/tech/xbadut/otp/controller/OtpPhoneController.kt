package tech.xbadut.otp.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import tech.xbadut.otp.model.OtpPhoneResponse
import tech.xbadut.otp.service.OtpPhoneService


@RestController
class OtpPhoneController(
    val otpPhoneService: OtpPhoneService
) {

    @GetMapping(
        value = ["api/otp/{phone}"]
    )
    fun requestOtp(@PathVariable("phone") phone: String): OtpPhoneResponse {
        return otpPhoneService.requestCodeOtp(phone)
    }

    @GetMapping(
        value = ["api/otp/{phone}/{code}"]
    )
    fun validateOtp(
        @PathVariable("phone") phone: String,
        @PathVariable("code") code: Int,
    ): OtpPhoneResponse {
        return otpPhoneService.validationCodeOtp(phone, code)
    }

}