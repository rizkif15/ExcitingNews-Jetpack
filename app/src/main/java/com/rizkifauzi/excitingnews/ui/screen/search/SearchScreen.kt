package com.rizkifauzi.excitingnews.ui.screen.search

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rizkifauzi.excitingnews.di.Injection
import com.rizkifauzi.excitingnews.model.ListNews
import com.rizkifauzi.excitingnews.ui.ViewModelFactory
import com.rizkifauzi.excitingnews.ui.common.UiState
import com.rizkifauzi.excitingnews.ui.components.HeroListItem
import com.rizkifauzi.excitingnews.ui.screen.bookmark.BookmarkViewModel
import com.rizkifauzi.excitingnews.ui.screen.home.ScrollToTopButton
import com.rizkifauzi.excitingnews.ui.theme.Grey02
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
 modifier: Modifier = Modifier,
 viewModel: SearchViewModel = viewModel(
  factory = ViewModelFactory(Injection.provideRepository())
 ),
 navigateToDetail: (Long) -> Unit,
 navController: NavController
) {
 var searchQuery by remember { mutableStateOf("") }

 viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
  when (uiState){
   is UiState.Loading -> {
    viewModel.searchNews(searchQuery)
   }
   is UiState.Success -> {
    SearchContent(
     listNews = uiState.data,
     modifier = modifier,
     navigateToDetail = navigateToDetail,
     navController = navController,
     searchQuery = searchQuery,
     onSearchQueryChanged = { newQuery ->
      searchQuery = newQuery
      viewModel.searchNews(newQuery)
     },
    )
   }
   is UiState.Error -> {}
  }
 }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(
 listNews: List<ListNews>,
 modifier: Modifier = Modifier,
 navigateToDetail: (Long) -> Unit,
 navController: NavController,
 searchQuery: String,
 onSearchQueryChanged: (String) -> Unit
){
 val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
 var searchText by remember { mutableStateOf(searchQuery) }

 Scaffold(
  modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
  topBar = {
   // Use a TextField as a SearchBar
   TextField(
    value = searchText,
    onValueChange = {
     searchText = it
     onSearchQueryChanged(it)
    },
    placeholder = {
     Text(text = "Search...")
    },
    singleLine = true,
    modifier = Modifier.fillMaxWidth().padding(8.dp).clip(RoundedCornerShape(15.dp)),
    colors = TextFieldDefaults.textFieldColors(
     focusedIndicatorColor = Color.Transparent,
     unfocusedIndicatorColor = Color.Transparent,
    )
   )
  }
 ){ innerPadding ->
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
