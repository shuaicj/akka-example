package shuaicj.example.akka;

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

        ActorRef director = system.actorOf(Director.props(), "director");

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
