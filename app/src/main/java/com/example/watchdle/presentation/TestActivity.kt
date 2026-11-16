package com.example.watchdle.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.watchdle.presentation.theme.WatchdleTheme

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestScreen()
        }
    }
}

@Composable
@Preview
fun TestScreen() {
    WatchdleTheme {

    }
}
