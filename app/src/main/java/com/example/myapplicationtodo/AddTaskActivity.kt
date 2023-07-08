package com.example.myapplicationtodo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var editTextTask: EditText
    private lateinit var buttonSetDate: Button
    private lateinit var textViewDueDate: TextView
    private lateinit var radioGroupCategory: RadioGroup
    private lateinit var buttonAddTask: Button

    private lateinit var calendar: Calendar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        editTextTask = findViewById(R.id.editTextTask)
        buttonSetDate = findViewById(R.id.buttonSetDate)
        textViewDueDate = findViewById(R.id.textViewDueDate)
        radioGroupCategory = findViewById(R.id.radioGroupCategory)
        buttonAddTask = findViewById(R.id.buttonAddTask)

        calendar = Calendar.getInstance()

        buttonSetDate.setOnClickListener {
            showDatePickerDialog()
        }

        buttonAddTask.setOnClickListener {
            addTask()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                showTimePickerDialog()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateDueDate()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false        )
        timePickerDialog.show()
    }

    private fun updateDueDate() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        val dueDate = dateFormat.format(calendar.time)
        textViewDueDate.text = dueDate
        textViewDueDate.visibility = TextView.VISIBLE
    }

    private fun addTask() {
        val task = editTextTask.text.toString()
        val category = when (radioGroupCategory.checkedRadioButtonId) {
            R.id.radioButtonProfessional -> "Professional"
            R.id.radioButtonPersonal -> "Personal"
            else -> ""
        }
        val dueDate = textViewDueDate.text.toString()

        if (task.isNotEmpty() && dueDate.isNotEmpty() && category.isNotEmpty()) {
            val intent = Intent()
            intent.putExtra("task", task)
            intent.putExtra("dueDate", dueDate)
            intent.putExtra("category", category)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}

