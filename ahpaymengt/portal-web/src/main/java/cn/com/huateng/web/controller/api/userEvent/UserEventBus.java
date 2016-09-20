/*
 * Copyright (c) 2013 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller.api.userEvent;

import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Component;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-10
 */
@Component
public class UserEventBus {

    private final EventBus eventBus;

    public UserEventBus() {
        this.eventBus = new EventBus("userEventBus");
    }

    /**
     * Registers all handler methods on {@code object} to receive events.
     * Handler methods are selected and classified using this EventBus's
     * {@link com.google.common.eventbus.HandlerFindingStrategy}; the default strategy is the
     * {@link com.google.common.eventbus.AnnotatedHandlerFinder}.
     *
     * @param object  object whose handler methods should be registered.
     */
    public void register(Object object) {
        eventBus.register(object);
    }

    /**
     * Posts an event to all registered handlers.  This method will return
     * successfully after the event has been posted to all handlers, and
     * regardless of any exceptions thrown by handlers.
     *
     * <p>If no handlers have been subscribed for {@code event}'s class, and
     * {@code event} is not already a {@link com.google.common.eventbus.DeadEvent}, it will be wrapped in a
     * DeadEvent and reposted.
     *
     * @param event  event to post.
     */
    public void post(Object event) {
        eventBus.post(event);
    }

    /**
     * Unregisters all handler methods on a registered {@code object}.
     *
     * @param object  object whose handler methods should be unregistered.
     * @throws IllegalArgumentException if the object was not previously registered.
     */
    public void unregister(Object object) {
        eventBus.unregister(object);
    }
}
