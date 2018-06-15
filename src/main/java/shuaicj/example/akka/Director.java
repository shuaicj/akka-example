package shuaicj.example.akka;

import java.util.Collections;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * The director actor.
 *
 * @author shuaicj 2018/06/15
 */
public class Director extends AbstractActor {

    private final List<ActorRef> singers;
    private final List<ActorRef> dancers;

    public Director(List<ActorRef> singers, List<ActorRef> dancers) {
        this.singers = Collections.unmodifiableList(singers);
        this.dancers = Collections.unmodifiableList(dancers);
    }

    public static Props props(List<ActorRef> singers, List<ActorRef> dancers) {
        return Props.create(Director.class, () -> new Director(singers, dancers));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Action.class, action -> {
                    for (ActorRef singer : singers) {
                        singer.tell(new Singer.Song("HAPPY"), getSelf());
                    }
                    for (ActorRef dancer : dancers) {
                        dancer.tell(new Dancer.Dance("WALTZ"), getSelf());
                    }
                })
                .build();
    }

    public static class Action {
        public Action() {}
    }
}
