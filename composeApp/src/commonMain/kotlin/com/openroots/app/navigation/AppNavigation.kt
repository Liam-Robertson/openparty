//File: composeApp/src/commonMain/kotlin/com/openroots/app/navigation/AppNavigation.kt
package com.openroots.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.openroots.app.features.engagement.comments.feature_add_comment.presentation.AddCommentScreen
import com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_article.presentation.CouncilMeetingArticleScreen
import com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_preview.presentation.CouncilMeetingsPreviewScreen
import com.openroots.app.features.newsfeed.discussions.feature_add_discussion.presentation.AddDiscussionScreen
import com.openroots.app.features.newsfeed.discussions.feature_discussions_article.presentation.DiscussionArticleScreen
import com.openroots.app.features.newsfeed.discussions.feature_discussions_preview.presentation.DiscussionsPreviewScreen
import com.openroots.app.features.startup.account.feature_login.presentation.LoginScreen
import com.openroots.app.features.startup.account.feature_register.presentation.RegisterScreen
import com.openroots.app.features.startup.feature_policy.presentation.PolicyScreen
import com.openroots.app.features.startup.feature_screen_name_generation.presentation.ScreenNameGenerationScreen
import com.openroots.app.features.startup.feature_splash.presentation.SplashScreen
import com.openroots.app.features.startup.verification.feature_email_verification.presentation.EmailVerificationScreen
import com.openroots.app.features.startup.verification.feature_location_verification.presentation.LocationVerificationScreen
import com.openroots.app.features.startup.verification.feature_manual_verification.presentation.ManualVerificationScreen
import com.openroots.app.features.utils.settings.feature_disclaimer.presentation.DisclaimerScreen
import com.openroots.app.features.utils.settings.feature_settings.presentation.SettingsScreen
import com.openroots.app.features.utils.settings.feature_delete_user.presentation.DeleteUserScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(
        Screen.DiscussionsPreview.route,
        Screen.CouncilMeetingsPreview.route,
        Screen.Settings.route
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Splash.route) { SplashScreen(navController) }
                composable(Screen.Login.route) { LoginScreen(navController) }
                composable(Screen.Register.route) { RegisterScreen(navController) }
                composable(Screen.EmailVerification.route) { EmailVerificationScreen(navController) }
                composable(Screen.LocationVerification.route) { LocationVerificationScreen(navController) }
                composable(Screen.ScreenNameGeneration.route) { ScreenNameGenerationScreen(navController) }
                composable(Screen.Policy.route) { PolicyScreen(navController) }
                composable(Screen.ManualVerification.route) { ManualVerificationScreen() }
                composable(Screen.DiscussionsPreview.route) { DiscussionsPreviewScreen(navController) }
                composable(Screen.CouncilMeetingsPreview.route) { CouncilMeetingsPreviewScreen(navController) }
                composable(Screen.AddDiscussion.route) { AddDiscussionScreen(navController) }
                composable(Screen.Settings.route) { SettingsScreen(navController) }
                composable("disclaimer") { DisclaimerScreen(navController) }
                composable("deleteUser") { DeleteUserScreen(navController) }
                composable(
                    route = Screen.DiscussionsArticle("").route,
                    arguments = listOf(navArgument("discussionId") { type = NavType.StringType })
                ) {
                    DiscussionArticleScreen(navController)
                }
                composable(
                    route = Screen.CouncilMeetingsArticle("").route,
                    arguments = listOf(navArgument("councilMeetingId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val councilMeetingId = backStackEntry.arguments?.getString("councilMeetingId") ?: ""
                    val viewModel = koinViewModel<com.openroots.app.features.newsfeed.council_meetings.feature_council_meetings_article.presentation.CouncilMeetingArticleViewModel> {
                        parametersOf(councilMeetingId)
                    }
                    CouncilMeetingArticleScreen(navController, viewModel)
                }
                composable(
                    route = Screen.AddComment("", "").route,
                    arguments = listOf(
                        navArgument("discussionId") { type = NavType.StringType },
                        navArgument("titleText") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val discussionId = backStackEntry.arguments?.getString("discussionId") ?: ""
                    val titleText = backStackEntry.arguments?.getString("titleText") ?: ""
                    val viewModel = koinViewModel<com.openroots.app.features.engagement.comments.feature_add_comment.presentation.AddCommentViewModel> {
                        parametersOf(discussionId, titleText)
                    }
                    AddCommentScreen(navController, discussionId, titleText, viewModel)
                }
            }
        }
        if (currentRoute in bottomBarRoutes) {
            NavigationFooter(
                navController = navController,
                currentRoute = currentRoute,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
