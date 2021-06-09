package com.example.studyapp.ui.home

import com.example.studyapp.R
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studyapp.realm.models.FlashCard
import com.example.studyapp.realm.models.Trivia
import io.realm.RealmResults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.studyapp.realm.RealmHelper

@ExperimentalAnimationApi
@Composable
fun MainScreen(viewModel: MainViewModel, flashCards: RealmResults<FlashCard>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen_container") {
        composable("main_screen_container") {
            MainScreenContainer(
                viewModel,
                flashCards,
                navController
            )
        }
        composable(route = "full_card/{question}/{answer}") { backStackEntry ->

            FullCard(
                backStackEntry.arguments?.getString("question"),
                backStackEntry.arguments?.getString("answer")
            )
        }
    }

}

@ExperimentalAnimationApi
@Composable
fun MainScreenContainer(
    viewModel: MainViewModel,
    flashCards: RealmResults<FlashCard>,
    navController: NavController
) {

    Column {
        TopBar()
        FlashCardList(viewModel, flashCards, navController)
    }


    var editable by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)

    ) {
        Button(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(Alignment.TopStart)
                .padding(start = 14.dp, top = 12.dp),
            onClick = {
                editable = !editable
            }
        ) {
            Text("LANGUAGE")
        }


        AnimatedVisibility(
            visible = editable,
            enter = fadeIn(),
            exit = fadeOut()
        ) {

            ScrollBoxes(viewModel)
        }
    }

}

@ExperimentalAnimationApi
@Composable
fun FlashCardList(
    viewModel: MainViewModel,
    flashCards: RealmResults<FlashCard>,
    navController: NavController
) {

    val language: String by viewModel.language.observeAsState("English")

    var editable by remember { mutableStateOf(true) }

    Box {
        Image(
            painter = painterResource(R.drawable.blue_paper),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )


        LazyColumn(
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(flashCards.size) { index ->
                val trivia =
                    flashCards[index]?.triviaList?.find { (it as Trivia).language == language }
                val title = "${index + 1}."

                FlashCard(title, trivia, viewModel, onClick = {
                    Log.e("WES_TEST", "CLICK")
                    navController.navigate("full_card/${trivia!!.question}/${trivia.answer}")
                })
            }
        }
    }
}

@Composable
fun TopBar() {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        elevation = 30.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.LightGray)

        )
    }
}


@Composable
fun ScrollBoxes(viewModel: MainViewModel) {

    val languageList = RealmHelper.getLanguages()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 14.dp, top = 60.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .background(Color.Gray)
                .wrapContentHeight()
                .width(100.dp)
                .size(100.dp)
                .verticalScroll(rememberScrollState())
        ) {

            repeat(languageList.size) { index ->
                Text(
                    text = languageList[index],
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onLanguageChange(languageList[index])
                        }

                )
            }
        }
    }
}


@Composable
fun FlashCard(
    title: String,
    trivia: Trivia?,
    viewModel: MainViewModel,
    onClick: () -> Unit
) {

    val language: String by viewModel.language.observeAsState("English")

    MaterialTheme {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(Color(R.color.purple_200))
                .clickable(onClick = onClick)
        ) {
            Image(
                painter = painterResource(R.drawable.paper),
                contentDescription = null,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,

                )

            Column(
            ) {

                Spacer(
                    Modifier
                        .height(10.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = title,
                    modifier = Modifier.padding(start = 4.dp)
                )

                Spacer(
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Color(R.color.flashcard_red))
                )

                Spacer(
                    Modifier
                        .height(6.dp)
                        .fillMaxWidth()
                )

                Text(

                    text = if (trivia?.question.isNullOrBlank()) {
                        "No Question for Language: $language"
                    } else {
                        "Question:  ${trivia?.question}"
                    },
                    modifier = Modifier.padding(start = 4.dp)
                )

                Spacer(
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Color(R.color.flashcard_blue))
                )

                Spacer(
                    Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                )

                Spacer(
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Color(R.color.flashcard_blue))
                )

                Spacer(
                    Modifier
                        .height(6.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = if (trivia?.answer.isNullOrBlank()) {
                        "No Answer for Language: $language"
                    } else {
                        "Answer:  ${trivia?.answer}"
                    },
                    modifier = Modifier.padding(start = 4.dp),
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Color(R.color.flashcard_blue))
                )

                Spacer(
                    Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                )

                Spacer(
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Color(R.color.flashcard_blue))
                )
            }

        }
    }
}

@ExperimentalAnimationApi
@Composable
fun FullCard(question: String?, answer: String?) {

    var isQuestion by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.paper),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .clickable { isQuestion = !isQuestion }
        ) {

            Text(
                text = if (isQuestion) "Question:" else "Answer:",
                fontSize = 30.sp,
                fontFamily = FontFamily(
                    Font(R.font.gotham_medium, FontWeight.Bold)
                )
            )
            Text(
                text = if (isQuestion) question.toString() else answer.toString(),
                fontSize = 20.sp,
                fontFamily = FontFamily(
                    Font(R.font.gotham_medium, FontWeight.Normal)
                )
            )
        }
    }
}