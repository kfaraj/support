package com.kfaraj.support.appcompat.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.appbar.MaterialToolbar

/**
 * Demonstrates how to use the AppCompat library.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbar = ActivityCompat.requireViewById<MaterialToolbar>(this, R.id.toolbar)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<MainFragment>(R.id.container)
                setReorderingAllowed(true)
            }
        }
    }

}
