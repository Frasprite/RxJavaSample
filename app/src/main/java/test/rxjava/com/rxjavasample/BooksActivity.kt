package test.rxjava.com.rxjavasample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_books.*

import test.rxjava.com.rxjavasample.service.RestClient


class BooksActivity : AppCompatActivity() {

    private var bookSubscription: Disposable? = null
    private var stringAdapter: SimpleStringAdapter? = null
    private var restClient: RestClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restClient = RestClient(this)
        configureLayout()
        createObservable()
    }

    private fun createObservable() {
        val booksObservable = Observable.fromCallable { restClient!!.favoriteBooks }
        bookSubscription = booksObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { strings -> displayBooks(strings) }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bookSubscription != null && !bookSubscription!!.isDisposed) {
            bookSubscription!!.dispose()
        }
    }

    private fun displayBooks(books: List<String>) {
        stringAdapter!!.setStrings(books)
        loader.visibility = View.GONE
        booksListView.visibility = View.VISIBLE
    }

    private fun configureLayout() {
        setContentView(R.layout.activity_books)
        booksListView.layoutManager = LinearLayoutManager(this)
        stringAdapter = SimpleStringAdapter(this)
        booksListView.adapter = stringAdapter
    }

    override fun onStop() {
        super.onStop()
        if (bookSubscription != null && !bookSubscription!!.isDisposed) {
            bookSubscription!!.dispose()
        }
    }
}
