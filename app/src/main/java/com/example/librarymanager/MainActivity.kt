package com.example.librarymanager

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanager.adapter.BookAdapter
import com.example.librarymanager.model.Book
import com.example.librarymanager.model.Student

class MainActivity : AppCompatActivity() {

    private lateinit var etStudentName: EditText
    private lateinit var btnChangeStudent: Button
    private lateinit var btnAddBook: Button
    private lateinit var rvBooks: RecyclerView
    private lateinit var tvBookList: TextView

    private val students = mutableListOf<Student>()
    private var currentStudent: Student? = null

    // ✅ Tao san 10 sach ban dau
    private val allBooks = mutableListOf<Book>().apply {
        for (i in 1..10) {
            add(Book(i, "Ten sach $i"))
        }
    }

    // Danh sach hien thi tren RecyclerView
    private val displayBooks = mutableListOf<Book>()
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Anh xa view
        etStudentName = findViewById(R.id.etStudentName)
        btnChangeStudent = findViewById(R.id.btnChangeStudent)
        btnAddBook = findViewById(R.id.btnAddBook)
        rvBooks = findViewById(R.id.rvBooks)
        tvBookList = findViewById(R.id.tvBookList)

        // Tạo dữ liệu ví dụ sẵn
        val studentA = Student(1, "Nguyen Van A")
        val studentB = Student(2, "Nguyen Van B")

        studentA.borrowedBooks.add(allBooks[0]) // sách 1
        studentA.borrowedBooks.add(allBooks[1]) // sách 2
        studentB.borrowedBooks.add(allBooks[2]) // sách 3

        students.add(studentA)
        students.add(studentB)

        // Thiet lap RecyclerView
        rvBooks.layoutManager = LinearLayoutManager(this)
        displayBooks.addAll(allBooks) // Ban dau hien tat ca sach
        adapter = BookAdapter(displayBooks) { book ->
            currentStudent?.let {
                if (book.isBorrowed && !it.borrowedBooks.contains(book)) {
                    it.borrowedBooks.add(book)
                } else if (!book.isBorrowed) {
                    it.borrowedBooks.remove(book)
                }
            }
        }
        rvBooks.adapter = adapter

        // ✅ Xu ly nut doi sinh vien
        btnChangeStudent.setOnClickListener {
            val name = etStudentName.text.toString().trim()

            // ⚙️ Neu ten sinh vien rong -> hien tat ca sach
            if (name.isEmpty()) {
                currentStudent = null
                displayBooks.clear()
                displayBooks.addAll(allBooks)
                adapter.notifyDataSetChanged()
                tvBookList.text = "Danh sach sach"
                Toast.makeText(this, "Da hien tat ca sach trong thu vien", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ⚙️ Tim hoac tao moi sinh vien
            currentStudent = students.find { it.name == name } ?: Student(students.size + 1, name)
            if (!students.contains(currentStudent)) students.add(currentStudent!!)

            Toast.makeText(this, "Dang quan ly: $name", Toast.LENGTH_SHORT).show()

            // Cap nhat danh sach hien thi
            displayBooks.clear()
            if (currentStudent!!.borrowedBooks.isEmpty()) {
                tvBookList.text = "Sinh vien chua muon sach nao"
            } else {
                tvBookList.text = "Danh sach sach da muon"
                displayBooks.addAll(currentStudent!!.borrowedBooks)
            }

            adapter.notifyDataSetChanged()
        }

        // ✅ Xu ly nut them sach
        btnAddBook.setOnClickListener {
            val newId = allBooks.size + 1
            val newBook = Book(newId, "Ten sach $newId")
            allBooks.add(newBook)

            // Neu khong co sinh vien nao dang duoc chon thi cap nhat luon danh sach hien thi
            if (currentStudent == null) {
                displayBooks.add(newBook)
                rvBooks.adapter?.notifyItemInserted(displayBooks.size - 1)
                rvBooks.scrollToPosition(displayBooks.size - 1)
            }

            Toast.makeText(this, "Da them: Ten sach $newId", Toast.LENGTH_SHORT).show()
        }
    }
}
