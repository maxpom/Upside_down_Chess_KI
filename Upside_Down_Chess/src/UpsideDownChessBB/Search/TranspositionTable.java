package UpsideDownChessBB.Search;

// References: https://www.chessprogramming.org/Transposition_Table
//             https://www.chessprogramming.org/Node_Types#PV
//             https://www.youtube.com/watch?v=U4ogK0MIzqk
//             https://web.archive.org/web/20071031100051/http://www.brucemo.com/compchess/programming/hashing.htm
//             http://mediocrechess.blogspot.com/2007/01/guide-transposition-tables.html


public class TranspositionTable {

    // Static variables
    //This evaluation was found during a whole search of the given position, therefore we can guarantee the found move
    //to be the best move possible according to our evaluation
    public static final int Exact = 0;

    // This evaluation was found during a search that was at some point cut off due to the current beta being lower
    // than what black could at least get in this position -> beta cutoff
    public static final int LowerBound = 1;

    // This evaluation was found during a search that was at some point cut off due to the current alpha being higher
    // than what white could at most get in this position -> alpha cuttoff
    public static final int UpperBound = 2;

    public static final int LookupFailed = Integer.MIN_VALUE;

    // Statistics
    public static int numCollisions = 0;
    public static int tableHits = 0;


    // Instance variables
    public HashEntry[] table;

    public TranspositionTable(int sizeOfTable) {
        this.table = new HashEntry[sizeOfTable];
    }

    /**
     * Checks if the evaluation for a given board position is stored in the transposition table, and returns it
     * if that is the case.
     * @param depth - the depth that will be searched ahead from this position
     * @return The stored evaluation value or Integer.MIN_VALUE if no value was stored.
     */
    public HashEntry LookupTableEvaluation(long WP, long WN, long WB, long WR, long WQ, long WK,
                                           long BP, long BN, long BB, long BR, long BQ, long BK, int depth, boolean WhiteToMove) {
        long zKey = Zobrist.generateZobristKey(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,WhiteToMove);
        HashEntry entry = table[(int)(zKey & 0x7FFFFFFF) % table.length];
        //System.out.println(entry);
        // Check if the Key is present in the lookup table, otherwise a search has to be performed
        if(entry == null) return null;
        if(entry.depth <  depth) return null;
        if(entry.key != zKey) {
            numCollisions++;
            return null;
        } else {
            tableHits++;
            return entry;
        }
    }

    /**
     * Stores the value for a given board position in the transposition table.
     *
     * @param depth - depth of the search at which the position was evaluated
     * @param eval - value of the evaluation at this position
     * @param evalType - refers to the nodeType: Exact, UpperBound or LowerBound
     * @param move - best move found in this position
     */
    public void StoreEvaluationInTable(long WP, long WN, long WB, long WR, long WQ, long WK,
                                              long BP, long BN, long BB, long BR, long BQ, long BK, boolean WhiteToMove,
                                              int depth, int eval, int evalType, String move) {
        long zKey = Zobrist.generateZobristKey(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, WhiteToMove);
        HashEntry oldEntry = this.table[(int)(zKey & 0x7FFFFFFF) % table.length];
        if(oldEntry == null) {
            this.table[(int)(zKey & 0x7FFFFFFF) % table.length] = new HashEntry(zKey,eval,move,depth,evalType);
        }
        // If the found entry has been searched to a lesser depth than it was searched to in the current search,
        // override the old entry, otherwise leave it as it is
        else if(oldEntry.depth <= depth) {
            this.table[(int) (zKey & 0x7FFFFFFF) % table.length] = new HashEntry(zKey, eval, move, depth, evalType);
        } else {
            return;
        }
    }
}
