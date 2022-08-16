package com.alexcrookes.medal_case.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.alexcrookes.medal_case.databinding.ComponentSectionHeaderBinding

class SectionHeader: ConstraintLayout {

	//
	// region properties
	//

	private lateinit var binding: ComponentSectionHeaderBinding

	var title: String = ""
		set(new) {
			field = new
			binding.title.text = new
		}

	var page: String = ""
		set(new) {
			field = new
			binding.page.text = new
		}

	// endregion


	//
	// region creation
	//

	constructor(context: Context): super(context)
	constructor(context: Context, attributes: AttributeSet): super(context, attributes) {
		initialize(context, attributes, 0)
	}
	constructor(
		context: Context, attributes: AttributeSet, defaultStyle: Int): super(context, attributes, defaultStyle
	) {
		initialize(context, attributes, defaultStyle)
	}

	// endregion


	//
	// region helpers
	//

	private fun initialize(context: Context, attributes: AttributeSet, style: Int) {
		val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		ComponentSectionHeaderBinding.inflate(inflater, this, true).apply { binding = this }
	}

	// endregion

}
