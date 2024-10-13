package kr.co.common.model

class KamoException(
    val code: Int? = null,
    override val message: String? = null,
    private var errorUrl: String,
) : Throwable() {

    val location: String
        get() = when (
            errorUrl.substringAfterLast("/api/v1/coding-assignment/")
                .substringBefore("?")
        ) {
            "locations" -> "출발지/도착지 리스트 API"
            "routes" -> "경로 조회 API"
            "distance-time" -> "시간/거리 조회 API"
            else -> errorUrl
        }

    override fun toString(): String {
        return "KamoException(code=$code, message='$message')"
    }

    override fun getLocalizedMessage(): String? {
        return when (code) {
            4041 -> "경로를 찾을 수 없습니다."
            else -> message
        }
    }
}
