package com.example.calcnumeric.presenter.fragment.home

import android.view.View

object OnClickWrapper {

    var lastPressedId: Int? = null
        private set

    fun View.setOnClickListenerWrapped(onClick: (View) -> Unit) {
        this.setOnClickListener {
            onClick(it).also {
                this@OnClickWrapper.lastPressedId = this.id
            }
        }
    }

    fun View.setOnLongClickListenerWrapped(onClick: (View) -> Boolean) {
        this.setOnLongClickListener {
            onClick(it).also {
                this@OnClickWrapper.lastPressedId = this.id
            }
        }
    }
}