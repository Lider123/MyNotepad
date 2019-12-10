package com.babaetskv.mynotepad.ui

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.babaetskv.mynotepad.R

/**
 * @author babaetskv on 10.12.19
 */
class TitleDialog(context: Context, onSubmit: (title: String) -> Unit) : AlertDialog(context) {
    private var title = ""

    init {
        val view = layoutInflater.inflate(R.layout.dialog_set_title, null)
        val textWatcher = object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                title = p0.toString()
            }

        }
        view.findViewById<EditText>(R.id.note_title).addTextChangedListener(textWatcher)


        setView(view)
        setTitle(R.string.set_title)
        setButton(BUTTON_POSITIVE, context.getString(R.string.ok)) { _, _ ->
            onSubmit(title)
            cancel()
        }
        setButton(BUTTON_NEGATIVE, context.getString(R.string.cancel)) { _, _ ->
            cancel()
        }
    }
}