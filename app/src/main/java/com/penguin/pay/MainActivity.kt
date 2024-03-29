package com.penguin.pay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.penguin.pay.ui.theme.PenguinPayTheme
import com.penguin.pay.ui.transact.TransactScreen
import com.penguin.pay.ui.transact.TransactViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenguinPayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val vieModel by viewModels<TransactViewModel>()
                    TransactScreen(vieModel)
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PenguinPayTheme {
      //  TransactScreen(transactViewModel = )
    }
}