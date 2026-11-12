package UpsideDownChessBB;

import UpsideDownChessBB.BoardGeneration;
import UpsideDownChessBB.PiecesAndMoves.MoveGenerator;
import UpsideDownChessBB.PlayGame;
import UpsideDownChessBB.Search.BetterSearch;
import UpsideDownChessBB.Search.Evaluation;
import UpsideDownChessBB.Search.Zobrist;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * This class simulates a game between 1 Player and 1 AI.
 */
public class GameAgainstKI {
    public static void main(String[] args) throws IOException {
        File currentDir = new File(".");
        File parentDir = currentDir.getParentFile();
        File file = new File(parentDir,"evaluation-result.csv");
        Evaluation ev = new Evaluation(file);
        System.out.println(ev);
        //Start as White
        boolean alternating = true;
        BetterSearch.showBoards = true;
        BoardGeneration.initiateUpsideDownChess();

        Zobrist.fillZobristArray();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int counter=0;
        while (!BoardGeneration.gameOver) {
            Boolean isWhite= BoardGeneration.WhiteToMove;
            if (alternating) {
                String[] moves = MoveGenerator.generateAllPossibleMoves(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB,
                        BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK, BoardGeneration.BP, BoardGeneration.BN,
                        BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        isWhite).split("(?<=\\G.{4})");
                System.out.println(Arrays.toString(moves));
                System.out.println("Enter your move: ");
                String move = reader.readLine();
                if(move.equals("exit")) {
                    System.out.println("The player quit.");
                    break;
                }



                if(!Arrays.asList(moves).contains(move)) {
                    System.out.println("player made an invalid move, game will be terminated.");
                    break;
                }
                long WP2 = MoveGenerator.makeMove(BoardGeneration.WP, move, 'P');
                long WN2 = MoveGenerator.makeMove(BoardGeneration.WN, move, 'N');
                long WB2 = MoveGenerator.makeMove(BoardGeneration.WB, move, 'B');
                long WR2 = MoveGenerator.makeMove(BoardGeneration.WR, move, 'R');
                long WQ2 = MoveGenerator.makeMove(BoardGeneration.WQ, move, 'Q');
                long WK2 = MoveGenerator.makeMove(BoardGeneration.WK, move, 'K');
                long BP2 = MoveGenerator.makeMove(BoardGeneration.BP, move, 'p');
                long BN2 = MoveGenerator.makeMove(BoardGeneration.BN, move, 'n');
                long BB2 = MoveGenerator.makeMove(BoardGeneration.BB, move, 'b');
                long BR2 = MoveGenerator.makeMove(BoardGeneration.BR, move, 'r');
                long BQ2 = MoveGenerator.makeMove(BoardGeneration.BQ, move, 'q');
                long BK2 = MoveGenerator.makeMove(BoardGeneration.BK, move, 'k');
                BoardGeneration.updateStaticVariables(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2);

                if (Evaluation.isThisACheckState(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, isWhite)) {
                    if (Evaluation.isThisACheckmate(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2, isWhite)) {
                        BoardGeneration.gameOver = true;
                        System.out.println("This game is over.");
                        System.out.println("White won!");
                    }
                    else {
                        System.out.println("Player made an invalid move, game will be terminated.");
                        break;
                    }
                    BoardGeneration.updateStaticVariables(WP2, WN2, WB2, WR2, WQ2, WK2, BP2, BN2, BB2, BR2, BQ2, BK2);
                }
            } else {
                if (counter <= 2) {
                    String move = PlayGame.firstThreeMoves(counter);
                    if(!move.equals("search")) {
                        BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                        BetterSearch.gameAgainstKI=true;
                        BetterSearch.bestMoveString=BoardGeneration.reverseServerMove(move);
                        System.out.println(BoardGeneration.reverseServerMove(move));
                        BetterSearch.doBetterSearchMove(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                                BoardGeneration.WhiteToMove,ev);
                        BetterSearch.gameAgainstKI=false;
                        BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                    }

                    else {
                        BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                        BetterSearch.doBetterSearchMove(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                                BoardGeneration.WhiteToMove,ev);
                        System.out.println(BetterSearch.bestMoveString);
                        BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                    }
                    counter++;
                }
                else {
                    BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                        BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                    BetterSearch.doBetterSearchMove(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                        BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK, BoardGeneration.WhiteToMove,ev);
                    System.out.println(BetterSearch.bestMoveString);
                    BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                        BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                }
            }
            BoardGeneration.WhiteToMove=!BoardGeneration.WhiteToMove;
            alternating = !alternating;
        }
        reader.close();

    }
}
