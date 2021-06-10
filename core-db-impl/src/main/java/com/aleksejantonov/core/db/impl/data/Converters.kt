package com.aleksejantonov.core.db.impl.data

import androidx.room.TypeConverter
import com.aleksejantonov.core.model.SyncStatus
import org.threeten.bp.LocalDateTime

class Converters {

  @TypeConverter
  fun dateFromString(value: String): LocalDateTime? {
    if (value.isBlank()) return null
    return LocalDateTime.parse(value)
  }

  @TypeConverter
  fun dateToString(date: LocalDateTime?): String {
    return date?.toString() ?: ""
  }

  @TypeConverter
  fun intToSyncStatus(status: Int) = SyncStatus.values()[status]

  @TypeConverter
  fun syncStatusToInteger(status: SyncStatus) = status.ordinal

}