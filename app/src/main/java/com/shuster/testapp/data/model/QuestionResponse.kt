package com.shuster.testapp.data.model

data class QuestionResponse(
    val id: Long,
    val text: String,
    val options: List<String>?,
    val required: Boolean,
    val manualInputEnable: Boolean,
)