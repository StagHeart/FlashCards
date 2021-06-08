package com.example.studyapp.ui.full_card


import android.app.Activity
import com.example.studyapp.R
import android.graphics.Typeface.BOLD
import android.graphics.drawable.Drawable
import android.graphics.fonts.FontStyle
import android.util.Log
import android.view.View
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.studyapp.realm.RealmHelper
import com.example.studyapp.ui.home.FlashCard




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

  //  FullCard()
}