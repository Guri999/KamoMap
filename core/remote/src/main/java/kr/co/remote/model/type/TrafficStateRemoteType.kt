package kr.co.remote.model.type

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class TrafficStateRemoteType {

    @SerialName("UNKNOWN")
    UNKNOWN,

    @SerialName("JAM")
    JAM,

    @SerialName("DELAY")
    DELAY,

    @SerialName("SLOW")
    SLOW,

    @SerialName("NORMAL")
    NORMAL,

    @SerialName("BLOCK")
    BLOCK,
}