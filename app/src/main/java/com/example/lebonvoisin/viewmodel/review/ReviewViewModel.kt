package com.example.lebonvoisin.viewmodel.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lebonvoisin.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel@Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    var message by mutableStateOf("")

    fun createReview(sellerId: String,
                     rating: Int,
                     title : String,
                     comment: String) {
        //userRepository.createReview(userId, rating, comment)
    }



}