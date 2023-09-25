import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import moe.tlaster.precompose.PreComposeApplication
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface

actual fun getPlatformName(): String = "iOS"
fun MainViewController() = PreComposeApplication { App() }

private fun loadCustomFont(name: String): Typeface {
    return Typeface.makeFromName(name, FontStyle.NORMAL)
}

actual val icehimchanFontFamily: FontFamily = FontFamily(
    Typeface(loadCustomFont("icehimchan"))
)
actual val icejaramFontFamily: FontFamily = FontFamily(
    Typeface(loadCustomFont("icejaram"))
)
actual val icesiminFontFamily: FontFamily = FontFamily(
    Typeface(loadCustomFont("icesimin"))
)
actual val icesotongFontFamily: FontFamily = FontFamily(
    Typeface(loadCustomFont("icesotong"))
)
actual val pretendardFontFamily: FontFamily = FontFamily(
    Typeface(loadCustomFont("pretendard"))
)