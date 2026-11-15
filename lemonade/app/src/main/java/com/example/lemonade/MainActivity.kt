package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            LemonadeTheme {

                Surface(

                    modifier = Modifier.fillMaxSize(),

                    color = MaterialTheme.colorScheme.background

                ) {

                    LemonadeApp()

                }

            }

        }

    }

}

@Composable

fun LemonadeApp() {

    var step by remember { mutableStateOf(1) }

    var squeezeCount by remember { mutableStateOf(0) }

    var requiredSqueezes by remember { mutableStateOf(Random.nextInt(2, 5)) }

    val imageRes = when (step) {

        1 -> R.drawable.lemon_tree

        2 -> R.drawable.lemon_squeeze

        3 -> R.drawable.lemon_drink

        4 -> R.drawable.lemon_restart

        else -> R.drawable.lemon_tree

    }

    val textRes = when (step) {

        1 -> "Toque no limoeiro para pegar um limão"

        2 -> "Toque no limão para espremer"

        3 -> "Toque no copo para beber"

        4 -> "Toque no copo vazio para recomeçar"

        else -> "Toque para começar"

    }

    Box(

        modifier = Modifier.fillMaxSize(),

        contentAlignment = Alignment.Center

    ) {

        Column(

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Image(

                painter = painterResource(id = imageRes),

                contentDescription = null,

                modifier = Modifier

                    .size(200.dp)

                    .clickable {

                        when (step) {

                            1 -> step = 2

                            2 -> {

                                squeezeCount++

                                if (squeezeCount >= requiredSqueezes) {

                                    step = 3

                                    squeezeCount = 0

                                    requiredSqueezes = Random.nextInt(2, 5)

                                }

                            }

                            3 -> step = 4

                            4 -> step = 1

                        }

                    }

            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = textRes)

        }

    }

}

@Preview(showBackground = true)

@Composable

fun LemonadeAppPreview() {

    LemonadeTheme {

        LemonadeApp()

    }

}
