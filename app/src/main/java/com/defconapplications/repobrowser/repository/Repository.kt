package com.defconapplications.repobrowser.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.defconapplications.repobrowser.repository.network.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
        @ApplicationContext appContext: Context
) {
    val database : RepoDatabase = Room.databaseBuilder(appContext.applicationContext,
            RepoDatabase::class.java,
            "repos").build()
    private val apiService : ApiService by lazy {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl( "https://api.github.com/users/")
                .build()
        retrofit.create(ApiService::class.java)
    }
    private val sharedPref = appContext.getSharedPreferences("repository_shared_pref",Context.MODE_PRIVATE)


    val shortRepos: LiveData<List<ShortRepo>> = database.repoDao.getShortRepos()
    val user = MutableLiveData<String>()
    init {
        val cachedUser = sharedPref?.getString("user", null)
        if (cachedUser == null) {
            user.value = "allegro"
            CoroutineScope(Dispatchers.Main).launch{fetchRepos()}
        } else {
            user.value = cachedUser!!
        }
    }

    /**
     * Returns:
     * A LongRepo LiveData object for the given id
     */
    fun getRepo(id : Int) : LiveData<LongRepo> {
        return database.repoDao.getLongRepo(id)
    }

    /**
     * Returns:
     * An error message if failed or null if succeeded
     */
    suspend fun fetchRepos(newUser: String? = null): String? {
        val errorMessage = withContext(Dispatchers.IO) {
            try {
                if (newUser == null) {
                    val repos = apiService.getRepos(user.value!!).await()
                    database.repoDao.replaceRepos(*repos.asRoomRepoDatabaseModel())
                } else {
                    val repos = apiService.getRepos(newUser).await()
                    database.repoDao.replaceRepos(*repos.asRoomRepoDatabaseModel())
                    sharedPref?.edit()?.putString("user", newUser)?.apply()
                }
                null
            } catch (e : Exception) {
                "Network call faliure: ${e.message}"
            }
        }
        if (errorMessage == null && newUser != null) {
            user.value = newUser!!
        }
        return errorMessage
    }

    /**
     * Returns:
     * An error message if failed or null if succeeded
     */
    suspend fun downloadLanguages(url: String, id: Int): String? {
        return withContext(Dispatchers.IO) {
            try {
                val langs = apiService.getRepoLanguages(url).await()
                database.repoDao.updateLanguagesForSingleRepo(langs.asRoomLanguagesDatabaseModel(id))
                null
            } catch (e : Exception) {
                "Network call faliure: ${e.message}"
            }

        }
    }

}
