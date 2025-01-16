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
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            SplashScreen(navController)
        }
        composable<Screen.Login> {
            LoginScreen(navController)
        }
//        composable<Screen.Register> {
//            RegisterScreen(navController)
//        }
//        composable<Screen.EmailVerification> {
//            EmailVerificationScreen(navController)
//        }
//        composable<Screen.LocationVerification> {
//            LocationVerificationScreen(navController)
//        }
//        composable<Screen.ScreenNameGeneration> {
//            ScreenNameGenerationScreen(navController)
//        }
//        composable<Screen.ManualVerification> {
//            ManualVerificationScreen(navController)
//        }
//        composable<Screen.DiscussionsPreview> {
//            DiscussionsPreviewScreen(navController)
//        }
//        composable<Screen.DiscussionsArticle> { backStackEntry ->
//            val route = backStackEntry.toRoute<Screen.DiscussionsArticle>()
//            DiscussionArticleScreen(navController, route.discussionId)
//        }
//        composable<Screen.CouncilMeetingsPreview> {
//            CouncilMeetingsPreviewScreen(navController)
//        }
//        composable<Screen.CouncilMeetingsArticle> { backStackEntry ->
//            val route = backStackEntry.toRoute<Screen.CouncilMeetingsArticle>()
//            CouncilMeetingArticleScreen(navController, route.councilMeetingId)
//        }
//        composable<Screen.AddComment> { backStackEntry ->
//            val route = backStackEntry.toRoute<Screen.AddComment>()
//            AddCommentScreen(navController, route.discussionId, route.titleText)
//        }
//        composable<Screen.AddDiscussion> {
//            AddDiscussionScreen(navController)
//        }
    }
}
