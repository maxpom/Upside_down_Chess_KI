package UpsideDownChessBB.Search;

import UpsideDownChessBB.BoardGeneration;
import UpsideDownChessBB.PiecesAndMoves.MoveGenerator;
import UpsideDownChessBB.Tests.BenchmarkTest;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

public class Evaluation {
    public  int pawnValue;
    public  int knightValue;
    public  int bishopValue;
    public  int rookValue;
    public  int queenValue;
    public  int kingValue;

    public Evaluation(){
         pawnValue = 100;
         knightValue = 220;
         bishopValue = 250;
         rookValue = 400;
         queenValue = 600;
         kingValue = 9999;
    }

    public Evaluation(int PV,int NV,int BV,int RV,int QV,int KV){
        pawnValue=PV;
        knightValue=NV;
        bishopValue=BV;
        rookValue=RV;
        queenValue=QV;
        kingValue=KV;
    }
    public Evaluation(File file){
        try {
            Scanner Reader = new Scanner(file);
            String data = Reader.nextLine();
            while(Reader.hasNextLine()){
                 data = Reader.nextLine();
            }
            String[] a=data.split(",");
            int counter=0;
            int[]b=new int[a.length];
            for (String e:a) {
                b[counter]=Integer.parseInt(e);
                counter++;
            }
            pawnValue=b[0];
            knightValue=b[1];
            bishopValue=b[2];
            rookValue=b[3];
            queenValue=b[4];
            kingValue=b[5];

            Reader.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
    // Piece-square tables to evaluate the positioning of certain chessboards

    static int whiteKnightTable [] = {-50, -40, -30, -30, -30, -30, -40, -50, -40, -20, 0, 5,5,0,-20,-40, -30, 5, 10 ,15
            ,15 ,10, 5, -30, -30, 0, 15, 20, 20, 15, 0, -30, -30, 5, 15, 20, 20, 15, 5 ,-30, -30, 0, 10, 15, 15, 10, 0, -30, -40,-20,
            0,0,0,0,-20,-40, -50,-40,-30,-30,-30,-30,-40,-50 };

    static int whiteBishopTable [] = {-20,-10, -10, -10, -10 , -10, -10 , -20, -10, 5, 0,0,0,0,5,-10,-10,10,10,10,10,10,10,
            -10,-10,0,10,10,10,10,0,-10,-10,5,5,10,10,5,5,-10,-10,0,5,10,10,5,0,-10,-10,0,0,0,0,0,0,-10,-20,-10,-10,-10,-10
            ,-10,-10,-20};

    static int whiteRookTable [] = {0,0,0,5,5,0,0,0,-5,0,0,0,0,0,0,-5,-5,0,0,0,0,0,0,-5,-5,0,0,0,0,0,0,-5,-5,0,0,0,0,0,0,
            -5,-5,0,0,0,0,0,0,-5,5,10,10,10,10,10,10,5,0,0,0,0,0,0,0,0};

    static int whiteQueenTable [] = {-20,-10,-10,-5,-5,-10,-10,-20,-10,0,0,0,0,0,0,-10,-10,0,5,5,5,5,0,-10,-5,0,5,5,5,5,0,-5,
            -5,0,5,5,5,5,0,-5, -10 ,0,5,5,5,5,0,-10,-10,0,0,0,0,0,0,-10,-20,-10,-10,-5,-5,-10,-10,-20};

    static int whiteKingTable [] = {20,30,10,0,0,10,30,20,
                                    20,20,0,0,0,0,20,20,
                                    -10,-20,-20,-20,-20,-20, -20,-10,
                                    -20,-30,-30, -40,-40,-30,-30,-20,
                                    -30,-40,-40,-50,-50,-40,-40,-30,
                                    -30,-40,-40,-50,-50,-40,-40,-30,
                                    -30,-40,-40,-50,-50,-40, -40,-30,
                                    -30,-40,-40,-50,-50,-40,-40,-30};

    static int blackKnightTable [] = {-50, -40, -30, -30, -30, -30, -40, -50, -40, -20, 0, 0, 0, 0, -20, -40, -30, 0, 10,
            15, 15, 10, 0, -30, -30, 5, 15, 20, 20, 15, 5, -30, -30, 0, 15, 20, 20, 15, 0, -30, -30, 5, 10, 15, 15, 10, 5, -30,
            -40, -20, 0, 5, 5, 0, -20, -40, -50, -40, -30, -30, -30, -30, -40, -50};

    static int blackBishopTable [] = {20, -10, -10, -10, -10, -10, -10, -20, -10, 0, 0, 0, 0, 0, 0, -10, -10, 0, 5, 10,
            10, 5, 0, -10, -10, 5, 5, 10, 10, 5, 5, -10, -10, 0, 10, 10, 10, 10, 0, -10, -10, 10, 10, 10, 10, 10, 10, -10,
            -10, 5, 0, 0, 0, 0, 5, -10, -20, -10, -10, -10, -10, -10, -10, -20};

    static int blackRookTable [] ={0, 0, 0, 0, 0, 0, 0, 0, 5, 10, 10, 10, 10, 10, 10, 5, -5, 0, 0, 0, 0, 0, 0, -5, -5,
            0, 0, 0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5, 0, 0, 0,
            5, 5, 0, 0, 0};

    static int blackQueenTable [] = {-20, -10, -10, -5, -5, -10, -10, -20, -10, 0, 0, 0, 0, 0, 0, -10, -10, 0, 5, 5, 5,
            5, 0, -10, -5, 0, 5, 5, 5, 5, 0, -5, -5, 0, 5, 5, 5, 5, 0, -5, -10, 0, 5, 5, 5, 5, 0, -10, -10, 0, 0, 0, 0,
            0, 0, -10, -20, -10, -10, -5, -5, -10, -10, -20};

    static int blackKingTable [] = {-30, -40, -40, -50, -50, -40, -40, -30, -30, -40, -40, -50, -50, -40, -40, -30, -30,
            -40, -40, -50, -50, -40, -40, -30, -30, -40, -40, -50, -50, -40, -40, -30, -20, -30, -30, -40, -40, -30, -30,
            -20, -10, -20, -20, -20, -20, -20, -20, -10, 20, 20, 0, 0, 0, 0, 20, 20, 20, 30, 10, 0, 0, 10, 30, 20};


    //triviale Bewertungsfunktion, erstmal kopiert von: https://de.wikipedia.org/wiki/Schachprogramm#Bewertungsfunktionen
    // https://www.freecodecamp.org/news/simple-chess-ai-step-by-step-1d55a9266977/

    /**
     * Diese Methode bewertet eine einzelne übergebene Board-Stellung, dafür werden alle 12 bitboards übergeben.
     *
     * @param WP
     * @param WN
     * @param WB
     * @param WR
     * @param WQ
     * @param WK
     * @param BP
     * @param BN
     * @param BB
     * @param BR
     * @param BQ
     * @param BK
     * @return - berechneter Wert des boards
     */
    /*
    public static int evaluate (long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK){
        int value = 0;
        BenchmarkTest.movesEvaluated++;
        // value größer 0 -> gut für weiß
        // value kleiner als 0 -> gut für schwarz

        value += Long.bitCount(WP) * pawnValue;
        value += Long.bitCount(WN) * knightValue;
        value += Long.bitCount(WB) * bishopValue;
        value += Long.bitCount(WR) * rookValue;
        value += Long.bitCount(WQ) * queenValue;
        value += (Long.bitCount(WK) - 1) * kingValue;
        //1 King vorhanden -> value bleibt gleich
        //kein King vorhanden -> sehr schlechte Stellung für weiß

        //if(King.possibleK(WK) == "") value -= 1801; TODO: attempt at mobilizing the king in early moves

        value -= Long.bitCount(BP) * pawnValue;
        value -= Long.bitCount(BN) * knightValue;
        value -= Long.bitCount(BB) * bishopValue;
        value -= Long.bitCount(BR) * rookValue;
        value -= Long.bitCount(BQ) * queenValue;
        value -= (Long.bitCount(BK) - 1) * kingValue;

        //if(King.possibleK(BK) == "") value += 1801;

        return value;
    }
    */




    // bitboards represent a given board;
    // list contains all possible moves
    // depth --> when engine searches deeper the accuracy of the rating changes. Deeper rating positions are more important than earlier ones.

    /**
     * Evaluates the board for both, the white and the black player, maximizing for the white and minimizing for the white player
     * @param WP
     * @param WN
     * @param WB
     * @param WR
     * @param WQ
     * @param WK
     * @param BP
     * @param BN
     * @param BB
     * @param BR
     * @param BQ
     * @param BK
     * @return  an int value, assessing the value for the given chess board
     */
    public static int evaluate(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK){

        BenchmarkTest.movesEvaluated++;

        String possibleMovesWhite= MoveGenerator.generateAllPossibleMoves(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, true);

        String possibleMovesBlack= MoveGenerator.generateAllPossibleMoves(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, false);


        int value = 0;

        value += rateMaterial(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);

        value += rateMoveAbility(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, possibleMovesWhite.length());

        value -= rateMoveAbility(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, possibleMovesBlack.length());

        value += ratePositional(WN, WB, WR, WQ, WK,BN, BB,BR, BQ, BK);

        value += rateAttack(WP, WN, WB, WR, WQ, WK, BP, BN, BB,BR, BQ, BK);



        // höhere Depth soll value erhöhen?

        return value;
    }

    /**
     * Assessing the material for the white and the black player
     */

    public static int rateMaterial(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK){
        int value = 0;
        Evaluation ev = new Evaluation();
        value += Long.bitCount(WP) * ev.pawnValue;
        value += Long.bitCount(WN) * ev.knightValue;
        value += Long.bitCount(WB) * ev.bishopValue;
        value += Long.bitCount(WR) * ev.rookValue;
        value += Long.bitCount(WQ) * ev.queenValue;
        value += (Long.bitCount(WK) - 1) * ev.kingValue;

        value -= Long.bitCount(BP) * ev.pawnValue;
        value -= Long.bitCount(BN) * ev.knightValue;
        value -= Long.bitCount(BB) * ev.bishopValue;
        value -= Long.bitCount(BR) * ev.rookValue;
        value -= Long.bitCount(BQ) * ev.queenValue;
        value -= (Long.bitCount(BK) - 1) * ev.kingValue;


        return value;
    }

    /** ratePositional rates the given chessboard regarding the positions of figures using the different piece square tables
     *
     * @param WN
     * @param WB
     * @param WR
     * @param WQ
     * @param WK
     * @param BN
     * @param BB
     * @param BR
     * @param BQ
     * @param BK
     * @return
     */
    public static int ratePositional(long WN, long WB, long WR, long WQ, long WK, long BN, long BB, long BR, long BQ, long BK){
        int value = 0;

        value+= rateBoardPositions(WN, whiteKnightTable);
        value+= rateBoardPositions(WB, whiteBishopTable);
        value+= rateBoardPositions(WR, whiteRookTable);
        value+= rateBoardPositions(WQ, whiteQueenTable);
        value+= rateBoardPositions(WK, whiteKingTable);


        value-= rateBoardPositions(BN, blackKnightTable);
        value-= rateBoardPositions(BB, blackBishopTable);
        value-= rateBoardPositions(BR, blackRookTable);
        value-= rateBoardPositions(BQ, blackQueenTable);
        value-= rateBoardPositions(BK, blackKingTable);


        return value;
    }

    /**
     * help function that uses piece square tables to rate boards for a given figure
     * @param BB bitboard of respective figure
     * @param pieceSquareTable table rating the positions for a particular figure
     * @return  value that rates the positions for the figure
     */
    public static int rateBoardPositions(long BB, int[] pieceSquareTable){
        int value = 0;

        String bits = String.format("%64s", Long.toBinaryString(BB)).replace(' ', '0');
        for(int i = 0 ; i < bits.length(); i ++){
            if(bits.charAt(i) == '1')value+=pieceSquareTable[63-i];
        }

        return value;
    }

    /**
     *
     * @param WP
     * @param WN
     * @param WB
     * @param WR
     * @param WQ
     * @param WK
     * @param BP
     * @param BN
     * @param BB
     * @param BR
     * @param BQ
     * @param BK
     * @param listLength - moves times 4 which can be conducted by the white or black player, assessing the moveability and flexibility for a given board
     * @return a value assessing the moveability for the given player; the more valid moves available the higher the score
     */

    public static int rateMoveAbility(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK, int listLength){
        int value = 0 ;
        value += listLength; // (4 pointer per valid move)

        /*
        if(listLength == 0 ) { // current side is in checkmate or stalemate
            // depth wirkt sich positiv auf Verlust bei Checkmate aus?
            if (isThisACheckmate(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, BoardGeneration.WhiteToMove)) ;
            value += -200000 ;
        }else {
            value += -150000 ;
        }
        */

        return value;
    }


    /**
     *
     * @param WP
     * @param WN
     * @param WB
     * @param WR
     * @param WQ
     * @param WK
     * @param BP
     * @param BN
     * @param BB
     * @param BR
     * @param BQ
     * @param BK
     * @return a value for a given board, assessing the danger of losing figures for the white and black player
     */
    public static int rateAttack(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK
            ){

        int value = 0;

        value += Long.bitCount(MoveGenerator.unsafeForWhite(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&WP)*-50;
        value += Long.bitCount(MoveGenerator.unsafeForWhite(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&WN)*-200;
        value += Long.bitCount(MoveGenerator.unsafeForWhite(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&WR)*-350;
        value += Long.bitCount(MoveGenerator.unsafeForWhite(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&WQ)*-750;
        value += Long.bitCount(MoveGenerator.unsafeForWhite(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&WK)*-2000;

        value -= Long.bitCount(MoveGenerator.unsafeForBlack(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&BP)*-50;

        value -= Long.bitCount(MoveGenerator.unsafeForBlack(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&BN)*-200;
        value -= Long.bitCount(MoveGenerator.unsafeForBlack(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&BR)*-350;
        value -= Long.bitCount(MoveGenerator.unsafeForBlack(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&BQ)*-750;
        value -= Long.bitCount(MoveGenerator.unsafeForBlack(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK)&BK)*-2000;


        return value;
    }


    /**
     * This method is only called after a possible check-state has been discovered. It checks if the existing check
     * is a checkmate by computing all possible moves for the opponent. If the opponent is still in check after every move,
     * it is a checkmate.
     *
     * @return - is this a checkmate?
     */
    public static boolean isThisACheckmate(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ
            ,long BK, boolean isWhitesTurn) {
        BenchmarkTest.possibleCheckMatesAnalyzed++;
        if(isWhitesTurn) {     //Is the black king checkmate?
            String moves = MoveGenerator.generateAllPossibleMoves(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK,
                   false);
            String[] movesArray = moves.split("(?<=\\G.{4})");
            // System.out.println("\n The list of moves: "+ Arrays.toString(movesArray)+"(white is checking) \n");
            for (String move : movesArray) {
                long WP2 = MoveGenerator.makeMove(WP, move, 'P');
                long WN2 = MoveGenerator.makeMove(WN, move, 'N');
                long WB2 = MoveGenerator.makeMove(WB, move, 'B');
                long WR2 = MoveGenerator.makeMove(WR, move, 'R');
                long WQ2 = MoveGenerator.makeMove(WQ, move, 'Q');
                long WK2 = MoveGenerator.makeMove(WK, move, 'K');
                long BP2 = MoveGenerator.makeMove(BP, move, 'p');
                long BN2 = MoveGenerator.makeMove(BN, move, 'n');
                long BB2 = MoveGenerator.makeMove(BB, move, 'b');
                long BR2 = MoveGenerator.makeMove(BR, move, 'r');
                long BQ2 = MoveGenerator.makeMove(BQ, move, 'q');
                long BK2 = MoveGenerator.makeMove(BK, move, 'k');

                if (!isThisACheckState(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, true)) {
                    return false;
                } else {

                }
            }
        } else { // is the white king checkmate?
            String moves = MoveGenerator.generateAllPossibleMoves(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK,
                    true);
            String[] movesArray = moves.split("(?<=\\G.{4})");
            // System.out.println("\n The list of moves: "+Arrays.toString(movesArray)+"(black is checking) \n");
            for (String move : movesArray) {
                long WP2 = MoveGenerator.makeMove(WP, move, 'P');
                long WN2 = MoveGenerator.makeMove(WN, move, 'N');
                long WB2 = MoveGenerator.makeMove(WB, move, 'B');
                long WR2 = MoveGenerator.makeMove(WR, move, 'R');
                long WQ2 = MoveGenerator.makeMove(WQ, move, 'Q');
                long WK2 = MoveGenerator.makeMove(WK, move, 'K');
                long BP2 = MoveGenerator.makeMove(BP, move, 'p');
                long BN2 = MoveGenerator.makeMove(BN, move, 'n');
                long BB2 = MoveGenerator.makeMove(BB, move, 'b');
                long BR2 = MoveGenerator.makeMove(BR, move, 'r');
                long BQ2 = MoveGenerator.makeMove(BQ, move, 'q');
                long BK2 = MoveGenerator.makeMove(BK, move, 'k');

                if (!isThisACheckState(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, false)) {

                    return false;
                } else {

                }
            }
        }
        return true;
    }

    /**
     * This method checks if the opponent is in check after the board has been altered by the players move.
     * If the opponent is indeed checked by the move that white is trying to make, it also has to be a checkmate,
     * otherwise the move is illegal.
     *
     * @return - is this a check?
     */
    public static boolean isThisACheckState(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ
            ,long BK, boolean isWhitesTurn) {
        long attackedSquares;
        BenchmarkTest.possibleCheckStatesAnalyzed++;
        //checks if whites move checks the black king
        if (isWhitesTurn) {
            attackedSquares = MoveGenerator.unsafeForBlack(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
            if ((attackedSquares & BK) == 0) {
                return false;
            } else {
                return true;
            }
        }
        // checks if blacks move checks the white king
        else {
            attackedSquares = MoveGenerator.unsafeForWhite(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
            if ((attackedSquares & WK) == 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "pawnValue=" + pawnValue +
                ", knightValue=" + knightValue +
                ", bishopValue=" + bishopValue +
                ", rookValue=" + rookValue +
                ", queenValue=" + queenValue +
                ", kingValue=" + kingValue +
                '}';
    }
    /*
    public String pickBestMove(String[][] chessBoard, long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK, boolean white){
        String possibleMoves = MoveGenerator.generateAllPossibleMoves(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK, white);
        String bestmove="";
        return bestmove;
    } */
}
