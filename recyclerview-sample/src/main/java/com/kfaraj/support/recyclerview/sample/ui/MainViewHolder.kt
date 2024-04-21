package com.kfaraj.support.recyclerview.sample.ui

import android.animation.StateListAnimator
import android.animation.TimeAnimator
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.kfaraj.support.recyclerview.sample.ui.theme.AppTheme

/**
 * Demonstrates how to use the RecyclerView library.
 */
class MainViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    ComposeView(parent.context)
) {

    private val composeView = itemView as ComposeView

    /**
     * Binds the [item] with the view.
     */
    fun bind(item: String) {
        composeView.setContent {
            var isChecked by remember { mutableStateOf(itemView.isActivated) }
            itemView.stateListAnimator = StateListAnimator().apply {
                addState(intArrayOf(), TimeAnimator().apply {
                    setTimeListener { _, _, _ ->
                        isChecked = itemView.isActivated
                    }
                })
            }
            AppTheme {
                Main(
                    text = item,
                    isChecked,
                    { itemView.performClick() },
                    { itemView.performLongClick() }
                )
            }
        }
    }

}
