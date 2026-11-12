package UpsideDownChessBB;

import UpsideDownChessBB.Search.BetterSearch;
import UpsideDownChessBB.Search.Zobrist;

import java.util.Arrays;

/* Beim Aufbau unserer Bitboards haben wir uns an der Youtube Tutorial Reihe zu Bitboards in Java von „Logic Crazy Chess" orientiert
 * und die Funktionen teilweise übernommen und an unser Upside-Down Chess angepasst.
 * Youtube Link : https://www.youtube.com/playlist?list=PLQV5mozTHmacMeRzJCW_8K3qw2miYqd0c
 *  */

public class BoardGeneration {
    public static long WP = 0L, WN = 0L, WB = 0L, WR = 0L, WQ = 0L, WK = 0L, BP = 0L, BN = 0L, BB = 0L, BR = 0L, BQ = 0L, BK = 0L;
    public static boolean WhiteToMove = true; //true = castle is possible
    public static boolean gameOver = false;
    public static long TimeLimit = 10000;
    public static int currentTurn = 0;
    public static final int maxTurns = 50;

    /**
     * This function initiates an UpsideDown-ChessBoard using 12 different bitboards saved in a long variable
     * for each type of piece and an array representing the initial configuration.
     */

    public static void initiateUpsideDownChess() {
        Zobrist.fillZobristArray();
        long WP = 0L, WR = 0L, WN = 0L, WB = 0L, WQ = 0L, WK = 0L, BP = 0L, BR = 0L, BN = 0L, BB = 0L, BQ = 0L, BK = 0L;

        // two dimensional Array
        String[][] UDChessBoard = {
                {"R", "N", "B", "Q", "K", "B", "N", "R"},
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {"r", "n", "b", "q", "k", "b", "n", "r"}
        };


        // Calling function to translate array into the bitboards
        arrayToBitBoards(UDChessBoard, WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK);
        if (BetterSearch.showBoards) {
            BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                    BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
        }
        BoardGeneration.TimeLimit = 1000;
    }

    /**
     * arrayToBitboards is using a two-dimensional array (representing the board) to fill the 12 bitboards.
     *
     * @param chessBoard - chessboard used to fill bitboards of 12 different figures
     * @param WP         - Bitboard for white Pawns
     * @param WN         - Bitboard for white Knights
     * @param WB         - Bitboard for white Bishops
     * @param WR         - Bitboard for white Rooks
     * @param WQ         - Bitboard for white Queens
     * @param WK         - Bitboard for the white King
     * @param BP         - Bitboard for black Pawns
     * @param BN         - Bitboard for black Knights
     * @param BB         - Bitboard for black Bishops
     * @param BR         - Bitboard for black Rooks
     * @param BQ         - Bitboard for black Queens
     * @param BK         - Bitboard for the black King
     */


