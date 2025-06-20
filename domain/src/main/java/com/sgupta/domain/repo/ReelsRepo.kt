package com.sgupta.domain.repo

import androidx.paging.PagingData
import com.sgupta.domain.model.ReelVideo
import kotlinx.coroutines.flow.Flow

interface ReelsRepo {
//    suspend fun getReelsPage(page: Int, pageSize: Int): Flow<PagingData<ReelVideo>>
    fun getReelsPage(): Flow<PagingData<ReelVideo>>
    suspend fun likeVideo(videoId: String): Boolean
    suspend fun shareVideo(videoId: String): Boolean
}