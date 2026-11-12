package UpsideDownChessBB.PiecesAndMoves;

/* Beim Aufbau unserer Bitboards haben wir uns an der Youtube Tutorial Reihe zu Bitboards in Java von „Logic Crazy Chess" orientiert
 * und die Funktionen teilweise übernommen und an unser Upside-Down Chess angepasst.
 * Youtube Link : https://www.youtube.com/playlist?list=PLQV5mozTHmacMeRzJCW_8K3qw2miYqd0c
 *  */


public class Knight {

    /**
     *
     * @param N - Bitboard for the Knights of the respective player
     * @return - String value containing all the possible moves for respective Knights.

     */
    public static String possibleN(long N) {
        String list="";
        long i=N&~(N-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility= MoveGenerator.KNIGHT_SPAN<<(iLocation-18);
            }
            else {
                possibility=MoveGenerator.KNIGHT_SPAN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~MoveGenerator.FILE_GH&MoveGenerator.NOT_MY_PIECES;
            }
            else {
                possibility &=~MoveGenerator.FILE_AB&MoveGenerator.NOT_MY_PIECES;
            }
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                list+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            N&=~i;
            i=N&~(N-1);
        }
        return list;
    }
}
