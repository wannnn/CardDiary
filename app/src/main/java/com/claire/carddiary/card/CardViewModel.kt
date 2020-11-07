package com.claire.carddiary.card

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.claire.carddiary.Resource
import com.claire.carddiary.data.CardRepository
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.model.Post
import com.claire.carddiary.utils.SingleLiveEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.singleOrNull

class CardViewModel(
    private val repository: CardRepository
) : ViewModel() {

    private val _cardList = SingleLiveEvent<PagingData<Card>>()
    val cardList: SingleLiveEvent<PagingData<Card>> = _cardList

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val _navigateToEdit = SingleLiveEvent<Any>()
    val navigateToEdit: SingleLiveEvent<Any> = _navigateToEdit

    private val _isExpand = MutableLiveData(false)
    val isExpand: LiveData<Boolean> = _isExpand

    private val _fabClick = SingleLiveEvent<Int>()
    val fabClick: SingleLiveEvent<Int> = _fabClick

    private var _card = Card()

    private var _post = Post()

    private var imagesUrl = listOf<String>()

    private var count = 0

    private val _progress = SingleLiveEvent<List<Post>>()
    val progress: SingleLiveEvent<List<Post>> = _progress



    init {
        getCards()
    }

    private fun getCards() = viewModelScope.launch {

        repository.getCards().cachedIn(viewModelScope).collect {
            _cardList.value = it
        }
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

    fun upload(card: Card) = viewModelScope.launch {
        count = 0
        _card = card
        _progress.value = listOf(Post(card.images[0], 0))

        val photos = card.images.map { it.toUri() }

        imagesUrl = try {

            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                photos.map { value ->
                    async {
                        when(val resource = repository.uploadPhoto(value)) {
                            is Resource.Success -> {
                                withContext(Dispatchers.Main) {
                                    _post = _post.copy(progress = (count++ / photos.size.toDouble() * 100).toInt())
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