package com.kfaraj.support.recyclerview.sample

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main),
    OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbar = ActivityCompat.requireViewById<MaterialToolbar>(this, R.id.toolbar)
        val fab = ActivityCompat.requireViewById<FloatingActionButton>(this, R.id.fab)
        setSupportActionBar(toolbar)
        fab.setOnClickListener(this)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<MainFragment>(R.id.container)
                setReorderingAllowed(true)
            }
        }
    }

    override fun onClick(v: View) {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            (fragment as? OnClickListener)?.onClick(v)
        }
    }

}
