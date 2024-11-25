package com.hal_domae.kadai08_pi12

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hal_domae.kadai08_pi12.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var dbHelper: DatabaseHelper
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

        // データベースを用意
        dbHelper = DatabaseHelper(this@EditActivity)

        // 保存ボタンのクリックイベント
        binding.saveButton.setOnClickListener {
            // 入力チェック
            if (binding.selectDate.text.isNullOrBlank() || binding.inputDiary.text.isNullOrBlank()){
                Toast.makeText(this, "未入力の項目があります", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // データを保存する
            // データ書き込みはwritableDatabaseを使う
            dbHelper.writableDatabase.use { db ->
                val  value = ContentValues().apply {
                    put("diary_date", binding.selectDate.text.toString())
                    put("diary_text", binding.inputDiary.text.toString())
                }
                // insertで保存
                db.insert("diary_items", null, value)

                // 日記一覧に戻る
                startActivity(Intent(this@EditActivity, MainActivity::class.java))
            }
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