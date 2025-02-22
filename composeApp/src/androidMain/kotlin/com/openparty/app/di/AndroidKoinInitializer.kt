// File: composeApp/src/androidMain/kotlin/com/openparty/app/di/AndroidKoinInitializer.kt

package com.openparty.app.di

import android.app.Application
import com.openparty.app.core.analytics.di.analyticsModule
import com.openparty.app.features.engagement.comments.feature_add_comment.di.addCommentKoinModule
import com.openparty.app.features.engagement.comments.feature_comments_section.di.commentsSectionModule
import com.openparty.app.features.engagement.engagement_bars.feature_preview_enagement_footer.di.engagementFooterKoinModule
import com.openparty.app.features.newsfeed.council_meetings.shared.di.councilMeetingsModule
import com.openparty.app.features.newsfeed.discussions.feature_add_discussion.di.addDiscussionModule
import com.openparty.app.features.newsfeed.discussions.feature_discussions_article.di.discussionArticleKoinModule
import com.openparty.app.features.newsfeed.discussions.feature_discussions_preview.di.discussionsKoinModule
import com.openparty.app.features.shared.feature_permissions.di.androidPermissionModule
import com.openparty.app.features.shared.feature_permissions.di.permissionModule
import com.openparty.app.features.shared.feature_user.di.userModule
import com.openparty.app.features.startup.account.feature_login.di.loginModule
import com.openparty.app.features.startup.account.feature_register.di.registerModule
import com.openparty.app.features.startup.feature_authentication.di.authenticationModule
import com.openparty.app.features.startup.feature_policy.di.policyModule
import com.openparty.app.features.startup.feature_screen_name_generation.di.screenNameGenerationModule
import com.openparty.app.features.startup.feature_splash.di.splashModule
import com.openparty.app.features.startup.verification.feature_email_verification.di.emailVerificationModule
import com.openparty.app.features.startup.verification.feature_location_verification.di.locationVerificationModule
import com.openparty.app.main.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object AndroidKoinInitializer {
    fun initializeKoin(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                sharedModules() + androidModules() + listOf(androidPermissionModule(application))
            )
        }
    }

    private fun androidModules() = listOf(
        androidModule,
        mainModule
    )

    private fun sharedModules(): List<Module> = listOf(
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
        policyModule,
    )
}
