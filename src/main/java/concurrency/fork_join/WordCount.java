package concurrency.fork_join;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class WordCount {

    public static void main(String[] args) {
        String[] lines = {
                "Hello world!",
                "hello fork",
                "hello join",
                "hello pool join",
                "This is fork important join world",
                "This is"
        };

        ForkJoinPool forkJoinPool = new ForkJoinPool(3);

        MapReduce mapReduce = new MapReduce(lines, 0, lines.length);

        System.out.println(forkJoinPool.invoke(mapReduce));
    }

    static class MapReduce extends RecursiveTask<Map<String, Long>> {

        private String[] lines;

        private int start, end;

        public MapReduce(String[] lines, int start, int end) {
            this.lines = lines;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Map<String, Long> compute() {
            if (end - start == 1) {
                return calculate(lines[start]);
            }

            int mid = (end + start) / 2;
            MapReduce mr1 = new MapReduce(lines, start, mid);
            mr1.fork();

            MapReduce mr2 = new MapReduce(lines, mid, end);

            return merge(mr2.compute(), mr1.join());
        }

        private Map<String, Long> merge(Map<String, Long> map1, Map<String, Long> map2) {
            Map<String, Long> result = new HashMap<>();

            result.putAll(map1);

            map2.forEach((key, value) -> {
                Long count = result.get(key);
                if (count != null) {
                    result.put(key, count + value);
                } else {
                    result.put(key, value);
                }
            });

            return result;
        }

        private Map<String, Long> calculate(String line) {
            Map<String, Long> result = new HashMap<>();

            String[] words = line.split("\\s+");

            for (String word : words) {
                Long count = result.get(word);
                if (count != null) {
                    result.put(word, count + 1);
                } else {
                    result.put(word, 1L);
                }
            }

            return result;
        }
    }
}
