package com.example.librarymanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanager.R
import com.example.librarymanager.adapter.BookAdapter
import com.example.librarymanager.model.Book
import com.example.librarymanager.model.Student

class MainActivity : AppCompatActivity() {

    private lateinit var etStudentName: EditText
    private lateinit var btnChangeStudent: Button
    private lateinit var btnAddBook: Button
    private lateinit var rvBooks: RecyclerView

    private val students = mutableListOf<Student>()
    private var currentStudent: Student? = null

    private val books = mutableListOf(
        Book(1, "Sách 01"),
        Book(2, "Sách 02"),
        Book(3, "Sách 03")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etStudentName = findViewById(R.id.etStudentName)
        btnChangeStudent = findViewById(R.id.btnChangeStudent)
        btnAddBook = findViewById(R.id.btnAddBook)
        rvBooks = findViewById(R.id.rvBooks)

        rvBooks.layoutManager = LinearLayoutManager(this)
        val adapter = BookAdapter(books) { book ->
            currentStudent?.let {
                if (book.isBorrowed && !it.borrowedBooks.contains(book)) {
                    it.borrowedBooks.add(book)
                } else if (!book.isBorrowed) {
                    it.borrowedBooks.remove(book)
                }
            }
        }
        rvBooks.adapter = adapter

        btnChangeStudent.setOnClickListener {
            val name = etStudentName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên sinh viên", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            currentStudent = students.find { it.name == name } ?: Student(students.size + 1, name)
            if (!students.contains(currentStudent)) students.add(currentStudent!!)

            Toast.makeText(this, "Đang quản lý: $name", Toast.LENGTH_SHORT).show()

            // cập nhật danh sách sách đã mượn
            books.forEach { it.isBorrowed = currentStudent!!.borrowedBooks.contains(it) }
            adapter.notifyDataSetChanged()
        }

        btnAddBook.setOnClickListener {
            Toast.makeText(this, "Nhấn vào checkbox để mượn/trả sách", Toast.LENGTH_SHORT).show()
        }
    }
}
