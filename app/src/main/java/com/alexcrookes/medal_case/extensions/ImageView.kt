package com.alexcrookes.medal_case.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

private fun gitHubUrlTo(path: String): String = "https://alex-crookes.github" +
		".io/ubiquitous-octo-computing-machine/images/$path"

fun ImageView.loadBadge(path: String) {
	val url = gitHubUrlTo(path)
	Glide.with(this).load(url).into(this)
}
