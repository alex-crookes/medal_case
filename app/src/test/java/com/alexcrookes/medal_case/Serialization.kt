package com.alexcrookes.medal_case

import com.alexcrookes.medal_case.model.PersonalRecord
import com.alexcrookes.medal_case.model.VirtualRecord
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


	// region: Virtual Records

	private val virtualRecordOne = VirtualRecord("title", "image_url", "00:00")
	private val virtualRecordOneAsJson = """{"title":"title","image":"image_url","duration":"00:00"}"""

	@Test
	fun virtualRecord_Encode() {
		val jsonText = json.encodeToString(VirtualRecord.serializer(), virtualRecordOne)
		assertEquals(jsonText, virtualRecordOneAsJson)
	}

	@Test
	fun virtualRecord_Decode() {
		val asClass = json.decodeFromString(VirtualRecord.serializer(), virtualRecordOneAsJson)
		assertEquals(asClass, virtualRecordOne)
	}

	// endregion

	private val distanceRecord = PersonalRecord.Distance(
		"distance", "00:00:00", "image_url", 1000L
	)
	private val distanceRecordAsJson = """{"type":"DISTANCE","title":"distance","duration":"00:00:00",""" +
		""""image":"image_url","achieved":1000}""".trimMargin()
	@Test
	fun personalRecord_Encode() {
		val jsonText = json.encodeToString(PersonalRecord.serializer(), distanceRecord)
		assertEquals(jsonText, distanceRecordAsJson)
	}

	@Test
	fun personalRecord_Decode() {
		val obj = json.decodeFromString(PersonalRecord.serializer(), distanceRecordAsJson)
		assertEquals(obj, distanceRecord)
	}

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
}


// "title":"title","image":"image_url","duration":"00:00"
//