/*package UpsideDownChessBB.JUnitTests.Tests;


import UpsideDownChessBB.BoardGeneration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlphaBetaSearchTest {


// änderung

    @Test
    void alphaBeta() {
        //white
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w KQkq - 0 1");

        assertEquals(750,
                AlphaBetaSearch.alphaBeta(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                        BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        3,Integer.MIN_VALUE,Integer.MAX_VALUE,BoardGeneration.WhiteToMove, System.currentTimeMillis()));
        //black
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr b KQkq - 0 1");

        assertEquals(-750,
                AlphaBetaSearch.alphaBeta(
                        BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                        BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        3,Integer.MIN_VALUE,Integer.MAX_VALUE,BoardGeneration.WhiteToMove, System.currentTimeMillis()));

        // searchCutoff tree


        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr b KQkq - 0 1");
        assertEquals(0,
                AlphaBetaSearch.alphaBeta(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP,
                        BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        3,Integer.MIN_VALUE,Integer.MAX_VALUE,BoardGeneration.WhiteToMove, 0));


    }




    @Test
    void iterativeDeepSearching() {
    }

    @Test

    void doAlphaBetaMoveServer() {
        assertEquals(BoardUtils.importFEN( "R1BQKBNR/PPPPPPPP/N7/8/8/8/pppppppp/rnbqkbnr b KQkq - 0 1"),
                AlphaBetaSearch.doAlphaBetaMoveServer(
                        BoardUtils.importFEN( "RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w KQkq - 0 1"),
                        3));

    }



    @Test
    void doAlphaBetaMove() {
    }



}*/
