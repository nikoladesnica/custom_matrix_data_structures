package assignmenttwo;

import java.util.Objects;

/**
 * Represents a pair of integer values corresponding to a row and column.
 * This class provides utility functions for equality checks, hash code computation,
 * and string representation for the pair.
 *
 * @author Nikola Desnica (UNI: ndd2131)
 * @version 1.0
 */
public class Pair {
    public final int row;
    public final int column;

    /**
     * Constructs a new pair with the specified row and column values.
     *
     * @param row the row value to be associated with this pair
     * @param column the column value to be associated with this pair
     */
    public Pair(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Determines whether the provided object is equal to this pair.
     * Two pairs are considered equal if their row and column values are identical.
     *
     * @param o the object to be compared for equality with this pair
     * @return true if the specified object is equal to this pair, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return row == pair.row && column == pair.column;
    }

    /**
     * Returns a hash code value for the pair based on its row and column values.
     *
     * @return a hash code value for this pair
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Returns a string representation of the pair in the format "(row, column)".
     *
     * @return a string representation of the pair
     */
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}