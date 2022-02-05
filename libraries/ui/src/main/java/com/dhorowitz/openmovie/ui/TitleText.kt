package com.dhorowitz.openmovie.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier, fontSize: TextUnit = 24.sp) {
    Text(
        style = TextStyle(
            color = colorResource(id = R.color.yellow_bg_2),
            shadow = Shadow(
                color = colorResource(id = R.color.black),
                offset = Offset(10f, 10f),
                blurRadius = 8f
            ),
            fontFamily = FontFamily(Font(R.font.alfa_slab_one, FontWeight.Normal))
        ),
        text = text.uppercase(),
        fontSize = fontSize,
        modifier = modifier
    )
}