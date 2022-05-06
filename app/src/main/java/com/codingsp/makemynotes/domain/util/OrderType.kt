package com.codingsp.makemynotes.domain.util

sealed class OrderType{
    object Ascending:OrderType()
    object Descending:OrderType()
}
