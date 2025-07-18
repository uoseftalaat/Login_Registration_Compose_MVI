package com.example.task_prp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task_prp.R
import com.example.task_prp.data.remote.Country

@Composable
fun AppCountryCode(
    country: Country,
    onCountrySelected:()-> Unit
) {
    Column(
        Modifier.clickable {
            onCountrySelected()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(country.icon),
                contentDescription = "flag for ${country.countryName}",
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = "+" + country.countryCode,
                color = Color.Gray
            )

            Text(
                text = country.countryName
            )
            Spacer(modifier = Modifier.weight(1f))

            PickedIcon(
                country.isPicked
            )

        }

        HorizontalDivider()
    }
}

@Composable
fun PickedIcon(
    isPicked:Boolean
) {
    if (isPicked)
        Image(
            painter = painterResource(R.drawable.chosengreenicon),
            contentDescription = "icon picked country",
            Modifier.padding(8.dp)
        )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppCountryCode(
        Country(
            0,
            icon = R.drawable.egyptflag,
            countryCode = "20",
            countryName = "Egypt",
            true
        )
    ){

    }
}