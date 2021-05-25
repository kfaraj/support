package com.kfaraj.support.appcompat.sample

import androidx.fragment.app.Fragment

/**
 * Demonstrates how to use the AppCompat library.
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {

        /**
         * Creates a new instance of this fragment class.
         */
        fun newInstance(): MainFragment {
            return MainFragment()
        }

    }

}
