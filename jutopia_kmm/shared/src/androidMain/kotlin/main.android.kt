import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.myapplication.common.R

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App()

actual val icehimchanFontFamily: FontFamily = FontFamily(
    Font(R.font.icehimchan)
)
actual val icejaramFontFamily: FontFamily = FontFamily(
    Font(R.font.icejaram)
)
actual val icesiminFontFamily: FontFamily = FontFamily(
    Font(R.font.icesimin)
)
actual val icesotongFontFamily: FontFamily = FontFamily(
    Font(R.font.icesotong)
)
actual val pretendardFontFamily: FontFamily = FontFamily(
    Font(R.font.pretendard)
)