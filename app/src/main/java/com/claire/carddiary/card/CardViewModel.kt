package com.claire.carddiary.card

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.claire.carddiary.Resource
import com.claire.carddiary.data.CardRepository
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.model.Post
import com.claire.carddiary.utils.SingleLiveEvent
import com.claire.carddiary.utils.toSimpleDateFormat
import kotlinx.coroutines.*
import java.io.InputStream
import java.util.*

class CardViewModel(
    private val repository: CardRepository
) : ViewModel() {

    private val _cardList = SingleLiveEvent<List<Card>>()
    val cardList: SingleLiveEvent<List<Card>> = _cardList

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val _navigateToEdit = SingleLiveEvent<Any>()
    val navigateToEdit: SingleLiveEvent<Any> = _navigateToEdit

    private val _isExpand = MutableLiveData(false)
    val isExpand: LiveData<Boolean> = _isExpand

    private val _fabClick = SingleLiveEvent<Int>()
    val fabClick: SingleLiveEvent<Int> = _fabClick

    private var _card = Card.Empty

    private var _post = Post()

    private var imagesUrl = listOf<String>()

    private var count = 0

    private val _progress = SingleLiveEvent<List<Post>>()
    val progress: SingleLiveEvent<List<Post>> = _progress



    init {
        getCards()
    }

    private fun getCards() = viewModelScope.launch {

        when(val resource = repository.getCards()) {
            is Resource.Success -> _cardList.value = resource.data
            is Resource.NetworkError -> _errorMsg.value = resource.errorMessage
        }

    }

    fun getCard(index: Int): Card {
        return _cardList.value?.getOrNull(index) ?: Card()
    }

    fun navigateToEdit() {
        _navigateToEdit.call()
    }

    fun setExpand() {
        _isExpand.value = _isExpand.value?.not()
    }

    fun fabClick(position: Int) {
        _fabClick.value = position
    }

    fun uploadPhotos(card: Card, inputStreams: List<InputStream>) = viewModelScope.launch {
        count = 0
        _card = card
        _progress.value = listOf(Post(card.images[0], 0))

        val pathString = _card.images.map { it.toUri().lastPathSegment ?: Date().toSimpleDateFormat }


        imagesUrl = try {

            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                inputStreams.mapIndexed { index, value ->
                    async {
                        when(val resource = repository.uploadPhoto(pathString[index], value)) {
                            is Resource.Success -> {
                                withContext(Dispatchers.Main) {
                                    _post = _post.copy(progress = (count++ / pathString.size.toDouble() * 100).toInt())
                                    _progress.value = listOf(_post)
                                    println(resource.data)
                                }
                                resource.data
                            }
                            else -> ""
                        }
                    }
                }
            }.awaitAll()

        } catch (e: Exception) {
            _errorMsg.value = "Error upload photos"
            listOf()
        }

        insertCard()
    }

    private fun insertCard() = viewModelScope.launch {
        _card = _card.copy(images = imagesUrl)

        when(val resource = repository.insertCard(_card)) {
            is Resource.Success -> {
                _post = _post.copy(progress = 100)
                _progress.value = listOf(_post)
                delay(1500)
                getCards()
                _progress.value = listOf()
            }
            is Resource.NetworkError -> _errorMsg.value = resource.errorMessage
        }

    }

}