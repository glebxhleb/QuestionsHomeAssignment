package com.shuster.testapp.presentation.questions_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shuster.testapp.databinding.ItemInputQuestionBinding
import com.shuster.testapp.databinding.ItemOptionsQuestionBinding
import com.shuster.testapp.presentation.questions_list.model.AnswerUiModel
import com.shuster.testapp.presentation.questions_list.model.QuestionType
import com.shuster.testapp.presentation.questions_list.model.QuestionUiModel
import com.shuster.testapp.presentation.questions_list.view.QuestionsAdapter.Companion.ARG_ERROR

class QuestionsAdapter(
    private val onAnswerChanged: (AnswerUiModel) -> Unit
) : ListAdapter<QuestionUiModel, QuestionHolder>(QuestionDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            QuestionType.OPTIONS.code -> {
                val binding = ItemOptionsQuestionBinding.inflate(layoutInflater, parent, false)
                OptionsQuestionHolder(binding, onAnswerChanged)
            }
            QuestionType.INPUT.code -> {
                val binding = ItemInputQuestionBinding.inflate(layoutInflater, parent, false)
                InputQuestionHolder(binding, onAnswerChanged)
            }
            else -> error("incorrect view type")
        }
    }

    override fun onBindViewHolder(holder: QuestionHolder, position: Int, payload: List<Any?>) {
        val item = getItem(position)
        if (payload.isEmpty() || payload[0] !is Bundle) {
            holder.bind(item)
        } else {
            val bundle = payload[0] as Bundle
            holder.update(bundle)
        }
    }

    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }

    override fun getItemViewType(position: Int): Int =
        getItem(position).type.code

    companion object {
        const val ARG_ERROR = "arg_error"
    }
}

abstract class QuestionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: QuestionUiModel)

    abstract fun update(payload: Bundle)
}

private class QuestionDiffUtilCallback : DiffUtil.ItemCallback<QuestionUiModel>() {

    override fun areItemsTheSame(oldItem: QuestionUiModel, newItem: QuestionUiModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: QuestionUiModel, newItem: QuestionUiModel) =
        oldItem == newItem

    override fun getChangePayload(oldItem: QuestionUiModel, newItem: QuestionUiModel): Any? =
        when {
            oldItem.id != newItem.id -> super.getChangePayload(oldItem, newItem)
            oldItem.error != newItem.error -> bundleOf(ARG_ERROR to newItem.error)
            else -> super.getChangePayload(oldItem, newItem)
        }
}