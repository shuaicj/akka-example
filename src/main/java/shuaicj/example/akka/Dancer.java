package shuaicj.example.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * The dancer actor.
 *
 * @author shuaicj 2018/06/15
 */
public class Dancer extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final String name;

    public Dancer(String name) {
        this.name = name;
    }

    public static Props props(String dancer) {
        return Props.create(Dancer.class, dancer);
    }

    @Override
    public void preStart() {
        log.info("{} started!", name);
    }

    @Override
    public void postStop() {
        log.info("{} stopped!", name);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Dance.class, dance -> log.info("{} is dancing {}!", name, dance.name))
                .build();
    }

    public static class Dance {

        final String name;

        public Dance(String name) {
            this.name = name;
        }
    }
}
