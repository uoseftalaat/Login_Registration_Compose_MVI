package com.example.task_prp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task_prp.R
import com.example.task_prp.data.Country

@Composable
fun AppPhoneField(
    phoneNumber:String,
    country: Country,
    onValueChange:(String) ->Unit,
    onPickFlagClick:(Int) -> Unit,
    isError:Boolean = false,
    errorMessage:String = ""
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
                            onPickFlagClick(country.id)
                        }
                ) {
                    Image(
                        painter = painterResource(country.icon),
                        contentDescription = "${country.countryName} flag image"
                    )

                    Text(
                        text = "+" + country.countryCode
                    )

                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "open countries picker button"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
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
            0,
            R.drawable.egyptflag,
            "20",
            "Egypt"
        ),
        {},
        {}
    )
}