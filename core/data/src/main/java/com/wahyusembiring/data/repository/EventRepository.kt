package com.wahyusembiring.data.repository

import com.wahyusembiring.data.model.Event
import com.wahyusembiring.data.model.Subject
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getEvents(): Flow<Map<Event, Subject?>>

}