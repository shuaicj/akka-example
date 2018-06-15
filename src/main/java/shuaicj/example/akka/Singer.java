package shuaicj.example.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * The singer actor.
 *
 * @author shuaicj 2018/06/15
 */
public class Singer extends AbstractActor {

    private final LoggingAdapter logger = Logging.getLogger(getContext().getSystem(), this);

    private final String name;

    public Singer(String name) {
        this.name = name;
    }

    public static Props props(String singer) {
        return Props.create(Singer.class, () -> new Singer(singer));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Song.class, song -> logger.info("{} is singing {}!", name, song.name))
                .build();
    }

    public static class Song {

        final String name;

        public Song(String name) {
            this.name = name;
        }
    }
}
