package com.rizkifauzi.excitingnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rizkifauzi.excitingnews.data.NewsRepo
import com.rizkifauzi.excitingnews.ui.screen.bookmark.BookmarkViewModel
import com.rizkifauzi.excitingnews.ui.screen.detail.DetailViewModel
import com.rizkifauzi.excitingnews.ui.screen.home.HomeViewModel
import com.rizkifauzi.excitingnews.ui.screen.search.SearchViewModel


class ViewModelFactory(private val repository: NewsRepo) :
    ViewModelProvider.NewInstanceFactory(){

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
                return HomeViewModel(repository) as T
            }
            else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(repository) as T
            }
            else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
                return BookmarkViewModel(repository) as T
            }
            else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
}