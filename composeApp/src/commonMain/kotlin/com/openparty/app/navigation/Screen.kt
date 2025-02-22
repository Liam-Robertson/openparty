//File: composeApp/src/commonMain/kotlin/com/openparty/app/navigation/Screen.kt
package com.openparty.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {

    @Serializable object Splash : Screen("splash")
    @Serializable object Login : Screen("login")
    @Serializable object Register : Screen("register")
    @Serializable object EmailVerification : Screen("email_verification")
    @Serializable object LocationVerification : Screen("location_verification")
    @Serializable object ScreenNameGeneration : Screen("screen_name_generation")
    @Serializable object Policy : Screen("policy")
    @Serializable object ManualVerification : Screen("manual_verification")
    @Serializable object DiscussionsPreview : Screen("discussions_preview")
    @Serializable object CouncilMeetingsPreview : Screen("council_meetings_preview")
    @Serializable object AddDiscussion : Screen("add_discussion")
    @Serializable object Settings : Screen("settings")

    @Serializable
    data class DiscussionsArticle(val discussionId: String) : Screen("discussions_article/{discussionId}") {
        companion object {
            fun createRoute(discussionId: String): String = "discussions_article/$discussionId"
        }
    }

    @Serializable
    data class CouncilMeetingsArticle(val councilMeetingId: String) : Screen("council_meetings_article/{councilMeetingId}") {
        companion object {
            fun createRoute(councilMeetingId: String): String = "council_meetings_article/$councilMeetingId"
        }
    }

    @Serializable
    data class AddComment(val discussionId: String, val titleText: String) : Screen("add_comment/{discussionId}/{titleText}") {
        companion object {
            fun createRoute(discussionId: String, titleText: String): String = "add_comment/$discussionId/$titleText"
        }
    }
}
