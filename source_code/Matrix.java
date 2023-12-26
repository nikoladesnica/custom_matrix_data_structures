package assignmenttwo;

import java.util.*;

/**
 * Maintains the entries of the Matrix in a HashMap for operation runtimes of O(1).
 * Compare to previous ArrayList implementation with operation runtimes of O(n).
 * The Entries class became obsolete, we have cleaner and less code now.
 * Has getter and setter methods for entries.
 * Performs matrix operations (i.e. addition and multiplication).
 * Has methods that validate and handle potential errors.
 *
 * @author Nikola Desnica (UNI: ndd2131)
 * @version 5.0
 */
public class Matrix implements MatrixInterface {

    /**
     * Efficiently holds the non-zero entries of this Matrix Instance.
     */
    protected HashMap<Pair, Integer> entries;

    /**
     * The number of rows in this Matrix Instance.
     */
    private final int rows;

    /**
     * The number of columns in this Matrix Instance.
     */
    private final int columns;

    /**
     * Constructs a Matrix Instance with the given dimensions.
     * @param rows The indicated number of rows.
     * @param columns The indicated number of columns.
     */
    public Matrix(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Can not construct a matrix " +
                    "with values of 0 for rows or columns.");
        }
        this.rows = rows; this.columns = columns;
        this.entries = new HashMap<>();
    }

    /**
     * @return rows.
     */
    @Override
    public int getRows() {
        return this.rows;
    }

    /**
     * @return columns.
     */
    @Override
    public int getColumns() {
        return this.columns;
    }

    /**
     * Fetches the value at the specified index.
     *
     * @param row the row position.
     * @param column the column position.
     * @return the value.
     */
    @Override
    public int get(int row, int column) {
        validateIndices(row, column);
        return this.entries.getOrDefault(new Pair(row, column), 0);
    }

    /**
     * This method:
     * <ul>
     *     <li>Sets the value of an entry.</li>
     *     <li>Erases an entry if the value passed is 0.</li>
     *     <li>Otherwise, a new entry is added (unless value == 0).</li>
     * </ul>
     *
     * @param row The row index.
     * @param column The column index.
     * @param value The desired value (value of 0 removes entry from sparse matrix).
     */
    @Override
    public void set(int row, int column, int value) {
        validateIndices(row, column);
        Pair pairKey = new Pair(row, column);
        if (value == 0) {
            this.entries.remove(pairKey);
        } else {
            this.entries.put(pairKey, value);
        }
    }

    /**
     * Validates the indices passed (for get or set).
     *
     * @param row The row index.
     * @param column The column index.
     */
    public void validateIndices(int row, int column) {
        if (row < 0 || row > this.rows - 1) {
            throw new IllegalArgumentException("The indicated row index is out of bounds for this matrix.");
        }
        if (column < 0 || column > this.columns - 1) {
            throw new IllegalArgumentException("The indicated column index is out of bounds for this matrix.");
        }
    }

    /**
     * Transforms a Matrix instance into a String representation of the Matrix.
     *
     * @return The String representation of this Matrix.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Determine the maximum width required for any entry
        int maxWidth = getMaxWidth();

        String formatString = "%" + maxWidth + "d";

        for (int i = 0; i < this.rows; i++) {
            sb.append("[");
            for (int j = 0; j < this.columns; j++) {
                if (j != 0) {
                    sb.append(" ");
                    sb.append(String.format(formatString, get(i, j)));
                } else {
                    sb.append(get(i, j)); // for the first column
                }
            }
            sb.append("]\n");
        }
        return sb.toString();
    }

    /**
     * Helper method that finds the maximum width of an element for pretty printing toString().
     *
     * @return the element with the maximum width.
     */
    private int getMaxWidth() {
        int maxWidth = 0;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                int width = Integer.toString(get(i, j)).length();
                if (width > maxWidth) {
                    maxWidth = width;
                }
            }
        }
        return maxWidth;
    }

    /**
     * Converts the matrix to a 2D array.
     *
     * @return A 2D array representation of the matrix.
     */
    @Override
    public int[][] toArray() {
        int[][] array = new int[this.getRows()][this.getColumns()]; // initialize to all zeros by default

        for (Map.Entry<Pair, Integer> entry : this.entries.entrySet()) {
            Pair indices = entry.getKey();
            int value = entry.getValue();
            array[indices.row][indices.column] = value;
        }

        return array;
    }

    /**
     * Checks if the provided Matrix is equal to the current Matrix.
     * Uses the HashMap class equals method (way simpler than ArrayList implementation).
     * <p>
     * Two Matrix objects are considered equal if:
     * <ul>
     *     <li>They are instances of the Matrix class.</li>
     *     <li>They have the same dimensions.</li>
     *     <li>All corresponding entries between the two matrices are equal.</li>
     * </ul>
     *
     * @param matrix the object to be compared for equality with the current Matrix.
     * @return true if the specified object is equal to the current Matrix, false otherwise.
     */
    @Override
    public boolean equals(Object matrix) {
        if (this == matrix) {
            return true;
        }

        if (!(matrix instanceof Matrix)) {
            return false;
        }

        Matrix other = (Matrix) matrix;

        if (this.rows != other.rows || this.columns != other.columns) {
            return false;
        }

        return this.entries.equals(other.entries);
    }

    /**
     * Adds the current Matrix with another Matrix.
     *
     * @param other The Matrix to add.
     * @return A new matrix that is the result of the addition.
     */
    @Override
    public Matrix plus(MatrixInterface other) {
        validateAddition(other);
        Matrix result = new Matrix(this.rows, this.columns);
        performAddition(result, other);
        return result;
    }

    /**
     * Validates the passed matrix before addition.
     *
     * @param other The Matrix to add.
     */
    @Override
    public void validateAddition(MatrixInterface other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot add these two matrices, the passed Matrix is null.");
        }
        if (this.rows != other.getRows() || this.columns != other.getColumns()) {
            throw new IllegalArgumentException("Cannot add these two matrices, their dimensions don't match.");
        }
    }

    /**
     * Facilitates matrix addition for the plus method.
     * Omits value from resulting sparse matrix if entry is 0.
     *
     * @param result The empty result matrix to be populated.
     * @param other The matrix to be added to our Matrix instance.
     */
    @Override
    public void performAddition(MatrixInterface result, MatrixInterface other) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                int sum = this.get(i, j) + other.get(i, j);
                if (sum != 0) {
                    result.set(i, j, sum);
                }
            }
        }
    }

    /**
     * Multiplies the current Matrix with another Matrix.
     *
     * @param other The Matrix to multiply by.
     * @return A new matrix that is the result of the multiplication.
     */
    @Override
    public Matrix times(MatrixInterface other) {
        validateMultiplication(other);
        Matrix result = new Matrix(this.rows, other.getColumns());
        performMultiplication(result, other);
        return result;
    }

    /**
     * Validates the passed matrix before multiplication.
     *
     * @param other The Matrix to add.
     */
    @Override
    public void validateMultiplication(MatrixInterface other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot multiply these matrices, the passed Matrix is null.");
        }
        if (this.columns != other.getRows()) {
            throw new IllegalArgumentException("Cannot multiply these matrices, the columns of this matrix" +
                    "don't match the rows of the matrix that was passed.");
        }
    }

    /**
     * Facilitates matrix multiplication for the times method.
     * Omits value from resulting sparse matrix if entry is 0.
     *
     * @param result The empty result matrix to be populated.
     * @param other The matrix to be multiplied with our Matrix instance.
     */
    @Override
    public void performMultiplication(MatrixInterface result, MatrixInterface other) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.getColumns(); j++) {
                int sum = 0;
                for (int k = 0; k < this.columns; k++) {
                    sum += this.get(i, k) * other.get(k, j);
                }
                if (sum != 0) {
                    result.set(i, j, sum);
                }
            }
        }
    }
}