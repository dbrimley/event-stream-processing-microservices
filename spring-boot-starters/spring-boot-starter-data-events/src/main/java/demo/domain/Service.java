package demo.domain;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * A {@link Service} is a functional unit that provides a need. Services are immutable and often stateless. Services
 * always consume or produce {@link Commodity} objects. Services are addressable and discoverable by other services.
 *
 * @author Kenny Bastani
 */
@org.springframework.stereotype.Service
public abstract class Service<T extends Aggregate> implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public <A extends Action<T>> A getAction(Class<? extends A> clazz) {
        return applicationContext.getBean(clazz);
    }
}
