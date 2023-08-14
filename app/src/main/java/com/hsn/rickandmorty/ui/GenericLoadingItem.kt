package com.hsn.rickandmorty.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.hsn.rickandmorty.utils.Constants

@Composable
fun GenericLoadingItem(index : Int, modifier: Modifier, loadingItem : @Composable (Modifier) -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = index.toString())

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(Constants.LIST_ANIMATION_DURATION, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(index*(Constants.LIST_ANIMATION_DURATION/ Constants.LIST_LOADING_ITEMS))
        ), label = ""
    )

    loadingItem(modifier.alpha(alpha))
}