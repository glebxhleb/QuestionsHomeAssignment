package com.shuster.testapp.presentation.questions_list.model

import android.text.SpannableString
import com.shuster.testapp.common.Text

data class QuestionUiModel(
    val id: Long,
    val text: SpannableString,
    val options: List<String>,
    val required: Boolean,
    val manualInputEnable: Boolean,
    val type: QuestionType,
    val error: Text? = null,
)

enum class QuestionType(val code: Int) {

    OPTIONS(0),
    INPUT(1);
}