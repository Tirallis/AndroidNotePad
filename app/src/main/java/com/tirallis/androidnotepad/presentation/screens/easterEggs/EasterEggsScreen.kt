@file:OptIn(ExperimentalMaterial3Api::class)

package com.tirallis.androidnotepad.presentation.screens.easterEggs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tirallis.androidnotepad.R

@Composable
fun EasterEggScreen(
    modifier: Modifier = Modifier,
    viewModel: EasterEggsViewModel = viewModel(),
    onFinished: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    when (val currentState = state) {
        EasterEggsState.Initial -> {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Курлык!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 8.dp)
                                    .clickable {
                                        viewModel.processCommand(EasterEggsCommand.Back)
                                    },
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад"
                            )
                        }
                    )
                }) { innerPadding ->
                Image(
                    modifier = modifier
                        .padding(innerPadding)
                        .padding(start = 24.dp, end = 24.dp),
                    bitmap = ImageBitmap.imageResource(R.drawable.ic_please_image),
                    contentDescription = "Позязя.."
                )
            }
        }

        EasterEggsState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }

    }
}