package test.rxjava.com.rxjavasample.service

import android.content.Context
import android.os.SystemClock

import java.util.ArrayList

/**
 * This is a fake REST client.
 *
 * It simulates making blocking calls to an REST endpoint.
 */
class RestClient(private val mContext: Context) {

    // "Simulate" the delay of network.
    val favoriteBooks: List<String>
        get() {
            SystemClock.sleep(8000)
            return createBooks()
        }

    // "Simulate" the delay of network.
    val favoriteBooksWithException: List<String>
        get() {
            SystemClock.sleep(8000)
            throw RuntimeException("Failed to load")
        }

    private fun createBooks(): List<String> {
        val books = ArrayList<String>()
        books.add("Lord of the Rings")
        books.add("The dark elf")
        books.add("Eclipse Introduction")
        books.add("History book")
        books.add("Der kleine Prinz")
        books.add("7 habits of highly effective people")
        books.add("The Hobbit")
        books.add("Jurassic Park")
        books.add("React for dummies")
        books.add("Here and back again")
        return books
    }
}