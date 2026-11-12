package UpsideDownChessBB.PiecesAndMoves;
import UpsideDownChessBB.Tests.BenchmarkTest;

import java.util.Arrays;

/* Beim Aufbau unserer Bitboards haben wir uns an der Youtube Tutorial Reihe zu Bitboards in Java von „Logic Crazy Chess" orientiert
 * und die Funktionen teilweise übernommen und an unser Upside-Down Chess angepasst.
 * Youtube Link : https://www.youtube.com/playlist?list=PLQV5mozTHmacMeRzJCW_8K3qw2miYqd0c
 *  */

public class MoveGenerator {
    //Files: Spalten von A bis H
    //Ranks: Zeilen von 1 bis 8
    final static long FILE_A = 72340172838076673L;
    final static long FILE_H = -9187201950435737472L;
    final static long FILE_AB = 217020518514230019L;
    final static long FILE_GH = -4557430888798830400L;
    final static long RANK_1 = -72057594037927936L;
    final static long RANK_4 = 1095216660480L;
    final static long RANK_5 = 4278190080L;
    final static long RANK_8 = 255L;
    final static long CENTRE = 103481868288L;
    final static long EXTENDED_CENTRE = 66229406269440L;
    final static long KING_SIDE = -1085102592571150096L;
    final static long QUEEN_SIDE = 1085102592571150095L;
    final static long KING_SPAN = 460039L;
    final static long KNIGHT_SPAN = 43234889994L;
    static long NOT_MY_PIECES;
    static long MY_PIECES;
    static long OCCUPIED;
    static long EMPTY;
    static long[] CASTLE_ROOKS = {63, 56, 7, 0};

    static long[] RankMasks8 =/*from rank1 to rank8*/
            {
                    0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L, 0xFF00000000L, 0xFF0000000000L, 0xFF000000000000L, 0xFF00000000000000L
            };
    static long[] FileMasks8 =/*from fileA to FileH*/
            {
                    0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
                    0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
            };
    static long[] DiagonalMasks8 =/*from top left to bottom right*/
            {
                    0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L,
                    0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L,
                    0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L
            };
    static long[] AntiDiagonalMasks8 =/*from top right to bottom left*/
            {
                    0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L,
                    0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L,
                    0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L
            };


    /** HAndVMoves function calculates all possible horizontal and vertical moves for a given figure
     * @param a - location of the figure going from 0 to 63
     * @return a long value representing the possible vertical or horizontal moves
     */
    static long HAndVMoves(int a){
        long binary =1L<<a; //converting into a binary bitboard
        long possibilitiesHorizontal = (OCCUPIED - 2 * binary) ^ Long.reverse(Long.reverse(OCCUPIED)- 2 * Long.reverse(binary) );
        long possibilitiesVertical = ((OCCUPIED & FileMasks8[a % 8]) - (2 * binary)) ^ Long.reverse(Long.reverse(OCCUPIED & FileMasks8[a % 8]) - (2 * Long.reverse(binary)));
        return (possibilitiesHorizontal & RankMasks8[a/8]) | (possibilitiesVertical & FileMasks8[a%8]);
    }

    /** DAndAntiDMoves function calculates all possible diagonal and antidiagonal moves for a given figure
     * @param s - location of the figure from 0 to 63
     * @return a long value representing the possible diagonal or antidiagonal moves
     */
    static long DAndAntiDMoves (int s){
        long binary= 1L<<s;
        long possibilitiesDiagonal = ((OCCUPIED & DiagonalMasks8[(s/8)+(s%8)]) - (2 * binary)) ^ Long.reverse(Long.reverse(OCCUPIED & DiagonalMasks8[(s/8)+(s%8)]) - (2 * Long.reverse(binary)));
        long possibilitiesAntiDiagonal = ((OCCUPIED & AntiDiagonalMasks8[(s/8) + 7 - (s%8)])-(2 * binary)) ^ Long.reverse(Long.reverse(OCCUPIED & AntiDiagonalMasks8[(s/8) + 7 - (s%8)]) - (2* Long.reverse(binary)));
        return (possibilitiesDiagonal & DiagonalMasks8[(s/8) + (s%8)]) | (possibilitiesAntiDiagonal & AntiDiagonalMasks8[(s/8) + 7 - (s%8)]);
    }

    /** possibleMovesW returns a list containing all possible moves which can be conducted by the white player given a specific board configuration.
     *  ALl possible moves for all white figures are taken into account.
     *
     * @param WP - Bitboard for white Pawns
     * @param WN - Bitboard for white Knights
     * @param WB - Bitboard for white Bishops
     * @param WR - Bitboard for white Rooks
     * @param WQ - Bitboard for white Queens
     * @param WK - Bitboard for the white King
     * @param BP - Bitboard for black Pawns
     * @param BN - Bitboard for black Knights
     * @param BB - Bitboard for black Bishops
     * @param BR - Bitboard for black Rooks
     * @param BQ - Bitboard for black Queens
     * @param BK - Bitboard for the black King
     * @return String list containing all possible moves for the white player
     */

