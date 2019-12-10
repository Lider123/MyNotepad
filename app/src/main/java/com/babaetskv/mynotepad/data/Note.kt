package com.babaetskv.mynotepad.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 * @author babaetskv on 10.12.19
 */
@Entity
data class Note(var title: String, var text: String, var updatedAt: Long) : Serializable {
    @PrimaryKey
    var id = UUID.randomUUID().toString()
}
