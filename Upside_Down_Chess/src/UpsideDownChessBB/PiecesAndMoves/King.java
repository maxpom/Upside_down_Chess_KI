package UpsideDownChessBB.PiecesAndMoves;

/* Beim Aufbau unserer Bitboards haben wir uns an der Youtube Tutorial Reihe zu Bitboards in Java von „Logic Crazy Chess" orientiert
 * und die Funktionen teilweise übernommen und an unser Upside-Down Chess angepasst.
 * Youtube Link : https://www.youtube.com/playlist?list=PLQV5mozTHmacMeRzJCW_8K3qw2miYqd0c
 *  */


public class King {

    /**
     *
     * @param K - Bitboard for the King of the respective player
     * @return - String value containing all the possible moves for the respective King.

     */
    public static String possibleK(long K) {
        String list="";
        long possibility;
        int iLocation=Long.numberOfTrailingZeros(K);
        if (iLocation>9)
        {
            possibility= MoveGenerator.KING_SPAN<<(iLocation-9);
        }
        else {
            possibility=MoveGenerator.KING_SPAN>>(9-iLocation);
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
        return list;
    }
}
