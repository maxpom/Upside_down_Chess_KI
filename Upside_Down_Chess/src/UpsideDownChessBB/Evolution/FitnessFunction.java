package UpsideDownChessBB.Evolution;

import UpsideDownChessBB.BoardGeneration;
import UpsideDownChessBB.PlayGame;
import UpsideDownChessBB.Search.BetterSearch;
import UpsideDownChessBB.Search.Evaluation;
import UpsideDownChessBB.Search.Zobrist;

import java.io.FileWriter;
import java.util.*;

public class FitnessFunction {
    Evaluation ev = new Evaluation();

    static boolean first=true;

    static Evaluation ev1 ;
    static Evaluation ev2 ;
    static Evaluation ev3 ;
    static Evaluation ev4 ;
    static Evaluation ev5 ;
    static Evaluation ev6 ;
    static Evaluation ev7 ;
    static Evaluation ev8 ;
    static Evaluation ev9 ;
    static Evaluation ev10;

    HashMap <Evaluation,Integer> eveScore  =new HashMap<>();
    HashMap <Evaluation,Integer> winScore  =new HashMap<>();


    /**
     * Starts our Machine Learning Process to optimze the Values of our Evaluation function
     * @param rounds to play
     */
    public void startFitness(int rounds){
        for (int o =0;o<rounds;o++){
            Evaluation[] Gen = generateGeneration(first);
            first=false;
            Evaluation[] Played = Gen;
            ArrayList<Evaluation> list = new ArrayList<>();
            for (Evaluation i : Played) {
                list.add(i);
            }


            while (list.size()>0) {                            //2 random Evaluations play against each other
                    if (list.size() % 2 == 0) {
                        Random random = new Random();
                        int a = random.nextInt(list.size() - 1);
                        Evaluation one = list.get(a);
                        list.remove(a);

                        Random random1 = new Random();
                        int b;
                        if(list.size()==1){
                            b=0;
                        }
                        else {
                             b = random1.nextInt(list.size() - 1);
                        }
                        Evaluation two = list.get(b);
                        list.remove(b);

                        System.out.println(one);
                        System.out.println(two+ "\n");
                        computeFitness(one, two);
                    }
                    else {
                        System.out.println("Something went Wrong");
                        return;
                    }
            }
            Gen=computeBest(Gen);
            Evaluation best= Gen[0];
            try {
                FileWriter csvWriter = new FileWriter("evaluation-result.csv",true);
                System.out.println(best.pawnValue + "," + best.knightValue + "," + best.bishopValue +","+ best.rookValue +","+ best.queenValue +","+ best.kingValue+"\n");
                csvWriter.append(best.pawnValue + "," + best.knightValue + "," + best.bishopValue +","+ best.rookValue +","+ best.queenValue +","+ best.kingValue+"\n");
                csvWriter.flush();
                csvWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }





    /**
     * Plays a Game between an Evalaluation evone and an Evaluation evtwo, return the winning Evaluation
     */
    private Evaluation computeFitness(Evaluation evone,Evaluation evtwo){
        int counter=0;
        BoardGeneration.importServerFEN("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr w 1 100");
        Zobrist.fillZobristArray();
        BoardGeneration.gameOver = false;
        while(!BoardGeneration.gameOver){
            //make move
            if (counter <= 2) {
                String move = PlayGame.firstThreeMoves(counter);
                if(!move.equals("search")) {
                    System.out.println(evone + "s turn.");
                    BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                            BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                    BetterSearch.gameAgainstKI=true;
                    BetterSearch.bestMoveString=BoardGeneration.reverseServerMove(move);
                    System.out.println(BoardGeneration.reverseServerMove(move));
                    BetterSearch.doBetterSearchMove(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                            BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                            BoardGeneration.WhiteToMove,evone);
                    BetterSearch.gameAgainstKI=false;
                    BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                            BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);

                }

                else {
                    System.out.println(evone + "s turn.");
                    BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                            BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                    BetterSearch.doBetterSearchMove(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                            BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK,
                            BoardGeneration.WhiteToMove,evone);
                    System.out.println(BetterSearch.bestMoveString);
                    BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                            BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                }
            }
            else {
                System.out.println(evone + "s turn.");
                BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                        BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
                BetterSearch.doBetterSearchMove(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                        BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK, BoardGeneration.WhiteToMove,evone);
                System.out.println(BetterSearch.bestMoveString);
                BoardGeneration.drawArray(BoardGeneration.WP, BoardGeneration.WN, BoardGeneration.WB, BoardGeneration.WR, BoardGeneration.WQ, BoardGeneration.WK,
                        BoardGeneration.BP, BoardGeneration.BN, BoardGeneration.BB, BoardGeneration.BR, BoardGeneration.BQ, BoardGeneration.BK);
            }
            counter++;
            BoardGeneration.WhiteToMove=!BoardGeneration.WhiteToMove;
            Evaluation a = evone;
            evone=evtwo;                                                        //swap Players
            evtwo=a;
            BoardGeneration.currentTurn++;
        }
        if (BetterSearch.hasWon){
            System.out.println(evtwo + " won."+"\n");
            eveScore.put(evtwo,counter);
            winScore.put(evtwo,winScore.get(evtwo)+1);
            return evtwo; //made the last move
        }
        else{ //Won through TimeOut
            System.out.println(evone + " won."+"\n");
            eveScore.put(evone,counter);
            winScore.put(evone,winScore.get(evtwo)+1);
            return evone;
        }

    }

    /**
     * Keeps the best 4 Evaluations, creates 2 new Random Evaluations and merges 4 Evaluation together
     */
    private Evaluation[] generateGeneration(Boolean firstRound){
        Evaluation Gen[]=new Evaluation[10];

        if(firstRound){
             ev1=mergeEvaluations(ev,ev,3);
             ev2=mergeEvaluations(ev,ev,3);
             ev3=mergeEvaluations(ev,ev,3);
             ev4=mergeEvaluations(ev,ev,3);
             ev5=mergeEvaluations(ev,ev,3);
             ev6=mergeEvaluations(ev,ev,3);
             ev7=mergeEvaluations(ev,ev,3);
             ev8=mergeEvaluations(ev,ev,3);
             ev9=mergeEvaluations(ev,ev,3);
             ev10=mergeEvaluations(ev,ev,3);

            Gen[0]=ev1;
            Gen[1]=ev2;
            Gen[2]=ev3;
            Gen[3]=ev4;
            Gen[4]=ev5;
            Gen[5]=ev6;
            Gen[6]=ev7;
            Gen[7]=ev8;
            Gen[8]=ev9;
            Gen[9]=ev10;
        }
        else {
            Gen[0]=ev1;
            Gen[1]=ev2;
            Gen[2]=ev3;
            Gen[3]=ev4;
            Gen[4]=ev5;
            Gen[5]=ev6;
            Gen[6]=ev7;
            Gen[7]=ev8;
            Gen[8]=ev9;
            Gen[9]=ev10;

            Evaluation[] evs = computeBest(Gen);
            System.out.println(Arrays.toString(evs));
            ev1=evs[0];         //best 4 of last generation
            ev2=evs[1];
            ev3=evs[2];
            ev4=evs[3];

            ev5=mergeEvaluations(ev5,ev5,2);    // Random generated
            ev6=mergeEvaluations(ev6,ev6,2);

            ev7=mergeEvaluations(ev1,ev4,0.5);    //Merged from the best ones
            ev8=mergeEvaluations(ev2,ev4,0.5);
            ev9=mergeEvaluations(ev1,ev3,0.5);
            ev10=mergeEvaluations(ev2,ev3,0.5);

            Gen[0]=ev1;
            Gen[1]=ev2;
            Gen[2]=ev3;
            Gen[3]=ev4;
            Gen[4]=ev5;
            Gen[5]=ev6;
            Gen[6]=ev7;
            Gen[7]=ev8;
            Gen[8]=ev9;
            Gen[9]=ev10;

        }
        eveScore.clear();
        winScore.clear();


        eveScore.put(ev1,0);
        eveScore.put(ev2,0);
        eveScore.put(ev3,0);
        eveScore.put(ev4,0);
        eveScore.put(ev5,0);
        eveScore.put(ev6,0);
        eveScore.put(ev7,0);
        eveScore.put(ev8,0);
        eveScore.put(ev9,0);
        eveScore.put(ev10,0);

        winScore.put(ev1,0);
        winScore.put(ev2,0);
        winScore.put(ev3,0);
        winScore.put(ev4,0);
        winScore.put(ev5,0);
        winScore.put(ev6,0);
        winScore.put(ev7,0);
        winScore.put(ev8,0);
        winScore.put(ev9,0);
        winScore.put(ev10,0);


        return Gen;
    }

    /**
     *
     * @return Evaluation [] sorted after Rounds needed to Win Descending
     */
    private Evaluation[] computeBest(Evaluation[] Gen) {
        Evaluation[] best = new Evaluation[5];
        for (int i = 0; i < Gen.length; i++) {
            if(winScore.containsKey(Gen[i])) {
                if (winScore.get(Gen[i]) == 0) {
                    winScore.remove(Gen[i]);
                    eveScore.remove(Gen[i]);
                }
            }
        }
        LinkedHashMap<Evaluation, Integer> eveScore1 = hashMapSortedByValue(eveScore);
        int counter = 0;
        for (Map.Entry<Evaluation, Integer> entry : eveScore1.entrySet()) {
            Evaluation key = entry.getKey();
            best[counter] = key;
            counter++;
        }
        return best;
    }

    /**
     * Merges two evaluation functions (aka creates an offspring) and optionally mutates the resulting values;
     * @param ev1
     * @param ev2
     * @param mutationFactor - if this is zero, the two evaluations are merged without being mutated
     * @return
     */
    private static Evaluation mergeEvaluations(Evaluation ev1, Evaluation ev2, double mutationFactor){
        int addedValue= distortion(mutationFactor);
        int mergedPawn = (ev1.pawnValue + ev2.pawnValue)/2 + (addedValue*sign());

        addedValue= distortion(mutationFactor);
        int mergedKnight = (ev1.knightValue + ev2.knightValue)/2 + (addedValue*sign());

        addedValue= distortion(mutationFactor);
        int mergedBishop = (ev1.bishopValue + ev2.bishopValue)/2 + (addedValue*sign());

        addedValue= distortion(mutationFactor);
        int mergedRook = (ev1.rookValue + ev2.rookValue)/2 + (addedValue*sign());

        addedValue= distortion(mutationFactor);
        int mergedQueen = (ev1.queenValue + ev2.queenValue)/2 + (addedValue*sign());

        addedValue= distortion(mutationFactor);
        int mergedKing = (ev1.kingValue + ev2.kingValue)/2 + (addedValue*sign());

        return new Evaluation(mergedPawn, mergedKnight, mergedBishop, mergedRook, mergedQueen, mergedKing);


    }

    /**
     * Computes Distortion, used for mergeEvaluations
     */
    private static int distortion(double mutationFactor){
        int max = 100;
        int min = -100;
        Random random = new Random();
        int distortion = random.nextInt((max - min) + 1) + min;
        int addedValue = Integer.valueOf((int) Math.round(distortion*mutationFactor));
        return addedValue;
    }

    /**
     * Used for mergeEvaluations
     */
    private static int sign() {
       double r = Math.random();
       if(r < 0.5) {
           return 1;
       } else {
           return -1;
       }
    }

    /** Used to Sort our Hashmaps eveScore and winScore
     *source: https://www.javatpoint.com/how-to-sort-hashmap-by-value
     */
    public static LinkedHashMap<Evaluation, Integer> hashMapSortedByValue(HashMap<Evaluation, Integer> hashMapToSort) {

        // convert HashMap into List
        List<Map.Entry<Evaluation, Integer>> list = new LinkedList<Map.Entry<Evaluation, Integer>>(hashMapToSort.entrySet());

        //sorting the list elements
        Collections.sort(list, new Comparator<Map.Entry<Evaluation, Integer>>() {
            @Override
            public int compare(Map.Entry<Evaluation, Integer> o1, Map.Entry<Evaluation, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        // Prints the sorted HashMap
        LinkedHashMap<Evaluation, Integer> sortedMap = new LinkedHashMap<Evaluation, Integer>();
        for (Map.Entry<Evaluation, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;

    }




    public static void main(String[] args) {
        FitnessFunction function = new FitnessFunction();
        function.startFitness(15);
    }
}


