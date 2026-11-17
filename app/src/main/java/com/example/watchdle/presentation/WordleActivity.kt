package com.example.watchdle.presentation

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import com.example.watchdle.presentation.theme.WatchdleTheme
import android.app.RemoteInput
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender

class WordleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordleScreen()
        }
    }
}

fun getTopWord(): String {
    val najdluzszeSlowo = "konstantynopolitańczykowianeczka"
    return najdluzszeSlowo
}

@Composable
@Preview
fun WordleScreen() {
    WatchdleTheme {
        AppScaffold() {
            val context = LocalContext.current
            val listState = rememberTransformingLazyColumnState()
            val transformationSpec = rememberTransformationSpec()

            var topWord by remember {
                mutableStateOf("Tu się wyświetli Słowo")
            }
            val inputKey = "wordle_input_key"
            val textFromKeyboard = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data ?: return@rememberLauncherForActivityResult
                    val results = RemoteInput.getResultsFromIntent(data)
                    val input = results?.getCharSequence(inputKey)
                    topWord = input.toString()
                }
            }
            ScreenScaffold(
                scrollState = listState,
            ) { contentPadding ->
                TransformingLazyColumn(contentPadding = contentPadding, state = listState) {
                    item {
                        ListHeader(
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text("Gra Niedostępna, Możesz zobaczyć najdłuższe słowo klikając na przycisk poniżej")
                        }
                    }
                    for (i in 1..5) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val remoteInputs = listOf(
                                            RemoteInput.Builder(inputKey)
                                                .setLabel("Podaj 5 literowe słowo")
                                                .wearableExtender {
                                                    setEmojisAllowed(false)

                                                }
                                                .build()
                                        )
                                        val intent =
                                            RemoteInputIntentHelper.createActionRemoteInputIntent()
                                        RemoteInputIntentHelper.putRemoteInputsExtra(
                                            intent,
                                            remoteInputs
                                        )
                                        textFromKeyboard.launch(intent)
                                    },

                                horizontalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    Alignment.CenterHorizontally
                                ),

                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                for (i in 1..5) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .border(2.dp, Color(red = 58, green = 59, blue = 60)),
                                        contentAlignment = Alignment.Center

                                    ) {
                                        Text(
                                            text = "A",
                                            color = Color.White,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item {
                        ListHeader(
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text(topWord)
                        }
                    }
                    item {
                        Button(
                            onClick = {
                                topWord = getTopWord()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text("Niespodzianka")
                        }
                    }
                    item {
                        Button(
                            onClick = {
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text("Wroc")
                        }
                    }
                }
            }
        }
    }
}
