package com.samzuhalsetiawan.habbits.ui.screen.menu_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.samzuhalsetiawan.habbits.models.AnsweredQuestion
import com.samzuhalsetiawan.habbits.models.Habit
import com.samzuhalsetiawan.habbits.repository.MainRepository
import com.samzuhalsetiawan.habbits.singletons.ListPertanyaan
import com.samzuhalsetiawan.habbits.ui.screen.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MenuResultViewModel (
    private val repository: MainRepository
): ViewModel() {

    private val defaultResult = mapOf(
        Habit.Types.HEALTH to 0f,
        Habit.Types.PRODUCTIVITY to 0f,
        Habit.Types.SELF_DEVELOPMENT to 0f
    )

    private val _state = MutableStateFlow(State(results = defaultResult))
    val state: StateFlow<State> = _state

    private val listPertanyaan = ListPertanyaan.listPertanyaan

    init {
        _state.update {
            it.copy(
                results = mapOf(
                    Habit.Types.HEALTH to getPercentageOf(Habit.Types.HEALTH),
                    Habit.Types.PRODUCTIVITY to getPercentageOf(Habit.Types.PRODUCTIVITY),
                    Habit.Types.SELF_DEVELOPMENT to getPercentageOf(Habit.Types.SELF_DEVELOPMENT),
                )
            )
        }
    }

    private fun getPercentageOf(habitType: Habit.Types): Float {
        val healthTypeQuestions = listPertanyaan.filter { it.types == habitType }
        val poin = healthTypeQuestions.fold(0) { acc, pertanyaan ->
            acc + (pertanyaan.choise?.bobot ?: 0)
        }
        return poin / (healthTypeQuestions.size * 3f)
    }

    fun onConfirmButtonClicked() {
        viewModelScope.launch {
            repository.setIsNewUser(isNewUser = false)
            repository.saveAnsweredQuestion(
                listPertanyaan.map {
                    AnsweredQuestion(
                        question = it.question,
                        answer = it.choise?.jawaban ?: "",
                        score = it.choise?.bobot ?: 0
                    )
                }
            )
        }
    }

    data class State(
        val results: Map<Habit.Types, Float>
    )

}