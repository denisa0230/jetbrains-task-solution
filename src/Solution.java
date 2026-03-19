import java.util.*;

class Station {
    int id;
    int unloadType;
    int loadType;

    Station(int id, int unloadType, int loadType) {
        this.id = id;
        this.unloadType = unloadType;
        this.loadType = loadType;
    }
}


class Solution {
    public Map<Integer, Set<Integer>> solve(Map<Integer, Station> stations, List<int[]> edges, int startStation) {

        // Build graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int stationId : stations.keySet()) {
            graph.put(stationId, new ArrayList<>());
        }

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];

            graph.get(from).add(to);
        }

        // Topological sort
        List<Integer> topSortOrder = topologicalSort(graph, startStation);

        // Maps each station ID to the set of cargo types
        // that may be present on a train upon arrival at that station
        Map<Integer, Set<Integer>> arrival = new HashMap<>();
        for (int stationId : stations.keySet()) {
            arrival.put(stationId, new HashSet<>());
        }

        for (int node : topSortOrder) {
            Station station = stations.get(node);

            // Determine the cargo types that may be present
            // on a train upon departure from this station
            Set<Integer> departure = new HashSet<>(arrival.get(node));
            departure.remove(station.unloadType);
            departure.add(station.loadType);

            // Propagate the resulting cargo set to all neighboring stations
            for (int neigh : graph.get(node)) {
                arrival.get(neigh).addAll(departure);
            }
        }

        // Prepare sorted output to ensure deterministic results
        Map<Integer, Set<Integer>> result = new LinkedHashMap<>();
        List<Integer> sortedStationIds = new ArrayList<>(stations.keySet());
        Collections.sort(sortedStationIds);

        for (int stationId : sortedStationIds) {
            result.put(stationId, new TreeSet<>(arrival.get(stationId)));
        }

        return result;
    }

    private List<Integer> topologicalSort(Map<Integer, List<Integer>> graph, int startStation) {
        Set<Integer> visited = new HashSet<>();
        List<Integer> order = new ArrayList<>();

        dfs(startStation, graph, visited, order);
        Collections.reverse(order);

        return order;
    }

    private void dfs(int node, Map<Integer, List<Integer>> graph, Set<Integer> visited, List<Integer> order) {
        visited.add(node);

        for (int neigh : graph.get(node)) {
            if (!visited.contains(neigh)) {
                dfs(neigh, graph, visited, order);
            }
        }

        order.add(node);
    }
}
