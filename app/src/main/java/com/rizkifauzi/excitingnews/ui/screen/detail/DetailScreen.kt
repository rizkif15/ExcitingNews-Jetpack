package com.rizkifauzi.excitingnews.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rizkifauzi.excitingnews.R
import com.rizkifauzi.excitingnews.di.Injection

import com.rizkifauzi.excitingnews.ui.ViewModelFactory
import com.rizkifauzi.excitingnews.ui.common.UiState
import com.rizkifauzi.excitingnews.ui.theme.ExcitingNewsTheme

@Composable
fun DetailScreen(
    newsId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState){
            is UiState.Loading -> {
                viewModel.getNewsById(newsId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.newsIdentity.photoDetail,
                    data.newsIdentity.titleNews,
                    data.newsIdentity.publisherImage,
                    data.newsIdentity.publisherName,
                    data.newsIdentity.publisherType,
                    data.newsIdentity.newsDesc,
                    onBackClick = navigateBack,
                    isBookmarked = data.newsIdentity.isBookmarked, // Tambahkan isBookmarked di sini
                    onClick = { viewModel.toggleBookmarkStatus(newsId) }
                )
            }
            is UiState.Error -> {}
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    @DrawableRes imageNews: Int,
    title: String,
    @DrawableRes imagePublisher: Int,
    namePublisher: String,
    publisherType: String,
    isiBerita: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    isBookmarked: Boolean, // Tambahkan parameter isBookmarked di sini
    onClick: () -> Unit,
){
    val scrollState = rememberScrollState()

    Scaffold (
        topBar = {
            TopAppBar(
                actions = {
                    IconButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = { onClick() }
                    ) {
                        val bookmarkIcon = if (isBookmarked) {
                            R.drawable.baseline_bookmark_24 // Ganti dengan icon yang menunjukkan bookmark terisi
                        } else {
                            R.drawable.baseline_bookmark_border_24 // Ganti dengan icon yang menunjukkan bookmark tidak terisi
                        }

                        Icon(
                            painter = painterResource(bookmarkIcon),
                            contentDescription = "Icon Bookmark",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = { onBackClick() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Icon Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                title = {
                    Text(
                        text = "Detail News",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        modifier = modifier
                            .padding(start = 8.dp, top = 12.dp, bottom = 12.dp)
                    )
                }
            )
        }
    ){innerPadding ->
        Column(modifier = modifier
            .verticalScroll(scrollState)
            .padding(innerPadding)
            .padding(start = 18.dp, end = 18.dp, bottom = 18.dp)
        ){
            Image(
                painter = painterResource(imageNews),
                contentDescription = null,
                modifier = modifier
                    .height(190.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = modifier.height(12.dp))
            Text(
                text = title,
                fontSize = 24.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier
            )
            Spacer(modifier = modifier.height(12.dp))
            Row (modifier = modifier) {
                Image(
                    painter = painterResource(imagePublisher),
                    contentDescription = null,
                    Modifier.size(50.dp)
                )
                Spacer(modifier = modifier.width(12.dp))
                Column(modifier= modifier){
                    Text(text = namePublisher, fontWeight = FontWeight.SemiBold)
                    Text(text = publisherType, fontSize = 14.sp)
                }
            }
            Spacer(modifier = modifier.height(12.dp))
            Text(text = isiBerita, modifier=modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    ExcitingNewsTheme {
        DetailContent(
            R.drawable.rill_serangan_udara,
            "Serangan Udara Israel Hantam Permukiman Gaza, Puluhan Warga Sipil Tewas",
            R.drawable.ic_kompas_com,
            "Kompas.com",
            "Portal berita",
            "YERUSALEM, KOMPAS.com - Serangan udara Israel terhadap blok-blok permukiman di Gaza selatan menewaskan sedikitnya 47 orang pada Sabtu (18/11/2023), kata petugas medis.\n" +
                    "\n" +
                    "Sementara badan pengungsi PBB untuk Palestina (UNRWA) mengatakan sejumlah orang lainnya tewas dan terluka di sebuah sekolah di utara yang menjadi tempat penampungan pengungsi.\n" +
                    "\n" +
                    "Pertumpahan darah terbaru ini terjadi setelah Israel kembali memperingatkan warga sipil untuk pindah demi bersiap-siap menghadapi serangan terhadap Hamas di wilayah selatan Gaza, setelah menaklukkan wilayah utara.\n" +
                    "\n" +
                    "\"Menerima gambar dan rekaman mengerikan dari sejumlah orang yang terbunuh dan terluka di sekolah UNRWA yang menaungi ribuan pengungsi di bagian utara Jalur Gaza,\" ujar Komisioner Jenderal UNRWA, Philippe Lazzarini, dalam akun media sosialnya, seperti dilansir dari Reuters.\n" +
                    "\n" +
                    "\"Serangan-serangan ini tidak boleh menjadi hal yang biasa, harus dihentikan. Gencatan senjata kemanusiaan tidak bisa menunggu lebih lama lagi,\" tambahnya.\n" +
                    "\n" +
                    "Militer Israel tidak segera memberikan komentar.\n" +
                    "\n" +
                    "Seorang juru bicara otoritas Hamas Gaza mengatakan 200 orang telah terbunuh atau terluka.\n" +
                    "\n" +
                    "Para pejabat Palestina sebelumnya menuduh tentara Israel mengevakuasi secara paksa sebagian besar staf, pasien dan pengungsi dari Rumah Sakit Al Shifa di bagian utara, yang merupakan rumah sakit terbesar di Gaza, dan meninggalkan mereka dalam perjalanan berbahaya ke arah selatan dengan berjalan kaki.\n" +
                    "\n" +
                    "Pasukan Israel membantah tuduhan tersebut. Mereka mengatakan bahwa evakuasi dilakukan secara sukarela.\n" +
                    "\n" +
                    "Mereka juga menyita rumah sakit Al Shifa dalam serangan mereka di Gaza utara awal pekan ini.\n" +
                    "\n" +
                    "Israel mengeklaim bahwa rumah sakit tersebut menyembunyikan pusat komando Hamas di bawah tanah.",
            onBackClick = {},
            isBookmarked = true,
            onClick = {}
        )
    }
}