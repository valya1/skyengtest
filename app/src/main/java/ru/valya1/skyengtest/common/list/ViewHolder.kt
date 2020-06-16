package ru.valya1.skyengtest.common.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
                                    LayoutContainer {
    override val containerView: View = itemView
}