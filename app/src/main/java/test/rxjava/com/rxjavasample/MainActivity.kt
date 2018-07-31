package test.rxjava.com.rxjavasample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*

import test.rxjava.com.rxjavasample.extensions.launchActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        first.setOnClickListener { launchActivity<BooksActivity>() }
        second.setOnClickListener { launchActivity<ColorsActivity>() }
        third.setOnClickListener { launchActivity<RxJavaSimpleActivity>() }
        fourth.setOnClickListener { launchActivity<SchedulerActivity>() }
    }
}