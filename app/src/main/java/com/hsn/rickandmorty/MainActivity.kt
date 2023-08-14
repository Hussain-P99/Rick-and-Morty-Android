package com.hsn.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hsn.rickandmorty.data.viewmodels.DataSourceViewModel
import com.hsn.rickandmorty.ui.HomeScreen
import com.hsn.rickandmorty.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel : DataSourceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data = viewModel.episodes.collectAsState()
            val isLoading = viewModel.isLoading.collectAsState()

            RickAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    GenericList(list = data.value,1,isLoading.value, {
//                        if (viewModel.hasMoreData()) {
//                            viewModel.getAllEpisodes()
//                        }
//                    }) { index ->
//                        GenericLoadingItem(index = index, modifier = Modifier) {
//                            EpisodeLoadingItem(modifier = it)
//                        }
//                    }
                    HomeScreen(Modifier,viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickAndMortyTheme {
        Greeting("Android")
    }
}