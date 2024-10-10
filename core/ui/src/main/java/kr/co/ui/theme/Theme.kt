package kr.co.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object KakaoTheme {
    val colors: ColorSet
        @Composable
        @ReadOnlyComposable
        get() = LocalColorSet.current

    val typography: KakaoTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalKakaoTypography.current
}

@Composable
fun KaKaoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    CompositionLocalProvider(
        LocalColorSet provides if (darkTheme) darkColorSet else lightColorSet,
        LocalKakaoTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = LocalColorSet.current.material,
            content = content
        )
    }
}