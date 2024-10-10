package kr.co.remote.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kr.co.remote.model.response.GetRoutesResponse

internal object PointSerializer : KSerializer<List<GetRoutesResponse.Point>> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Point", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): List<GetRoutesResponse.Point> {
        return decoder.decodeString().split(" ").map { s ->
            val (a, b) = s.split(",").map { it.toDouble() }
            GetRoutesResponse.Point(b, a)
        }
    }

    override fun serialize(encoder: Encoder, value: List<GetRoutesResponse.Point>) {
        val s = value.joinToString(" ") { "${it.latitude},${it.longitude}" }
        encoder.encodeString(s)
    }

}
