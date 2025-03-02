package com.openroots.app.features.startup.account.feature_login.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.openroots.app.features.startup.account.shared.presentation.AccountScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = koinViewModel()
) {
    AccountScreen(
        viewModel = viewModel,
        title = "Login",
        actionText = "Login",
        footerText = "Don't have an account? Register",
        onActionClick = { viewModel.onLoginButtonClick() },
        onFooterClick = { viewModel.onTextFooterClick() },
        uiEvent = viewModel.uiEvent,
        navController = navController
    )
}
