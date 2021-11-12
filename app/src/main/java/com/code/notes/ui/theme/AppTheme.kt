package com.code.notes.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorPalette = darkColors(
    primary = Color.White,
    background = DarkGray,
    onBackground = Color.White,
    surface = LightBlue,
    onSurface = DarkGray
)

@Composable
fun NoteAppTheme(darkThem:Boolean = true, content:@Composable() ()->Unit){
MaterialTheme(colors = colorPalette,typography = typography, shapes = Shapes, content = content)
}