package test.rxjava.com.rxjavasample

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_rxjavasimple.*

import org.jetbrains.anko.toast


class RxJavaSimpleActivity : AppCompatActivity() {

    private var disposable: CompositeDisposable? = CompositeDisposable()
    var value = 0

    private val serverDownloadObservable = Observable.create<Int> { emitter ->
        SystemClock.sleep(10000) // simulate delay
        emitter.onNext(5)
        emitter.onComplete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjavasimple)

        button.setOnClickListener { v ->
            v.isEnabled = false // disables the button until execution has finished
            val subscribe = serverDownloadObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { integer ->
                        updateTheUserInterface(integer!!) // this methods updates the ui
                        v.isEnabled = true // enables it again
                    }
            disposable!!.add(subscribe)
        }

        toastButton.setOnClickListener { toast("Still active ${value++}") }
    }

    private fun updateTheUserInterface(integer: Int) {
        resultView.text = integer.toString()
    }

    override fun onStop() {
        super.onStop()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }
}
