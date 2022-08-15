package com.alexcrookes.medal_case.model

import kotlinx.serialization.Serializable

@Serializable
data class VirtualRecord(
	val title: String,
	val image: String,
	val duration: String
)
