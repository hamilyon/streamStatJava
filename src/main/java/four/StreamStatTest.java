package four;

import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class StreamStatTest {

    StreamStat streamStat;

    @BeforeMethod
    public void setUp() {
       streamStat = new StreamStat();
    }

    @Test
    public void testAdd() throws Exception {
        streamStat.add(1, LocalDateTime.now());
        streamStat.add(1, LocalDateTime.now());
        streamStat.add(1, LocalDateTime.now());

        assertEquals(streamStat.getMax(), (Integer) 1);
    }

    @Test
    public void testAdd2() throws Exception {
        streamStat.add(1, LocalDateTime.now());
        streamStat.add(1, LocalDateTime.now());
        streamStat.add(2, LocalDateTime.now());

        assertEquals(streamStat.getMax(), (Integer) 1);
    }

    @Test
    public void testGetMax() throws Exception {
        streamStat.add(1, LocalDateTime.now());
        streamStat.add(1, LocalDateTime.now());
        streamStat.add(1, LocalDateTime.now());

        assertEquals((Integer) 1, streamStat.getMax());
        streamStat.add(1, LocalDateTime.now());
        streamStat.add(1, LocalDateTime.now());
        streamStat.add(1, LocalDateTime.now());

        assertEquals((Integer) 1, streamStat.getMax());

        streamStat.add(2, LocalDateTime.now());
        streamStat.add(2, LocalDateTime.now());
        streamStat.add(2, LocalDateTime.now());

        assertEquals(streamStat.getMax(), (Integer) 1);

        List<Node> toDecrement = new ArrayList<>();

        Iterator<Pair<Integer, LocalDateTime>> it = streamStat.sequential.iterator();
        for (int i = 0; i < 4; i++) {
            Pair<Integer, LocalDateTime> current = it.next();
            toDecrement.add(streamStat.indexed.get(current.getKey()));
        }
        streamStat.stat.updateDecrementedNodes(toDecrement);

        assertEquals(streamStat.getMax(), (Integer) 2);

        toDecrement = new ArrayList<>();

        it = streamStat.sequential.iterator();
        Pair<Integer, LocalDateTime> current = it.next();
        toDecrement.add(streamStat.indexed.get(current.getKey()));
        streamStat.stat.updateDecrementedNodes(toDecrement);

        assertEquals(streamStat.getMax(), (Integer) 2);
    }
}