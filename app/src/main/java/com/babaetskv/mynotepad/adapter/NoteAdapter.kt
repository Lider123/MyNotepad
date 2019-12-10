package com.babaetskv.mynotepad.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.babaetskv.mynotepad.R
import com.babaetskv.mynotepad.data.Note
import com.babaetskv.mynotepad.ui.NoteActivity
import java.text.SimpleDateFormat

/**
 * @author babaetskv on 10.12.19
 */
class NoteAdapter(private val context: Context, private val items: List<Note>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        val note = getItem(position)
        view.findViewById<TextView>(R.id.item_note_title).text = note.title
        view.findViewById<TextView>(R.id.item_note_date).text = SimpleDateFormat("dd.MM.yyyy").format(note.updatedAt)
        view.setOnClickListener {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(NoteActivity.EXTRA_NOTE, note)
            context.startActivity(intent)
        }
        return view
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = items.size
}