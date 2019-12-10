package com.babaetskv.mynotepad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import org.w3c.dom.Text

/**
 * @author babaetskv on 10.12.19
 */
class NoteAdapter(private val context: Context, private val items: List<Note>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        view.findViewById<TextView>(android.R.id.text1).text = getItem(position).title
        return view
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = items.size
}