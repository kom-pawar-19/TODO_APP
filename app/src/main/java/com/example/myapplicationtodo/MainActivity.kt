package com.example.myapplicationtodo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddTask: Button
    private val addTaskRequestCode = 1
    private lateinit var linearLayoutTasks: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddTask = findViewById(R.id.buttonAddTask)
        linearLayoutTasks = findViewById(R.id.linearLayoutTasks)

        buttonAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, addTaskRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addTaskRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                val task = data?.getStringExtra("task")
                val dueDate = data?.getStringExtra("dueDate")
                val category = data?.getStringExtra("category")

                if (task != null && dueDate != null && category != null) {
                    val newTaskView = layoutInflater.inflate(R.layout.task_item, null)
                    val textViewTask = newTaskView.findViewById<TextView>(R.id.textViewTask)
                    val checkBoxTask = newTaskView.findViewById<CheckBox>(R.id.checkBoxTask)

                    val newTask = "Task: $task\nDue Date: $dueDate\nCategory: $category"
                    textViewTask.text = newTask

                    linearLayoutTasks.addView(newTaskView)

                    checkBoxTask.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            linearLayoutTasks.removeView(newTaskView)
                        } else {
                            textViewTask.paintFlags = textViewTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                        }
                    }

                    textViewTask.setOnClickListener {
                        if (checkBoxTask.isChecked) {
                            checkBoxTask.isChecked = false
                            textViewTask.paintFlags = textViewTask.paintFlags and Paint                        .STRIKE_THRU_TEXT_FLAG.inv()
                        } else {
                            checkBoxTask.isChecked = true
                            textViewTask.paintFlags = textViewTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Task not added", Toast.LENGTH_SHORT).show()
            }
        }
    }
}




