package com.claire.carddiary.card

import androidx.core.net.toUri
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.claire.carddiary.CardApplication
import com.claire.carddiary.Resource
import com.claire.carddiary.data.CardRepository
import com.claire.carddiary.data.model.Card
import com.claire.carddiary.data.model.Post
import com.claire.carddiary.utils.SingleLiveEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CardViewModel(
    private val repository: CardRepository
) : ViewModel() {

    private val _cardList = SingleLiveEvent<PagingData<Card>>()
    val cardList: SingleLiveEvent<PagingData<Card>> = _cardList

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    private val _navigateToEdit = SingleLiveEvent<Unit>()
    val navigateToEdit: SingleLiveEvent<Unit> = _navigateToEdit

    private val _optionClick = SingleLiveEvent<Unit>()
    val optionClick: SingleLiveEvent<Unit> = _optionClick

    private val _clearSearch = SingleLiveEvent<Unit>()
    val clearSearch: SingleLiveEvent<Unit> = _clearSearch

    private val _clearEnable = MutableLiveData<Boolean>()
    val clearEnable: LiveData<Boolean> = _clearEnable

    private val _listType = SingleLiveEvent<Unit>()
    val listType: LiveData<Unit> = _listType

    private val _queryKeyword = MutableLiveData<String>()

    private val _progress = SingleLiveEvent<List<Post>>()
    val progress: SingleLiveEvent<List<Post>> = _progress

//    private val _stateFlow = MutableStateFlow("")

//    @ExperimentalCoroutinesApi
//    @FlowPreview
//    val searchResult = _stateFlow
//        .debounce(500)
//        .filter { s ->
//            return@filter s.isNotEmpty()
//        }
//        .flatMapLatest { s ->
//            repository.getCards(s).cachedIn(viewModelScope)
//        }
//        .catch {
//            _errorMsg.value = "Error query search result!"
//        }
//        .asLiveData()


    init {
        getCards()
    }

    fun getCards(key: String = "") = viewModelScope.launch {
        repository.getCards(key).cachedIn(viewModelScope).collect {
            _cardList.value = it
        }
    }

    fun searchQuery(s: String) {
        _queryKeyword.value = s
        _clearEnable.value = s.isNotBlank()
    }

    fun onSearch() {
        if (_queryKeyword.value.isNullOrBlank().not()) getCards(_queryKeyword.value.orEmpty())
    }

    fun navigateToEdit() = _navigateToEdit.call()

    fun optionClick() = _optionClick.call()

    fun clearSearch() = _clearSearch.call()

    fun changeListType() {
        CardApplication.isSingleRaw = CardApplication.isSingleRaw.not()
        _listType.call()
    }

    fun upload(card: Card) = viewModelScope.launch {
        var count = 0
        _progress.value = listOf(Post(card.images[0], 0))

        val photos = card.images.map { it.toUri() }

        val imagesUrl = runCatching {

            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                photos.map { value ->
                    async {
                        when(val resource = repository.uploadPhoto(value)) {
                            is Resource.Success -> {
                                withContext(Dispatchers.Main) {
                                    val progress = (count++ / photos.size.toDouble() * 100).toInt()
                                    _progress.value = _progress.value?.map { it.copy(progress = progress) }
                                    println(resource.data)
                                }
                                resource.data
                            }
                            else -> ""
                        }
                    }
                }
            }.awaitAll()

        }.getOrElse {
            _errorMsg.value = "Error upload photos"
            emptyList()
        }

        insertCard(card.copy(images = imagesUrl))
    }

    private fun insertCard(card: Card) = viewModelScope.launch {

        when(val resource = repository.insertCard(card)) {
            is Resource.Success -> {
                _progress.value = _progress.value?.map { it.copy(progress = 100) }
                delay(1500)
                getCards()
                _progress.value = listOf()
            }
            is Resource.NetworkError -> _errorMsg.value = resource.errorMessage
        }
    }

}