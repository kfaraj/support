package com.kfaraj.support.recyclerview.sample.util

import android.os.Bundle

/**
 * Returns the value associated with the given [key].
 */
fun Bundle.requireStringArrayList(key: String?): ArrayList<String> {
    return getStringArrayList(key)
        ?: throw IllegalArgumentException()
}
