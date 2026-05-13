package com.rai.accuratepeople.feature.users.data.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.rai.accuratepeople.feature.users.domain.model.SortOrder
import javax.inject.Inject

class UserAnalytics @Inject constructor(
    private val analytics: FirebaseAnalytics
) {
    fun logScreenView() = analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
        param(FirebaseAnalytics.Param.SCREEN_NAME, "user_list")
    }

    fun logSearch(queryLength: Int) = analytics.logEvent("search_users") {
        param("query_length", queryLength.toLong())
    }

    fun logFilterByCity(cityName: String) = analytics.logEvent("filter_by_city") {
        param("city_name", cityName)
    }

    fun logSortUsers(sortOrder: SortOrder) = analytics.logEvent("sort_users") {
        param("sort_order", if (sortOrder == SortOrder.ASCENDING) "asc" else "desc")
    }

    fun logOpenAddUser() = analytics.logEvent("open_add_user") {}

    fun logAddUserSuccess(city: String, gender: Int) = analytics.logEvent("add_user_success") {
        param("user_city", city)
        param("user_gender", gender.toLong())
    }

    fun logAddUserFailure(errorMessage: String) = analytics.logEvent("add_user_failure") {
        param("error_message", errorMessage)
    }

    fun logSyncStarted(trigger: String) = analytics.logEvent("sync_started") {
        param("trigger", trigger)
    }

    fun logSyncCompleted(usersCount: Int) = analytics.logEvent("sync_completed") {
        param("users_synced", usersCount.toLong())
    }

    fun logSyncFailed(errorMessage: String) = analytics.logEvent("sync_failed") {
        param("error_message", errorMessage)
    }
}
