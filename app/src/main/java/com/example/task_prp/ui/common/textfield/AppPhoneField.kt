package com.example.task_prp.ui.common.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_prp.domain.model.Country
import com.example.task_prp.ui.screen.loginscreen.LoginTestTags


@Composable
fun AppPhoneField(
    phoneNumber:String,
    country: Country,
    onValueChange:(String) ->Unit,
    onPickFlagClick:(String) -> Unit,
    isError:Boolean = false,
    errorMessage:String = "",
    modifier: Modifier = Modifier
) {
    Column {
        OutlinedTextField(
            value = phoneNumber,
            placeholder = {
                Text(
                    text = "5xxxxxxxx",
                    color = Color.Gray
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color.Black
            ),
            onValueChange = { newPhoneNumber ->
                onValueChange(newPhoneNumber)
            },
            isError = isError,
            leadingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onPickFlagClick(country.countryCode)
                        }
                        .testTag(LoginTestTags.FLAG_ICON_TEST_TAG)
                ) {
//                    Image(
//                        painter = painterResource(country.icon),
//                        contentDescription = "${country.countryName} flag image"
//                    )
                    Text(
                        text = country.icon,
                        fontSize = 25.sp
                    )

                    Text(
                        text = "+" + country.dialCode
                    )

                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "open countries picker button"
                    )
                }
            },
            modifier = modifier
                .fillMaxWidth()
            ,keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            errorMessage,
            color = Color.Red,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppPhoneField(
        "",
        Country(
            "EG",
            "20",
            "Egypt",
            "\uD83C\uDDE6\uD83C\uDDEA"
        ),
        {},
        {},
        modifier = Modifier
    )
}