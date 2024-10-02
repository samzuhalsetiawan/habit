package com.wahyusembiring.ui.component.popup

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.wahyusembiring.data.model.Attachment
import com.wahyusembiring.data.model.entity.ExamCategory
import com.wahyusembiring.data.model.entity.Subject
import com.wahyusembiring.data.model.Time
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import java.util.Date

sealed class Picker<T> : PopUp {

    private val _deferred = CompletableDeferred<Result<T>>()
    val result: Deferred<Result<T>> = _deferred

    fun dismiss() {
        _deferred.complete(Result.Dismiss())
    }

    fun onPicked(value: T) {
        _deferred.complete(Result.Picked(value))
    }

    class ColorPicker : Picker<Color>()

    class DatePicker : Picker<Date>()

    class TimePicker : Picker<Time>()

    class AttachmentPicker : Picker<List<Attachment>>()

    class SubjectPicker : Picker<Subject>()

    class ExamCategoryPicker : Picker<ExamCategory>()

    sealed class Result<T> {
        data class Picked<T>(val value: T) : Result<T>()
        class Dismiss<T> : Result<T>()
    }

}