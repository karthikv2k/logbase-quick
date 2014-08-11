package io.logbase.sample;

import bb.util.Benchmark;

import io.logbase.collections.impl.BaseList;
import io.logbase.collections.impl.IntegerLinkedArrayList;
import io.logbase.consumer.EventConsumer;
import io.logbase.consumer.impl.TwitterFileConsumer;
import io.logbase.event.JSONEvent;
import io.logbase.exceptions.ConsumerInitException;
import io.logbase.node.Node;
import io.logbase.node.NodeConnector;
import io.logbase.node.impl.SimpleRealtimeNodeConnector;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;


/**
 * Created by kousik on 06/08/14.
 */

/**
 * To benchmark your code, add the appropriate subroutine to benchmark here
 * and associate it to an enum.
 */
public class PerfBenchmark implements Callable<Integer> {

    public enum ListType {
        INSERT_LINKEDLIST,
        INSERT_ARRAYLIST,
        INSERT_INTLINKARRLIST,
        TWITTERFILEWRITE};

    static int testSize = 10 * 1000 * 1000;
    static ListType listType;

    public PerfBenchmark (ListType listType) {
        this.listType = listType;
    }

    /*
     * The Benchmark library executes the this call subroutine.
     */

    public Integer call() {
        switch (this.listType) {
            case INSERT_INTLINKARRLIST:
                BaseList<Integer> intList = new IntegerLinkedArrayList(64 * 1024);
                insert(intList);
                break;
            case INSERT_LINKEDLIST:
                List<Integer> linkedList = new LinkedList<Integer>();
                insert(linkedList);
                break;
            case INSERT_ARRAYLIST:
                List<Integer> arrayList = new ArrayList<Integer>(testSize + 1);
                insert(arrayList);
                break;
            case TWITTERFILEWRITE:
                consumeTwitterFile();
                break;
        }
        return 1;
    }

    public static void main(String[] args) throws Exception {
        Callable<Integer> task;
        Benchmark.Params params = new Benchmark.Params();

        /*
         * Unset this if you don't want any feedback on what the benchmark
         * routine is doing.
         */
        params.setConsoleFeedback(true);
        params.setNumberMeasurements(5);

        /*
         * Set this if you only need to measure CPU time
         */
        params.setMeasureCpuTime(false);

        for (ListType listType : ListType.values()) {
            task = new PerfBenchmark(listType);
            System.out.println(listType.toString() + " took " + new Benchmark(task, params).getStats() + "\n");
        }
    }

    /**
     * Benchmarking subroutines go here
     */

    private static void insert(BaseList<Integer> list) {
        for (int i = 0; i < testSize; i++) {
            list.add(i);
        }
    }

    private static void insert(List<Integer> list) {
        for (int i = 0; i < testSize; i++) {
            list.add(i);
        }
    }

    private static void consumeTwitterFile() {

        NodeConnector nodeConnector = new SimpleRealtimeNodeConnector();
        Node node = nodeConnector.connect();

        String fileName = "twitter_events_mini.dat";
        URL url = ClassLoader.getSystemResource(fileName);
        EventConsumer consumer = new TwitterFileConsumer(url.getFile());

        try {
            consumer.init(node.getWriter("Twitter", JSONEvent.class));
        } catch (ConsumerInitException e) {
            System.exit(-1);
        }
        /*
         * Consumer, reads every line from the twitter file and
         * inserts it to the table it creates.
         */
        consumer.start();
    }
}