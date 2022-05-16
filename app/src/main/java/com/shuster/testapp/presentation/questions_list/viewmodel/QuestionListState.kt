package com.shuster.testapp.presentation.questions_list.viewmodel

import com.shuster.testapp.presentation.questions_list.model.QuestionUiModel

sealed class QuestionListState {

    data class Success(val data: List<QuestionUiModel>) : QuestionListState()

    data class Error(val message: String) : QuestionListState()

    object Loading : QuestionListState()
}