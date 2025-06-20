package com.sgupta.composite.model

import com.sgupta.domain.model.ReelVideo

// Data Models
data class ReelVideoDataModel(
    val id: String,
    val title: String,
    val description: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val duration: Long,
    val author: String,
    val likes: Int,
    val comments: Int,
    val shares: Int,
    val isLiked: Boolean = false,
    val category: String = "News"
)

fun ReelVideoDataModel.toReelVideoDomainModel() = ReelVideo(
    id = this.id,
    title,
    description,
    videoUrl,
    thumbnailUrl,
    duration,
    author,
    likes,
    comments,
    shares,
    isLiked,
    category
)