    public static String possibleMovesW(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK) {
        NOT_MY_PIECES=~(WP|WN|WB|WR|WQ|WK|BK);//added BK to avoid illegal capture
        MY_PIECES=WP|WN|WB|WR|WQ;//omitted WK to avoid illegal capture
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        EMPTY=~OCCUPIED;
        return PawnW.possibleWP(WP)+
                Knight.possibleN(WN)+
                Bishop.possibleB(WB)+
                Rook.possibleR(WR)+
                Queen.possibleQ(WQ)+
                King.possibleK(WK);
    }

    /** possibleMovesW returns a list containing all possible moves which can be conducted by the black player given a specific board configuration.
     *  All possible moves for all black figures are taken into account.
     *
     * @param WP - Bitboard for white Pawns
     * @param WN - Bitboard for white Knights
     * @param WB - Bitboard for white Bishops
     * @param WR - Bitboard for white Rooks
     * @param WQ - Bitboard for white Queens
     * @param WK - Bitboard for the white King
     * @param BP - Bitboard for black Pawns
     * @param BN - Bitboard for black Knights
     * @param BB - Bitboard for black Bishops
     * @param BR - Bitboard for black Rooks
     * @param BQ - Bitboard for black Queens
     * @param BK - Bitboard for the black King
     * @return String list containing all possible moves for the white player
     */
    public static String possibleMovesB(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK) {
        NOT_MY_PIECES=~(BP|BN|BB|BR|BQ|BK|WK);//added WK to avoid illegal capture
        MY_PIECES=BP|BN|BB|BR|BQ;//omitted BK to avoid illegal capture
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        EMPTY=~OCCUPIED;
        return PawnB.possibleBP(BP)+
                Knight.possibleN(BN)+
                Bishop.possibleB(BB)+
                Rook.possibleR(BR)+
                Queen.possibleQ(BQ)+
                King.possibleK(BK);
    }

    /**
     * generateAllPossibleMoves returns either all possible moves for the white or the black player according to the value of isWhitesTurn.
     *
     * @param WP white pawns
     * @param WN white knights
     * @param WB white
     * @param WR white rooks
     * @param WQ white queens
     * @param WK white kings
     * @param BP black pawns
     * @param BN black knights
     * @param BB black
     * @param BR black rooks
     * @param BQ black queens
     * @param BK black kings
     * @param isWhitesTurn is it white ?
     * @return all possible moves for either the white or the black player
     */
    public static String generateAllPossibleMoves(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ
            ,long BK, boolean isWhitesTurn) {
        String allMoves = "";
        if(isWhitesTurn) {
            allMoves = possibleMovesW(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
            //System.out.println("Weiß am Zug.");
        } else {
            allMoves = possibleMovesB(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
            //System.out.println("Schwarz am Zug.");
        }
        /*
        String[] movesArray = allMoves.split("(?<=\\G.{4})");
        System.out.println(movesArray.length);
        System.out.println(Arrays.toString(movesArray));
        */
        BenchmarkTest.possibleMovesCalculated += allMoves.length()/4;
        return allMoves;
    }


    /**
     * makeMove function conducts a move and returns the new bitboard as a long
     * @param board - bitboard on which move is conducted
     * @param move - String containing 4 numbers; 2 for start and 2 for end position
     * @param type - Type of the figure for which move is conducted
     * @return new bitboard as a long
     */

    public static long makeMove(long board, String move, char type) {
        if (Character.isDigit(move.charAt(3))) {//'regular' move
            int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            int end=(Character.getNumericValue(move.charAt(2))*8)+(Character.getNumericValue(move.charAt(3)));
            if (((board>>>start)&1)==1) {board&=~(1L<<start); board|=(1L<<end);} else {board&=~(1L<<end);}
        } else if (move.charAt(3)=='P') {//pawn promotion
            int start, end;
            if (Character.isUpperCase(move.charAt(2))) {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[1]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[0]);
            } else {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[6]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[7]);
            }
            if (type==move.charAt(2)) {board|=(1L<<end);} else {board&=~(1L<<start); board&=~(1L<<end);}
        } else {
            System.out.print("ERROR: Invalid move type");
        }
        return board;
    }

