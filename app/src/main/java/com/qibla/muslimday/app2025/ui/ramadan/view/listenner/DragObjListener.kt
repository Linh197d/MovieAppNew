package com.qibla.muslimday.app2025.ui.ramadan.view.listenner

import androidx.recyclerview.widget.RecyclerView

open interface DragObjListener {
    fun requestDrag(viewHolder: RecyclerView.ViewHolder?)
}