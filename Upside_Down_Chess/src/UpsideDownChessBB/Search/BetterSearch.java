package UpsideDownChessBB.Search;

import UpsideDownChessBB.BoardGeneration;
import UpsideDownChessBB.PiecesAndMoves.MoveGenerator;
import UpsideDownChessBB.Tests.BenchmarkTest;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Integer;

import static UpsideDownChessBB.BoardGeneration.updateStaticVariables;

public class BetterSearch {

    private static boolean searchCutOff = false;
    //dependent on King Value
    private static int winCutOff = new Evaluation().kingValue;
    //best found move after Iterative Deep Searching
    public static String bestMoveString = "";
    //Transposition table for this search
    private static TranspositionTable tt = new TranspositionTable(1_000_000);
    public static boolean benchmark = false;
    public static boolean showBoards = false;
    public static boolean gameAgainstKI=false;
    public static boolean hasWon=false;

    /**
     * Computes BestMove and stores it in BestMoveString
     *
     * @return Value if computet Move would be executed,in compared to the old Board
     */
    public static int BetterSearch(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ
            , long BK, int maxDepth, int alpha, int beta, long startTime, boolean isWhite, Evaluation ev) {
        //String bestMoveThisIteration = "";
        int bestMove = 0;
        int evalType;
        HashEntry entry = tt.LookupTableEvaluation(WP,WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, maxDepth, isWhite);
       // if(isWhite) {
       //     evalType = TranspositionTable.LowerBound;
       // } else {
            evalType = TranspositionTable.UpperBound;
       // }
       if(entry != null) {
            if(entry.nodeType == TranspositionTable.LowerBound) {
                if(entry.value >= beta) {
                    // The entry contained a beta value that was higher than the current beta, aka:
                    // A move for black that was higher in evaluation than the lowest black can get, therefore,
                    // we know that this position does not contain any moves worth searchable, thus we can return
                    // beta and initiate beta-cutoff
                    return entry.value;
                }
            } else if (entry.nodeType == TranspositionTable.UpperBound) {
                if(entry.value <= alpha) {
                    // The entry contained an alpha value that was lower than the current alpha, aka:
                    // A move for white that was lower than the highest score white can currently get, therefore
                    // we know that this position is not worth to be searched and thus we can return alpha and
                    // initiate alpha cutoff
                    return entry.value;
                }
            }else if(entry.nodeType == TranspositionTable.Exact) {
                // The entry contained an exact score from the complete search of one position so we can
                // just return that score
                return entry.value;
            }
        }
        long currentTime = System.currentTimeMillis();
        //Search Cutoff
        if (currentTime - startTime >= BoardGeneration.TimeLimit) {
            //System.out.println("Timelimit erreicht");
            searchCutOff = true;
        }
        //Transposition Tables implentieren
        if (maxDepth == 0 || searchCutOff) {
            int score = ev.evaluate(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
            return score;
        }

        boolean PVgefunden = false;
        int best = Integer.MIN_VALUE;

        //Generate Moves
        String moveString = MoveGenerator.generateAllPossibleMoves(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK,
                isWhite);
        String[] moves = moveString.split("(?<=\\G.{4})");

        if(moves.length==0){
            int score = ev.evaluate(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);
            return score;
        }
        moves=sortMoves(moves);

        int index = 0;
        for (String move : moves) {
            //do Move
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



            if (Evaluation.isThisACheckState(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, isWhite)) {
                if (Evaluation.isThisACheckmate(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, isWhite)) {
                    bestMove = index;

                    //best= Evaluation.kingValue;
                    break;
                    //System.out.println("aa");
                    // handle game-end logic
                } else {
                    //this is a check but not a checkmate -> does not need to be evaluated as it is an illegal move
                    continue;
                }
            }

            int value;
            if (PVgefunden) {
                value = -BetterSearch(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, (maxDepth - 1), (-alpha - 1), -alpha, startTime, !isWhite, ev);
                if (value > alpha && value < beta) {
                    value = -BetterSearch(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, (maxDepth - 1), -beta, -value, startTime, !isWhite, ev);
                   // evalType = TranspositionTable.Exact;
                }
            } else {
                value = -BetterSearch(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, (maxDepth - 1), -beta, -alpha, startTime, !isWhite, ev);
            }


            if (value > best) {
                //bestMoveThisIteration = move;
                bestMove = index;

                // Move was *too* good, so opponent won't allow this position to be reached
                // (by choosing a different move earlier on). Skip remaining moves.
                if (value >= beta) {
                    tt.StoreEvaluationInTable(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK,isWhite,maxDepth,beta,TranspositionTable.LowerBound,move);
                    return value;
                }

                best = value;

                if (value > alpha) {
                    alpha = value;
                    PVgefunden = true;
                    //PV-node found
                    evalType = TranspositionTable.Exact;
                }
            }
            index++;
        }

        bestMoveString = moves[bestMove];
        tt.StoreEvaluationInTable(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK,isWhite,maxDepth,alpha,evalType,moves[bestMove]);
        return best;
    }

    /**
     * Executes BetterSearch and increases the Depth each Loop, until the TimeLimit is met
     * @returns Same as BetterSearch
     */
    public static int IterativeDeepSearching(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ
            , long BK, boolean isWhite, Evaluation ev) {
        long starttime = System.currentTimeMillis();
        int depth = 1;
        int bestscore = 0;
        searchCutOff=false;

        while (true) {
            long current_time = System.currentTimeMillis();

            if (current_time - starttime >= BoardGeneration.TimeLimit) {
                break;
            }
            if(benchmark) System.out.println("Current Depth:" + depth);


            int score;

            if (isWhite) {
                score = BetterSearch(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, starttime, isWhite, ev);
            } else {
                score = -BetterSearch(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, starttime, isWhite, ev);
            }

            if (score >= winCutOff || score <= -winCutOff) {
                return score;
            }
            if (!searchCutOff) {
                bestscore = score;
            }
            BenchmarkTest.maxDepth = depth;
            depth++;
        }
        //System.out.println("depth reached: " + depth);
        return bestscore;
    }

    /**
     * Computes the best Move for current Board and executes it
     */
    public static void doBetterSearchMove(long WP, long WN, long WB, long WR, long WQ, long WK,
                                             long BP, long BN, long BB, long BR, long BQ, long BK, boolean isWhite, Evaluation ev) {




        if (!gameAgainstKI){
            BetterSearch.bestMoveString="";
            int score = IterativeDeepSearching(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, isWhite, ev);
        }
        if(BetterSearch.bestMoveString==""){
            BoardGeneration.gameOver=true;
            hasWon=false;
        }
        else {
            long WP3 = MoveGenerator.makeMove(WP, bestMoveString, 'P');
            long WN3 = MoveGenerator.makeMove(WN, bestMoveString, 'N');
            long WB3 = MoveGenerator.makeMove(WB, bestMoveString, 'B');
            long WR3 = MoveGenerator.makeMove(WR, bestMoveString, 'R');
            long WQ3 = MoveGenerator.makeMove(WQ, bestMoveString, 'Q');
            long WK3 = MoveGenerator.makeMove(WK, bestMoveString, 'K');
            long BP3 = MoveGenerator.makeMove(BP, bestMoveString, 'p');
            long BN3 = MoveGenerator.makeMove(BN, bestMoveString, 'n');
            long BB3 = MoveGenerator.makeMove(BB, bestMoveString, 'b');
            long BR3 = MoveGenerator.makeMove(BR, bestMoveString, 'r');
            long BQ3 = MoveGenerator.makeMove(BQ, bestMoveString, 'q');
            long BK3 = MoveGenerator.makeMove(BK, bestMoveString, 'k');

            if (Evaluation.isThisACheckmate(WP3, WN3, WB3, WR3, WQ3, WK3, BP3, BN3, BB3, BR3, BQ3, BK3, isWhite)) {
                BoardGeneration.gameOver = true;
                hasWon=true;
            }

            updateStaticVariables(WP3, WN3, WB3, WR3, WQ3, WK3, BP3, BN3, BB3, BR3, BQ3, BK3); //static Werte
        }
    }



    /**
     * Used For Sorting the Moves
     */
    private static class Move{
        int score;
        String move;
        private Move(int score,String move){
            this.move=move;
            this.score=score;
        }
        public int compareTo(Move move){
            if (this.score > move.score)
                    return 1;
            else if(this.score < move.score)
                return -1;
            else return 0;
        }
    }

    /** Sorts all given Moves after their Evaluation Score
     *
     * @param moves
     * @return newMoves
     */
    private static String [] sortMoves(String[] moves){
        int index=0;
        ArrayList<Move> Movelist= new ArrayList<Move>();
        for (String move : moves){
            long WP3 = MoveGenerator.makeMove(BoardGeneration.WP, move, 'P');
            long WN3 = MoveGenerator.makeMove(BoardGeneration.WN, move, 'N');
            long WB3 = MoveGenerator.makeMove(BoardGeneration.WB, move, 'B');
            long WR3 = MoveGenerator.makeMove(BoardGeneration.WR, move, 'R');
            long WQ3 = MoveGenerator.makeMove(BoardGeneration.WQ, move, 'Q');
            long WK3 = MoveGenerator.makeMove(BoardGeneration.WK, move, 'K');
            long BP3 = MoveGenerator.makeMove(BoardGeneration.BP, move, 'p');
            long BN3 = MoveGenerator.makeMove(BoardGeneration.BN, move, 'n');
            long BB3 = MoveGenerator.makeMove(BoardGeneration.BB, move, 'b');
            long BR3 = MoveGenerator.makeMove(BoardGeneration.BR, move, 'r');
            long BQ3 = MoveGenerator.makeMove(BoardGeneration.BQ, move, 'q');
            long BK3 = MoveGenerator.makeMove(BoardGeneration.BK, move, 'k');

            Evaluation ev= new Evaluation();
            int score = ev.evaluate(WP3,WN3,WB3,WR3,WQ3,WK3,BP3,BN3,BB3,BR3,BQ3,BK3);
            Move move1 = new Move(score,move);
            Movelist.add(move1);
            index++;
        }
        Collections.sort(Movelist, new Comparator<Move>() {
            @Override
            public int compare(Move move1, Move move2)
            {
                return  move1.compareTo(move2);
            }
        });
        String[] newMoves = new String[moves.length];
        for (int i =0;i<Movelist.size();i++){
            newMoves[i]=Movelist.get(i).move;
        }
        return newMoves;
    }

}
