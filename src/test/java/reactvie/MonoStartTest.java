package reactvie;

import org.junit.Test;
import reactor.core.publisher.Mono;

/**
 * @author chanwook
 */
public class MonoStartTest {

    @Test
    public void emptyMono() throws Exception {
        final Mono<Object> empty = Mono.empty();
        final SubscriberMock subscriber = new SubscriberMock();
        empty.subscribe(subscriber);

        subscriber.assertSubscriptionCount(1).assertComplete();
    }

    @Test
    public void fromValue() throws Exception {

        final Mono<String> mono = Mono.just("hallo");

        final SubscriberMock<String> subscriber = new SubscriberMock<>();
        mono.subscribe(subscriber);

        subscriber.assertSubscriptionCount(1).assertValueCount(1).assertComplete();
    }

    @Test
    public void throwError() throws Exception {

        final Mono<Object> mono = Mono.error(new IllegalStateException());

        final SubscriberMock<Object> subscriber = new SubscriberMock<>();
        mono.subscribe(subscriber);

        subscriber.assertSubscriptionCount(1).assertErrorCount(1).assertError(IllegalStateException.class);
    }
}
