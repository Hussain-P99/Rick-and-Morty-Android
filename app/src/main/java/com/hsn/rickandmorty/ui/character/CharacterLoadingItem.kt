package com.hsn.rickandmorty.ui.character

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CharacterLoadingItem(modifier: Modifier) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {

        }
    }
}