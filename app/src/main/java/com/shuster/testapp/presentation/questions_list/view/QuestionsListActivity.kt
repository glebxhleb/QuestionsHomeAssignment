package com.shuster.testapp.presentation.questions_list.view

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuster.testapp.R
import com.shuster.testapp.common.MarginItemDecoration
import com.shuster.testapp.databinding.ActivityQuestionsListBinding
import com.shuster.testapp.presentation.questions_list.model.QuestionUiModel
import com.shuster.testapp.presentation.questions_list.viewmodel.QuestionListState
import com.shuster.testapp.presentation.questions_list.viewmodel.QuestionsListViewModel
import com.shuster.testapp.utils.repeatWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsListActivity : AppCompatActivity() {

    private var _binding: ActivityQuestionsListBinding? = null

    private val binding: ActivityQuestionsListBinding
        get() = _binding!!

    private val viewModel: QuestionsListViewModel by viewModels()

    private val questionsAdapter = QuestionsAdapter {
        viewModel.setAnswer(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityQuestionsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeQuestions()
        observeMessages()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView() {
        with(binding.rvQuestionsList) {
            layoutManager = LinearLayoutManager(this@QuestionsListActivity)
            val headerAdapter = HeaderAdapter()
            val footerAdapter = FooterAdapter {
                viewModel.submit()
            }
            adapter = ConcatAdapter(
                headerAdapter,
                questionsAdapter,
                footerAdapter
            )
            addItemDecoration(MarginItemDecoration(64, 48))
        }
    }

    private fun observeQuestions() {
        viewModel.questionsResult.repeatWhenStarted(this) { data ->
            processResult(data)
        }
    }

    private fun observeMessages() {
        viewModel.messages.repeatWhenStarted(this) { data ->
            Toast.makeText(this, data.getString(this), Toast.LENGTH_LONG).show()
        }
    }

    private fun processResult(data: QuestionListState) {
        when (data) {
            is QuestionListState.Error -> showError(data.message)
            is QuestionListState.Loading -> showLoading()
            is QuestionListState.Success -> showQuestions(data.data)
        }
    }

    private fun showQuestions(data: List<QuestionUiModel>) {
        questionsAdapter.submitList(data)
        with(binding) {
            rvQuestionsList.visibility = View.VISIBLE
            tvError.visibility = View.INVISIBLE
            pbLoading.visibility = View.INVISIBLE
        }
    }

    private fun showLoading() {
        with(binding) {
            rvQuestionsList.visibility = View.INVISIBLE
            tvError.visibility = View.INVISIBLE
            pbLoading.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String) {
        with(binding) {
            rvQuestionsList.visibility = View.INVISIBLE
            tvError.visibility = View.VISIBLE
            pbLoading.visibility = View.INVISIBLE
            tvError.text = message
        }
    }
}