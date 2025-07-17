package com.example.task_prp.ui.common.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.task_prp.R

@Composable
fun AppTextField(
    textFieldName:String,
    hintText:String,
    text:String,
    isPassword:Boolean = false,
    isPasswordHidden:Boolean = false,
    onIconClick: () -> Unit = {},
    onValueChanges: (String) -> Unit,
    isError:Boolean = false,
    errorMessage:String = ""
) {
    Column {
        Text(
            text = textFieldName
        )
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                onValueChanges(newText)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color.Black
            ),
            isError = isError,
            placeholder = {
                Text(
                    hintText,
                    color = Color.Gray
                )
            },
            trailingIcon = {
                TrailingIconView(isPassword,onIconClick)
            },
            modifier = Modifier
                .fillMaxWidth(),
            visualTransformation = if(isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = if(isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            errorMessage,
            color = Color.Red,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun TrailingIconView(
    isPassword: Boolean,
    onIconClick:() -> Unit
) {
    if(isPassword) {
        IconButton(
            onClick = onIconClick
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.passwordview),
                contentDescription = stringResource(R.string.password_icon_description)
            )
        }
    }
}