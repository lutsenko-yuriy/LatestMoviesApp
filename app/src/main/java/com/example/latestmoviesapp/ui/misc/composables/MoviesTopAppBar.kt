package com.example.latestmoviesapp.ui.misc.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.latestmoviesapp.R

@Composable
fun MoviesTopAppBar(
    modifier: Modifier = Modifier,
    text: String,
    onNavigateBack: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = text,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.padding(horizontal = 16.dp),
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

        },
        navigationIcon = onNavigateBack?.let {
            {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        modifier = Modifier,
                        contentDescription = stringResource(id = R.string.move_back_content_description)
                    )
                }
            }
        }
    )
}