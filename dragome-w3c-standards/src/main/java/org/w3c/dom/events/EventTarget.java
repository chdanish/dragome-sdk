// Generated by esidl 0.4.0.

package org.w3c.dom.events;

public interface EventTarget
{
    // EventTarget
    public void addEventListener(String type, EventListener listener);
    public void addEventListener(String type, EventListener listener, boolean capture);
    public void removeEventListener(String type, EventListener listener);
    public void removeEventListener(String type, EventListener listener, boolean capture);
    public boolean dispatchEvent(Event event);
}
