package com.dhorowitz.openmovie.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp


@Composable
fun ErrorView(error: Throwable? = null, onRetryClicked: () -> Unit) {
    LaunchedEffect(Unit) {
        Log.e("Error View", "An error has occurred", error)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
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