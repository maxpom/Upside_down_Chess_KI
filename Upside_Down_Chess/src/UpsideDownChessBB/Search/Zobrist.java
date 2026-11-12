package UpsideDownChessBB.Search;

import java.security.SecureRandom;

public class Zobrist {
    // Array dimensions referring to piece type, color, square index
    public static final long[][][] piecesArray = new long[6][2][64];
    public static final long sideToMove = randomLong();

    // Standard Zobrist-key generators would include arrays for castling-rights and en-passant opportunities,
    // But in the case of our upside down chess implementation they can be omitted


    private static long randomLong() {
        SecureRandom r = new SecureRandom();
        return r.nextLong();
    }

    /**
     * Fills the zobrist array for later zobrist-key generation, should be called before the board is initialized.
     */
    public static void fillZobristArray() {
        for (int squareIndex = 0; squareIndex < 64; squareIndex++) {
            for (int pieceIndex = 0; pieceIndex < 6; pieceIndex++) {
                piecesArray[pieceIndex][0][squareIndex] = randomLong();
                piecesArray[pieceIndex][1][squareIndex] = randomLong();
                // white pieces are indexed with index 0, black pieces with index 1
                // piece index:
                // pawn: 0
                // knight: 1
                // bishop: 2
                // rook: 3
                // queen: 4
                // king: 5
            }
        }
    }

    /**
     * Generates a (hopefully) unique Zobrist-key for a give board position. Used for the transposition table.
     *
     * @return the generated key.
     */
    public static long generateZobristKey(long WP, long WN, long WB, long WR, long WQ, long WK,
                                          long BP, long BN, long BB, long BR, long BQ, long BK,
                                          boolean WhiteToMove) {
        long zobristKey = 0L;

        for (int squareIndex = 0; squareIndex < 64; squareIndex++) {
            if (((WP >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[0][0][squareIndex];
            } else if (((BP >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[0][1][squareIndex];
            } else if (((WN >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[1][0][squareIndex];
            } else if (((BN >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[1][1][squareIndex];
            } else if (((WB >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[2][0][squareIndex];
            } else if (((BB >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[2][1][squareIndex];
            } else if (((WR >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[3][0][squareIndex];
            } else if (((BR >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[3][1][squareIndex];
            } else if (((WQ >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[4][0][squareIndex];
            } else if (((BQ >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[4][1][squareIndex];
            } else if (((WK >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[5][0][squareIndex];
            } else if (((BK >> squareIndex) & 1) == 1) {
                zobristKey = zobristKey ^ piecesArray[5][1][squareIndex];
            }
        }
        if(!WhiteToMove) {
            zobristKey = zobristKey^sideToMove;
            //If its blacks turn to move, the sideToMove is XORed
        }
        // Eigentlich würde man den key jetzt noch basierend auf castle rights verändern, aber die scheinen ja
        // gar nicht mehr teil der ganzen Sache zu sein
        return zobristKey;
    }

}
