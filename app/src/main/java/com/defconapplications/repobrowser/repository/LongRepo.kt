package com.defconapplications.repobrowser.repository

/**
 * DetailFragment's domain model
 */
data class LongRepo(
        val id : Int,
        val name: String,
        val url: String,
        val html_url: String,
        val description: String?,
        val forks_count: Int,
        val stargazers_count: Int,
        val open_issues_count: Int,
        val created_at: String,
        val updated_at: String,
        val languages : List<String>?)