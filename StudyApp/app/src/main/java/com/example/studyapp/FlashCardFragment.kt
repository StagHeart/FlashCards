package com.example.studyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.studyapp.realm.RealmHelper.getFlashCards
import com.example.studyapp.ui.home.FlashCardList
import com.example.studyapp.ui.home.MainScreen

class FlashCardFragment : Fragment() {

    private lateinit var viewModel: MainViewModel



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this as ViewModelStoreOwner).get(MainViewModel::class.java)

    }

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setContent {

                MainScreen(viewModel, getFlashCards())
            }
        }
    }
}