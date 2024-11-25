package com.hal_domae.kadai08_pi12

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hal_domae.kadai08_pi12.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private var textFeeling: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 気分を押した時のイベントリスナー
        binding.feelGreat.setOnClickListener { feelClicked(it) }
        binding.feelGood.setOnClickListener { feelClicked(it) }
        binding.feelNormal.setOnClickListener { feelClicked(it) }
        binding.feelBad.setOnClickListener { feelClicked(it) }
        binding.feelAwful.setOnClickListener { feelClicked(it) }

        binding.selectDate.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.show(supportFragmentManager, "datePicker")
        }
    }

    // 気分が選択されたとき
    private fun feelClicked(view: View){
        textFeeling = when(view.id){
            R.id.feel_great -> "今日の気分は最高でした！"
            R.id.feel_good -> "今日の気分はいい！"
            R.id.feel_normal -> "今日の気分は普通"
            R.id.feel_bad -> "今日の気分は微妙"
            else -> "今日の気分は最悪..."
        }
        // テキストを置き換える
        binding.inputDiary.setText(getString(R.string.diary_text, textFeeling, ""))
    }
}