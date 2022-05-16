package com.shuster.testapp.presentation.questions_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuster.testapp.R
import com.shuster.testapp.common.Failure
import com.shuster.testapp.common.Result
import com.shuster.testapp.common.Success
import com.shuster.testapp.common.Text
import com.shuster.testapp.data.model.Answer
import com.shuster.testapp.domain.model.Question
import com.shuster.testapp.domain.repository.QuestionsRepository
import com.shuster.testapp.presentation.questions_list.model.AnswerUiModel
import com.shuster.testapp.presentation.questions_list.model.mapper.QuestionUiMapper
import kotlinx.coroutines.flow.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsListViewModel @Inject constructor(
    private val repository: QuestionsRepository,
    private val mapper: QuestionUiMapper,
) : ViewModel() {

    private val answers = mutableMapOf<Long, String>()

    init {
        fetchQuestions()
    }

    private val _questionsResult: MutableStateFlow<QuestionListState> =
        MutableStateFlow(QuestionListState.Loading)

    private val _messages = MutableSharedFlow<Text>()

    val questionsResult: StateFlow<QuestionListState> = _questionsResult.asStateFlow()

    val messages = _messages.asSharedFlow()

    fun setAnswer(answer: AnswerUiModel) {
        answers[answer.id] = answer.text
        clearError(answer)
    }

    fun submit() {
        checkHasErrors {
            viewModelScope.launch {
                handleSaveResult(repository.saveAnswers(answers.map { entry ->
                    Answer(entry.key, entry.value)
                }))
            }
        }
    }

    private suspend fun handleSaveResult(saveAnswers: Result<Unit>) {
        when (saveAnswers) {
            is Success -> _messages.emit(Text.Resource(R.string.saved))
            is Failure -> _messages.emit(Text.Resource(R.string.save_error))
        }
    }

    private fun clearError(answer: AnswerUiModel) {
        val newData = (questionsResult.value as? QuestionListState.Success)?.data?.map { question ->
            if (question.id == answer.id && question.required && answer.text.isNotBlank()) {
                question.copy(error = null)
            } else {
                question
            }
        } ?: error("Illegal state")
        _questionsResult.tryEmit(QuestionListState.Success(newData))
    }

    private fun checkHasErrors(doIfNoErrors: () -> Unit) {
        var hasErrors = false
        val newData = (questionsResult.value as? QuestionListState.Success)?.data?.map { question ->
            if (question.required && answers[question.id].isNullOrBlank()) {
                hasErrors = true
                question.copy(error = Text.Resource(R.string.required_field_error))
            } else if (question.error != null) {
                question.copy(error = null)
            } else {
                question
            }
        } ?: error("Illegal state")
        _questionsResult.tryEmit(QuestionListState.Success(newData))
        if (hasErrors) {
            viewModelScope.launch { _messages.emit(Text.Resource(R.string.fill_required_fields_error)) }
        } else {
            doIfNoErrors()
        }
    }

    private fun fetchQuestions() {
        viewModelScope.launch {
            handleResult(repository.fetchQuestions())
        }
    }

    private fun handleResult(result: Result<List<Question>>) =
        when (result) {
            is Success -> _questionsResult.tryEmit(QuestionListState.Success(result.data.map {
                mapper.map(it)
            }))
            is Failure -> _questionsResult.tryEmit(
                QuestionListState.Error(
                    result.exception.localizedMessage ?: "error"
                )
            )
        }
}