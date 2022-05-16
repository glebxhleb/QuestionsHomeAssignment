package com.shuster.testapp.data.network

import com.shuster.testapp.data.model.Answer
import com.shuster.testapp.data.model.QuestionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("questions")
    suspend fun fetchQuestions(): List<QuestionResponse>

    @POST("answers")
    suspend fun saveAnswers(@Body() answers: List<Answer>)
}