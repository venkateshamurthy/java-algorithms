package algos.backtracking;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
@Slf4j
public class KColorable implements BackTracker<KColorable.State> {
    enum Colour {
        BLUE, GREEN, RED;

        public Stream<Colour> otherColours() {
            return Arrays.stream(Colour.values())
                    .filter(c -> c != this);
        }
    }

    /**
     * <pre>
     *
     *    A 1    2,6
     *    B 2    1,3,7
     *    C 3    2,4,8
     *    D 4    3,5,9
     *    E 5    1,4,10
     *    F 6    1,8,9
     *    G 7    2,9,10
     *    H 8    3,6,10
     *    I 9    4,6,7
     *    J 10   5,7,8
     *
     * </pre>
     */
    private final List<Integer> vertices = IntStream.range(0,10).boxed().collect(Collectors.toList());
    private final Map<Integer, List<Integer>> graph = ImmutableMap.<Integer, List<Integer>>builder()
           //1  2  3  4  5  6  7  8  9 10
            .put(0, ImmutableList.of(1, 3, 4, 5, 6)) //1
            .put(1, ImmutableList.of(0, 2, 6))       //2
            .put(2, ImmutableList.of(1, 3, 7))       //3
            .put(3, ImmutableList.of(0, 2, 4, 8))    //4
            .put(4, ImmutableList.of(0, 3, 8, 9))    //5
            .put(5, ImmutableList.of(0, 7, 8))       //6
            .put(6, ImmutableList.of(1, 8, 9))       //7
            .put(7, ImmutableList.of(2, 5, 9))       //8
            .put(8, ImmutableList.of(3, 4, 5, 6, 9)) //9
            .put(9, ImmutableList.of(4, 6, 7, 8))
            .build();


    private final Colour[] solution = new Colour[vertices.size()];
    private final Range<Integer> rowRange = Range.closedOpen(0, vertices.size());

    private Stream<State> adjacentVertices(State state){
       return rowRange.contains(state.index)?
               graph.getOrDefault(state.index, ImmutableList.of())
               .stream().filter(i->solution[i]==null)
                       .filter(i->solution[i]!=state.colour)
                .map(i->State.of(i, solution[i])) :
               Stream.empty();
    }

    private void print() {
        log.info("{}", Arrays.stream(solution)
                .filter(Objects::nonNull)
                .map(Enum::ordinal)
                .map(i->i+1)
                .collect(Collectors.toList()));
    }

    public static void main(String[] args) {
        KColorable kColorable = new KColorable();
        if(kColorable.search(new State(0, Colour.BLUE))) {
            log.info("YES...");
        } else {
            log.info("NO...");
        }
        kColorable.print();
        // 1  2  1  2  3  2  1  3  3  2  is the actua answer
        //[1, 2, 1, 2, 3, 1, 2, 1, 2, 1] is what i am getting
    }

    @Override
    public boolean isStateGoal(State state) {
        return state.index == solution.length - 1;
    }

    @Override
    public boolean isStateValid(State state) {
        return rowRange.contains(state.index) && state.colour!=null &&
                graph.getOrDefault(state.index, ImmutableList.of())
                        .stream()
                        .allMatch(i->solution[i]!=state.colour);
    }

    @Override
    public void markSolution(State state) {
        solution[state.index] = state.colour;
    }

    @Override
    public void unmarkSolution(State state) {
        solution[state.index] = null;
    }

    @Override
    public Stream<State> nextPossibleStates(State state) {
        return rowRange.contains(state.index+1) &&
                solution[state.index+1] == null ?
                state.colour.otherColours()
                     .map(colour -> State.of(state.index+1, colour)) :
                Stream.empty();
    }

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    static class State {
        private final int index;
        private final Colour colour;
    }
}