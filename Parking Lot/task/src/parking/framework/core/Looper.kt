package parking.framework.core

import java.lang.IllegalStateException
import java.lang.RuntimeException

class Looper private constructor(quitAllowed: Boolean) {
    val messageQueue: MessageQueue = MessageQueue(quitAllowed)
    val mThread: Thread = Thread.currentThread()

    // static fields
    companion object {
        private var isAlive = true
        private val sThreadLocal: ThreadLocal<Looper> = ThreadLocal()

        var sMainLooper: Looper? = null
            private set

        /**
         * Starts the loop
         */
        fun loop() {
            val currentInstance = myLooper()
            // loop
            while (isAlive) {
                val nextMessage = currentInstance.messageQueue.next()
                nextMessage.callback?.run() ?: return
            }
        }

        fun shutDown() {
            isAlive = false
        }

        /**
         * Method prepares all loopers except the Main looper. QuitAllowed is always true
         */
        fun prepare() {
            prepare(true)
        }
        /**
         * Method prepares main Looper, quidAllowed is false, because UI thread
         * should not be stopped without exception
         */
        fun prepareMainLooper() {
            prepare(false)
            synchronized(Looper::class.java) {
                if (sMainLooper != null) {
                    throw IllegalStateException("The main looper is already been prepared")
                }
                sMainLooper = myLooper()
            }
        }

        /**
         * Private static method for preparing Lopper and add it to ThreadLocal variable.
         * Every thread has its own Looper, and only unique one.
         */
        private fun prepare(quitAllowed: Boolean) {
            if (sThreadLocal.get() != null) {
                throw RuntimeException("Only one Looper may be created per thread")
            }
            sThreadLocal.set(Looper(quitAllowed))
        }

        /**
         *
         */
        fun myLooper(): Looper = sThreadLocal.get()

    }

}
