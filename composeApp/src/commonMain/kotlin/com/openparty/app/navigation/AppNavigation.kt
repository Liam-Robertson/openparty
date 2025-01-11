package com.openparty.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openparty.app.features.startup.account.feature_login.presentation.LoginScreen
import com.openparty.app.features.startup.feature_splash.presentation.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestinations.Splash.route
    ) {
        composable(NavDestinations.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavDestinations.Login.route) {
            LoginScreen(navController)
        }
//        composable(NavDestinations.Register.route) {
//            RegisterScreen(navController)
//        }
//        composable(NavDestinations.EmailVerification.route) {
//            EmailVerificationScreen(navController)
//        }
//        composable(NavDestinations.LocationVerification.route) {
//            LocationVerificationScreen(navController)
//        }
//        composable(NavDestinations.ScreenNameGeneration.route) {
//            ScreenNameGenerationScreen(navController)
//        }
//        composable(NavDestinations.ManualVerification.route) {
//            ManualVerificationScreen(navController)
//        }
//        composable(NavDestinations.DiscussionsPreview.route) {
//            DiscussionsPreviewScreen(navController)
//        }
//        composable(
//            "discussion_article/{discussionId}"
//        ) { backStackEntry ->
//            val discussionId = backStackEntry.arguments?.getString("discussionId") ?: ""
//            DiscussionArticleScreen(navController, discussionId)
//        }
//        composable(NavDestinations.CouncilMeetingsPreview.route) {
//            CouncilMeetingsPreviewScreen(navController)
//        }
//        composable(
//            "council_meetings_article/{councilMeetingId}"
//        ) { backStackEntry ->
//            val councilMeetingId = backStackEntry.arguments?.getString("councilMeetingId") ?: ""
//            CouncilMeetingArticleScreen(navController, councilMeetingId)
//        }
//        composable(
//            "add_comment_screen?discussionId={discussionId}&titleText={titleText}"
//        ) { backStackEntry ->
//            val discussionId = backStackEntry.arguments?.getString("discussionId") ?: ""
//            val titleText = backStackEntry.arguments?.getString("titleText") ?: ""
//            AddCommentScreen(navController, discussionId, titleText)
//        }
//        composable(NavDestinations.AddDiscussion.route) {
//            AddDiscussionScreen(navController)
//        }
    }
}
