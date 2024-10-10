package kr.co.common.model

import java.io.IOException

class KamoException(
    val code: Int? = null,
    override val message: String? = null,
): IOException() {
    override fun toString(): String {
        return "KamoException(code=$code, message='$message')"
    }

    override fun getLocalizedMessage(): String? {
        return when(code) {
            4041 -> "경로를 찾을 수 없습니다."
            else -> message
        }
    }

    private val _extras: MutableMap<String, Any?> = mutableMapOf()

    val extras: Map<String, Any?>
        get() = _extras.toMap()

    fun addExtras(key: String, value: Any?) {
        synchronized(_extras) {
            _extras[key] = value
        }
    }
}
