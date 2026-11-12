package UpsideDownChessBB.Tests;

import UpsideDownChessBB.BoardGeneration;
import UpsideDownChessBB.PiecesAndMoves.MoveGenerator;
import UpsideDownChessBB.Search.*;

import java.io.File;
import java.io.IOException;



/**
 * This class is used to measure the speed of our move generator. It uses a doRandomMove-method as opposed to our
 * doMove method, which would always go for the same moves and thus always lead to the same sequence of moves.
 */
public class BenchmarkTest {
    public static long possibleMovesCalculated = 0;
    public static long movesTakenUntilGameEnd = 0;
    public static long possibleCheckStatesAnalyzed = 0;
    public static long possibleCheckMatesAnalyzed = 0;
    public static long timeUntilGameEnd = 0;
    public static long movesEvaluated = 0;
    static boolean alternating = true;
    static long start;
    static long end;
    static int movesMadePerSecond = 0;
    static int possibleMovesPerSecond = 0;
    public static int maxDepth =0;
    public static String bestmove="";


    public static void main(String[] args) {
        possibleMovesCalculated = 0;
        movesTakenUntilGameEnd = 0;
        possibleCheckStatesAnalyzed = 0;
        possibleCheckMatesAnalyzed = 0;
        timeUntilGameEnd = 0;
        BetterSearch.benchmark = true;
        Zobrist.fillZobristArray();
        BenchmarkTest bench1 = new BenchmarkTest();
        try {
            bench1.benchmarkToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * runs exactly one game until it ends and appends collected data to a csv-file.
     *
     * @throws IOException
     */
    private void benchmarkToFile() throws IOException {
        //BoardGeneration.initiateUpsideDownChess();
        BoardGeneration.importServerFEN("RQBQKBQR/P1PPPP1P/N4N2/1n6/8/8/p1pppppp/rqbqkbnr b 0 0");

        start = System.currentTimeMillis();
        /*while (!BoardGeneration.gameOver) {
            doRandomMove(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                    BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                    BoardGeneration.WhiteToMove);
            BoardGeneration.WhiteToMove = !BoardGeneration.WhiteToMove;
            System.out.println(BoardGeneration.exportFEN(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                    BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                    BoardGeneration.WhiteToMove, BoardGeneration.CWK, BoardGeneration.CWQ, BoardGeneration.CBK, BoardGeneration.CBQ));
        }*/
        System.out.println("Better Search:");
        File currentDir = new File(".");
        File parentDir = currentDir.getParentFile();
        File file = new File(parentDir,"evaluation-result.csv");
        Evaluation ev = new Evaluation(file);
        BetterSearch.IterativeDeepSearching(BoardGeneration.WP,BoardGeneration.WN,BoardGeneration.WB,BoardGeneration.WR,BoardGeneration.WQ,BoardGeneration.WK,BoardGeneration.BP,BoardGeneration.BN,BoardGeneration.BB,BoardGeneration.BR,BoardGeneration.BQ,BoardGeneration.BK,BoardGeneration.WhiteToMove,ev);
        end = System.currentTimeMillis();
        timeUntilGameEnd = end - start;
        movesMadePerSecond = (int) ((double) movesTakenUntilGameEnd / timeUntilGameEnd * 1000);
        possibleMovesPerSecond = (int) ((double) possibleMovesCalculated / timeUntilGameEnd * 1000);
        bestmove=BetterSearch.bestMoveString;
        /*FileWriter csvWriter = new FileWriter("benchmark-results.csv",true);
        csvWriter.append("time needed until game ended,Moves needed until game ended,moves made per second,possible moves computed," +
                "possible moves per second,possible checks analyzed,possible checkmates analyzed\n");
        csvWriter.append(timeUntilGameEnd+" ms,"+movesTakenUntilGameEnd+" moves,"+ movesMadePerSecond + "moves/s," +
                possibleMovesCalculated+" moves," + possibleMovesPerSecond + "moves/s," + possibleCheckStatesAnalyzed +
                " checks," + possibleCheckMatesAnalyzed + " checkmates\n");
        csvWriter.flush();
        csvWriter.close();*/
        /*System.out.println(("time needed until game ended,Moves needed until game ended,moves made per second,possible moves computed," +
                "possible moves per second,possible checks analyzed,possible checkmates analyzed\n"));
        System.out.println(timeUntilGameEnd + " ms," + movesTakenUntilGameEnd + " moves," + movesMadePerSecond + "moves/s," +
                possibleMovesCalculated + " moves," + possibleMovesPerSecond + "moves/s," + possibleCheckStatesAnalyzed +
                " checks," + possibleCheckMatesAnalyzed + " checkmates\n");*/



        System.out.println(timeUntilGameEnd + " ms," + possibleMovesCalculated + " moves," + maxDepth + " depth," + bestmove + " move") ;
        System.out.println("Lookups: "+ TranspositionTable.tableHits+ ", Collisions: "+TranspositionTable.numCollisions);

    }

    public static boolean doRandomMove(long WP, long WN, long WB, long WR, long WQ, long WK,
                                       long BP, long BN, long BB, long BR, long BQ, long BK, boolean isWhiteTurn) {
        BenchmarkTest.movesTakenUntilGameEnd++;
        System.out.println("");
        String moves = MoveGenerator.generateAllPossibleMoves(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK,  isWhiteTurn);
        String[] movesArray = moves.split("(?<=\\G.{4})");
        int index = (int) (Math.random() * movesArray.length);
        long WP2 = MoveGenerator.makeMove(WP, movesArray[index], 'P');
        long WN2 = MoveGenerator.makeMove(WN, movesArray[index], 'N');
        long WB2 = MoveGenerator.makeMove(WB, movesArray[index], 'B');
        long WR2 = MoveGenerator.makeMove(WR, movesArray[index], 'R');
        long WQ2 = MoveGenerator.makeMove(WQ, movesArray[index], 'Q');
        long WK2 = MoveGenerator.makeMove(WK, movesArray[index], 'K');
        long BP2 = MoveGenerator.makeMove(BP, movesArray[index], 'p');
        long BN2 = MoveGenerator.makeMove(BN, movesArray[index], 'n');
        long BB2 = MoveGenerator.makeMove(BB, movesArray[index], 'b');
        long BR2 = MoveGenerator.makeMove(BR, movesArray[index], 'r');
        long BQ2 = MoveGenerator.makeMove(BQ, movesArray[index], 'q');
        long BK2 = MoveGenerator.makeMove(BK, movesArray[index], 'k');

        if (Evaluation.isThisACheckState(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, isWhiteTurn)) {
            if (Evaluation.isThisACheckmate(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, isWhiteTurn)) {
                BoardGeneration.gameOver = true;
                System.out.println("This game is over.");
                if (isWhiteTurn) {
                    System.out.println("White won!");
                } else {
                    System.out.println("Black won!");
                }
            }
        }
        BoardGeneration.updateStaticVariables(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2); //static Werte
        /*System.out.println(exportFEN(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2,BoardGeneration.CWK,BoardGeneration.CWQ,
                BoardGeneration.CBK, BoardGeneration.CBQ, !isWhiteTurn));*/
        BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
        return true;
    }

}
