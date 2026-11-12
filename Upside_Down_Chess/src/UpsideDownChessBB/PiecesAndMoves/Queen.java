package UpsideDownChessBB.PiecesAndMoves;

/* Beim Aufbau unserer Bitboards haben wir uns an der Youtube Tutorial Reihe zu Bitboards in Java von „Logic Crazy Chess" orientiert
 * und die Funktionen teilweise übernommen und an unser Upside-Down Chess angepasst.
 * Youtube Link : https://www.youtube.com/playlist?list=PLQV5mozTHmacMeRzJCW_8K3qw2miYqd0c
 *  */


public class Queen {
    /**
     *
     * @param Q - Bitboard for the Queens of the respective player
     * @return - String value containing all the possible moves for respective Queens.

     */
    public static String possibleQ(long Q) {
        String list="";
        long i=Q&~(Q-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=(MoveGenerator.HAndVMoves(iLocation)|MoveGenerator.DAndAntiDMoves(iLocation))&MoveGenerator.NOT_MY_PIECES;
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            Q&=~i;
            i=Q&~(Q-1);
        }
        return list;
    }
}
