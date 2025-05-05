package com.qibla.muslimday.app2025.ui.ramadan.view.listenner

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ObjMoveCallback constructor(private val mAdapter: ItemTouchHelperContract) :
    ItemTouchHelper.Callback() {
    open interface ItemTouchHelperContract {
        //        fun onRowClear(myViewHolder: LayerItemAdapter.MyViewHolder)
        fun onRowMoved(i: Int, i2: Int)
//        fun onRowSelected(myViewHolder: LayerItemAdapter.MyViewHolder)
    }

    public override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    public override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    public override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {}
    public override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(3, 0)
    }

    public override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        viewHolder2: RecyclerView.ViewHolder
    ): Boolean {
        mAdapter.onRowMoved(viewHolder.adapterPosition, viewHolder2.adapterPosition)
        return true
    }

    public override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, i: Int) {
//        if (i != 0 && (viewHolder is LayerItemAdapter.MyViewHolder)) {
//            mAdapter.onRowSelected(viewHolder)
//        }
        super.onSelectedChanged(viewHolder, i)
    }

    public override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
//        if (viewHolder is LayerItemAdapter.MyViewHolder) {
//            mAdapter.onRowClear(viewHolder)
//        }
    }
}