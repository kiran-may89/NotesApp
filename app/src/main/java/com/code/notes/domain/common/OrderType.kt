package com.code.notes.domain.common

sealed class OrderType{
    object Ascending:OrderType()
    object  Descending:OrderType()
}
