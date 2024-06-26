package com.samzuhalsetiawan.habbits.models

class Pertanyaan(
    val question: String,
    val types: Habit.Types,
    val answers: List<OpsiJawaban>,
    var choise: OpsiJawaban? = null
)