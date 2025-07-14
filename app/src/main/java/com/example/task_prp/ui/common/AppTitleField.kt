package com.example.task_prp.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_prp.R

@Composable
fun AppTitleField(
    title:String,
    modifier: Modifier = Modifier,
    onBackIconClick:() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onBackIconClick
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.backarrow),
                contentDescription = null
            )
        }
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTitleField(
        "SelectCountry"
    ) { }
}