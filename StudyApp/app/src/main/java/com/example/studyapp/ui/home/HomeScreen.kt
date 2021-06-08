package com.example.studyapp.ui.home


import android.app.Activity
import com.example.studyapp.R
import android.graphics.Typeface.BOLD
import android.graphics.drawable.Drawable
import android.graphics.fonts.FontStyle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyapp.realm.RealmHelper.getFlashCards
import com.example.studyapp.realm.models.FlashCard
import com.example.studyapp.realm.models.Trivia
import com.example.studyapp.realm.realm
import io.realm.RealmList
import io.realm.RealmResults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.studyapp.MainViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.studyapp.realm.RealmHelper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.material.FabPosition.Companion.Center


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
        TopBar(viewModel)
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
fun FullCard(answer: String?, question: String?) {

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
                text = if (isQuestion) "Question:" else "Answer:"
            )
            Text(
                text = if (isQuestion) question.toString() else answer.toString()
            )
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

        LazyColumn {
            items(flashCards.size) { index ->
                val trivia =
                    flashCards[index]?.triviaList?.find { (it as Trivia).language == language }
                val title = flashCards[index]!!.title!!

                FlashCard(title, trivia, onClick = {
                    Log.e("WES_TEST", "CLICK")
                    navController.navigate("full_card/${trivia!!.question}/${trivia.answer}")
                })
            }
        }
    }
}

@Composable
fun TopBar(viewModel: MainViewModel) {

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

        ) {

            Button(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 14.dp),
                onClick = {
                    //
                }
            ) {
                Text("Add Card")
            }
        }
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
    onClick: () -> Unit,
) {
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
                    text = "Question: ${trivia?.question}",
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
                    text = "Answer: ${trivia?.answer}",
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


//            Text(
//                "A day wandering through the sandhills " +
//                        "in Shark Fin Cove, and a few of the " +
//                        "sights I saw",
//                style = typography.h6,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .padding(16.dp)
//                    .border(2.dp, MaterialTheme.colors.secondary, shape)
//                    .background(MaterialTheme.colors.primary, shape)
//                    .padding(16.dp)
//            )
//            Text(
//                "Davenport, California",
//                style = typography.body2
//            )
//            Text(
//                "December 2018",
//                style = typography.body2
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    //  ScrollBoxes()

    // create dummy data

    //  FlashCardList(flashCards = list as RealmResults<FlashCard>, "English")
}