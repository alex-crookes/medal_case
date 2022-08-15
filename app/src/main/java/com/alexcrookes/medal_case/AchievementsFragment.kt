package com.alexcrookes.medal_case

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexcrookes.medal_case.databinding.FragmentAchievementsBinding

/**
 * Displays the Achievements Pane
 */
class AchievementsFragment : Fragment() {

	private var _binding: FragmentAchievementsBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAchievementsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		with (binding.sectionPersonalRecords) {
			page = "4 of 6"
			title = getString(R.string.personal_records)
		}

		with (binding.sectionVirtualRaces) {
			page = ""
			title = getString(R.string.virtual_races)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}