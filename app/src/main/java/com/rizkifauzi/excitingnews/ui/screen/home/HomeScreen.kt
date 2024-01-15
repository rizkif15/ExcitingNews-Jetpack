package com.rizkifauzi.excitingnews.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rizkifauzi.excitingnews.R
import com.rizkifauzi.excitingnews.di.Injection
import com.rizkifauzi.excitingnews.model.ListNews
import com.rizkifauzi.excitingnews.ui.ViewModelFactory
import com.rizkifauzi.excitingnews.ui.common.UiState
import com.rizkifauzi.excitingnews.ui.components.HeroListItem
import com.rizkifauzi.excitingnews.ui.theme.Grey02
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
    navController: NavController
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState){
            is UiState.Loading -> {
                viewModel.getAllNews()
            }
            is UiState.Success -> {
                HomeContent(
                    listNews = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    navController = navController
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    listNews: List<ListNews>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    navController: NavController
){
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Breaking News",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        modifier = modifier
                            .padding(start = 8.dp, top = 12.dp, bottom = 12.dp)
                    )
                }
            )
        }
    ){innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            val scope = rememberCoroutineScope()
            val listState = rememberLazyListState()
            val showButton: Boolean by remember{
                derivedStateOf { listState.firstVisibleItemIndex > 0 }
            }
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 80.dp),
                modifier = modifier
            ) {
                items(listNews){ data->
                    HeroListItem(
                        typeNews = data.newsIdentity.typeNews,
                        titleNews = data.newsIdentity.titleNews,
                        releaseDate = data.newsIdentity.releaseDate,
                        readTime = data.newsIdentity.readTime,
                        photoUrl = data.newsIdentity.photoUrl,
                        modifier = Modifier
                            .clickable {
                                navigateToDetail(data.newsIdentity.id)
                                //navController.navigate(Screen.Detail.createRoute(data.newsIdentity.id))
                            }
                            .fillMaxWidth()
                    )
                    Divider(color = Grey02, modifier = Modifier.padding(start = 18.dp, end = 18.dp))
                }
            }
            AnimatedVisibility(
                visible = showButton,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .align(Alignment.BottomCenter)
            ) {
                ScrollToTopButton(
                    onClick = {
                        scope.launch {
                            listState.scrollToItem(index = 0)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top)
        )
    }
}


