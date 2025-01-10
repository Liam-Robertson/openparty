package com.openparty.app.features.startup.account.feature_login.presentation

import com.arkivanov.decompose.ComponentContext

class LoginComponent(
    componentContext: ComponentContext,
    private val onFinished: () -> Unit
) : ComponentContext by componentContext
