package com.example.task_prp.ui.screen.signupscreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import com.example.task_prp.ui.common.AppTitleField
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SignUpScreen(
    id:Int?,
    modifier: Modifier = Modifier,
    onPickFlagClick: (Int) -> Unit,
    onBackClicked: (Int) -> Unit,
    onConfirmButtonClick: (Int) -> Unit
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<SignUpViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setIntent(SignUpContract.SignUpIntent.OnFlagChange(id))
        viewModel.effect.collect{ event ->
            when(event){
                is SignUpContract.SignUpEffect.OnBackButtonClick -> onBackClicked(event.countryId)
                is SignUpContract.SignUpEffect.OnCountryPickClick -> onPickFlagClick(event.countryId)
                is SignUpContract.SignUpEffect.OnCreateAccountClick -> {
                    Toast.makeText(context,"Account Created",Toast.LENGTH_SHORT).show()
                    onConfirmButtonClick(event.countryId)
                }
            }
        }
    }

    SignUpContent(
        modifier,
        state.value,
        viewModel::setIntent
    )
}

@Composable
fun SignUpContent(
    modifier: Modifier,
    state:SignUpContract.SignupState,
    setIntent:(SignUpContract.SignUpIntent) -> Unit,
) {



    Column (
        modifier = modifier.padding(top = 20.dp)
    ){
        AppTitleField(
            stringResource(R.string.Sigu_up_page_title)
        ) {
            setIntent(SignUpContract.SignUpIntent.OnBackButtonClick(state.country?.id ?: 0))
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {

            Spacer(
                Modifier.size(4.dp)
            )

            Text(
                stringResource(R.string.personal_details),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(
                Modifier.size(4.dp)
            )

            AppTextField(
                textFieldName = stringResource(R.string.name_textField_title),
                hintText = stringResource(R.string.text_hint_textfield),
                text = state.name,
                onValueChanges = { name ->
                    setIntent(SignUpContract.SignUpIntent.OnNameChange(name))
                },
                isError = state.isNameValid?.not() ?: false,
                errorMessage = state.nameError
            )

            AppTextField(
                textFieldName = stringResource(R.string.surname_textfield_title),
                hintText = stringResource(R.string.text_hint_textfield),
                text = state.surName,
                onValueChanges = { surName ->
                    setIntent(SignUpContract.SignUpIntent.OnSurNameChange(surName))
                },
                isError = state.isSurNameValid?.not() ?: false,
                errorMessage = state.surNameError
            )

            AppTextField(
                textFieldName = stringResource(R.string.email_textfield_title),
                hintText = stringResource(R.string.text_hint_textfield),
                text = state.email,
                onValueChanges = { email ->
                    setIntent(SignUpContract.SignUpIntent.OnEmailChange(email))
                },
                isError = state.isEmailValid?.not() ?: false,
                errorMessage = state.emailError
            )

            AppPhoneField(
                state.phoneNumber,
                state.country ?: Country(
                    0,
                    R.drawable.outline_flag_24,
                    "",
                    "",
                ),
                onValueChange = { phoneNumber ->
                    setIntent(SignUpContract.SignUpIntent.OnPhoneNumberChange(phoneNumber))
                },
                onPickFlagClick= {
                    setIntent(SignUpContract.SignUpIntent.OnCountryPickClick(state.country?.id ?: 0))
                },
                isError = state.isPhoneNumberValid?.not() ?: false,
                errorMessage = state.phoneError
            )
            Spacer(
                Modifier.weight(1f)
            )

            AppButton(
                "Confirm",
                state.isCreateAccountButtonEnabled,
            ) {
                setIntent(SignUpContract.SignUpIntent.OnConfirmClick)
            }
        }
    }
}