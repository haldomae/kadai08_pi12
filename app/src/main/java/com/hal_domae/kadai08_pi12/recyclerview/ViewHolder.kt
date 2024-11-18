package com.hal_domae.kadai08_pi12.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hal_domae.kadai08_pi12.R

// RecyclerViewで使いたいパーツを定義
class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val itemDate: TextView = itemView.findViewById(R.id.date)
    val itemText: TextView = itemView.findViewById(R.id.text)
}