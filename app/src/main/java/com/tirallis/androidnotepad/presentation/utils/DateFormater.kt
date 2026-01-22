package com.tirallis.androidnotepad.presentation.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

object DateFormater {

    private val millisInMinutes = TimeUnit.MINUTES.toMillis(1)
    private val millisInHour = TimeUnit.HOURS.toMillis(1)
    private val millisInDay = TimeUnit.DAYS.toMillis(1)
    private val formatter = SimpleDateFormat.getDateInstance(DateFormat.SHORT)


    fun formatDateToString(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < millisInMinutes -> "Только что"
            diff < millisInHour -> {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
                "$minutes мин. назад"
            }
            diff < millisInDay -> {
                val hours = TimeUnit.MILLISECONDS.toHours(diff)
                "$hours ч. назад"
            }

            else -> {
                formatter.format(timestamp)
            }
        }
    }

}