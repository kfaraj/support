package com.kfaraj.support.recyclerview.sample

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main),
    OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fab = ActivityCompat.requireViewById<FloatingActionButton>(this, R.id.fab)
        fab.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            (fragment as? OnClickListener)?.onClick(v)
        }
    }

}
