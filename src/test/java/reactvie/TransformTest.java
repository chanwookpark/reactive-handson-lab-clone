package reactvie;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactvie.User.*;

/**
 * @author chanwook
 */
public class TransformTest {

    ReactiveUserRepository reactiveRepository = new ReactiveUserRepository();

    @Test
    public void transformFromMono() throws Exception {
        Mono<User> mono = reactiveRepository.findFirst();
        assert mono != null;

        // 대문자로 변환
        final Mono<User> transformed =
                mono.map(u -> new User(u.getFirstName().toUpperCase(), u.getLastName().toUpperCase()));

        SubscriberMock.subscribe(transformed)
                .assertValueCount(1).assertValues(CHANWOOK_UPPER)
                .assertComplete();
    }

    //TODO await() 있을 때와 없을 때의 차이 이해하기

    @Test
    public void transformFromFlux() throws Exception {
        Flux<User> flux = reactiveRepository.findAll();
        assert flux != null;

        // 대문자로 변환
        final Flux<User> transformed =
                flux.map(u -> new User(u.getFirstName().toUpperCase(), u.getLastName().toUpperCase()));

        SubscriberMock.subscribe(transformed)
                .assertValueCount(4)
                .assertValues(CHANWOOK_UPPER, SEOJIN_UPPER, WOOJIN_UPPER, HYUNJOO_UPPER)
                .assertComplete();
    }

    @Test
    public void asyncTransform() throws Exception {
        // 그럼 위에는 sync라는 건가?? ror9=-09op=\

    }
}
