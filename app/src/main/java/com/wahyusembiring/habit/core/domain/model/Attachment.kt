package com.wahyusembiring.habit.core.domain.model

import android.content.Context
import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wahyusembiring.habit.core.domain.util.GetterUtil

@Entity(tableName = "attachment")
open class Attachment(
   @PrimaryKey(autoGenerate = false)
   val uri: Uri,
   val type: AttachmentType
)

class Link(uri: Uri) : Attachment(uri, AttachmentType.LINK)

class Photo(
   uri: Uri,
   val name: String,
   val size: Long,
   val mimeType: String
) : Attachment(uri, AttachmentType.PHOTO)

class File(
   uri: Uri,
   val name: String,
   val size: Long?
) : Attachment(uri, AttachmentType.FILE)

enum class AttachmentType {
   LINK, PHOTO, FILE
}