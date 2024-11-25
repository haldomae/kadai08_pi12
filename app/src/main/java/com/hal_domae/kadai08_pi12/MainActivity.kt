package com.hal_domae.kadai08_pi12

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hal_domae.kadai08_pi12.databinding.ActivityMainBinding
import com.hal_domae.kadai08_pi12.recyclerview.ListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // データベースから取り出したデータを入れる
        val data = mutableListOf<Map<String, String>>()
        // データベースを用意
        dbHelper = DatabaseHelper(this@MainActivity)
        // データベースからデータを取り出す
        dbHelper.readableDatabase.use { db -> }

        val sampleData = mutableListOf(
            mapOf("date" to "2024/01/01", "text" to "ここに日記のテキストが入ります"),
            mapOf("date" to "2024/02/01", "text" to "ここに日記のテキストが入ります"),
            mapOf("date" to "2024/03/01", "text" to "ここに日記のテキストが入ります"),
            mapOf("date" to "2024/04/01", "text" to "ここに日記のテキストが入ります"),
            mapOf("date" to "2024/05/01", "text" to "ここに日記のテキストが入ります"),
            mapOf("date" to "2024/06/01", "text" to "ここに日記のテキストが入ります"),
            mapOf("date" to "2024/07/01", "text" to "ここに日記のテキストが入ります"),
        )
//        binding.diaryList.adapter = SimpleAdapter(
//            this,
//            sampleData,
//            R.layout.list_item,
//            arrayOf("date", "text"),
//            intArrayOf(R.id.date, R.id.text)
//        )
        // RecyclerViewの設定
        // LayoutManagerでリストの表示形式を決める
        binding.diaryList.layoutManager= LinearLayoutManager(this)
        binding.diaryList.adapter = ListAdapter(sampleData)

        val dividerItemDecoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        binding.diaryList.addItemDecoration(dividerItemDecoration)
    }
}