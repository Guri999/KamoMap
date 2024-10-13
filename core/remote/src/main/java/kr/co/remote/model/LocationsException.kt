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
        get() = when (
            errorUrl.substringAfterLast("/api/v1/coding-assignment/")
                .substringBefore("?")
        ) {
            "locations" -> "출발지/도착지 리스트 API"
            "routes" -> "경로 조회 API"
            "distance-time" -> "시간/거리 조회 API"
            else -> errorUrl
        }

    override fun getLocalizedMessage(): String? {
        return when (code) {
            4041 -> "경로를 찾을 수 없습니다."
            else -> message
        }
    }
}