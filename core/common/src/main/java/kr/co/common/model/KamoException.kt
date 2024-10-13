package kr.co.common.model

abstract class KamoException(
    open val code: Int? = null,
    override val message: String? = null,
) : Throwable() {

    abstract val location: String

    override fun toString(): String {
        return "KamoException(code=$code, message='$message')"
    }
}
