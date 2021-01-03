package tech.xbadut.otp.controller

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import tech.xbadut.otp.error.OtpException
import tech.xbadut.otp.model.OtpPhoneResponse
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class OtpErrorController {

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun validatorHandler(constraintViolationException: ConstraintViolationException): OtpPhoneResponse {
        return OtpPhoneResponse(
            success = false,
            message = constraintViolationException.message!!,
            phone = ""
        )

    }

    @ExceptionHandler(value = [OtpException::class])
    fun noFound2(otpException: OtpException): OtpPhoneResponse {
        return OtpPhoneResponse(
            success = false,
            message = otpException.error,
            phone = otpException.phone
        )
    }
}