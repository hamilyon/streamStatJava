package four;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class StreamStat {
    final Map<Integer, Node> indexed = new HashMap<Integer, Node>(); // userId -> statisticsNode
    final List<Pair<Integer, LocalDateTime>> sequential = new LinkedList<>();
    final Heap stat = new Heap();

    public void add(Integer userId, LocalDateTime date) {
        Node node = indexed.get(userId);
        if (node == null) {
            node = new Node(userId, 0);
            indexed.put(userId, node);
        }
        stat.incrementNode(node);
        sequential.add(new ImmutablePair<>(userId, date));
    }

    public Integer getMax() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = now.minus(300, ChronoUnit.SECONDS);
        List<Node> toDecrement = new ArrayList<>();
        Iterator<Pair<Integer, LocalDateTime>> it = sequential.iterator();
        if (it.hasNext()) {
            Pair<Integer, LocalDateTime> current = it.next();
            while (current.getRight().isBefore(from)) {
                it.remove();
                toDecrement.add(indexed.get(current.getKey()));
                current = it.next();
            }
        }

        stat.updateDecrementedNodes(toDecrement);
        return stat.max().userId;
    }
}
