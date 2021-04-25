package com.defconapplications.repobrowser

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

fun parseLanguages(map : Map<String,Int>) : List<String> {
    var totalBytes = 0
    val pairlist = map.toList().toMutableList()
    val list = mutableListOf<String>()
    for (lang in pairlist) totalBytes += lang.second
    pairlist.sortByDescending {
        it.second
    }
    for (lang in pairlist) {
        var p = BigDecimal((lang.second.toDouble()/totalBytes.toDouble())*100)
                .setScale(2, RoundingMode.HALF_EVEN)
        list.add("${lang.first}: ${p}%")
    }
    if (list.size == 0)
        list.add("No languages to show!")
    return list
}

fun formatCreatedDate(input: String) : String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val date = formatter.parse(input.substring(0,10))
    return DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH)
            .format(date!!)
}

fun formatUpdatedDate(input: String) : String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    val milisUpdated = formatter.parse(input.replace("Z","+0000"))!!.time
    var timeBetween = System.currentTimeMillis() - milisUpdated

    //ms -> s
    timeBetween /= 1000
    if (timeBetween < 60)
        return "${timeBetween} seconds ago"

    //s -> m
    timeBetween /= 60
    if (timeBetween < 60)
        return "${timeBetween} minutes ago"

    //m -> h
    timeBetween /= 60
    if (timeBetween < 24)
        return "${timeBetween} hours ago"

    //h -> d
    timeBetween /= 24
    if (timeBetween < 30)
        return "${timeBetween} days ago"

    //d -> mth
    timeBetween /= 30
    if (timeBetween < 12)
        return "${timeBetween} months ago"

    //mth -> y
    timeBetween /= 12
    return "${timeBetween} years ago"
}