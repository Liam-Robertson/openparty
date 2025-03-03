//File: composeApp/src/iosMain/kotlin/com/openroots/app/di/IOSKoinInitializer.kt
package com.openroots.app.di

import com.openroots.app.core.analytics.di.analyticsModule
import com.openroots.app.features.engagement.comments.feature_add_comment.di.addCommentKoinModule
import com.openroots.app.features.engagement.comments.feature_comments_section.di.commentsSectionModule
import com.openroots.app.features.engagement.engagement_bars.feature_preview_enagement_footer.di.engagementFooterKoinModule
import com.openroots.app.features.newsfeed.council_meetings.shared.di.councilMeetingsModule
import com.openroots.app.features.newsfeed.discussions.feature_add_discussion.di.addDiscussionModule
import com.openroots.app.features.newsfeed.discussions.feature_discussions_article.di.discussionArticleKoinModule
import com.openroots.app.features.newsfeed.discussions.feature_discussions_preview.di.discussionsKoinModule
import com.openroots.app.features.shared.feature_permissions.di.permissionModule
import com.openroots.app.features.shared.feature_user.di.userModule
import com.openroots.app.features.startup.account.feature_login.di.loginModule
import com.openroots.app.features.startup.account.feature_register.di.registerModule
import com.openroots.app.features.startup.feature_authentication.di.authenticationModule
import com.openroots.app.features.startup.feature_screen_name_generation.di.screenNameGenerationModule
import com.openroots.app.features.startup.feature_splash.di.splashModule
import com.openroots.app.features.startup.verification.feature_email_verification.di.emailVerificationModule
import com.openroots.app.features.startup.verification.feature_location_verification.di.locationVerificationModule
import com.openroots.app.features.utils.feature_hide_posts.di.hidePostsModule
import com.openroots.app.features.utils.feature_report.di.reportModule
import com.openroots.app.features.utils.settings.feature_settings.di.settingsModule
import com.openroots.app.core.storage.clearAllKeychainData
import com.openroots.app.features.startup.feature_policy.di.policyModule
import com.openroots.app.features.utils.settings.feature_delete_user.di.deleteUserModule
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin

object IOSKoinInitializer {
    fun initializeKoin() {
        // First, initialize Firebase.

        // Optionally, delay clearing the keychain until after Firebase is fully initialized.
        MainScope().launch {
            delay(1000) // delay half a second; adjust as needed
            clearAllKeychainData()
        }

        startKoin {
            modules(
                listOf(
                    permissionModule,
                    userModule,
                    loginModule,
                    registerModule,
                    authenticationModule,
                    splashModule,
                    analyticsModule,
                    screenNameGenerationModule,
                    emailVerificationModule,
                    locationVerificationModule,
                    discussionsKoinModule,
                    discussionArticleKoinModule,
                    addDiscussionModule,
                    councilMeetingsModule,
                    commentsSectionModule,
                    addCommentKoinModule,
                    engagementFooterKoinModule,
                    reportModule,
                    hidePostsModule,
                    settingsModule,
                    policyModule,
                    deleteUserModule,
                )
            )
        }
    }
}
