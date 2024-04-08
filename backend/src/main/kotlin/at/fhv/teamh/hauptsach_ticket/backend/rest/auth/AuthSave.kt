package at.fhv.teamh.hauptsach_ticket.backend.rest.auth

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.security.Key

internal object AuthSave {
    const val REALM =
        "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6IkhhdXB0c2FjaFRpY2tldCIsImV4cCI6MTY4NDMzNTE2MX0.7iGNhMvytX_peWTcDTYFQde6wuf-LgC8MjhSnS4_G8ybViNY8-YDoS-Ca2wH03sN"
    const val AUTHENTICATION_SCHEME = "Bearer"

    val KEY: Key = Keys.secretKeyFor(SignatureAlgorithm.HS384)
}