package com.rizkifauzi.excitingnews.model

data class Hero(
    val id: Long,
    val typeNews: String,
    val titleNews: String,
    val releaseDate: String,
    val readTime: String,
    val photoUrl: Int,
    val photoDetail: Int,
    val publisherImage: Int,
    val publisherName: String,
    val publisherType: String,
    val newsDesc : String,
    var isBookmarked: Boolean
)