package UpsideDownChessBB.Search;

/**
 * This class is used to store a board position with all relevant information in our Transposition Table.
 */
public class HashEntry {
    public final long key;
    public final int value; // Could possibly be declared as short? depends on our evaluation function
    public final String move;
    public final byte depth;
    public final byte nodeType;

    public HashEntry(long key, int value, String move, int depth, int nodeType) {
        this.key = key; // Zobrist-Key representing the board
        this.value = value; // evaluation value found in this position
        this.move = move; // Best move found in this position
        this.depth = (byte) depth;
        // depth in this case referring to how many depths were searched after this position, e.g.
        // a depth of 5 would mean that to evaluate this position, the search went on for 5 depths
        // a depth of 0 would mean that this was a leaf node and thus
        // the static evaluation of the position (Evaluation.Evaluate())
        this.nodeType = (byte) nodeType;
    }

    @Override
    public String toString(){
        String ret = "";
        ret += "Key: "+ this.key + "\n";
        ret += "Value: "+ this.value + "\n";
        ret += "Move: "+ this.move + "\n";
        ret += "Depth: "+ this.depth + "\n";
        ret += "nodeType: "+ this.nodeType + "\n";
        return ret;

    }
}
