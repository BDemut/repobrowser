package com.defconapplications.repobrowser.repository

/**
 * ListFragment's domain model
 */
data class ShortRepo (
    val id : Int,
    val name: String,
    val description: String?,
    val main_language: String?
)