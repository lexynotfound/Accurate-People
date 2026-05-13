package com.rai.accuratepeople.core.ui.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isCompactLayout(): Boolean = LocalConfiguration.current.screenWidthDp < 600

@Composable
fun isMediumLayout(): Boolean = LocalConfiguration.current.screenWidthDp in 600..839

@Composable
fun isExpandedLayout(): Boolean = LocalConfiguration.current.screenWidthDp >= 840
