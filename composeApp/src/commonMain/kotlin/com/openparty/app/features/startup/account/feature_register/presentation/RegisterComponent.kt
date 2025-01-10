package com.openparty.app.features.startup.account.feature_register.presentation

import com.arkivanov.decompose.ComponentContext

class RegisterComponent(
    componentContext: ComponentContext,
    private val onFinished: () -> Unit
) : ComponentContext by componentContext
