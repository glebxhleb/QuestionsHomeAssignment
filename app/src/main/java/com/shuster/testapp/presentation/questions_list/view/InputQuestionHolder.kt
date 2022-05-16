package com.shuster.testapp.presentation.questions_list.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.shuster.testapp.common.Text
import com.shuster.testapp.databinding.ItemInputQuestionBinding
import com.shuster.testapp.presentation.questions_list.model.AnswerUiModel
import com.shuster.testapp.presentation.questions_list.model.QuestionUiModel
import com.shuster.testapp.presentation.questions_list.view.QuestionsAdapter.Companion.ARG_ERROR

class InputQuestionHolder(
    private val binding: ItemInputQuestionBinding,
    private val onAnswerChanged: (AnswerUiModel) -> Unit
) : QuestionHolder(binding.root) {

    override fun bind(item: QuestionUiModel) {
        with(binding) {
            tvTitle.text = item.text
            etAnswer.doAfterTextChanged {
                onAnswerChanged(AnswerUiModel(item.id, it.toString()))
            }
            updateError(item.error)
        }
    }

    override fun update(payload: Bundle) {
        val errorText = payload.getSerializable(ARG_ERROR) as? Text
        updateError(errorText)
    }

    private fun updateError(errorText: Text?) {
        with(binding) {
            if (errorText == null) {
                tvError.visibility = View.GONE
            } else {
                tvError.text = errorText.getString(binding.root.context)
                tvError.visibility = View.VISIBLE
            }
        }
    }
}