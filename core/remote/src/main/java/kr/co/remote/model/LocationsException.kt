package kr.co.remote.model

import kr.co.common.model.KamoException

data class LocationsException(
    override val code: Int,
    override val message: String,
    private val errorUrl: String,
) : KamoException() {
    override fun toString(): String {
        return "LocationsException(code=$code, message='$message')"
    }

    override val location: String
        get() =
            errorUrl.substringAfterLast("/api/v1/coding-assignment/")
                .substringBefore("?")
}