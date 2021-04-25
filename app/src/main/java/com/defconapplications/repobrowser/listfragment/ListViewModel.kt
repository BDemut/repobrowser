package com.defconapplications.repobrowser.listfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.defconapplications.repobrowser.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val errorMessage = MutableLiveData<String?>(null)

    var user = repository.user
    val repoList = repository.shortRepos

    fun fetchRepoData(newUser : String? = null) {
        viewModelScope.launch {
                errorMessage.value = repository.fetchRepos(newUser)
            }
        }

    fun searchUser(user : String) {
        val lUser = user.toLowerCase()
        if (this.user.value != lUser) {
            fetchRepoData(lUser)
        }
    }
}