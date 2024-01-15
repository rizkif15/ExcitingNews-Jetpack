package com.rizkifauzi.excitingnews.data

import com.rizkifauzi.excitingnews.model.HeroesData
import com.rizkifauzi.excitingnews.model.ListNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class NewsRepo {
    private val news = mutableListOf<ListNews>()

    init {
        if (news.isEmpty()) {
            HeroesData.heroes.forEach {
                news.add(ListNews(it))
            }
        }
    }

    fun getAllNews(): Flow<List<ListNews>>{
        return flowOf(news)
    }

    fun getClickedNewsById(rewardId: Long): ListNews {
        return news.first {
            it.newsIdentity.id == rewardId
        }
    }

    fun setBookmarkedNews(newsId: Long, isBookmarked: Boolean) {
        val news = news.find { it.newsIdentity.id == newsId }
        news?.newsIdentity?.isBookmarked = isBookmarked
    }

    fun toggleBookmarkStatus(newsId: Long) {
        val news = news.find { it.newsIdentity.id == newsId }
        news?.let {
            val currentStatus = it.newsIdentity.isBookmarked
            setBookmarkedNews(newsId, !currentStatus)
        }
    }


    fun getBookmarkedNews(): Flow<List<ListNews>>{
        return getAllNews()
            .map {listNews ->
                listNews.filter {listNews ->
                    listNews.newsIdentity.isBookmarked
                }

            }
    }

    fun searchTitleNews(query: String): Flow<List<ListNews>>{
        return getAllNews()
            .map { listNews ->
                listNews.filter { listNews ->
                    listNews.newsIdentity.titleNews.contains(query, ignoreCase = true)
                }
            }
    }

    companion object{
        @Volatile
        private var instance: NewsRepo? = null
        fun getInstance(): NewsRepo =
            instance ?: synchronized(this){
                NewsRepo().apply {
                    instance = this
                }
            }
    }
}
