package com.rai.accuratepeople

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rai.accuratepeople.core.ui.theme.AccuratePeopleTheme
import com.rai.accuratepeople.feature.users.presentation.ui.UserListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AccuratePeopleTheme {
                UserListScreen()
            }
        }
    }
}
