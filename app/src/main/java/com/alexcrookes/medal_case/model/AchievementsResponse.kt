package com.alexcrookes.medal_case.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AchievementsResponse(
	@SerialName("personal_records") val personalRecord: List<PersonalRecord>,
	@SerialName("virtual_races") val virtualRaces: List<VirtualRace>
)
