package com.wahyusembiring.onboarding

sealed class OnBoardingScreenUIEvent {
    data object OnCompleted : OnBoardingScreenUIEvent()
}