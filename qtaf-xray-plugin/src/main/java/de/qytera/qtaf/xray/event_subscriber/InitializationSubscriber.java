package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.events.EventListenerInitializer;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.interfaces.IEventSubscriber;
import de.qytera.qtaf.testng.event_subscriber.TestNGLoggingSubscriber;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import org.testng.internal.ConstructorOrMethod;
import rx.Subscription;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Event subscriber which listens for QTAF initialization events.
 */
public class InitializationSubscriber implements IEventSubscriber {

    /**
     * Event subscription.
     */
    private Subscription eventInitializationSubscription;

    @Override
    public void initialize() {
        if (eventInitializationSubscription != null) {
            return;
        }
        this.eventInitializationSubscription = QtafEvents.eventListenersInitialized.subscribe(this::onEventListenersInitialized);
    }

    /**
     * Method that is executed when all event listeners have been initialized.
     *
     * @param v ignored
     */
    private void onEventListenersInitialized(Void v) {
        if (!XrayConfigHelper.isEnabled()) {
            return;
        }
        for (IEventSubscriber eventSubscriber : EventListenerInitializer.getEventSubscribers()) {
            if (eventSubscriber instanceof TestNGLoggingSubscriber subscriber) {
                subscriber.addLogMessageEnhancer(testResult -> {
                    ConstructorOrMethod constructorOrMethod = testResult.getMethod().getConstructorOrMethod();
                    if (constructorOrMethod.getMethod() != null) {
                        Method method = constructorOrMethod.getMethod();
                        XrayTest xrayAnnotation = method.getAnnotation(XrayTest.class);
                        if (xrayAnnotation != null) {
                            return Optional.of(xrayAnnotation.key());
                        }
                    }
                    return Optional.empty();
                });
            }
        }
    }
}
