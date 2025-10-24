package com.example.librarymanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanager.R
import com.example.librarymanager.adapter.StudentAdapter
import com.example.librarymanager.model.Book
import com.example.librarymanager.model.Student

class StudentListActivity : AppCompatActivity() {

    private lateinit var rvStudents: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val students = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        rvStudents = findViewById(R.id.rvStudents)

        // Dữ liệu mẫu
        val book1 = Book(1, "Sách 01")
        val book2 = Book(2, "Sách 02")
        val book3 = Book(3, "Sách 03")

        students.addAll(
            listOf(
                Student(1, "Nguyen Van A", mutableListOf(book1, book2)),
                Student(2, "Nguyen Thi B", mutableListOf(book1)),
                Student(3, "Nguyen Van C", mutableListOf())
            )
        )

        adapter = StudentAdapter(students)

        rvStudents.layoutManager = LinearLayoutManager(this)
        rvStudents.adapter = adapter
    }
}
