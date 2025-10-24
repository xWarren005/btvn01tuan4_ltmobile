package com.example.librarymanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanager.R
import com.example.librarymanager.model.Book

class BookAdapter(
    private val books: List<Book>,
    private val onCheckedChange: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbBook: CheckBox = view.findViewById(R.id.cbBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.cbBook.text = book.title
        holder.cbBook.isChecked = book.isBorrowed

        holder.cbBook.setOnCheckedChangeListener { _, isChecked ->
            book.isBorrowed = isChecked
            onCheckedChange(book)
        }
    }
}
