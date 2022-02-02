package com.dhorowitz.openmovie.discover.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.dhorowitz.openmovie.discover.R

@Composable
fun ErrorView(onRetryClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.popcorn),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )

        Text(text = stringResource(id = R.string.error_message), modifier = Modifier.padding(32.dp))

        Button(modifier = Modifier.padding(horizontal = 32.dp), onClick = onRetryClicked) {
            Text(
                text = stringResource(id = R.string.retry),
                color = colorResource(id = R.color.white)
            )
        }
    }
}