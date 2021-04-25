package com.defconapplications.repobrowser.repository

import androidx.room.TypeConverter
import com.defconapplications.repobrowser.parseLanguages
import com.defconapplications.repobrowser.repository.database.RoomRepo
import com.defconapplications.repobrowser.repository.network.NetworkRepo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun List<NetworkRepo>.asRoomRepoDatabaseModel() : Array<RoomRepo> {
    return map {
        it.asRoomRepoDatabaseModel()
    }.toTypedArray()
}
fun Map<String, Int>.asRoomLanguagesDatabaseModel(id: Int) : RepoDao.RoomLanguages {
    return RepoDao.RoomLanguages(id, parseLanguages(this))
}
fun NetworkRepo.asRoomRepoDatabaseModel(): RoomRepo {
    return RoomRepo(
            id = this.id,
            name = this.name,
            url = this.url,
            html_url = this.html_url,
            description = this.description,
            main_language = this.language,
            forks_count = this.forks_count,
            stargazers_count = this.stargazers_count,
            open_issues_count = this.open_issues_count,
            created_at = this.created_at,
            updated_at = this.updated_at,
            languages = null
    )
}

private val gson : Gson by lazy {
    Gson()
}

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        return gson.fromJson(value, object : TypeToken<List<String?>?>() {}.type)
    }

    @TypeConverter
    fun fromList(list: List<String?>?): String? {
        return gson.toJson(list)
    }
}