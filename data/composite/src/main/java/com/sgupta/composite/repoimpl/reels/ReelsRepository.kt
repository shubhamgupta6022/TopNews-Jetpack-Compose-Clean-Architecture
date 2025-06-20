package com.sgupta.composite.repoimpl.reels

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sgupta.composite.model.ReelVideoDataModel
import com.sgupta.composite.model.toReelVideoDomainModel
import com.sgupta.domain.model.ReelVideo
import com.sgupta.domain.repo.ReelsRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReelsRepositoryImpl @Inject constructor(
    private val source: ReelsPagingSource
): ReelsRepo {

//    override suspend fun getReelsPage(page: Int, pageSize: Int): Flow<PagingData<ReelVideo>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5,
//                prefetchDistance = 2,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { source }
//        ).flow
////        // Simulate network delay
////        delay(500)
////
////        val startIndex = page * pageSize
////        val endIndex = minOf(startIndex + pageSize, sampleNewsVideos.size)
////
////        return if (startIndex < sampleNewsVideos.size) {
////            // Cycle through videos for infinite scroll
////            val videos = mutableListOf<ReelVideoDataModel>()
////            repeat(pageSize) { i ->
////                val index = (startIndex + i) % sampleNewsVideos.size
////                videos.add(sampleNewsVideos[index].copy(id = "${page}_${i}_${sampleNewsVideos[index].id}"))
////            }
////            videos.map { it.toReelVideoDomainModel() }
////        } else {
////            emptyList()
////        }
//    }

    override fun getReelsPage(): Flow<PagingData<ReelVideo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { source }
        ).flow
    }

    override suspend fun likeVideo(videoId: String): Boolean {
        delay(200)
        return true
    }

    override suspend fun shareVideo(videoId: String): Boolean {
        delay(200)
        return true
    }
}