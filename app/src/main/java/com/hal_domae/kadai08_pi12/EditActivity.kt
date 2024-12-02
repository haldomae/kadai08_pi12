package com.hal_domae.kadai08_pi12

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
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

        Log.d("テスト01","test")
        // 項目タップした時に渡されたデータを反映
        intent?.extras?.let{
            val test = it.getString("DIARY_DATE")
            Log.d("aaaaa",test.toString())
            binding.selectDate.setText(it.getString("DIARY_DATE"))
            binding.inputDiary.setText(it.getString("DIARY_TEXT"))
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
                //db.insert("diary_items", null, value)
                // 日付が重複してたら置き換える
                //  SQLiteDatabase.CONFLICT_REPLACEが重複してたら置き換える
                db.insertWithOnConflict("diary_items", null,value, SQLiteDatabase.CONFLICT_REPLACE)


                // 日記一覧に戻る
                startActivity(Intent(this@EditActivity, MainActivity::class.java))
            }
        }

        // 削除処理
        // 1. 削除ボタンが押されたら
        // 2．日付が入力されているか? 入力されていなければToastを出します
        // 3. 削除処理を実行
        //  A. 配列の形式でデータを取得(arrayOf(日付の場所のテキストを取得した後、String型にキャストする))
        //  B. deleteメソッドを使ってデータ削除
        //   a. 3つ引数が必要で、1番目がテーブル名
        //   b. 2番目が削除する条件でプレースホルダーで指定する(例 : "user_id = ?")
        //   c. 3番目がプレースホルダーの?にハマるパラメータを配列で指定
        // 4. 一覧画面に遷移する
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