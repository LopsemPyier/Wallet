package com.eatthemoon.wallet.core.db

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun stringToDate(str: String?) = str?.let {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)
        }

        @TypeConverter
        @JvmStatic
        fun dateToString(dateTime: LocalDateTime?) = dateTime?.format(DateTimeFormatter.ISO_DATE_TIME)
    }
}