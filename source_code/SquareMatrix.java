package assignmenttwo;

import java.util.*;

/**
 * Represents a square matrix and offers operations tailored for square matrices.
 * Inherits basic matrix functionalities from the Matrix class, but overrides
 * some methods for specialized behavior.
 *
 * @author Nikola Desnica (UNI: ndd2131)
 * @version 3.0
 */
public class SquareMatrix extends Matrix implements MatrixInterface {

    /**
     * The dimensions of this SquareMatrix Instance (rows == columns);
     */
    int size;

    /**
     * Constructor for the SquareMatrix class, ensuring the matrix is square.
     *
     * @param size The number of rows/columns for the matrix (rows == columns).
     */
    public SquareMatrix(int size) {
        super(size, size);
        this.size = size;
    }

    /**
     * Constructs a SquareMatrix from a 2D integer array.
     *
     * @param array The 2D integer array to construct the SquareMatrix from.
     */
    public SquareMatrix(int[][] array) {
        this(getSquareSize(array));
        populateMatrix(array);
    }

    /** Static method just to get size and validate.*/
    private static int getSquareSize(int[][] array) {
        validateArray(array);
        return array.length;
    }

    /**
     * Validates Square property of the array passed to SquareMatrix(int[][] array) constructor.
     *
     * @param array the array to be converted to a SquareMatrix instance.
     */
    private static void validateArray(int[][] array) {
        if (array == null) {
            throw new IllegalArgumentException("The provided 2D array is null.");
        }
        int numRows = array.length;
        for (int[] row : array) {
            int numColumns = row.length;
            if (numColumns != numRows) {
                throw new IllegalArgumentException("The provided 2D array is not square.");
            }
        }
    }

    /**
     * Efficiently populates a SquareMatrix instance with non-zero values.
     *
     * @param array the array to be converted to a SquareMatrix instance.
     */
    public void populateMatrix(int[][] array) {
        int size = array.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.set(i, j, array[i][j]);
            }
        }
    }

    /**
     * Returns the size of the square matrix.
     *
     * @return The number of rows/columns of the square matrix.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Constructs an identity matrix of the given size.
     *
     * @param size The given size.
     * @return An identity matrix.
     */
    public static SquareMatrix identityMatrix(int size) {
        SquareMatrix identityMatrix = new SquareMatrix(size);
        for (int i = 0; i < size; i++) {
            identityMatrix.set(i, i, 1);
        }
        return identityMatrix;
    }


    @Override
    public Matrix plus(MatrixInterface other) {
        super.validateAddition(other);
        SquareMatrix result = new SquareMatrix(this.getRows());
        super.performAddition(result, other);
        return result;
    }

    @Override
    public Matrix times(MatrixInterface other) {
        super.validateMultiplication(other);
        Matrix result;
        if (this.getColumns() == other.getColumns()) {
            result = new SquareMatrix(this.size);
        } else { // The resulting matrix is not square
            result = new Matrix(this.getRows(), other.getColumns());
        }
        super.performMultiplication(result, other);
        return result;
    }

}