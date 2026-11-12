/*package UpsideDownChessBB.JUnitTests.Tests;

import UpsideDownChessBB.BoardGeneration;
import UpsideDownChessBB.Search.BetterSearch;
import UpsideDownChessBB.Search.Evaluation;
import org.junit.Test;

import static org.junit.Assert.*;

public class BetterSearchTestNew {

    @Test
    public void betterSearch() {
        //white
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        Evaluation ev = new Evaluation();

        assertEquals(74,
                BetterSearch.BetterSearch(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                        BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        3, Integer.MIN_VALUE, Integer.MAX_VALUE, System.currentTimeMillis(), BoardGeneration.WhiteToMove, ev));
        //black
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr b 0 1");
        assertEquals(-74,
                BetterSearch.BetterSearch(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                        BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        3, Integer.MIN_VALUE, Integer.MAX_VALUE, System.currentTimeMillis(), BoardGeneration.WhiteToMove, ev));

        // searchCutoff tree


        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr b 0 1");
        assertEquals(-74,
                BetterSearch.BetterSearch(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                        BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        3, Integer.MIN_VALUE, Integer.MAX_VALUE, System.currentTimeMillis(), BoardGeneration.WhiteToMove, ev));


    }

    @Test
    public void iterativeDeepSearching() {
        Evaluation ev = new Evaluation();
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");

        assertEquals(74,
                BetterSearch.IterativeDeepSearching(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                        BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        BoardGeneration.WhiteToMove, ev));

        BoardGeneration.importFEN("R1BQKB1R/PPPPPPPP/1N4N1/8/8/8/nppppppn/r1bqkb1r w 0 1");

        assertEquals(74,
                BetterSearch.IterativeDeepSearching(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                        BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        BoardGeneration.WhiteToMove, ev ));
        //black
        BoardGeneration.importFEN("R1BQKB1R/PPPPPPPP/1N4N1/8/8/8/nppppppn/r1bqkb1r b 0 1");

        assertEquals(74,
                BetterSearch.IterativeDeepSearching(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                        BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        BoardGeneration.WhiteToMove, ev));
    }

    @Test
    public void doBetterSearchMove() {
        Evaluation ev = new Evaluation();

        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");

        assertEquals("", BetterSearch.doBetterSearchMove(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                BoardGeneration.WhiteToMove, ev));
    }
} */