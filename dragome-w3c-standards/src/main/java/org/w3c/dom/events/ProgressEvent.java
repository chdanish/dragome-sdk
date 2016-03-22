// Generated by esidl 0.4.0.

package org.w3c.dom.events;

public interface ProgressEvent extends Event
{
    // ProgressEvent
    public boolean getLengthComputable();
    public int getLoaded();
    public int getTotal();
    public void initProgressEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, boolean lengthComputableArg, int loadedArg, int totalArg);
    public void initProgressEventNS(String namespaceURI, String typeArg, boolean canBubbleArg, boolean cancelableArg, boolean lengthComputableArg, int loadedArg, int totalArg);
}
