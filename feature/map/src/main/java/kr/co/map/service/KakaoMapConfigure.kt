package kr.co.map.service

import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import com.kakao.vectormap.route.RouteLineStylesSet
import kr.co.domain.model.Route
import kr.co.ui.theme.RouteBlock
import kr.co.ui.theme.RouteDelay
import kr.co.ui.theme.RouteJam
import kr.co.ui.theme.RouteNormal
import kr.co.ui.theme.RouteSlow
import kr.co.ui.theme.RouteUnknown
import timber.log.Timber

internal fun KakaoMap.configure(block: KakaoMapScope.() -> Unit): KakaoMap {
    KakaoMapScope(this).apply(block)
    return this
}

internal class KakaoMapScope(
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
        LabelStyle.from(kr.co.kamo.core.ui.R.drawable.img_marker_sample)
            .let { LabelStyles.from(it) }
            .let { kakaoMap.labelManager?.addLabelStyles(it) }
            .let { checkNotNull(it) }

    fun createLabel(
        path: LatLng,
    ) {
        kakaoMap.labelManager?.layer?.apply {
            LabelOptions.from(path)
                .setStyles(labelStyles)
                .let { this.addLabel(it) }
        } ?: Timber.d("kakaoMap.labelManager is null")
    }
}

internal fun KakaoMapScope.drawRoute(
    routes: List<Route>,
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

internal fun KakaoMapScope.setLabel(
    startPoint: LatLng,
    endPoint: LatLng,
) {
    createLabel(startPoint)
    createLabel(endPoint)
}