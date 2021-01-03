package tech.xbadut.otp.service.implementation

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.rest.api.v2010.account.MessageCreator
import com.twilio.type.PhoneNumber
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import tech.xbadut.otp.entity.OtpPhone
import tech.xbadut.otp.error.OtpException
import tech.xbadut.otp.model.OtpPhoneResponse
import tech.xbadut.otp.repository.OtpPhoneRepository
import tech.xbadut.otp.service.OtpPhoneService
import java.util.*
import kotlin.time.ExperimentalTime


@Service
class OtpPhoneServiceImpl(val otpPhoneRepository: OtpPhoneRepository) : OtpPhoneService {
    override fun requestCodeOtp(phone: String): OtpPhoneResponse {

        if (phone.length == 12) {
            throw OtpException("No phone not valid", phone)
        }

        if (phone.first() == '0') phone.replace('0', ' ').trim()

        val code = createRandomCode(phone)

        val otpData = OtpPhone(
            id = null,
            phone = phone,
            code = code,
            statusId = 0,
            createdAt = Date()
        )

        val createSMS = createSMS(phone, code)

        if (createSMS.errorCode != null) {
            throw OtpException(createSMS.errorMessage.toString(), phone)
        }

        otpPhoneRepository.save(otpData)

        return OtpPhoneResponse(
            success = true,
            message = "Code was sent",
            phone = phone,
        )
    }

    override fun validationCodeOtp(phone: String, code: Int): OtpPhoneResponse {
        val otpData = otpPhoneRepository.findByPhoneCode(phone, code)
        if (otpData == null) {
            throw OtpException("Code not valid", phone)
        } else {
            when (otpData.statusId) {
                0 -> {
                    otpData.statusId = 1
                    otpData.updateAt = Date()
                }
                2 -> throw OtpException("Code expired", phone)
                else -> throw OtpException("Code not valid", phone)
            }

            otpPhoneRepository.save(otpData)

            return OtpPhoneResponse(
                success = true,
                message = "Code is valid",
                phone = phone,
            )
        }
    }


    private fun createRandomCode(phone: String): Int {
        var code = 0
        val isExist = otpPhoneRepository.isExistCode(phone, code, 0) != null

        do {
            code = (1000..9999).shuffled().first()
        } while (isExist)

        return code
    }

    private fun createSMS(phone: String, code: Int): Message {
        Twilio.init(
"",
            ""
        )

        return MessageCreator(
            PhoneNumber("+62$phone"),
            PhoneNumber("+19293252759"),
            "Fahrud Company - Code OTP anda: [$code]"
        ).create()
    }


    @ExperimentalTime
    @Scheduled(fixedRate = 5000)
    private fun changeStatus() {
        val otpData = otpPhoneRepository.findByStatus(0)

        if (otpData.isNotEmpty()) {
            otpData.forEach {
                val diff: Long = Date().time - it.createdAt.time
                val minute = (diff / 1000) / 60

                if (minute > 3) {
                    it.statusId = 2
                }

                it.updateAt = Date()
                otpPhoneRepository.save(it)
            }
        }
    }

}