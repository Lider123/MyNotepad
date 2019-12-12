package com.babaetskv.mynotepad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.babaetskv.mynotepad.R
import com.babaetskv.mynotepad.data.Note
import java.text.SimpleDateFormat

/**
 * @author babaetskv on 10.12.19
 */
class NoteAdapter(
    private val context: Context,
    private var items: List<Note>,
    private val onItemClick: (item: Note) -> Unit,
    private val onItemLongClick: (item: Note) -> Unit
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(R.layout.item_note, parent, false).apply {
            val note = getItem(position)
            findViewById<TextView>(R.id.item_note_title).text = note.title
            findViewById<TextView>(R.id.item_note_date).text = SimpleDateFormat("dd.MM.yyyy").format(note.updatedAt)
            setOnClickListener {
                onItemClick(note)
            }
            setOnLongClickListener {
                onItemLongClick(note)
                true
            }
        }
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = items.size

    fun setItems(items: List<Note>) {
        this.items = items
        notifyDataSetChanged()
    }
}