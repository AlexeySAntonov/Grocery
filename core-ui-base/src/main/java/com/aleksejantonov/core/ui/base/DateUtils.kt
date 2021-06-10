package com.aleksejantonov.core.ui.base

import android.content.Context
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*
import kotlin.math.max

object DateUtils {

  private val EEEE_FORMAT = DateTimeFormatter.ofPattern("EEEE", Locale.US)
  private val MMM_D_FORMAT = DateTimeFormatter.ofPattern("MMM d", Locale.US)
  private val MMM_D_YYYY_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US)
  private val H_MM_AA_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.US)

  fun longFormat(date: LocalDateTime, context: Context): String {
    val diffSec = ChronoUnit.SECONDS.between(date, LocalDateTime.now())
    return when {
      diffSec < Duration.ofMinutes(1).seconds -> context.getString(R.string.long_date_format_now)
      diffSec < Duration.ofMinutes(3).seconds -> context.getString(R.string.long_date_format_few_minutes)
      diffSec < Duration.ofHours(1).seconds -> {
        val minsAgo = max(1, (diffSec / Duration.ofMinutes(1).seconds).toInt())
        context.getString(R.string.long_date_format_minutes_ago, minsAgo)
      }
      diffSec < Duration.ofHours(10).seconds -> {
        val hoursAgo = max(1, (diffSec / Duration.ofHours(1).seconds).toInt())
        context.resources.getQuantityString(R.plurals.long_date_format_hours_plural, hoursAgo, hoursAgo)
      }
      date >= LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT) -> {
        context.getString(R.string.long_date_format_today, date.format(H_MM_AA_FORMAT))
      }
      date >= LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).minusDays(1L) -> {
        context.getString(R.string.long_date_format_yesterday, date.format(H_MM_AA_FORMAT))
      }
      date >= LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).minusDays(6L) -> {
        val dayOfWeekArg = date.format(EEEE_FORMAT)
        val timeArg = date.format(H_MM_AA_FORMAT)
        context.getString(R.string.long_date_format_weekday_time, dayOfWeekArg, timeArg)
      }
      date >= LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).minusDays(364L) -> {
        val dayOfMonthArg = date.format(MMM_D_FORMAT)
        val timeArg = date.format(H_MM_AA_FORMAT)
        context.getString(R.string.long_date_format_month_day_time, dayOfMonthArg, timeArg)
      }
      else -> date.format(MMM_D_YYYY_FORMAT)
    }
  }

}
