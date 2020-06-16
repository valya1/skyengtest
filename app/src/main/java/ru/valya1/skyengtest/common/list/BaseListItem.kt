package ru.valya1.skyengtest.common.list

import androidx.annotation.LayoutRes

interface BaseListItem {
    @get:LayoutRes
    val layoutId: Int
}