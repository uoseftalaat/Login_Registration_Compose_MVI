package com.example.task_prp.ui.screen.loginscreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.task_prp.R
import com.example.task_prp.data.Country
import com.example.task_prp.ui.common.AppButton
import com.example.task_prp.ui.common.textfield.AppPhoneField
import com.example.task_prp.ui.common.textfield.AppTextField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    countryId:Int?,
    onPickFlagClick:(Int) -> Unit,
    onSignUpClick:(Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<LoginViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val effect = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.setIntent(LoginContract.LoginIntent.OnDefaultCountryChange(countryId))
        effect.collect{ event ->
            when(event){
                is LoginContract.LoginEffect.CountryPickerClick -> onPickFlagClick(event.countryId)
                LoginContract.LoginEffect.Login -> Toast.makeText(context,"login",Toast.LENGTH_SHORT).show()
                is LoginContract.LoginEffect.CreateAccount -> onSignUpClick(event.countryId)
            }
        }
    }
    LoginContent(
        modifier,
        state.value,
        viewModel::setIntent
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    state:LoginContract.LoginState,
    setIntent: (LoginContract.LoginIntent) -> Unit,
) {

    Column (
        modifier.padding(horizontal = 16.dp)
    ){
        Spacer(
            Modifier.size(30.dp)
        )
        Text(
            text = stringResource(R.string.login_page_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = stringResource(R.string.login_page_subtitle),
            fontSize = 14.sp
        )
        Spacer(
            Modifier.size(80.dp)
        )
        AppPhoneField(
            state.phoneNumber,
            country = state.country ?: Country(
                0,
                R.drawable.outline_flag_24,
                "",
                ""
            ),
            onValueChange = { phoneNumber ->
                setIntent(LoginContract.LoginIntent.OnPhoneNumberChange(phoneNumber))
            },
            onPickFlagClick = { countryId ->
                setIntent(LoginContract.LoginIntent.OnCountryPickerClick(countryId))
            },
            isError = state.isPhoneValid?.not() ?: false,
            errorMessage = state.phoneError
        )
        Spacer(
            Modifier.size(20.dp)
        )
        AppTextField(
            "Password",
            "Password",
            state.password,
            true,
            state.isPasswordHidden,
            onIconClick = {
                setIntent(LoginContract.LoginIntent.OnShowPasswordClick)
            },
            onValueChanges = { newPassword ->
                setIntent(LoginContract.LoginIntent.OnPasswordChange(newPassword))
            },
            isError = state.isPasswordValid?.not() ?: false,
            errorMessage = state.passwordError
        )
        Spacer(
            Modifier.weight(1f)
        )
        AppButton(
            buttonName = stringResource(R.string.continue_button),
            isEnabled = state.isProcessButtonEnabled
        ) {
            setIntent(LoginContract.LoginIntent.OnLoginClick)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        ){
            Text(
                stringResource(R.string.you_don_t_have_an_account)
            )
            TextButton(
                {
                    setIntent(LoginContract.LoginIntent.OnCreateAccountClick(state.countryId))
                }
            ) {
                Text(
                    stringResource(R.string.create_account),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}