package com.babaetskv.mynotepad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.babaetskv.mynotepad.R
import com.babaetskv.mynotepad.data.Note
import java.text.SimpleDateFormat
import java.util.*

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
        val note = getItem(position)

        return convertView
            ?: inflater.inflate(R.layout.item_note, parent, false).apply {
                setOnClickListener {
                    onItemClick(note)
                }
                setOnLongClickListener {
                    onItemLongClick(note)
                    true
                }

                tag = ViewHolder(this).apply {
                    titleView.text = note.title
                    dateView.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(note.updatedAt)
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

    private class ViewHolder(view: View) {
        var titleView: TextView = view.findViewById(R.id.item_note_title)
        var dateView: TextView = view.findViewById(R.id.item_note_date)
    }
}
