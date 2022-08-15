package com.alexcrookes.medal_case

class Storage {
	fun getResourceFile(fileName: String): String? = javaClass.getResourceAsStream(fileName)?.let { stream ->
		val stringValue = stream.bufferedReader().use { it.readText() }
		stream.close()
		return stringValue
	}
}
