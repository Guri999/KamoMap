package kr.co.common.model

class KamoException(
    val code: Int? = null,
    override val message: String? = null,
    val location: String,
) : Throwable() {
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
