package com.kfaraj.support.recyclerview.samples

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kfaraj.support.recyclerview.samples.util.applyWindowInsetsPadding

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main),
    OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val toolbar = ActivityCompat.requireViewById<MaterialToolbar>(this, R.id.toolbar)
        val fab = ActivityCompat.requireViewById<FloatingActionButton>(this, R.id.fab)
        (toolbar.parent as AppBarLayout).applyWindowInsetsPadding(
            WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.displayCutout(),
            applyLeft = true,
            applyTop = true,
            applyRight = true
        )
        fab.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            (fragment as? OnClickListener)?.onClick(v)
        }
    }

}
