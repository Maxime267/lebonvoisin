package com.example.lebonvoisin.viewmodel.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lebonvoisin.dataclass.Annonce
import com.example.lebonvoisin.dataclass.review.Review
import com.example.lebonvoisin.dataclass.review.ReviewUI
import com.example.lebonvoisin.repository.ReviewRepository
import com.example.lebonvoisin.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel@Inject constructor(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    var message by mutableStateOf("")

    private val _state = MutableStateFlow<Boolean?>(null)
    val state = _state.asStateFlow()

    fun createReviewByCurrentUser(sellerId: String,
                                  rating: Int,
                                  title : String,
                                  comment: String) {
        viewModelScope.launch {
            try {
                message = reviewRepository.createReview(
                    Review(
                        "",
                        sellerId = sellerId,
                        rating = rating,
                        title = title,
                        comment = comment
                    )
                )
                _state.value = true
            }catch (e: Exception){
                _state.value = false
                message = e.toString()
            }
        }
    }

    //=============
    // See reviews
    //=============


    var myReviews by mutableStateOf(listOf<ReviewUI>())
    var reviewsOnMe by mutableStateOf(listOf<ReviewUI>())

    fun loadReviews(){
        viewModelScope.launch {
            var tempo = reviewRepository.loadReviewsByOwnerCurrentUser()
            myReviews = enrichReviews(tempo)
            tempo = reviewRepository.loadReviewsBySellerCurrentUser()
            reviewsOnMe = enrichReviews(tempo)
        }
    }
    suspend fun enrichReviews(reviews: List<Review>): List<ReviewUI> {
        return reviews.map { review ->

            val owner = userRepository.getUserById(review.ownerId)
            val seller = userRepository.getUserById(review.sellerId)

            ReviewUI(
                ownerId = review.ownerId,
                ownerName = owner?.name ?: "Inconnu",
                sellerId = review.sellerId,
                sellerName = seller?.name ?: "Inconnu",
                rating = review.rating,
                title = review.title,
                comment = review.comment
            )
        }
    }

    fun avgReview(): Double {
        val allRatings = reviewsOnMe.map { it.rating }

        if (allRatings.isEmpty()) return 0.0

        return allRatings.average()
    }



}