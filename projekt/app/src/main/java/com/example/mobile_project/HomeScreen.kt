
package com.example.mobile_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Composable
fun HomeScreen() {

    AndroidView(modifier = Modifier.fillMaxWidth().height(180.dp),
        factory = { context -> RecyclerView(context).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ImageAdapter(context, populateCarousel()
                )
            }
        }
    )
}
fun populateCarousel(): ArrayList<String?> { // stub function to populate the carusel (will take from database later)

    val items = arrayListOf<String?>()

    items.add("Prezentacja: jak zrobić gre i się nie narobić")
    items.add("DNS Game jam 4 już dziś")
    items.add("Spotkanie projektowe 03.11.2026")
    items.add("Edycja 2026 Archiwum DNS")
    items.add("Edycja 2026 OST DNS")


    return items
}
class ImageAdapter(var context: Context, var arrayList: ArrayList<String?>)
    : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = arrayList[position]
        holder.textView.text = item ?: ""

        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(holder.textView, item)
        }
    }

    override fun getItemCount(): Int { return arrayList.size }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(R.id.list_item_text) }

    interface OnItemClickListener { fun onClick(textView: TextView, path: String?) }
}
