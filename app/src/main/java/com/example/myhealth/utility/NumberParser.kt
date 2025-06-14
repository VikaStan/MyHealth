package com.example.myhealth.utility
fun String.parseInt(): Int{
    var str=this
    if (contains('.')){
        str=str.filter { it != '.' }
    }
    if (contains(',')){
        str=str.filter { it != ',' }
    }
    return str.toInt()
}

fun String.parseFloat(): Float {
    val wholePart: Int
    val fractionalPart: Int
    if (contains('.')) {
        wholePart = split('.')[0].toInt()
        fractionalPart = split('.')[1].toInt()
        return wholePart.toFloat() + fractionalPart / 10f
    } else if (contains(',')) {
        wholePart = split(',')[0].toInt()
        fractionalPart = split(',')[1].toInt()
        return wholePart.toFloat() + fractionalPart / 10f
    } else {
        return toFloat()
    }
}
