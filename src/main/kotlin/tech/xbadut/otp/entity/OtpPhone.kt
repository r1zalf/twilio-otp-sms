package tech.xbadut.otp.entity

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "otp_phone")
data class OtpPhone(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "phone")
    val phone: String? = null,

    @Column(name = "code")
    val code: Int? = null,

    @Column(name = "status_id")
    var statusId: Int? = null,

    @Column(name = "created_at")
    val createdAt: Date,

    @Column(name = "update_at")
    var updateAt: Date? = null,

    )