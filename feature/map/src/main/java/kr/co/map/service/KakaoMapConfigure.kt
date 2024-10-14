package kr.co.map.service

import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import com.kakao.vectormap.route.RouteLineStylesSet
import kr.co.kamo.feature.map.R
import kr.co.ui.theme.RouteBlock
import kr.co.ui.theme.RouteDelay
import kr.co.ui.theme.RouteJam
import kr.co.ui.theme.RouteNormal
import kr.co.ui.theme.RouteSlow
import kr.co.ui.theme.RouteUnknown
import timber.log.Timber

internal fun MapView.setCallBack(
    reportMapError: (Exception?) -> Unit,
    routes: List<kr.co.model.Route>,
): MapView {
    start(
        object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                this@setCallBack.finish()
            }

            override fun onMapError(p0: Exception?) = reportMapError(p0)
        },
        object : KakaoMapReadyCallback() {
            override fun onMapReady(map: KakaoMap) {

                val startLatLng = routes.first().points.first()
                    .run { LatLng.from(latitude, longitude) }
                val endLatLng = routes.last().points.last()
                    .run { LatLng.from(latitude, longitude) }

                map.configure {
                    drawRoute(routes)
                    setLabel(startLatLng, endLatLng)
                }.apply {
                    CameraUpdateFactory.fitMapPoints(
                        arrayOf(
                            startLatLng,
                            endLatLng
                        ),
                        120
                    ).let { moveCamera(it) }
                }
            }
        }
    )
    return this
}

private fun KakaoMap.configure(block: KakaoMapController.() -> Unit): KakaoMap {
    KakaoMapController(this).block()
    return this
}

private class KakaoMapController(
    private val kakaoMap: KakaoMap,
) {
    val routeStyleSet: RouteLineStylesSet = RouteLineStylesSet.from(
        RouteLineStyles.from(RouteLineStyle.from(16f, RouteUnknown)),
        RouteLineStyles.from(RouteLineStyle.from(16f, RouteBlock)),
        RouteLineStyles.from(RouteLineStyle.from(16f, RouteJam)),
        RouteLineStyles.from(RouteLineStyle.from(16f, RouteDelay)),
        RouteLineStyles.from(RouteLineStyle.from(16f, RouteSlow)),
        RouteLineStyles.from(RouteLineStyle.from(16f, RouteNormal))
    )

    private var segments: List<RouteLineSegment> = emptyList()

    private val options: RouteLineOptions
        get() = RouteLineOptions.from(segments)
            .setStylesSet(routeStyleSet)

    fun createRouteLine(segments: List<RouteLineSegment>) {
        this.segments = segments

        kakaoMap.routeLineManager?.layer?.addRouteLine(options)
    }

    private val labelStyles: LabelStyles =
        LabelStyle.from(R.drawable.img_marker_start)
            .let { LabelStyles.from(it) }
            .let { kakaoMap.labelManager?.addLabelStyles(it) }
            .let { checkNotNull(it) }

    private val labelStyles2: LabelStyles =
        LabelStyle.from(R.drawable.img_marker_end)
            .let { LabelStyles.from(it) }
            .let { kakaoMap.labelManager?.addLabelStyles(it) }
            .let { checkNotNull(it) }

    fun createLabel(
        path: LatLng,
        isStart: Boolean = false,
    ) {
        kakaoMap.labelManager?.layer?.apply {
            LabelOptions.from(path)
                .setStyles(if (isStart) labelStyles else labelStyles2)
                .let { this.addLabel(it) }
        } ?: Timber.d("kakaoMap.labelManager is null")
    }
}

private fun KakaoMapController.drawRoute(
    routes: List<kr.co.model.Route>,
) {
    routes.map { route ->
        RouteLineSegment.from(
            route.points.map {
                LatLng.from(it.latitude, it.longitude)
            }
        ).setStyles(routeStyleSet.getStyles(route.trafficState.ordinal))
    }.run {
        createRouteLine(this)
    }
}

private fun KakaoMapController.setLabel(
    startPoint: LatLng,
    endPoint: LatLng,
) {
    createLabel(startPoint, isStart = true)
    createLabel(endPoint)
}