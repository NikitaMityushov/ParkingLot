package parking.framework.core;
/**
 * This class represents linked list with task(Runnable)
 */
public class Message {
    Runnable callback;
    long when;
    Message next;

    public Message(Runnable callback, long when) {
        this.callback = callback;
        this.when = when;
    }
}
