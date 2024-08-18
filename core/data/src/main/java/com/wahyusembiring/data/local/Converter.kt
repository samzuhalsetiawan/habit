package com.wahyusembiring.data.local

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter
import com.wahyusembiring.data.model.AttachmentType
import com.wahyusembiring.data.model.OfficeHour
import com.wahyusembiring.data.model.Time
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

class Converter {

   @TypeConverter
   fun dateToLong(date: Date): Long {
      return date.time
   }

   @TypeConverter
   fun longToDate(long: Long): Date {
      return Date(long)
   }

   @TypeConverter
   fun timeToString(time: Time): String {
      return "${time.hour}:${time.minute}"
   }

   @TypeConverter
   fun stringToTime(string: String): Time {
      val (hour, minute) = string.split(":")
      return Time(hour.toInt(), minute.toInt())
   }

   @TypeConverter
   fun colorToInt(color: Color): Int {
      return color.toArgb()
   }

   @TypeConverter
   fun intToColor(int: Int): Color {
      return Color(int)
   }

   @TypeConverter
   fun listOfStringToJsonString(listOfString: List<String>): String {
      return Json.encodeToString(listOfString)
   }

   @TypeConverter
   fun jsonStringToListOfString(jsonString: String): List<String> {
      return Json.decodeFromString(jsonString)
   }

   @TypeConverter
   fun listOfOfficeHourToJsonString(listOfOfficeHour: List<OfficeHour>): String {
      return Json.encodeToString(listOfOfficeHour)
   }

   @TypeConverter
   fun jsonStringToListOfOfficeHour(jsonString: String): List<OfficeHour> {
      return Json.decodeFromString(jsonString)
   }

   @TypeConverter
   fun attachmentTypeToString(type: AttachmentType): String {
      return type.name
   }

   @TypeConverter
   fun stringToAttachmentType(string: String): AttachmentType {
      return AttachmentType.valueOf(string)
   }

   @TypeConverter
   fun uriToString(uri: Uri): String {
      return uri.toString()
   }

   @TypeConverter
   fun stringToUri(string: String): Uri {
      return Uri.parse(string)
   }

}