package UpsideDownChessBB.JUnitTests.Tests;

import UpsideDownChessBB.BoardGeneration;
import UpsideDownChessBB.PiecesAndMoves.Bishop;
import UpsideDownChessBB.PiecesAndMoves.MoveGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveGeneratorTest {

    @Test
    void HAndVMoves() {
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 1 100");
        assertEquals("0120012206250627", MoveGenerator.generateAllPossibleMoves(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK,
                BoardGeneration.WhiteToMove));

        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr b 1 100");
        assertEquals("7150715276557657", MoveGenerator.generateAllPossibleMoves(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK,
                BoardGeneration.WhiteToMove));
    }

    @Test
    void DAndAntiDMoves() {
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        assertEquals("0120012206250627", MoveGenerator.generateAllPossibleMoves(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK,
                BoardGeneration.WhiteToMove));

    }

    @Test
    void possibleMovesW() {
    }

    @Test
    void possibleMovesB() {
    }

    @Test
    void generateAllPossibleMoves() {
    }

    @Test
    void transformMove() {
    }

    @Test
    void makeMove() {
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        assertEquals(65600, MoveGenerator.makeMove(BoardGeneration.WN,  "0120",'n'));

        BoardGeneration.importFEN("R1BQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        assertEquals(66, MoveGenerator.makeMove(BoardGeneration.WN,  "01NP",'N'));

        BoardGeneration.importFEN("R1BQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        assertEquals(64, MoveGenerator.makeMove(BoardGeneration.WN,  "01nP",'N'));

    }

    @Test
    void makeMoveCastle() {
    }

    @Test
    void possibleCW() {
    }

    @Test
    void possibleCB() {
    }

    @Test
    void unsafeForBlack() {
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        assertEquals(10878975, MoveGenerator.unsafeForBlack(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK));
    // knight
        BoardGeneration.importFEN("R1BQKB1R/PPPPPPPP/8/8/8/8/pppppppp/rnNqkbnr w 0 1");
        assertEquals(4796069720424447L, MoveGenerator.unsafeForBlack(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK));

        // king
        BoardGeneration.importFEN("R1BQ1B1R/PPPPPPKP/8/8/8/8/pppppppp/rnNqkbnr w 0 1");
        assertEquals(4796069735104511L, MoveGenerator.unsafeForBlack(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK));

        BoardGeneration.importFEN("R1BQ1B1R/PPPPPPPP/8/8/8/7K/pppppppp/rnNqkbnr w 0 1");
        assertEquals(58910458626760703L, MoveGenerator.unsafeForBlack(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK));

    }

    @Test
    void unsafeForWhite() {
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        assertEquals(-100055558127616L, MoveGenerator.unsafeForWhite(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK));
        // knight
        BoardGeneration.importFEN("RNnQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        assertEquals(-100055557467904L, MoveGenerator.unsafeForWhite(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK));
        // king
        BoardGeneration.importFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/kppppppp/rnbqkbnr w 0 1");
        assertEquals(-9105055789613056L, MoveGenerator.unsafeForWhite(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK));
// king spawn right
        BoardGeneration.importFEN("RNBQKBNR/kPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        assertEquals(-9107254812671485L, MoveGenerator.unsafeForWhite(
                BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,
                BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK));

        // test for bishop
        BoardGeneration.importFEN("RNBQKBNR/P1P1PPPP/8/8/8/8/pppppppp/rnbqkbnr w 0 1");
        assertEquals("1", Bishop.possibleB(BoardGeneration.WB));


    }
}