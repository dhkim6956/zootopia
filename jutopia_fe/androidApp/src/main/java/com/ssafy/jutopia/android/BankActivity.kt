package com.ssafy.jutopia.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.ssafy.jutopia.android.databinding.ActivityBankBinding

class BankActivity : AppCompatActivity() {
    private lateinit var sendLayout: FrameLayout
    private lateinit var saveLayout: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)
        val binding = ActivityBankBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sendLayout = binding.send
        saveLayout = binding.save
        sendLayout.setOnClickListener{
            val intent = Intent(this, SendActivity::class.java)
            startActivity(intent)
        }

        saveLayout.setOnClickListener {
            val intent = Intent(this, SaveActivity::class.java)
            startActivity(intent)
        }
    }
}