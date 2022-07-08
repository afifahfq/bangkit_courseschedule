package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var currView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_course)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val name: String = findViewById<TextInputEditText>(R.id.ed_course_name).text.toString()
                val day: Int = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
                val startTime: String = findViewById<TextView>(R.id.tv_start_time).text.toString()
                val endTime: String = findViewById<TextView>(R.id.tv_end_time).text.toString()
                val lecturer: String = findViewById<TextInputEditText>(R.id.ed_lecturer).text.toString()
                val note: String = findViewById<TextInputEditText>(R.id.ed_note).text.toString()

                viewModel.insertCourse(name, day, startTime, endTime, lecturer, note)
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showStartTimePicker(view: View) {
        val startTimePicker = TimePickerFragment().show(
            supportFragmentManager, " startTimePicker"
        )
        currView = view
    }

    fun showEndTimePicker(view: View) {
        val endTimePicker = TimePickerFragment().show(
            supportFragmentManager, " endTimePicker"
        )
        currView = view
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (currView.id) {
            R.id.ib_start_time -> findViewById<TextView>(R.id.tv_start_time).text = timeFormat.format(calendar.time)
            R.id.ib_end_time -> findViewById<TextView>(R.id.tv_end_time).text = timeFormat.format(calendar.time)
        }
    }
}