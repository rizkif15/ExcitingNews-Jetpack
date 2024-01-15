package com.rizkifauzi.excitingnews.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizkifauzi.excitingnews.R
import com.rizkifauzi.excitingnews.ui.theme.ExcitingNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(modifier: Modifier = Modifier){
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        modifier = modifier
                            .padding(start = 8.dp, top = 12.dp, bottom = 12.dp)
                    )
                },
            )
        }
    ){innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ){
            val scrollState = rememberScrollState()

            Column (
                modifier = modifier
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painterResource(R.drawable.img_apk_baru),
                    contentDescription = null,
                    modifier.size(110.dp)
                )

                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = "Exciting News",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Text(
                    text = "versi 1.0",
                    fontSize = 12.sp
                )

                Spacer(modifier = modifier.height(32.dp))
                Text(
                    text = "Dibuat oleh:",
                    fontSize = 12.sp
                )
                Spacer(modifier = modifier.height(32.dp))

                Image(
                    painterResource(R.drawable.img_saya),
                    contentDescription = null,
                    modifier.size(110.dp)
                )
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = "Rizki Fauzi",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Text(
                    text = "@rizkifauzi1512@gmail.com",
                    fontSize = 12.sp
                )
            }
        }
    }

}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun Preview() {
    ExcitingNewsTheme {
        AboutScreen()
    }
}