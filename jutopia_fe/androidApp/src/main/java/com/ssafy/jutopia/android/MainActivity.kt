package com.ssafy.jutopia.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.ssafy.jutopia.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            }
        }

    }
}