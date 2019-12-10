package com.babaetskv.mynotepad

import java.io.Serializable

/**
 * @author babaetskv on 10.12.19
 */
data class Note(val title: String, val text: String, val updatedAt: Long) : Serializable
