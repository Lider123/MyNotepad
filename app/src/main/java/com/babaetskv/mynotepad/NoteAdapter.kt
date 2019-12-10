package com.babaetskv.mynotepad

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * @author babaetskv on 10.12.19
 */
class NoteAdapter(private val context: Context, private val items: List<Note>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        val note = getItem(position)
        view.findViewById<TextView>(android.R.id.text1).text = note.title
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