    /** unsafeForBlack function returns all places white can go to and are therefore unsafe for the black King
     *
     * @param WP - Bitboard for white Pawns
     * @param WN - Bitboard for white Knights
     * @param WB - Bitboard for white Bishops
     * @param WR - Bitboard for white Rooks
     * @param WQ - Bitboard for white Queens
     * @param WK - Bitboard for the white King
     * @param BP - Bitboard for black Pawns
     * @param BN - Bitboard for black Knights
     * @param BB - Bitboard for black Bishops
     * @param BR - Bitboard for black Rooks
     * @param BQ - Bitboard for black Queens
     * @param BK - Bitboard for the black King
     * @return a bitboard which is unsafe for the black King
     */
    public static long unsafeForBlack(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK) {
        long unsafe;
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        //pawn
        unsafe=((WP>>>7)&~FILE_A);//pawn capture right
        unsafe|=((WP>>>9)&~FILE_H);//pawn capture left
        long possibility;
        //knight
        long i=(WN& -WN);
        while(i != 0){
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility=KNIGHT_SPAN<<(iLocation-18);
            }
            else {
                possibility=KNIGHT_SPAN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~FILE_GH;
            }
            else {
                possibility &=~FILE_AB;
            }
            unsafe |= possibility;
            WN&=~i;
            i=WN& -WN;
        }
        //bishop/queen
        long QB=WQ|WB;
        i=QB&~(QB-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=DAndAntiDMoves(iLocation);
            unsafe |= possibility;
            QB&=~i;
            i=QB&~(QB-1);
        }
        //rook/queen
        long QR=WQ|WR;
        i=QR&~(QR-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=HAndVMoves(iLocation);
            unsafe |= possibility;
            QR&=~i;
            i=QR&~(QR-1);
        }
        //king
        int iLocation=Long.numberOfTrailingZeros(WK);
        if (iLocation>9)
        {
            possibility=KING_SPAN<<(iLocation-9);
        }
        else {
            possibility=KING_SPAN>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~FILE_GH;
        }
        else {
            possibility &=~FILE_AB;
        }
        unsafe |= possibility;
        return unsafe;
    }

    /** unsafeForWhite function returns all places black can go to and are therefore unsafe for the white King
     *
     * @param WP - Bitboard for white Pawns
     * @param WN - Bitboard for white Knights
     * @param WB - Bitboard for white Bishops
     * @param WR - Bitboard for white Rooks
     * @param WQ - Bitboard for white Queens
     * @param WK - Bitboard for the white King
     * @param BP - Bitboard for black Pawns
     * @param BN - Bitboard for black Knights
     * @param BB - Bitboard for black Bishops
     * @param BR - Bitboard for black Rooks
     * @param BQ - Bitboard for black Queens
     * @param BK - Bitboard for the black King
     * @return a bitboard which is unsafe for the white King
     */
    public static long unsafeForWhite(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK) {
        long unsafe;
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        //pawn
        unsafe=((BP<<7)&~FILE_H);//pawn capture right
        unsafe|=((BP<<9)&~FILE_A);//pawn capture left
        long possibility;
        //knight
        long i=BN&~(BN-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility=KNIGHT_SPAN<<(iLocation-18);
            }
            else {
                possibility=KNIGHT_SPAN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~FILE_GH;
            }
            else {
                possibility &=~FILE_AB;
            }
            unsafe |= possibility;
            BN&=~i;
            i=BN&~(BN-1);
        }
        //bishop/queen
        // pieces that move diagonal
        long QB=BQ|BB;
        i=QB&~(QB-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=DAndAntiDMoves(iLocation);
            unsafe |= possibility;
            QB&=~i;
            i=QB&~(QB-1);
        }
        //rook/queen
        // pieces that move horizontal and vertical
        long QR=BQ|BR;
        i=QR&~(QR-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=HAndVMoves(iLocation);
            unsafe |= possibility;
            QR&=~i;
            i=QR&~(QR-1);
        }
        //king
        int iLocation=Long.numberOfTrailingZeros(BK);
        if (iLocation>9)
        {
            possibility=KING_SPAN<<(iLocation-9);
        }
        else {
            possibility=KING_SPAN>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~FILE_GH;
        }
        else {
            possibility &=~FILE_AB;
        }
        unsafe |= possibility;
        return unsafe;
    }


/* Function for debugging purposes only*/

    public static void drawBitboard(long bitBoard) {
        String chessBoard[][]=new String[8][8];
        for (int i=0;i<64;i++) {
            chessBoard[i/8][i%8]="";
        }
        for (int i=0;i<64;i++) {
            if (((bitBoard>>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
            if ("".equals(chessBoard[i/8][i%8])) {chessBoard[i/8][i%8]="   ";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }


    /* public static void timeExperiment(long WP) {
        int loopLength=1000;
        long startTime=System.currentTimeMillis();
        tEMethodA(loopLength, WP);
        long endTime=System.currentTimeMillis();
        System.out.println("That took "+(endTime-startTime)+" milliseconds for the first method");
        startTime=System.currentTimeMillis();
        tEMethodB(loopLength, WP);
        endTime=System.currentTimeMillis();
        System.out.println("That took "+(endTime-startTime)+" milliseconds for the second method");
    }
    public static void tEMethodA(int loopLength, long WP) {
        for (int loop=0;loop<loopLength;loop++)
        {

        }
    }
    public static void tEMethodB(int loopLength, long WP) {
        for (int loop=0;loop<loopLength;loop++)
        {

        }
    } */
    public long getOCCUPIED(){
        return OCCUPIED;
    }

}

