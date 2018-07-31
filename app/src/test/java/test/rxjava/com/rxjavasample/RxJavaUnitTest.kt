package test.rxjava.com.rxjavasample

import org.junit.Test

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.observers.TestObserver

import junit.framework.Assert.assertTrue

/**
 * Test, test everywhere..
 */
class RxJavaUnitTest {
    private var result = ""

    private val todoList: List<Todo>?
        get() = null

    // Simple subscription to a fix value
    @Test
    fun returnAValue() {
        result = ""
        val observer = Observable.just("Hello") // provides data
        observer.subscribe { s -> result = s } // Callable as subscriber
        assertTrue(result == "Hello")
    }

    @Test
    fun expectNPE() {
        val todoObservable = Observable.create(ObservableOnSubscribe<Todo> { emitter ->
            try {
                val todos = this@RxJavaUnitTest.todoList
                        ?: throw NullPointerException("Todo list was null!")
                for (todo in todos) {
                    emitter.onNext(todo)
                }
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        })
        val testObserver = TestObserver<Any>()
        todoObservable.subscribeWith(testObserver)

        // expect a NPE by using the TestObserver
        testObserver.assertError(NullPointerException::class.java)
    }

    @Test
    fun altExpectNPE() {
        val todoObservable = Observable.create(ObservableOnSubscribe<Todo> { emitter ->
            try {
                val todos = this@RxJavaUnitTest.getTodos()
                        ?: throw NullPointerException("todos was null")
                for (todo in todos) {
                    emitter.onNext(todo)
                }
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        })
        val testObserver = TestObserver<Any>()
        todoObservable.subscribeWith(testObserver)

        // expect a NPE by using the TestObserver
        testObserver.assertError(NullPointerException::class.java)
    }

    private fun getTodos(): List<Todo>? {
        return null
    }

    inner class Todo
}
