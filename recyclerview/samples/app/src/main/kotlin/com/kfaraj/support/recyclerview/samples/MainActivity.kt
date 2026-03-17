package com.kfaraj.support.recyclerview.samples

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.kfaraj.support.recyclerview.samples.ui.MainFragment

/**
 * Contains the [MainFragment].
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<MainFragment>(R.id.container)
                setReorderingAllowed(true)
            }
        }
    }

}
