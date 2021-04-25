package com.defconapplications.repobrowser.repository.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
data class RoomRepo(
        @PrimaryKey
        val id : Int,
        val name: String,
        val url: String,
        val html_url: String,
        val description: String?,
        val main_language: String?,
        val forks_count: Int,
        val stargazers_count: Int,
        val open_issues_count: Int,
        val created_at: String,
        val updated_at: String,
        val languages : List<String>?)

