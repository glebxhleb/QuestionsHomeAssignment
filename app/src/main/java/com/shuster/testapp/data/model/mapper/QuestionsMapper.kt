package com.shuster.testapp.data.model.mapper

import com.shuster.testapp.data.model.QuestionResponse
import com.shuster.testapp.domain.model.Question
import javax.inject.Inject

class QuestionsMapper @Inject constructor() {

    fun map(response: QuestionResponse) =
        Question(
            id = response.id,
            text = response.text,
            options = response.options,
            required = response.required,
            manualInputEnable = response.manualInputEnable,
        )
}