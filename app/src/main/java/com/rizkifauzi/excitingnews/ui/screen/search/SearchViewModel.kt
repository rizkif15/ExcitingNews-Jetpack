package com.rizkifauzi.excitingnews.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizkifauzi.excitingnews.data.NewsRepo
import com.rizkifauzi.excitingnews.model.ListNews
import com.rizkifauzi.excitingnews.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel (private val repository: NewsRepo) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<ListNews>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ListNews>>>
        get() = _uiState

    fun searchNews(query: String){
        viewModelScope.launch {
            repository.searchTitleNews(query)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { listNews ->
                    _uiState.value = UiState.Success(listNews)
                }
        }
    }
}