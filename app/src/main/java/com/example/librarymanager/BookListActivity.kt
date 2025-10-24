package com.example.librarymanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanager.R
import com.example.librarymanager.adapter.BookAdapter
import com.example.librarymanager.model.Book

class BookListActivity : AppCompatActivity() {

    private lateinit var rvAllBooks: RecyclerView
    private lateinit var adapter: BookAdapter
    private val allBooks = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        rvAllBooks = findViewById(R.id.rvAllBooks)

        // Dữ liệu mẫu
        allBooks.addAll(
            listOf(
                Book(1, "Sách 01"),
                Book(2, "Sách 02"),
                Book(3, "Sách 03"),
                Book(4, "Sách 04")
            )
        )

        adapter = BookAdapter(allBooks) {
            // Ở màn này, chỉ xem thông tin sách, không cần cập nhật trạng thái
        }

        rvAllBooks.layoutManager = LinearLayoutManager(this)
        rvAllBooks.adapter = adapter
    }
}
