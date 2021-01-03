package tech.xbadut.otp.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import tech.xbadut.otp.entity.OtpPhone

@Repository
interface OtpPhoneRepository : JpaRepository<OtpPhone, Long> {
    @Query("SELECT u FROM OtpPhone u WHERE u.phone=:phone AND u.code=:code AND u.statusId =:idStatus")
    fun isExistCode(phone: String, code: Int, idStatus: Int): OtpPhone?

    @Query("SELECT u FROM OtpPhone u WHERE u.phone=:phone AND u.code=:code")
    fun findByPhoneCode(phone: String, code: Int): OtpPhone?

    @Query("SELECT u FROM OtpPhone u WHERE u.statusId =:idStatus")
    fun findByStatus(idStatus: Int): List<OtpPhone>
}