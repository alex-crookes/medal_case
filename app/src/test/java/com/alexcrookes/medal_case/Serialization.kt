package com.alexcrookes.medal_case

import com.alexcrookes.medal_case.model.ApiResponse
import com.alexcrookes.medal_case.model.PersonalRecord
import com.alexcrookes.medal_case.model.VirtualRace
import kotlinx.serialization.builtins.ListSerializer
import org.junit.Test
import kotlinx.serialization.json.Json

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SerializationTest {

	private val json: Json = Json {
		ignoreUnknownKeys = true
		encodeDefaults = true
		isLenient = true
	}


	//
	// region: Virtual Races
	//

	private val virtualRecordOne = VirtualRace("title", "image_url", "00:00")
	private val virtualRecordOneAsJson = """{"title":"title","image":"image_url","duration":"00:00"}"""

	@Test
	fun virtualRecord_Encode() {
		val jsonText = json.encodeToString(VirtualRace.serializer(), virtualRecordOne)
		assertEquals(jsonText, virtualRecordOneAsJson)
	}

	@Test
	fun virtualRecord_Decode() {
		val asClass = json.decodeFromString(VirtualRace.serializer(), virtualRecordOneAsJson)
		assertEquals(asClass, virtualRecordOne)
	}

	// endregion


	//
	// region: Distance Records
	//

	private val fastestDistanceRecord = PersonalRecord.FastestDistance(
		"distance", "00:00:00", "image_url", 1000L
	)
	private val fastestDistanceRecordAsJson = """{"type":"FASTEST_DISTANCE","title":"distance",""" +
		""""duration":"00:00:00","image":"image_url","achieved":1000}""".trimMargin()

	private val fastestDistanceRecordNotAchieved = PersonalRecord.FastestDistance(
		"distance", "00:00:00", "image_url", null
	)
	private val fastestDistanceRecordNotAchievedAsJson = """{"type":"FASTEST_DISTANCE","title":"distance",""" +
			""""duration":"00:00:00","image":"image_url","achieved":null}""".trimMargin()

	private val fastestDistanceRecordNotAchievedAsJsonNoNull = """{"type":"FASTEST_DISTANCE","title":"distance",""" +
			""""duration":"00:00:00","image":"image_url"}""".trimMargin()

	@Test
	fun fastestRecord_Encode() {
		val jsonText = json.encodeToString(PersonalRecord.serializer(), fastestDistanceRecord)
		assertEquals(jsonText, fastestDistanceRecordAsJson)
	}

	@Test
	fun fastestRecord_Decode() {
		val obj = json.decodeFromString(PersonalRecord.serializer(), fastestDistanceRecordAsJson)
		assertEquals(obj, fastestDistanceRecord)
	}


	@Test
	fun fastestRecordUnachieved_Encode() {
		val jsonText = json.encodeToString(PersonalRecord.serializer(), fastestDistanceRecordNotAchieved)
		assertEquals(jsonText, fastestDistanceRecordNotAchievedAsJson)
	}

	@Test
	fun fastestRecordUnachieved_Decode() {
		val obj = json.decodeFromString(PersonalRecord.serializer(), fastestDistanceRecordNotAchievedAsJson)
		assertEquals(obj, fastestDistanceRecordNotAchieved)
	}
	@Test
	fun fastestRecordUnachievedNoNull_Decode() {
		val obj = json.decodeFromString(PersonalRecord.serializer(), fastestDistanceRecordNotAchievedAsJsonNoNull)
		assertEquals(obj, fastestDistanceRecordNotAchieved)
	}


	private val longestDistanceRecord = PersonalRecord.LongestDistance(
		"distance", "00:00:00", 100000.0,"image_url", 1000L
	)
	private val longestDistanceRecordAsJson = """{"type":"LONGEST_DISTANCE","title":"distance",""" +
		""""duration":"00:00:00","distance":100000.0,""" +
		""""image":"image_url","achieved":1000}""".trimMargin()

	@Test
	fun longestRecord_Encode() {
		val jsonText = json.encodeToString(PersonalRecord.serializer(), longestDistanceRecord)
		assertEquals(jsonText, longestDistanceRecordAsJson)
	}

	@Test
	fun longestRecord_Decode() {
		val obj = json.decodeFromString(PersonalRecord.serializer(), longestDistanceRecordAsJson)
		assertEquals(obj, longestDistanceRecord)
	}

	// endregion


	//
	// region: Elevation Records
	//

	private val elevationRecord = PersonalRecord.Elevation(
		"elevation", 12.2, "image_url", 1000L
	)
	private val elevationRecordAsJson = """{"type":"ELEVATION","title":"elevation","height":12.2,""" +
		""""image":"image_url","achieved":1000}""".trimMargin()

	@Test
	fun elevationRecord_Encode() {
		val jsonText = json.encodeToString(PersonalRecord.serializer(), elevationRecord)
		assertEquals(jsonText, elevationRecordAsJson)
	}

	@Test
	fun elevationRecord_Decode() {
		val obj = json.decodeFromString(PersonalRecord.serializer(), elevationRecordAsJson)
		assertEquals(obj, elevationRecord)
	}

	// endregion


	//
	// region Groups
	//

	@Test
	fun decodeLocalRecordsCollection() {
		val data = Storage().getResourceFile("/personal_records.json")
		assertNotNull(data)

		data?.let {
			val objects = json.decodeFromString(ListSerializer(PersonalRecord.serializer()), it)

			assertEquals(objects.size, 6)
		}
	}

	@Test
	fun decodeLocalRacesCollection() {
		val data = Storage().getResourceFile("/virtual_races.json")
		assertNotNull(data)

		data?.let {
			val objects = json.decodeFromString(ListSerializer(VirtualRace.serializer()), it)

			assertEquals(objects.size, 7)
		}
	}

	@Test
	fun decodeLocalApiResponse() {
		val data = Storage().getResourceFile("/api.json")
		assertNotNull(data)

		data?.let {
			val api = json.decodeFromString(ApiResponse.serializer(), it)

			assertEquals(api.virtualRaces.size, 7)
			assertEquals(api.personalRecord.size, 6)
		}
	}

	// endregion
}
