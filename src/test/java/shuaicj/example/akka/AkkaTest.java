package shuaicj.example.akka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test this example.
 *
 * @author shuaicj 2018/06/15
 */
public class AkkaTest {

    ActorSystem system;

    @Before
    public void setUp() {
        system = ActorSystem.create();
    }

    @After
    public void tearDown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testSinger() {
        TestKit probe = new TestKit(system);
        ActorRef singer = probe.getRef();
        ActorRef director = system.actorOf(Director.props(Arrays.asList(singer), Collections.emptyList()), "director");
        director.tell(new Director.Action(), ActorRef.noSender());
        Singer.Song song = probe.expectMsgClass(Singer.Song.class);
        assertThat(song.name).isEqualTo("HAPPY");
    }

    @Test
    public void testDancer() {
        TestKit probe = new TestKit(system);
        ActorRef dancer = probe.getRef();
        ActorRef director = system.actorOf(Director.props(Collections.emptyList(), Arrays.asList(dancer)), "director");
        director.tell(new Director.Action(), ActorRef.noSender());
        Dancer.Dance dance = probe.expectMsgClass(Dancer.Dance.class);
        assertThat(dance.name).isEqualTo("WALTZ");
    }
}
