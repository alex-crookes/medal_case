package com.alexcrookes.medal_case.model

import kotlinx.serialization.Serializable

@Serializable
data class VirtualRace(
	val title: String,
	val image: String,
	val duration: String
)
