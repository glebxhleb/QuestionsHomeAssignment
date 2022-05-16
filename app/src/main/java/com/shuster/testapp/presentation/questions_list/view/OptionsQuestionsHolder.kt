package com.shuster.testapp.presentation.questions_list.view

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.widget.doAfterTextChanged
import com.shuster.testapp.common.Text
import com.shuster.testapp.databinding.ItemOptionsQuestionBinding
import com.shuster.testapp.presentation.questions_list.model.AnswerUiModel
import com.shuster.testapp.presentation.questions_list.model.QuestionUiModel

class OptionsQuestionHolder(
    private val binding: ItemOptionsQuestionBinding,
    private val onAnswerChanged: (AnswerUiModel) -> Unit
) : QuestionHolder(binding.root) {

    override fun bind(item: QuestionUiModel) {
        with(binding) {
            tvTitle.text = item.text
            if (item.manualInputEnable) {
                llManualInput.visibility = View.VISIBLE
            } else {
                llManualInput.visibility = View.GONE
            }
            rgOptions.setOnCheckedChangeListener { _, checkedId ->
                rgOptions.findViewById<RadioButton>(checkedId)?.let {
                    if (it.isChecked) {
                        onAnswerChanged(AnswerUiModel(item.id, it.text.toString()))
                        rbManualInput.isChecked = false
                    }
                }
            }
            rbManualInput.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    rgOptions.clearCheck()
                    onAnswerChanged(AnswerUiModel(item.id, etManualInput.text.toString()))
                }
                etManualInput.isEnabled = isChecked
                etManualInput.isFocusableInTouchMode = isChecked
            }
            etManualInput.doAfterTextChanged {
                onAnswerChanged(AnswerUiModel(item.id, etManualInput.text.toString()))
            }
        }
        updateError(item.error)
        createOptions(item)
    }

    override fun update(payload: Bundle) {
        val errorText = payload.getSerializable(QuestionsAdapter.ARG_ERROR) as? Text
        updateError(errorText)
    }

    private fun createOptions(item: QuestionUiModel) {
        item.options.reversed().forEach { option ->
            val radioButton = RadioButton(binding.root.context)
            radioButton.text = option
            binding.rgOptions.addView(radioButton, 0)
        }
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