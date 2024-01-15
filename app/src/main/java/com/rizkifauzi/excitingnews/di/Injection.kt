package com.rizkifauzi.excitingnews.di

import com.rizkifauzi.excitingnews.data.NewsRepo

object Injection {
    fun provideRepository(): NewsRepo {
        return NewsRepo.getInstance()
    }
}