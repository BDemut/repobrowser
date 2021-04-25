package com.defconapplications.repobrowser.detailsfragment

import androidx.lifecycle.*
import com.defconapplications.repobrowser.repository.LongRepo
import com.defconapplications.repobrowser.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
        savedStateHandle : SavedStateHandle,
        val repository: Repository) : ViewModel() {

    val repo = repository.getRepo(
            savedStateHandle["id"] ?:
                    throw IllegalArgumentException("missing id"))
    var errorMessage = MutableLiveData<String?>(null)

    fun getLanguageData() {
        viewModelScope.launch {
            repo.value?.url?.let {
                errorMessage.value = repository.downloadLanguages(it, repo.value!!.id)
            }
        }
    }
}