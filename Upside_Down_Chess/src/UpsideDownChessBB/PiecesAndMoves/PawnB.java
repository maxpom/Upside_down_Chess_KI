package UpsideDownChessBB.PiecesAndMoves;

/* Beim Aufbau unserer Bitboards haben wir uns an der Youtube Tutorial Reihe zu Bitboards in Java von „Logic Crazy Chess" orientiert
 * und die Funktionen teilweise übernommen und an unser Upside-Down Chess angepasst.
 * Youtube Link : https://www.youtube.com/playlist?list=PLQV5mozTHmacMeRzJCW_8K3qw2miYqd0c
 *  */


public class PawnB {

    /** possibleBP function returns a list including all possible moves which can be conducted by the black pawns given a specific board configuration.
     * Pawn promotion is the only possible turn for pawns in UpsideDown Chess. Possible moves are made of 4 numbers representing the  possible start and end position for a single pawn.
     *
     * @param BP - Bitboard for the black pawns stored in a long
     * @return - String value containing all the possible moves for the black pawns.

     */
    public static String possibleBP(long BP) {
        String list = "";
        long PAWN_MOVES;
        long possibility;


        //y1,y2,Promotion Type,"P"
        PAWN_MOVES = (BP << 7) & MoveGenerator.NOT_MY_PIECES & MoveGenerator.OCCUPIED & MoveGenerator.RANK_1 & ~MoveGenerator.FILE_H;//pawn promotion by capture right
        possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        while (possibility != 0) {
            int index = Long.numberOfTrailingZeros(possibility);
            list += "" + (index % 8 + 1) + (index % 8) + "qP" + (index % 8 + 1) + (index % 8) + "nP";
            PAWN_MOVES &= ~possibility;
            possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        }
        PAWN_MOVES = (BP << 9) & MoveGenerator.NOT_MY_PIECES & MoveGenerator.OCCUPIED & MoveGenerator.RANK_1 & ~MoveGenerator.FILE_A;//pawn promotion by capture left
        possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        while (possibility != 0) {
            int index = Long.numberOfTrailingZeros(possibility);
            list += "" + (index % 8 - 1) + (index % 8) + "qP" + (index % 8 - 1) + (index % 8) + "nP";
            PAWN_MOVES &= ~possibility;
            possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        }
        PAWN_MOVES = (BP << 8) & MoveGenerator.EMPTY & MoveGenerator.RANK_1;//pawn promotion by move 1 forward
        possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        while (possibility != 0) {
            int index = Long.numberOfTrailingZeros(possibility);
            list += "" + (index % 8) + (index % 8) + "qP" + (index % 8) + (index % 8) + "nP";
            PAWN_MOVES &= ~possibility;
            possibility = PAWN_MOVES & ~(PAWN_MOVES - 1);
        }

        return list;


    }
}
