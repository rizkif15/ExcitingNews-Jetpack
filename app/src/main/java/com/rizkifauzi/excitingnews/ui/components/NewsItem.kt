package com.rizkifauzi.excitingnews.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rizkifauzi.excitingnews.R
import com.rizkifauzi.excitingnews.ui.theme.ExcitingNewsTheme

@Composable
fun HeroListItem(
    typeNews: String,
    titleNews: String,
    releaseDate: String,
    readTime: String,
    photoUrl: Int,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(start = 18.dp, end = 18.dp, top = 12.dp, bottom = 12.dp)
    ){
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 10.dp)
        ){
            Text(
                text = typeNews,
                fontSize = 14.sp
            )
            Text(
                text = titleNews,
                fontWeight = FontWeight.SemiBold
            )
            Row {
                Text(
                    text = releaseDate,
                    fontSize = 14.sp
                )
                Text(
                    text = " | "
                )
                Text(
                    text = readTime,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsItemPreview(){
    ExcitingNewsTheme{
        HeroListItem(
            "Teknologi",
            "Wamenkominfo Minta Masyarakat Waspada Penyalahgunaan Deepfake hingga Peretasan Toyota",
            "18 Nov 2023",
            "1 Menit Waktu baca",
            R.drawable.thumb_wamenkominfo_minta
        )
    }
}