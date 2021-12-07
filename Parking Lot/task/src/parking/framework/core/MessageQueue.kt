/*
package parking.framework.core

import java.lang.RuntimeException
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class MessageQueue1(private val mQuitAllowed: Boolean) {
    */
/**
     * This is the reference to Linked list (Class message)
     *//*

    private var messages: Message? = null

    */
/**
     * Monitor
     *//*

    private val lock: Lock = ReentrantLock()

    */
/**
     * if true, MessageQueue should stop to receive Messages
     *//*

    private var mQuiting = false

    */
/**
     * The method returns the current message
     *//*

    fun next(): Message {
        var nextWaitTime = -1
        while (true) {
            try {
                if (nextWaitTime >= 0) {
                    // lock.wait(nextWaitTime)
                    lock.newCondition().await()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            synchronized(lock) {
                val current = messages

                if (current != null) {
                    val now: Long = System.currentTimeMillis()

                    if (now < current.whenRun!!) {
                        nextWaitTime = (current.whenRun!! - now).toInt()
                    } else {
                        messages = current.next
                        return current
                    }
                } else {
                    nextWaitTime = 0
                }
            }
        }
    }

    */
/**
     * Add a new message
     *//*

    fun enqueueMessage(newMessage: Message?): Boolean {
        if (newMessage == null) {
            return false
        }
        synchronized(lock) {
            var current: Message? = messages

            if (current == null) {
                messages = newMessage
            } else {
                var previous: Message?
                while (true) {
                    previous = current
                    current = current?.next

                    if (current == null || newMessage.whenRun!! < current.whenRun!!) {
                        break
                    }
                }
                newMessage.next = previous?.next
                previous?.next = newMessage
            }
            // lock.notify()
            lock.newCondition().signal()
        }
        return true
    }

    fun quit() {
        if (!mQuitAllowed) {
            throw RuntimeException("Main thread not allowed to quit.")
        }

        synchronized(lock) {
            if (mQuiting) {
                return
            }
            mQuiting = true
        }
    }

}*/
