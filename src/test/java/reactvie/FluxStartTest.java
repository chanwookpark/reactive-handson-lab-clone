package reactvie;

import org.junit.Test;
import reactor.core.publisher.Flux;

/**
 * @author chanwook
 */
public class FluxStartTest {

    /**
     * empty flux가 publisher.
     * Test를 위해 subscriber를 만들고 메서드 호출이 정확하게 되는지 확인
     *
     * @throws Exception
     */
    @Test
    public void emptyFlux() throws Exception {
        final Flux empty = Flux.empty();
        final SubscriberMock subscriber = getSubscriberMock(empty);

        subscriber.assertSubscriptionCount(1).assertComplete();
    }

    @Test
    public void fromValuesWithFlux() throws Exception {
        final Flux<String> flux = Flux.just("test1", "test2");
        final SubscriberMock subscriber = getSubscriberMock(flux);

        subscriber.assertSubscriptionCount(1).assertValueCount(2).assertComplete();
    }

    @Test
    public void fromArraysWithFlux() throws Exception {
        final Flux<String> flux = Flux.fromArray(new String[]{"test1", "test2", "test3"});
        final SubscriberMock subscriber = getSubscriberMock(flux);

        subscriber.assertSubscriptionCount(1).assertValueCount(3).assertComplete();
    }

    @Test
    public void error() throws Exception {
        final Flux<Object> flux = Flux.error(new IllegalStateException("for test error..."));
        final SubscriberMock subscriber = getSubscriberMock(flux);

        subscriber.assertSubscriptionCount(1).assertErrorCount(1).assertError(IllegalStateException.class);
    }

    private SubscriberMock getSubscriberMock(Flux flux) {
        final SubscriberMock subscriber = new SubscriberMock();
        flux.subscribe(subscriber);
        return subscriber;
    }

}
