package com.openroots.app.features.startup.account.feature_register.presentation

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.NavHostController
import com.openroots.app.features.startup.account.shared.presentation.AccountScreen

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = koinViewModel()
) {
    AccountScreen(
        viewModel = viewModel,
        title = "Create Your Account",
        actionText = "Register",
        footerText = "Already have an account? Login",
        onActionClick = { viewModel.onRegisterButtonClick() },
        onFooterClick = { viewModel.onTextFooterClick() },
        uiEvent = viewModel.uiEvent,
        navController = navController
    )
}
