package com.example.latestmoviesapp.ui.general

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoviesTopAppBar(
    modifier: Modifier = Modifier,
    text: String
) {
    TopAppBar {
        Text(
            text = text,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.padding(horizontal = 16.dp),
            maxLines = 1,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}