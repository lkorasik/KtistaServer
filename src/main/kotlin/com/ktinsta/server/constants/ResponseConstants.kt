package com.ktinsta.server.constants

enum class ResponseConstants(val value: String) {
    SUCCESS("success"), ERROR("error"),
    USERNAME_UNAVAILABLE("USR_001"),
    INVALID_USER_ID("USR_002"),
    INVALID_USERNAME("USR_003"),
    INVALID_PASSWORD("USR_004"),
    EMPTY_STATUS("USR_005"),
    ACCOUNT_DEACTIVATED("GLO_001")
}