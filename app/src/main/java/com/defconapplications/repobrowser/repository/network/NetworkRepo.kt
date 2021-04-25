package com.defconapplications.repobrowser.repository.network

import android.os.Parcelable
import com.defconapplications.repobrowser.repository.database.RoomRepo
import kotlinx.parcelize.Parcelize

data class NetworkRepo(
    val id : Int,
    val name: String,
    val url: String,
    val html_url: String,
    val description: String?,
    val language: String?,
    val forks_count: Int,
    val stargazers_count: Int,
    val open_issues_count: Int,
    val created_at: String,
    val updated_at: String)
