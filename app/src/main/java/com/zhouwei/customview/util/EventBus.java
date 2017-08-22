package com.zhouwei.imitationeventbus.eventbus;

import java.util.ArrayList;

/**
 * 自撸一个EventBus
 */
public class EventBus {
    private EventBus() {
    }

    private static class EventBusHolder {
        private volatile static EventBus mController = new EventBus();
    }

    public static EventBus getDefault() {
        return EventBusHolder.mController;
    }

    private ArrayList<EventHandler> eventHandlers = new ArrayList<>();

    public interface EventType {
        final int CATEGORY = 0;
        final int TOGGLE = 1;
        final int DATA = 2;
        final int LOGIN = 3;
        final int LOGOUT = 4;
        final int THEME = 5;
    }

    public interface EventHandler {
        void onHandlerEvent(EventType eventType, Object object);
    }

    public void postEvent(EventType eventType, Object object) {
        if (eventHandlers.size() > 0) {
            int size = eventHandlers.size();
            for (EventHandler handler : eventHandlers) {
                if (handler != null) {
                    handler.onHandlerEvent(eventType, object);
                }
            }
        }
    }

    public void registerEventHandler(EventHandler handler) {
        synchronized (eventHandlers) {
            eventHandlers.add(handler);
        }
    }

}
