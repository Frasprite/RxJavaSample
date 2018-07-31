package test.rxjava.com.rxjavasample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import java.util.ArrayList

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

import kotlinx.android.synthetic.main.activity_colors.*


class ColorsActivity : AppCompatActivity() {

    private lateinit var simpleStringAdapter: SimpleStringAdapter
    private var disposable: Disposable? = null

    private val colorList: List<String>
        get() {
            val colors = ArrayList<String>()
            colors.add("red")
            colors.add("green")
            colors.add("blue")
            colors.add("pink")
            colors.add("brown")
            return colors
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureLayout()
        createObservable()
    }

    private fun createObservable() {
        val listObservable = Observable.just(colorList)
        disposable = listObservable.subscribe { colors -> simpleStringAdapter.setStrings(colors) }

    }

    private fun configureLayout() {
        setContentView(R.layout.activity_colors)
        colorListView.layoutManager = LinearLayoutManager(this)
        simpleStringAdapter = SimpleStringAdapter(this)
        colorListView.adapter = simpleStringAdapter
    }

    override fun onStop() {
        super.onStop()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }
}