    public static void arrayToBitBoards(String[][] chessBoard, long WP, long WN, long WB, long WR, long WQ, long WK,
                                        long BP, long BN, long BB, long BR, long BQ, long BK) {
        String Binary;
        for (int i = 0; i < 64; i++) {
            Binary = "0000000000000000000000000000000000000000000000000000000000000000";
            Binary = Binary.substring(i + 1) + "1" + Binary.substring(0, i);
            switch (chessBoard[i / 8][i % 8]) {
                case "P":
                    WP += convertStringToBitBoard(Binary);
                    break;
                case "N":
                    WN += convertStringToBitBoard(Binary);
                    break;
                case "B":
                    WB += convertStringToBitBoard(Binary);
                    break;
                case "R":
                    WR += convertStringToBitBoard(Binary);
                    break;
                case "Q":
                    WQ += convertStringToBitBoard(Binary);
                    break;
                case "K":
                    WK += convertStringToBitBoard(Binary);
                    break;
                case "p":
                    BP += convertStringToBitBoard(Binary);
                    break;
                case "n":
                    BN += convertStringToBitBoard(Binary);
                    break;
                case "b":
                    BB += convertStringToBitBoard(Binary);
                    break;
                case "r":
                    BR += convertStringToBitBoard(Binary);
                    break;
                case "q":
                    BQ += convertStringToBitBoard(Binary);
                    break;
                case "k":
                    BK += convertStringToBitBoard(Binary);
                    break;
            }
        }
        updateStaticVariables(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
    }

    /**
     * updateStaticVariables updates the static bitboards with bitboards given as function parameters
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
     */
    public static void updateStaticVariables(long WP, long WN, long WB, long WR, long WQ, long WK,
                                             long BP, long BN, long BB, long BR, long BQ, long BK) {
        BoardGeneration.WP = WP;
        BoardGeneration.WN = WN;
        BoardGeneration.WB = WB;
        BoardGeneration.WR = WR;
        BoardGeneration.WQ = WQ;
        BoardGeneration.WK = WK;
        BoardGeneration.BP = BP;
        BoardGeneration.BN = BN;
        BoardGeneration.BB = BB;
        BoardGeneration.BR = BR;
        BoardGeneration.BQ = BQ;
        BoardGeneration.BK = BK;
    }


    /**
     * convertStringToBitBoard function converts a given binary value stored in a String to a bitboard stored in a long
     *
     * @param Binary - String value to convert
     * @return Long value representing the binary String
     */
    public static long convertStringToBitBoard(String Binary) {
        if (Binary.charAt(0) == '0') {
            return Long.parseLong(Binary, 2);
        } else {
            return Long.parseLong("1" + Binary.substring(2), 2) * 2;
        }
    }

    /**
     * drawArray function uses input to print a two-dimensional 8x8 array representing the board
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
     */
    public static void drawArray(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK) {
        String[][] chessBoard = new String[8][8];
        for (int i = 0; i < 64; i++) {
            chessBoard[i / 8][i % 8] = " ";
        }
        for (int i = 0; i < 64; i++) {
            if (((WP >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "P";
            }
            if (((WN >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "N";
            }
            if (((WB >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "B";
            }
            if (((WR >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "R";
            }
            if (((WQ >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "Q";
            }
            if (((WK >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "K";
            }
            if (((BP >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "p";
            }
            if (((BN >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "n";
            }
            if (((BB >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "b";
            }
            if (((BR >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "r";
            }
            if (((BQ >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "q";
            }
            if (((BK >> i) & 1) == 1) {
                chessBoard[i / 8][i % 8] = "k";
            }
        }
        for (int i = 0; i < 8; i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
        System.out.println("\n");
    }

    /**
     * This method functions very similar to string.split(), but seems to be way more efficient.
     * <p>
     * Thanks to: https://stackoverflow.com/questions/12295711/split-a-string-at-every-nth-position/12297231
     *
     * @param s         - String that will be split
     * @param splitSize - size of each string in the result array
     * @return - result array containing strings of size splitSize
     */
    public static String[] splitStringEvery(String s, int splitSize) {
        int arrayLength = (int) Math.ceil(((s.length() / (double) splitSize)));
        String[] result = new String[arrayLength];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + splitSize);
            j = j + splitSize;
        } //Add the last bit
        result[lastIndex] = s.substring(j);

        return result;
    }


    /**
     * importFEN function sets necessary variables for an UpsideDown-Chess game using FEN-Notation String
     *
     * @param fenString - String representing the formation of figures on the board and other variables like player to make next turn
     */


    public static void importFEN(String fenString) {

        importServerFEN(fenString);
        // drawArray(BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK);
    }

    /**
     * importServerFEN is used in PlayGame to load one move
     */

    public static void importServerFEN(String fenString) {
//RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 1 100
        BoardGeneration.WP = 0;
        BoardGeneration.WN = 0;
        BoardGeneration.WB = 0;
        BoardGeneration.WR = 0;
        BoardGeneration.WQ = 0;
        BoardGeneration.WK = 0;
        BoardGeneration.BP = 0;
        BoardGeneration.BN = 0;
        BoardGeneration.BB = 0;
        BoardGeneration.BR = 0;
        BoardGeneration.BQ = 0;
        BoardGeneration.BK = 0;
        int charIndex = 0;
        int boardIndex = 0;
        while (fenString.charAt(charIndex) != ' ') {
            switch (fenString.charAt(charIndex++)) {
                case 'P':
                    BoardGeneration.WP |= (1L << boardIndex++);
                    break;
                case 'p':
                    BoardGeneration.BP |= (1L << boardIndex++);
                    break;
                case 'N':
                    BoardGeneration.WN |= (1L << boardIndex++);
                    break;
                case 'n':
                    BoardGeneration.BN |= (1L << boardIndex++);
                    break;
                case 'B':
                    BoardGeneration.WB |= (1L << boardIndex++);
                    break;
                case 'b':
                    BoardGeneration.BB |= (1L << boardIndex++);
                    break;
                case 'R':
                    BoardGeneration.WR |= (1L << boardIndex++);
                    break;
                case 'r':
                    BoardGeneration.BR |= (1L << boardIndex++);
                    break;
                case 'Q':
                    BoardGeneration.WQ |= (1L << boardIndex++);
                    break;
                case 'q':
                    BoardGeneration.BQ |= (1L << boardIndex++);
                    break;
                case 'K':
                    BoardGeneration.WK |= (1L << boardIndex++);
                    break;
                case 'k':
                    BoardGeneration.BK |= (1L << boardIndex++);
                    break;
                case '/':
                    break;
                case '1':
                    boardIndex++;
                    break;
                case '2':
                    boardIndex += 2;
                    break;
                case '3':
                    boardIndex += 3;
                    break;
                case '4':
                    boardIndex += 4;
                    break;
                case '5':
                    boardIndex += 5;
                    break;
                case '6':
                    boardIndex += 6;
                    break;
                case '7':
                    boardIndex += 7;
                    break;
                case '8':
                    boardIndex += 8;
                    break;
                default:
                    break;
            }
        }


        BoardGeneration.WhiteToMove = (fenString.charAt(++charIndex) == 'w');
        charIndex += 2;
        String tmp = "";

        while (fenString.charAt(charIndex) != ' ') {
            tmp += fenString.charAt(charIndex++);
        }

        BoardGeneration.currentTurn = Integer.parseInt(tmp);
        tmp = "";
        ++charIndex;
        while (fenString.charAt(charIndex) != ' ') {
            if (charIndex == fenString.length() - 1) {
                tmp += fenString.charAt(charIndex);
                break;
            } else {
                tmp += fenString.charAt(charIndex++);
            }
        }
        double time = Double.parseDouble(tmp);
        if (!BetterSearch.benchmark) BoardGeneration.setTimeLimit(time, BoardGeneration.currentTurn);

        if (BetterSearch.showBoards) {
            BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                    BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
        }
    }


    /**
     * Converts The current Board into a FEN String.
     */
    public static String exportFEN() {

        StringBuilder fenString = new StringBuilder();

        String wps = String.format("%64s", Long.toBinaryString(WP)).replace(' ', '0');
        String wns = String.format("%64s", Long.toBinaryString(WN)).replace(' ', '0');
        String wbs = String.format("%64s", Long.toBinaryString(WB)).replace(' ', '0');
        String wrs = String.format("%64s", Long.toBinaryString(WR)).replace(' ', '0');
        String wqs = String.format("%64s", Long.toBinaryString(WQ)).replace(' ', '0');
        String wks = String.format("%64s", Long.toBinaryString(WK)).replace(' ', '0');
        String bps = String.format("%64s", Long.toBinaryString(BP)).replace(' ', '0');
        String bns = String.format("%64s", Long.toBinaryString(BN)).replace(' ', '0');
        String bbs = String.format("%64s", Long.toBinaryString(BB)).replace(' ', '0');
        String brs = String.format("%64s", Long.toBinaryString(BR)).replace(' ', '0');
        String bqs = String.format("%64s", Long.toBinaryString(BQ)).replace(' ', '0');
        String bks = String.format("%64s", Long.toBinaryString(BK)).replace(' ', '0');


        for (int i = 0; i < 64; i++) {

            if (wps.charAt(i) == '1') fenString.append("P");
            else if (wns.charAt(i) == '1') fenString.append("N");
            else if (wbs.charAt(i) == '1') fenString.append("B");
            else if (wrs.charAt(i) == '1') fenString.append("R");
            else if (wqs.charAt(i) == '1') fenString.append("Q");
            else if (wks.charAt(i) == '1') fenString.append("K");
            else if (bps.charAt(i) == '1') fenString.append("p");
            else if (bns.charAt(i) == '1') fenString.append("n");
            else if (bbs.charAt(i) == '1') fenString.append("b");
            else if (bqs.charAt(i) == '1') fenString.append("q");
            else if (brs.charAt(i) == '1') fenString.append("r");
            else if (bks.charAt(i) == '1') fenString.append("k");


            else fenString.append("1");


            if ((i + 1) % 8 == 0 && i != 63) fenString.append("/");


        }
        fenString = new StringBuilder(fenString.toString().replace("11111111", "8"));
        fenString = new StringBuilder(fenString.toString().replace("1111111", "7"));
        fenString = new StringBuilder(fenString.toString().replace("111111", "6"));
        fenString = new StringBuilder(fenString.toString().replace("11111", "5"));
        fenString = new StringBuilder(fenString.toString().replace("1111", "4"));
        fenString = new StringBuilder(fenString.toString().replace("111", "3"));
        fenString = new StringBuilder(fenString.toString().replace("11", "2"));
        fenString = new StringBuilder(new StringBuilder(fenString.toString()).reverse().toString());

        if (WhiteToMove) fenString.append(" w ");
        else fenString.append(" b ");

        fenString.append(" - 0 0");

        fenString = new StringBuilder(fenString.toString().replace("  ", " "));

        return fenString.toString();
    }

    /**
     * @param move - move containing 4 digits or if promotion a "P" and the respective figure which will be promoted for
     * @return - a String which can be processed as a move by the server
     */

    public static String transformMove(String move) {

        int rowStart = 0;
        int columnStart = 0;
        int rowEnd = 0;
        int columnEnd = 0;
        int indexStart;
        int indexEnd;
        String promotion = "";
        String serverString;

        if (!move.contains("P")) {
            rowStart = Integer.parseInt(move.substring(0, 1));
            columnStart = Integer.parseInt(move.substring(1, 2));
            rowEnd = Integer.parseInt(move.substring(2, 3));
            columnEnd = Integer.parseInt(move.substring(3, 4));
        }

        if (move.contains("P")) {
            rowStart = Character.isLowerCase(move.charAt(2)) ? 6 : 1;
            rowEnd = Character.isLowerCase(move.charAt(2)) ? 7 : 0;
            columnStart = Integer.parseInt(move.substring(0, 1));
            columnEnd = Integer.parseInt(move.substring(1, 2));
            promotion = Character.toString(Character.toUpperCase(move.charAt(2)));
        }

        indexStart = rowStart * 8 + columnStart;
        indexEnd = rowEnd * 8 + columnEnd;

        serverString = "" + indexStart + "," + indexEnd + "," + promotion;

        return serverString;
    }

    /**
     *
     * @param ServerMove
     * @return Servermove parsed to our executable Move Format
     */
    public static String reverseServerMove(String ServerMove) {
        String[] move = ServerMove.split(",");
        int start = Integer.parseInt(move[0]);
        int end = Integer.parseInt(move[1]);
        String promotion = "";
        if (move.length > 2) promotion = move[2];
        String promotionString;

        if (promotion.length() > 0 && Integer.parseInt(move[1]) < 8) promotionString = "qP";
        else if (promotion.length() > 0) promotionString = "QP";
        else promotionString = "";


        String reversedMove = "" + start / 8 + start % 8 + "" + end / 8 + end % 8 + "" + promotionString;

        return reversedMove;

    }


    /**
     * Takes a time parameter that defines the time the AI has left in total. (Time is in seconds).
     * Sets the TimeLimit for one turn considering how much time is left in total and the number of the current turn.
     *
     * @param time
     * @param turn
     */
    public static void setTimeLimit(double time, int turn) {
        if (time < 5) {
            BoardGeneration.TimeLimit = 250;
            // Adrenaline mode: Low on time, only minimal time assigned per turn
        } else if (time < 10 || turn > (0.84 * BoardGeneration.maxTurns)) {
            BoardGeneration.TimeLimit = 1000;
            // getting close to the presumable end of the game
        } else {
            double restTime = time / (maxTurns - currentTurn);
            BoardGeneration.TimeLimit = Math.round(restTime * 1000);
            //Anywhere else: split remaining time evenly, depending on maxTurns
        }
    }


}


