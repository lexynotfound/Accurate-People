package com.rai.accuratepeople.feature.users.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rai.accuratepeople.core.common.result.Result
import com.rai.accuratepeople.feature.users.data.analytics.UserAnalytics
import com.rai.accuratepeople.feature.users.domain.usecase.SyncCitiesUseCase
import com.rai.accuratepeople.feature.users.domain.usecase.SyncUsersUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncUsersUseCase: SyncUsersUseCase,
    private val syncCitiesUseCase: SyncCitiesUseCase,
    private val analytics: UserAnalytics
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        analytics.logSyncStarted("periodic")
        return try {
            syncCitiesUseCase()
            when (val result = syncUsersUseCase()) {
                is com.rai.accuratepeople.core.common.result.Result.Success -> {
                    analytics.logSyncCompleted(result.data)
                    Result.success()
                }
                is com.rai.accuratepeople.core.common.result.Result.Error -> {
                    analytics.logSyncFailed(result.message ?: "unknown")
                    if (runAttemptCount < 3) Result.retry() else Result.failure()
                }
                else -> Result.failure()
            }
        } catch (e: Exception) {
            analytics.logSyncFailed(e.message ?: "unknown")
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

    companion object {
        const val WORK_NAME = "SyncWorker"
    }
}
