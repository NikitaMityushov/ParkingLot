package parking.framework.core;

public class MessageQueue {
    /**
     * This is the reference to Linked list (Class message)
     */
    private Message messages;

    private final boolean mQuitAllowed;

    private boolean mQuiting = false;
    /**
     * Package-private constructor should prevent using the queue outside,
     * you should use message queue only through @Handler class
     */
    // remove public after creating a Handler class
    public MessageQueue(boolean quitAllowed) {
        this.mQuitAllowed = quitAllowed;
    }

    /**
     * This method returns the current message
     */
    // remove public after creating a Handler class
    public synchronized Message next() {
        int nextWaitTime = -1;
        while (true) {
            try {
                if (nextWaitTime >= 0) {
                    this.wait(nextWaitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (this) {
                Message current = messages;

                if (current != null) {
                    long now = System.currentTimeMillis();

                    if (now < current.when) {
                        nextWaitTime = (int) (current.when - now);
                    } else {
                        messages = current.next;
                        return current;
                    }
                } else {
                    nextWaitTime = 0;
                }
            }
        }
    }

    /**
     * Add a new message
     */
    // remove public after creating a Handler class
    public boolean enqueueMessage(Message newMessage) {
        if (newMessage == null) {
            return false;
        }
        synchronized (this) {
            Message current = messages;

            if (current == null) {
                messages = newMessage;
            } else {
                Message previous;
                while (true) {
                    previous = current;
                    current = current.next;

                    if (current == null || newMessage.when < current.when) {
                        break;
                    }
                }
                newMessage.next = previous.next;
                previous.next = newMessage;
            }
            this.notify();
        }
        return true;
    }

    public void quit() {
        if (!mQuitAllowed) {
            throw new RuntimeException("Main thread not allowed to quit.");
        }

        synchronized (this) {
            if (mQuiting) {
                return;
            }
            mQuiting = true;
        }
    }

}
