package UpsideDownChessBB;

import UpsideDownChessBB.PiecesAndMoves.MoveGenerator;
import UpsideDownChessBB.Search.BetterSearch;
import UpsideDownChessBB.Search.Evaluation;
import UpsideDownChessBB.Search.Zobrist;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used for Contest, without Interface
 * Converts our Logic and Notation to the requirements of the Contest
 */

public class PlayGame {
    public static void main(String[] args) throws IOException {
         /*File currentDir = new File(".");
         File parentDir = currentDir.getParentFile();
         File file = new File(parentDir,"evaluation-result.csv");
         Evaluation ev = new Evaluation(file);

          */
         // Using the new figure values coming from the evolutionary algorithm
        Evaluation ev = new Evaluation(237,420,402,187,640,10401);
        int counter = 0;

        while(true) {

            String fen;
            Zobrist.fillZobristArray();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            //System.out.println("Put fenstring here: (exit to terminate)");
            // no prints außer am ende den gewählten move sonst wertet der server das als ivaliden move

            fen =
                    //"RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 1 100";
                    reader.readLine();


            if (fen.equals("\n")) continue;
            if (fen.equals("exit")) break;
            BoardGeneration.importServerFEN(fen);

            if (counter <= 2) {
                String move = firstThreeMoves(counter);
                if(!move.equals("search")) System.out.println(move);
                else {
                    BetterSearch.IterativeDeepSearching(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                            BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                            BoardGeneration.WhiteToMove,ev);
                    System.out.println(BoardGeneration.transformMove(BetterSearch.bestMoveString));
                }
            } else {

                //BoardGeneration.initiateUpsideDownChess();
        /*if(args.length!=1){
            System.out.println("Usage: call Program FEN");
            System.exit(0);
        }
        else {
            BoardGeneration.importServerFEN(args[0]);
        }*/
                BetterSearch.IterativeDeepSearching(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                        BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                        BoardGeneration.WhiteToMove,ev);
                System.out.println(BoardGeneration.transformMove(BetterSearch.bestMoveString));
            }

            counter++;
        }
    }


// The function "firstThreeMoves" returns the optimal move from a library when invoked within the first three moves for the white or black player. This saves precious time and helps preventing a check mate after three moves. 
    public static String firstThreeMoves (int counter){

        boolean fortyFiveSafe = String.format("64%s", Long.toBinaryString(MoveGenerator.unsafeForBlack(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK)).replace(' ','0')).charAt(18)=='0';

        boolean fortyThreeSafe = String.format("64%s", Long.toBinaryString(MoveGenerator.unsafeForBlack(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK)).replace(' ','0')).charAt(20)=='0';

        if (BoardGeneration.WhiteToMove) {
            switch (counter){
                case 0: return "1,18,";

                case 1: if (!getPositions(BoardGeneration.BN).contains(45))return "18,28,";
                else return "18,33,";


                case 2: if(getPositions(BoardGeneration.WN).contains(33)&&safeSpot(43,'w'))return "33,43,";
                        else if (getPositions(BoardGeneration.WN).contains(33)&&!safeSpot(43,'w'))return "6,21,";
                        else if (getPositions(BoardGeneration.WN).contains(28)&&safeSpot(45,'w'))return "28,45";
                        else if(getPositions(BoardGeneration.WN).contains(28)&&safeSpot(43,'w'))return "28,43,";

                        else return "search";
            }

        }else{

            switch (counter) {
                case 0 : return "62,45,";


                case 1 : if (getPositions(BoardGeneration.WN).contains(38))return "45,35";
                        else if (getPositions(BoardGeneration.WN).contains(18)&&getPositions(BoardGeneration.BN).contains(57))return "57,42,";
                        else return "45,28,";

                case 2 : if(getPositions(BoardGeneration.WN).contains(43))return "28,43,";
                        else if (getPositions(BoardGeneration.WN).contains(45))return "28,45,";
                        else if (getPositions(BoardGeneration.WN).contains(34))return "28,34,";
                        else if (getPositions(BoardGeneration.WN).contains(38))return "28,38,";
                        else if (getPositions(BoardGeneration.WN).contains(18))return "28,18,";
                        else if (getPositions(BoardGeneration.WN).contains(22))return "28,22,";
                        else if(getPositions(BoardGeneration.WN).contains(28)&&getPositions(BoardGeneration.BN).contains(45))return "45,28,";
                        else return "search";
            }
        }
        return "search";


    }

    public static ArrayList getPositions(long BitBoard){
        ArrayList <Integer> positions = new ArrayList<>();
        String BitBoardAsBitString = String.format("%64s", Long.toBinaryString(BitBoard)).replace(' ', '0');

        for (int i =  0; i < 64; i++){
            if (BitBoardAsBitString.charAt(63-i)=='1')positions.add(i);
        }
        return positions;
    }


    public static boolean safeSpot(int index, char color){
        String unsafeSpots;
        if(color=='w') {
            unsafeSpots = String.format("64%s", Long.toBinaryString(MoveGenerator.unsafeForWhite(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                    BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK)).replace(' ', '0'));


                if (unsafeSpots.charAt(63-index)=='1')return false;
                else return true;

        }
        else unsafeSpots = String.format("64%s", Long.toBinaryString(MoveGenerator.unsafeForBlack(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK)).replace(' ','0'));

        if (unsafeSpots.charAt(63-index)=='1')return false;
        else return true;
    }



}

