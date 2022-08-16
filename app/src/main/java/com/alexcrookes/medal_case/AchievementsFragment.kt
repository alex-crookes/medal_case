package com.alexcrookes.medal_case

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.alexcrookes.medal_case.api.AchievementsApi
import com.alexcrookes.medal_case.databinding.ComponentPersonalRecordBadgeBinding
import com.alexcrookes.medal_case.databinding.ComponentVirtualRaceBadgeBinding
import com.alexcrookes.medal_case.databinding.FragmentAchievementsBinding
import com.alexcrookes.medal_case.extensions.loadBadge
import com.alexcrookes.medal_case.model.PersonalRecord
import com.alexcrookes.medal_case.model.VirtualRace
import com.alexcrookes.medal_case.model.imageUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Displays the Achievements Pane
 */
class AchievementsFragment : Fragment() {

	//
	// region properties
	//

	private val api = AchievementsApi()
	private val scope = CoroutineScope(Dispatchers.Default)
	private var _binding: FragmentAchievementsBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	// endregion


	//
	// region implementation
	//

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

		scope.launch {
			val data = api.getAchievements()
			// @TODO - This would need ore time. The adapters below *will* not scroll well if the size changes
			binding.listVirtualRaces.adapter = VirtualRaceAdapter(data.virtualRaces)
			binding.listPersonalRecords.adapter = PersonalRecordsAdapter(data.personalRecords)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	// endregion


	//
	// region inner structures
	//

	inner class PersonalRecordsAdapter(
		private val records: List<PersonalRecord>
	): BaseAdapter() {
		private var layoutInflater: LayoutInflater? = null

		override fun getCount(): Int {
			return records.size
		}

		override fun getItem(position: Int): Any {
			return records[position]
		}

		override fun getItemId(position: Int): Long {
			return 0
		}

		override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
			var reuseView = view

			if (layoutInflater == null) {
				layoutInflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
			}

			if (reuseView == null) {
				val binding = ComponentPersonalRecordBadgeBinding.inflate(layoutInflater!!, parent, false)
				reuseView = binding.root
			}

			val record = records[position]

			val image = reuseView.findViewById<ImageView>(R.id.record_image)
			image.loadBadge(record.imageUrl)
			val title = reuseView.findViewById<TextView>(R.id.record_name)
			val property = reuseView.findViewById<TextView>(R.id.record_value)

			when (record) {
				is PersonalRecord.Elevation -> {
					title.text = record.title
					property.text = String.format("%.0f ft", record.height) // @TODO Should be localized
				}

				is PersonalRecord.LongestDistance -> {
					title.text = record.title
					property.text = record.duration
				}

				is PersonalRecord.FastestDistance -> {
					val achieved = record.achieved != null
					//@TODO - Dim the image, text /etc at this point - This may need a filter on the Glide loader,
						// but may be better handled with API data
					title.text = record.title
					property.text = if (achieved) record.duration else getString(R.string.not_yet)
				}
			}

			return reuseView
		}
	}


	inner class VirtualRaceAdapter(
		private val races: List<VirtualRace>
	): BaseAdapter() {
		private var layoutInflater: LayoutInflater? = null

		override fun getCount(): Int {
			return races.size
		}

		override fun getItem(position: Int): Any {
			return races[position]
		}

		override fun getItemId(position: Int): Long {
			return 0
		}

		override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
			var reuseView = view

			if (layoutInflater == null) {
				layoutInflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
			}

			if (reuseView == null) {
				val binding = ComponentVirtualRaceBadgeBinding.inflate(layoutInflater!!, parent, false)
				reuseView = binding.root
			}

			val race = races[position]

			val image = reuseView.findViewById<ImageView>(R.id.race_image)
			image.loadBadge(race.imageUrl)
			val title = reuseView.findViewById<TextView>(R.id.race_name)
			title.text = race.title
			val duration = reuseView.findViewById<TextView>(R.id.race_time)
			duration.text = race.duration

			return reuseView
		}
	}

	// endregion
}
