package com.eatthemoon.wallet.core.viewBinding

import android.content.Context
import com.eatthemoon.wallet.R
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.temporal.ChronoField
import java.util.*


fun LocalDateTime.toSmallString(context: Context): String {
    return when (dayOfYear) {
        LocalDateTime.now().dayOfYear -> context.getString(R.string.today).toLowerCase(Locale.getDefault())
        LocalDateTime.now().minusDays(1).dayOfYear -> context.getString(R.string.yesterday).toLowerCase(
            Locale.getDefault())
        else -> {
            val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(Locale.getDefault())
            format(dtFormatter).toLowerCase(Locale.getDefault())
        }
    }
}

fun LocalDateTime.toBigString(context: Context): String {
    return when (dayOfYear) {
        LocalDateTime.now().dayOfYear -> context.getString(
            R.string.big_format_date_today, get(
            ChronoField.HOUR_OF_DAY), get(ChronoField.MINUTE_OF_HOUR))
        LocalDateTime.now().minusDays(1).dayOfYear -> context.getString(
            R.string.big_format_date_yesterday, get(
            ChronoField.HOUR_OF_DAY), get(ChronoField.MINUTE_OF_HOUR))
        else -> {
            context.getString(
                R.string.big_format_date, get(ChronoField.DAY_OF_MONTH), format(
                DateTimeFormatter.ofPattern("MMMM")).toLowerCase(Locale.getDefault()),
                get(ChronoField.YEAR), get(ChronoField.HOUR_OF_DAY), get(ChronoField.MINUTE_OF_HOUR))
        }
    }
}