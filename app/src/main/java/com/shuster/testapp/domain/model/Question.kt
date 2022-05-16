package com.shuster.testapp.domain.model

data class Question(
    val id: Long,
    val text: String,
    val options: List<String>?,
    val required: Boolean,
    val manualInputEnable: Boolean,
)