import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int S = scanner.nextInt();
        int T = scanner.nextInt();

        Map<Integer, Station> stations = new HashMap<>();
        for (int i = 0; i < S; i++) {
            int stationId = scanner.nextInt();
            int unloadType = scanner.nextInt();
            int loadType = scanner.nextInt();
            stations.put(stationId, new Station(stationId, unloadType, loadType));
        }

        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < T; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            edges.add(new int[]{from, to});
        }

        int startStation = scanner.nextInt();

        Solution solution = new Solution();
        Map<Integer, Set<Integer>> result = solution.solve(stations, edges, startStation);

        for (Map.Entry<Integer, Set<Integer>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        scanner.close();
    }
}