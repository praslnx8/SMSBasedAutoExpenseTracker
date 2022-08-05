package app.expense.tracker.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.expense.domain.suggestion.SuggestionSyncService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class SMSSyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val suggestionSyncService: SuggestionSyncService
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        if (ActivityCompat.checkSelfPermission(appContext, Manifest.permission.READ_SMS)
            == PackageManager.PERMISSION_GRANTED) {
            suggestionSyncService.sync()
        }
        return Result.success()
    }
}