package com.abhay.abhaytask.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Error(modifier: Modifier = Modifier, errorMessage: String?) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = errorMessage ?: "Something went wrong!",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}