package reactvie;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author chanwook
 */
public class ReactiveUserRepository {

    private final List<User> userList;

    public ReactiveUserRepository() {
        userList = Arrays.asList(User.CHANWOOK, User.SEOJIN, User.WOOJIN, User.HYUNJOO);
    }

    public Mono<User> findFirst() {
        return Mono.just(userList.get(0));
    }

    public Flux<User> findAll() {
        return Flux.fromIterable(userList);
    }
}
