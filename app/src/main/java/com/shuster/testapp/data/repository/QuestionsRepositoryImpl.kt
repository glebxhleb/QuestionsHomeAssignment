package com.shuster.testapp.data.repository

import com.shuster.testapp.common.runCatchingResult
import com.shuster.testapp.data.model.mapper.QuestionsMapper
import com.shuster.testapp.data.network.ApiService
import com.shuster.testapp.domain.repository.QuestionsRepository
import com.shuster.testapp.domain.model.Question
import com.shuster.testapp.common.Result
import com.shuster.testapp.data.model.Answer
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: QuestionsMapper,
) : QuestionsRepository {

    override suspend fun fetchQuestions(): Result<List<Question>> =
        runCatchingResult {
            apiService.fetchQuestions().map { mapper.map(it) }
        }

    override suspend fun saveAnswers(answers: List<Answer>): Result<Unit> =
        runCatchingResult {
            apiService.saveAnswers(answers)
        }
}