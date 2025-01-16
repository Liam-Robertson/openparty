package com.openparty.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object Splash : Screen()

    @Serializable
    object Login : Screen()

    @Serializable
    object Register : Screen()

    @Serializable
    object EmailVerification : Screen()

    @Serializable
    object LocationVerification : Screen()

    @Serializable
    object ScreenNameGeneration : Screen()

    @Serializable
    object ManualVerification : Screen()

    @Serializable
    object DiscussionsPreview : Screen()

    @Serializable
    data class DiscussionsArticle(val discussionId: String) : Screen()

    @Serializable
    object CouncilMeetingsPreview : Screen()

    @Serializable
    data class CouncilMeetingsArticle(val councilMeetingId: String) : Screen()

    @Serializable
    data class AddComment(val discussionId: String, val titleText: String) : Screen()

    @Serializable
    object AddDiscussion : Screen()
}
