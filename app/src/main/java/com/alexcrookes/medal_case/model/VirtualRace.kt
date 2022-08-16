package com.alexcrookes.medal_case.model

import kotlinx.serialization.Serializable

@Serializable
data class VirtualRace(
	val title: String,
	val image: String,
	val duration: String
) {
	val imageUrl: String
		get() {
			return "virtual_races_assets/$image"
		}
}

