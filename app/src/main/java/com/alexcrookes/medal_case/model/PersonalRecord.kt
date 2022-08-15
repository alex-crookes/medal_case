package com.alexcrookes.medal_case.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias Epoch = Long


// https://alex-crookes.github.io/ubiquitous-octo-computing-machine/images/personal_records_assets/fastest_10k.png

/**
 * Data Definition for the Personal Records
 *
 * Sealed class that defines the format of the Personal Record. Either
 * _Distance_ or _Elevation_ at this time
 */
@Serializable
sealed class PersonalRecord {
	@Serializable @SerialName("FASTEST_DISTANCE")
	data class FastestDistance(
		val title: String,
		val duration: String,
		val image: String,
		val achieved: Epoch? = null): PersonalRecord()

	@Serializable @SerialName("LONGEST_DISTANCE")
	data class LongestDistance(
		val title: String,
		val duration: String,
		val distance: Double,
		val image: String,
		val achieved: Epoch): PersonalRecord()

	@Serializable @SerialName("ELEVATION")
	data class Elevation(
		val title: String,
		val height: Double, // in meters
		val image: String,
		val achieved: Epoch): PersonalRecord()
}
