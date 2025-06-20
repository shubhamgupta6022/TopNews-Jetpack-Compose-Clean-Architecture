package com.sgupta.domain.usecase

import androidx.paging.PagingData
import com.sgupta.domain.model.ReelVideo
import com.sgupta.domain.repo.ReelsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReelsUseCase @Inject constructor(
    private val repository: ReelsRepo,
) {
    operator fun invoke(): Flow<PagingData<ReelVideo>> {
        return repository.getReelsPage()
    }
}