package test.rxjava.com.rxjavasample

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.View

import java.util.concurrent.Callable

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_scheduler.*

/**
 * Demonstrates a long running operation of the main thread
 * during which a  progressbar is shown.
 */
class SchedulerActivity : AppCompatActivity() {

    private val subscription: Disposable? = null

    private var callable: Callable<String> = Callable { doSomethingLong() }

    /**
     * Observer
     * Handles the stream of data:
     */
    private val disposableObserver: DisposableObserver<String>
        get() = object : DisposableObserver<String>() {

            override fun onComplete() {
                messageArea.text = "${messageArea.text}\nOnComplete"
                progressBar.visibility = View.INVISIBLE
                longRunningOperation.isEnabled = true
                messageArea.text = "${messageArea.text}\nHiding progress bar"
            }

            override fun onError(e: Throwable) {
                messageArea.text = "${messageArea.text}\nOnError"
                progressBar.visibility = View.INVISIBLE
                longRunningOperation.isEnabled = true
                messageArea.text = "${messageArea.text}\nHiding progress bar"
            }

            override fun onNext(message: String) {
                messageArea.text = "${messageArea.text}\nonNext $message"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureLayout()
        createObservable()
    }

    private fun createObservable() {}

    override fun onDestroy() {
        super.onDestroy()
        if (subscription != null && !subscription.isDisposed) {
            subscription.dispose()
        }
    }

    private fun configureLayout() {
        setContentView(R.layout.activity_scheduler)

        longRunningOperation.setOnClickListener {
            Observable
                    .fromCallable(callable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { _ ->
                        progressBar.visibility = View.VISIBLE
                        longRunningOperation.isEnabled = false
                        messageArea.text = "${messageArea.text}\nProgress bar is now visible"
                    }
                    .subscribe(disposableObserver)
        }
    }

    private fun doSomethingLong(): String {
        SystemClock.sleep(1000)
        return "Hello"
    }
}