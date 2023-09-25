package com.ssafy.jutopia.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< HEAD
import android.util.Log
import android.widget.AdapterView
import com.ssafy.jutopia.android.databinding.ActivityMainBinding
=======
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.ssafy.jutopia.android.ui.theme.MyApplicationTheme
>>>>>>> e8ec0f4ca13795f3f067dc7612f73f28d37d6f1e

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
<<<<<<< HEAD
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val main_img = arrayOf(
            R.drawable.bank,
            R.drawable.stock,
            R.drawable.lease,
            R.drawable.trade,
            R.drawable.market,
            R.drawable.notice
        )

        val main_text = arrayOf(
            "은행",
            "주식",
            "임대",
            "환전",
            "상점",
            "공지사항"
        )

        val gridviewAdapter = GridviewAdapter(this, main_img, main_text)
        binding.gridview.adapter = gridviewAdapter

        binding.gridview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    val intent = Intent(this@MainActivity,BankActivity::class.java )
                    startActivity(intent)
                }
=======
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent{
            MyApplicationTheme {
                JutopiaApp()
>>>>>>> e8ec0f4ca13795f3f067dc7612f73f28d37d6f1e
            }
        }
    }
}