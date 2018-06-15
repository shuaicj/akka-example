package shuaicj.example.akka;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * The app entry.
 *
 * @author shuaicj 2018/06/15
 */
public class Application {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("tv-show");

        ActorRef singer1 = system.actorOf(Singer.props("Singer 1"), "singer-1");
        ActorRef singer2 = system.actorOf(Singer.props("Singer 2"), "singer-2");
        ActorRef dancer1 = system.actorOf(Dancer.props("Dancer 1"), "dancer-1");
        ActorRef dancer2 = system.actorOf(Dancer.props("Dancer 2"), "dancer-2");

        ActorRef director = system.actorOf(
                Director.props(Arrays.asList(singer1, singer2), Arrays.asList(dancer1, dancer2)), "director");

        director.tell(new Director.Action(), ActorRef.noSender());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            system.terminate();
        }
    }
}
