package com.shuster.testapp.domain.repository

import com.shuster.testapp.domain.model.Question
import com.shuster.testapp.common.Result
import com.shuster.testapp.data.model.Answer

interface QuestionsRepository {

    suspend fun fetchQuestions(): Result<List<Question>>

    suspend fun saveAnswers(answers: List<Answer>): Result<Unit>
}