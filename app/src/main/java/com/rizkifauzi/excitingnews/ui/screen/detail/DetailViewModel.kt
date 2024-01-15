package com.rizkifauzi.excitingnews.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkifauzi.excitingnews.data.NewsRepo
import com.rizkifauzi.excitingnews.model.ListNews
import com.rizkifauzi.excitingnews.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel (
    private val repository : NewsRepo
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ListNews>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ListNews>>
        get() = _uiState

    fun getNewsById(newsId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getClickedNewsById(newsId))
        }
    }

    fun toggleBookmarkStatus(newsId: Long) {
        viewModelScope.launch {
            repository.toggleBookmarkStatus(newsId)
        }
    }
}
