package shuaicj.example.akka;

import java.util.ArrayList;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * The director actor.
 *
 * @author shuaicj 2018/06/15
 */
public class Director extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    final List<ActorRef> singers = new ArrayList<>();
    final List<ActorRef> dancers = new ArrayList<>();

    public Director() {}

    public Director(List<ActorRef> singers, List<ActorRef> dancers) {
        this.singers.addAll(singers);
        this.dancers.addAll(dancers);
    }

    public static Props props() {
        return Props.create(Director.class);
    }

    public static Props props(List<ActorRef> singers, List<ActorRef> dancers) {
        return Props.create(Director.class, singers, dancers);
    }

    @Override
    public void preStart() {
        log.info("Director started!");
        if (singers.isEmpty() && dancers.isEmpty()) {
            ActorRef singer1 = getContext().actorOf(Singer.props("Singer 1"), "singer-1");
            ActorRef singer2 = getContext().actorOf(Singer.props("Singer 2"), "singer-2");
            singers.add(singer1);
            singers.add(singer2);
            ActorRef dancer1 = getContext().actorOf(Dancer.props("Dancer 1"), "dancer-1");
            ActorRef dancer2 = getContext().actorOf(Dancer.props("Dancer 2"), "dancer-2");
            dancers.add(dancer1);
            dancers.add(dancer2);
        }
    }

    @Override
    public void postStop() {
        log.info("Director stopped!");
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
