package com.alexcrookes.medal_case.api

import com.alexcrookes.medal_case.Storage
import com.alexcrookes.medal_case.model.AchievementsResponse
import kotlinx.serialization.json.Json

class AchievementsApi {
	private val json: Json = Json {
		ignoreUnknownKeys = true
		encodeDefaults = true
		isLenient = true
	}

	suspend fun getAchievements(): AchievementsResponse {
		Storage().getResourceFile("/api.json")?.let {
			return json.decodeFromString(AchievementsResponse.serializer(), it)
		}

		return AchievementsResponse(emptyList(), emptyList())
	}
}