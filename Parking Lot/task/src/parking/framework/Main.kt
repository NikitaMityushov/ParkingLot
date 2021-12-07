package parking.framework

import parking.framework.core.Looper
import parking.framework.core.Message
import parking.view.App

/**
 * Entry point of application
 */
fun main() {
    Looper.prepareMainLooper()

    val task = Runnable {
        App().performStart()
        Looper.shutDown()
    }

    val taskMessage = Message(task, 3000)

    Looper.myLooper().messageQueue.enqueueMessage(taskMessage)

    Looper.loop()
}
