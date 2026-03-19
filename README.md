# Railway Cargo Problem

## Project structure

```text
src/
  Main.java       # Entry point: reads input, calls the solution, prints results
  Solution.java   # Core logic: graph processing, topological traversal, cargo propagation

input/            # Input test cases
  input1.txt
  input2.txt
  ...             

output/           # Expected outputs for the test cases
  output1.txt
  output2.txt
  ...

README.md
```


## Approach

The stations form a directed acyclic graph (DAG), which allows us to process them in topological order. This guarantees that when a station is processed, all relevant information from its reachable predecessors has already been computed.

For each station, we maintain the set of cargo types that may be present on a train when it arrives there.

When processing a station:
- we start from its arrival set
- remove the cargo type that is unloaded at that station
- add the cargo type that is loaded
- propagate the resulting set to all neighboring stations

By going in topological order, we ensure that each station accumulates all possible cargo configurations from every valid path leading to it.

## Complexity

- **Time:** `O(S + T + T * C)`
- **Space:** `O(S + T + S * C)`

Where:
- `S` = number of stations (nodes)
- `T` = number of tracks (edges)
- `C` = max number of cargo types
- `S * C` = space occupied by the `arrival` map
- `T * C` = cost of propagating the cargo sets along edges


## Test Cases Overview

The following test cases were added to cover the core behaviors and edge conditions of the solution:

- **Test 1 — Minimal case**  
  Verifies the base scenario with a single station and no tracks.

- **Test 2 — Simple chain**  
  Tests basic propagation of cargo along a linear path and validates the correct order of operations (unload before load).

- **Test 3 — Branching and merging**  
  Ensures correct propagation to multiple neighbors and validates that incoming cargo sets are merged correctly.

- **Test 4 — Complex DAG with multiple paths**  
  Tests a more intricate graph with both branching and merging, starting from a non-root node (`2`).  
  This case highlights:
    - propagation across multiple paths
    - correct merging of cargo sets
    - handling of intermediate transformations (unload/load)
    - ignoring unreachable nodes
