package com.rizkifauzi.excitingnews.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkifauzi.excitingnews.data.NewsRepo
import com.rizkifauzi.excitingnews.model.ListNews
import com.rizkifauzi.excitingnews.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookmarkViewModel (
    private val repository: NewsRepo
) : ViewModel(){
    private val _uiState: MutableStateFlow<UiState<List<ListNews>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ListNews>>>
        get() = _uiState

    fun getBookmarkedNews() {
        viewModelScope.launch {
            repository.getBookmarkedNews()
                .catch { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "An error occurred")
                }
                .collect { listNews ->
                    _uiState.value = UiState.Success(listNews)
                }
        }
    }
}