package com.shuster.testapp.presentation.questions_list.model.mapper

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.shuster.testapp.domain.model.Question
import com.shuster.testapp.presentation.questions_list.model.QuestionType
import com.shuster.testapp.presentation.questions_list.model.QuestionUiModel
import javax.inject.Inject

class QuestionUiMapper @Inject constructor() {

    fun map(question: Question): QuestionUiModel =
        QuestionUiModel(
            id = question.id,
            text = prepareTitle(question.text, question.required),
            options = question.options ?: emptyList(),
            required = question.required,
            manualInputEnable = question.manualInputEnable,
            type = if (question.options.isNullOrEmpty()) QuestionType.INPUT else QuestionType.OPTIONS
        )


    private fun prepareTitle(text: String, required: Boolean): SpannableString =
        if (required) {
            val newTextSpannable = SpannableString("$text *")
            newTextSpannable.setSpan(
                ForegroundColorSpan(Color.RED),
                newTextSpannable.length - 1,
                newTextSpannable.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            newTextSpannable
        } else {
            SpannableString(text)
        }
}