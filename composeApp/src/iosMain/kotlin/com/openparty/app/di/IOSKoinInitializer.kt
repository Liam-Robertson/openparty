// File: composeApp/src/iosMain/kotlin/com/openparty/app/di/IOSKoinInitializer.kt
package com.openparty.app.di

import com.openparty.app.core.analytics.di.analyticsModule
import com.openparty.app.features.engagement.comments.feature_add_comment.di.addCommentKoinModule
import com.openparty.app.features.engagement.comments.feature_comments_section.di.commentsSectionModule
import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.di.engagementFooterKoinModule
import com.openparty.app.features.newsfeed.council_meetings.shared.di.councilMeetingsModule
import com.openparty.app.features.newsfeed.discussions.feature_add_discussion.di.addDiscussionModule
import com.openparty.app.features.newsfeed.discussions.feature_discussions_article.di.discussionArticleKoinModule
import com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.di.discussionsKoinModule
import com.openparty.app.features.shared.feature_permissions.di.permissionModule
import com.openparty.app.features.shared.feature_user.di.userModule
import com.openparty.app.features.startup.account.feature_login.di.loginModule
import com.openparty.app.features.startup.account.feature_register.di.registerModule
import com.openparty.app.features.startup.feature_authentication.di.authenticationModule
import com.openparty.app.features.startup.feature_screen_name_generation.di.screenNameGenerationModule
import com.openparty.app.features.startup.feature_splash.di.splashModule
import com.openparty.app.features.startup.verification.feature_email_verification.di.emailVerificationModule
import com.openparty.app.features.startup.verification.feature_location_verification.di.locationVerificationModule
import org.koin.core.context.startKoin

object IOSKoinInitializer {
    fun initializeKoin() {
        startKoin {
            modules(
                listOf(
                    iosModule,
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
                    engagementFooterKoinModule
                )
            )
        }
    }
}
