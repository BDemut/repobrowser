package com.defconapplications.repobrowser.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.defconapplications.repobrowser.repository.database.RoomRepo

@Dao
interface RepoDao {
    @Query("select id, name, description, main_language from roomrepo order by name collate nocase")
    fun getShortRepos(): LiveData<List<ShortRepo>>

    @Query("select id, name, url, html_url, description, forks_count, stargazers_count, open_issues_count," +
            " created_at, updated_at, languages from roomrepo where id = :id")
    fun getLongRepo(id : Int): LiveData<LongRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(vararg repos: RoomRepo)
    @Query("DELETE FROM roomrepo")
    fun deleteAll()
    @Transaction
    fun replaceRepos(vararg repos: RoomRepo) {
        deleteAll()
        insertRepos(*repos)
    }

    /**
     * Partial entity for language updates
     */
    @Entity
    data class RoomLanguages (
            @PrimaryKey
            val id : Int,
            val languages : List<String>
    )
    @Update(entity = RoomRepo::class)
    fun updateLanguagesForSingleRepo(languages: RoomLanguages)
}

@Database(entities = [RoomRepo::class], version = 1)
@TypeConverters(Converters::class)
abstract class RepoDatabase : RoomDatabase() {
    abstract val repoDao: RepoDao
}



