package reactvie;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Fuseable;

import java.util.*;

/**
 * @author chanwook
 */
public class SubscriberMock<T> implements Subscriber<T> {

    int subscriptionCount = 0;

    boolean completed = false;

    int valueCount = 0;

    int errorCount = 0;

    List<T> valueList = new ArrayList<>();

    Throwable error;

    @Override
    public void onSubscribe(Subscription s) {
        // subscribe가 될때마다 카운트 증가
        ++subscriptionCount;

        if (s instanceof Fuseable.QueueSubscription) {
            Fuseable.QueueSubscription<T> fqs = (Fuseable.QueueSubscription<T>) s;

            //TODO sync, async 처리. 이건 async 함수 테스트를 할 때 하면 되겠다.
            while (true) {
                final T t = fqs.poll();

                //비동기일 때만 fqs.request()하거 동기일 때는 onNext(), onComplete()을 직접 호출해줘야 한다는건가?
                //인터페이스 자바닥보면 request() 호출하면 자연스럽게 호출되는 걸로 나오는데...흠...
                if (t == null) {
                    onComplete();
                    break;
                }
                onNext(t);
            }
        }
    }

    @Override
    public void onNext(T t) {
        ++valueCount;
        valueList.add(t);
    }

    @Override
    public void onError(Throwable t) {
        ++errorCount;

        this.error = t;
    }

    @Override
    public void onComplete() {
        completed = true;
    }

    public SubscriberMock<T> assertSubscriptionCount(int expectedCount) {
        assert expectedCount == subscriptionCount;
        return this;
    }

    public SubscriberMock<T> assertComplete() {
        assert true == completed;
        return this;
    }

    public SubscriberMock<T> assertValueCount(int expectedCount) {
        assert expectedCount == valueCount;
        return this;
    }

    public SubscriberMock<T> assertErrorCount(int expectedCount) {
        assert expectedCount == errorCount;
        return this;
    }

    public SubscriberMock assertError(Class errorClassType) {
        assert error.getClass().isAssignableFrom(errorClassType);
        return this;
    }

    public static <T> SubscriberMock<T> subscribe(Publisher<T> publisher) {
        final SubscriberMock<T> mock = new SubscriberMock<>();
        publisher.subscribe(mock);
        return mock;
    }

    public final SubscriberMock<T> assertValues(T... expectedParam) {

        final Iterator<T> actual = valueList.iterator();
        final Iterator<T> expected = Arrays.asList(expectedParam).iterator();

        while (actual.hasNext() & expected.hasNext()) {
            final T t1 = actual.next();
            final T t2 = expected.next();

            if (!Objects.equals(t1, t2)) {
                throw new AssertionError("요청 객체와 실제 객체의 값이 맞지 않습니다! (actual: " + t1 + ", expected: " + t2);
            }
        }

        return this;
    }
}
