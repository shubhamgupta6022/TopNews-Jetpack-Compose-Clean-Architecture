package com.sgupta.composite.repoimpl.reels

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sgupta.composite.model.ReelVideoDataModel
import com.sgupta.composite.model.toReelVideoDomainModel
import com.sgupta.domain.model.ReelVideo
import javax.inject.Inject

// Paging Source
class ReelsPagingSource @Inject constructor() : PagingSource<Int, ReelVideo>() {

    private val sampleNewsVideos = listOf(
        ReelVideoDataModel(
            id = "1",
            title = "Breaking: Climate Summit Results",
            description = "World leaders reach historic agreement on climate action at COP28",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
            duration = 654000,
            author = "Climate News",
            likes = 15420,
            comments = 892,
            shares = 234,
            category = "Environment"
        ),
        ReelVideoDataModel(
            id = "2",
            title = "Tech Innovation Showcase",
            description = "Latest breakthroughs in AI and machine learning from major tech companies",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
            duration = 596000,
            author = "Tech Today",
            likes = 28750,
            comments = 1250,
            shares = 445,
            category = "Technology"
        ),
        ReelVideoDataModel(
            id = "3",
            title = "Global Economic Update",
            description = "Market analysis and economic forecasts for the upcoming quarter",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
            duration = 15000,
            author = "Economic Weekly",
            likes = 8945,
            comments = 567,
            shares = 123,
            category = "Economics"
        ),
        ReelVideoDataModel(
            id = "4",
            title = "Sports Championship Highlights",
            description = "Best moments from the international championship finals",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg",
            duration = 15000,
            author = "Sports Central",
            likes = 42350,
            comments = 2890,
            shares = 678,
            category = "Sports"
        ),
        ReelVideoDataModel(
            id = "5",
            title = "Health & Wellness Trends",
            description = "Latest research on nutrition and mental health awareness",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg",
            duration = 60000,
            author = "Health News",
            likes = 18670,
            comments = 1045,
            shares = 287,
            category = "Health"
        ),
        ReelVideoDataModel(
            id = "6",
            title = "Space Exploration Update",
            description = "NASA's latest mission to Mars and upcoming space ventures",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg",
            duration = 15000,
            author = "Space Today",
            likes = 35890,
            comments = 1876,
            shares = 534,
            category = "Science"
        ),
        ReelVideoDataModel(
            id = "7",
            title = "Political News Briefing",
            description = "Weekly political roundup and policy analysis",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg",
            duration = 15000,
            author = "Political Weekly",
            likes = 12450,
            comments = 2340,
            shares = 445,
            category = "Politics"
        ),
        ReelVideoDataModel(
            id = "8",
            title = "Cultural Festival Coverage",
            description = "Celebrating diversity through international cultural events",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",
            duration = 888000,
            author = "Culture Hub",
            likes = 25670,
            comments = 1234,
            shares = 389,
            category = "Culture"
        ),
        ReelVideoDataModel(
            id = "9",
            title = "Wildlife Conservation",
            description = "Efforts to protect endangered species and their habitats",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/SubaruOutbackOnStreetAndDirt.jpg",
            duration = 15000,
            author = "Nature Watch",
            likes = 31250,
            comments = 987,
            shares = 456,
            category = "Nature"
        ),
        ReelVideoDataModel(
            id = "10",
            title = "Education Revolution",
            description = "How technology is transforming modern education systems",
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            thumbnailUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg",
            duration = 734000,
            author = "EduTech News",
            likes = 19890,
            comments = 1567,
            shares = 234,
            category = "Education"
        ),
        ReelVideoDataModel(
            id = "11",
            title = "Nature in 4K (HLS)",
            description = "Relaxing scenes of nature and wildlife in ultra HD",
            videoUrl = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8",
            thumbnailUrl = "https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217",
            duration = 888000,
            author = "Nature Vibes",
            likes = 34890,
            comments = 1024,
            shares = 642,
            category = "Nature"
        ),
        ReelVideoDataModel(
            id = "12",
            title = "Big Buck Bunny (HLS)",
            description = "The famous open-source animated short",
            videoUrl = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8",
            thumbnailUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/75/Big_buck_bunny_poster_big.jpg/800px-Big_buck_bunny_poster_big.jpg",
            duration = 596000,
            author = "Open Animations",
            likes = 26780,
            comments = 1356,
            shares = 487,
            category = "Animation"
        ),
        ReelVideoDataModel(
            id = "13",
            title = "Live Stream Simulation (HLS)",
            description = "Simulated live HLS stream for testing purposes",
            videoUrl = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
            thumbnailUrl = "https://mango.blender.org/wp-content/uploads/2013/05/ToS_poster_comp_final_web.jpg",
            duration = 734000,
            author = "Stream Sim",
            likes = 19890,
            comments = 1357,
            shares = 354,
            category = "Live"
        )
    )

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReelVideo> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val startIndex = page * pageSize
            val endIndex = minOf(startIndex + pageSize, sampleNewsVideos.size)

            val videos = if (startIndex < sampleNewsVideos.size) {
                // Cycle through videos for infinite scroll
                val videos = mutableListOf<ReelVideoDataModel>()
                repeat(pageSize) { i ->
                    val index = (startIndex + i) % sampleNewsVideos.size
                    videos.add(sampleNewsVideos[index].copy(id = "${page}_${i}_${sampleNewsVideos[index].id}"))
                }
                videos.map { it.toReelVideoDomainModel() }
            } else {
                emptyList()
            }
            
            LoadResult.Page(
                data = videos,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (videos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, ReelVideo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}