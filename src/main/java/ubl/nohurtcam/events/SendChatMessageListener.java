package ubl.nohurtcam.events;

import ubl.nohurtcam.event.Listener;

import java.util.ArrayList;

public interface SendChatMessageListener extends Listener {
    public void sendChatMessage(SendChatMessageEvent var1);

    public static class SendChatMessageEvent
            extends CancelEvent<SendChatMessageListener> {
        private String message;
        private boolean modified;

        public SendChatMessageEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
            this.modified = true;
        }

        public boolean isModified() {
            return this.modified;
        }

        @Override
        public void fire(ArrayList<SendChatMessageListener> listeners) {
            listeners.forEach(listener -> listener.sendChatMessage(this));
        }

        @Override
        public Class<SendChatMessageListener> getListenerType() {
            return SendChatMessageListener.class;
        }
    }
}