//File: composeApp/src/commonMain/kotlin/com/openparty/app/navigation/AppNavigation.kt
package com.openparty.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openparty.app.features.startup.account.feature_login.presentation.LoginScreen
import com.openparty.app.features.startup.account.feature_register.presentation.RegisterScreen
import com.openparty.app.features.startup.feature_splash.presentation.SplashScreen
import com.openparty.app.features.startup.verification.feature_email_verification.presentation.EmailVerificationScreen
import com.openparty.app.features.startup.verification.feature_location_verification.presentation.LocationVerificationScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(route = Screen.EmailVerification.route) {
            EmailVerificationScreen(navController)
        }
        composable(route = Screen.LocationVerification.route) {
            LocationVerificationScreen(navController)
        }
        // composable(route = Screen.ScreenNameGeneration.route) {
        //     ScreenNameGenerationScreen(navController)
        // }
        // composable(route = Screen.ManualVerification.route) {
        //     ManualVerificationScreen(navController)
        // }
        // composable(route = Screen.DiscussionsPreview.route) {
        //     DiscussionsPreviewScreen(navController)
        // }
        // composable(route = Screen.DiscussionsArticle("").route) { backStackEntry ->
        //     // Extract argument "discussionId" from backStackEntry.arguments and pass to screen
        //     val discussionId = backStackEntry.arguments?.getString("discussionId") ?: ""
        //     DiscussionArticleScreen(navController, discussionId)
        // }
        // composable(route = Screen.CouncilMeetingsPreview.route) {
        //     CouncilMeetingsPreviewScreen(navController)
        // }
        // composable(route = Screen.CouncilMeetingsArticle("").route) { backStackEntry ->
        //     val councilMeetingId = backStackEntry.arguments?.getString("councilMeetingId") ?: ""
        //     CouncilMeetingArticleScreen(navController, councilMeetingId)
        // }
        // composable(route = Screen.AddComment("").route) { backStackEntry ->
        //     val discussionId = backStackEntry.arguments?.getString("discussionId") ?: ""
        //     val titleText = backStackEntry.arguments?.getString("titleText") ?: ""
        //     AddCommentScreen(navController, discussionId, titleText)
        // }
        // composable(route = Screen.AddDiscussion.route) {
        //     AddDiscussionScreen(navController)
        // }
    }
}
