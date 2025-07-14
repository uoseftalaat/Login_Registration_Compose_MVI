package com.example.task_prp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.task_prp.ui.screen.countrypickerScreen.CountryPickerScreen
import com.example.task_prp.ui.screen.loginscreen.LoginScreen
import com.example.task_prp.ui.screen.signupscreen.SignUpScreen


@Composable
fun AppNavComp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavigationGraph(modifier,navController)
}

@Composable
fun NavigationGraph(modifier: Modifier, navController: NavHostController) {

    NavHost(navController,Navigation.Login()) {
        composable<Navigation.Login>
        {
            val id = it.savedStateHandle.remove<Int>("selected_country_id")
            LoginScreen(
                id,
                onPickFlagClick = { countryId ->
                    navController.navigate(Navigation.CountryPicker(countryId))
                },
                onSignUpClick = { countryId ->
                    navController.navigate(Navigation.SignUp(countryId))
                },
                modifier = modifier,
            )
        }

        composable<Navigation.SignUp>
        {
            val id = it.savedStateHandle.remove<Int>("selected_country_id")
            SignUpScreen(
                id = id,
                onPickFlagClick = { countryId ->
                    navController.navigate(Navigation.CountryPicker(countryId))
                },
                onBackClicked = { countryId ->
                    navController.navigate(Navigation.Login(countryId))
                },
                onConfirmButtonClick = { countryId ->
                    navController.navigate(Navigation.Login(countryId))
                },
                modifier = modifier,
            )
        }

        composable<Navigation.CountryPicker>
        {
            CountryPickerScreen(
                modifier = modifier,
                onBackClicked = { countryId ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("selected_country_id", countryId)
                    navController.popBackStack()
                },
                onCountrySelected = { countryId ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("selected_country_id", countryId)
                    navController.popBackStack()
                }
            )
        }
    }
}