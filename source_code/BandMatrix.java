package assignmenttwo;

/**
 * Represents a symmetrical band matrix.
 * Band matrices have a diagonal and a fixed number of sub-diagonals and super-diagonals.
 * In the case of symmetric band matrices, the sub-diagonals mirror the super-diagonals.
 * Performs BandMatrix operations (i.e. addition and multiplication).
 * Has methods that validate and handle potential errors.
 * <p>
 * This class is slightly longer (note: the bulk is helper methods and javadoc).
 * My design choice was to not implement helper classes.
 * This class has functionalities that only pertain to a symmetric BandMatrix.
 * A helper class would have separated abstraction in a way that isn't as clean.
 *
 * @author Nikola Desnica (UNI: ndd2131)
 * @version 6.0
 */
public class BandMatrix implements MatrixInterface {

    /** The size of the matrix (both rows and columns) */
    private final int size;

    /** Array storing the values of the bands, starting with the main diagonal (enforces symmetry).*/
    private int[] bands;

    /**
     * Constructs a BandMatrix of the given size starting with just the main diagonal.
     *
     * @param size The size of the matrix.
     */
    public BandMatrix(int size) {
        this.size = size;
        this.bands = new int[1];
    }

    /**
     * Constructs a BandMatrix of the given size with a specified number of bands above the diagonal.
     *
     * @param size The size of the matrix.
     * @param bandsAboveDiagonal Number of bands above the main diagonal.
     */
    public BandMatrix(int size, int bandsAboveDiagonal) {
        this.size = size;
        this.bands = new int[bandsAboveDiagonal + 1];
    }

    /**
     * @return size.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * @return size.
     */
    @Override
    public int getRows() {
        return this.size;
    }

    /**
     * @return size.
     */
    @Override
    public int getColumns() {
        return this.size;
    }

    /**
     * Retrieves the value at the specified row and column in the matrix.
     *
     * @param row The row index.
     * @param column The column index.
     * @return The value at the specified position.
     */
    @Override
    public int get(int row, int column) {
        validateIndices(row, column);
        int bandIndex = column >= row ? column - row : row - column;
        if(bandIndex < this.bands.length) {
            return this.bands[bandIndex];
        } else {
            return 0;
        }
    }

    /**
     * Sets the value at the specified row and column in the matrix.
     * If the band for the position doesn't exist, it's created.
     * Optimizes data storage.
     * Validates symmetric BandMatrix structure.
     *
     * @param row The row index.
     * @param column The column index.
     * @param value The value to set.
     */
    @Override
    public void set(int row, int column, int value) {
        validateIndices(row, column);
        int bandIndex = column >= row ? column - row : row - column;
        setHelper(bandIndex, value);
        if (bandIndex >= this.bands.length && value == 0) {
            return;
        }

        this.bands[bandIndex] = value;

    }

    /**
     * Takes care of maintaining and optimizing symmetric BandMatrix structure.
     * <p>
     * Edge cases that are considered:
     * <ul>
     *     <li>Allowed operations:</li>
     *     <ul>
     *         <li>When user wants to remove the outermost bands.</li>
     *         <li>If user wants to add new bands 1 tier outside outermost band.</li>
     *     </ul>
     *     <li>Forbidden operations:</li>
     *     <ul>
     *         <li>If user tries to set bands further in than the outermost non-zero bands to zero.</li>
     *         <li>If user tries to set non-zero value for bands 2 tiers outside outermost bands.</li>
     *     </ul>
     * </ul>
     *
     * @param bandIndex The bands (n and -n) to be set.
     * @param value The value to be assigned.
     */
    public void setHelper(int bandIndex, int value) {
        if (value == 0) {
            if (bandIndex < this.bands.length - 1) {
                throw new IllegalArgumentException("Adding values to these bands will ruin the symmetric band matrix " +
                        "structure.");
            }
            if (bandIndex == this.bands.length - 1) {
                int[] newBands = new int[this.bands.length - 1];
                System.arraycopy(bands, 0, newBands, 0, this.bands.length - 1);
                this.bands = newBands;
            }
        }

        if (value != 0) {
            if (bandIndex > this.bands.length) {
                throw new IllegalArgumentException("Adding values to these bands will ruin the symmetric band matrix " +
                        "structure.");
            }
            if (bandIndex == this.bands.length) {
                // Increase the size of the bands array
                int[] newBands = new int[bandIndex + 1];
                System.arraycopy(this.bands, 0, newBands, 0, this.bands.length);
                this.bands = newBands;
            }
        }
    }

    /**
     * Validates the indices passed (for get or set).
     *
     * @param row The row index.
     * @param column The column index.
     */
    public void validateIndices(int row, int column) {
        if (row < 0 || row > this.size - 1) {
            throw new IllegalArgumentException("The indicated row index is out of bounds for this matrix.");
        }
        if (column < 0 || column > this.size - 1) {
            throw new IllegalArgumentException("The indicated column index is out of bounds for this matrix.");
        }
    }

    /**
     * Converts the BandMatrix to a 2D array.
     *
     * @return A 2D array representation of the matrix.
     */
    @Override
    public int[][] toArray() {
        int[][] array = new int[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int bandIndex = j >= i ? j - i : i - j;

                // Only set values inside the bands
                if (bandIndex < bands.length) {
                    array[i][j] = get(i, j);
                }
            }
        }

        return array;
    }


    /**
     * Transforms a BandMatrix instance into a String representation of the band matrix.
     *
     * @return The String representation of this BandMatrix.
     */
    @Override
    public String toString() {
        // Determine maxWidth
        int maxWidth = getMaxWidth();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            sb.append("[");
            for (int j = 0; j < this.size; j++) {
                int bandIndex = j >= i ? j - i : i - j;

                // Only fetch values inside the bands
                if (bandIndex < this.bands.length) {
                    sb.append(String.format("%" + maxWidth + "d ", get(i, j)));
                } else {
                    sb.append(String.format("%" + maxWidth + "d ", 0));
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
        int maxVal = Integer.MIN_VALUE;
        for (int val : bands) {
            maxVal = Math.max(maxVal, Math.abs(val));
        }
        int maxWidth = Integer.toString(maxVal).length();
        // Consider negative numbers
        for (int val : bands) {
            if (val < 0 && Integer.toString(val).length() > maxWidth) {
                maxWidth = Integer.toString(val).length();
            }
        }
        return maxWidth;
    }


    /**
     * Adds the current BandMatrix with another BandMatrix.
     * Optimized for symmetric BandMatrix addition.
     *
     * @param other The BandMatrix to add.
     * @return A new matrix that is the result of the addition.
     */
    public BandMatrix plus(BandMatrix other) {
        validateAddition(other);
        BandMatrix result = new BandMatrix(this.size);
        int bandSize = Math.max(this.bands.length, other.bands.length);

        for (int i = 0; i < this.size; i++) {
            for (int j = i; j < this.size && (j - i) < bandSize; j++) {
                int sum = this.get(i, j) + other.get(i, j);
                if (sum != 0) {
                    result.set(i, j, sum);
                }
            }
        }

        return result;
    }

    /**
     * Adds the current BandMatrix with a SquareMatrix.
     *
     * @param other The Matrix to add.
     * @return A new matrix that is the result of the addition.
     */
    @Override
    public SquareMatrix plus(MatrixInterface other) {
        validateAddition(other);
        SquareMatrix result = new SquareMatrix(this.size);
        performAddition(result, other);

        return result;
    }

    /**
     * Validates the passed matrix before addition.
     * For the second if statement, keep in mind that other.getRows() == size for Square and Band Matrices.
     *
     * @param other The Matrix to add.
     */
    @Override
    public void validateAddition(MatrixInterface other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot add these two matrices, the passed Matrix is null.");
        }
        if (this.size != other.getRows()) {
            throw new IllegalArgumentException("Cannot add these two matrices, their dimensions don't match.");
        }
    }

    /**
     * Multiplies the current BandMatrix with another BandMatrix.
     * Optimized for symmetric BandMatrix multiplication.
     *
     * @param other The BandMatrix to multiply by.
     * @return A new matrix that is the result of the multiplication.
     */
    public SquareMatrix times(BandMatrix other) {
        validateMultiplication(other);
        SquareMatrix result = new SquareMatrix(this.size);

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int sum = 0;

                int minK = Math.max(0, Math.max(i - (this.bands.length - 1), j - (other.bands.length - 1)));
                int maxK = Math.min(this.size, Math.min(i + this.bands.length, j + other.bands.length));

//                int minK = Math.max(i - this.bands.length + 1, j - other.bands.length + 1);
//                int maxK = Math.min(i + this.bands.length, j + other.bands.length);

                for (int k = minK; k < maxK; k++) {
                    sum += this.get(i, k) * other.get(k, j);
                }
                if (sum != 0) {
                    result.set(i, j, sum);
                }
            }
        }

        return result;
    }


    /**
     * Multiplies the current BandMatrix with a Matrix.
     *
     * @param other The Matrix to multiply by.
     * @return A new matrix that is the result of the multiplication.
     */
    @Override
    public Matrix times(MatrixInterface other) {
        validateMultiplication(other);
        Matrix result = new Matrix(this.size, other.getRows());
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
        if (this.size != other.getRows()) {
            throw new IllegalArgumentException("Cannot multiply these matrices, the columns of this matrix" +
                    "don't match the rows of the matrix that was passed.");
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
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int sum = this.get(i, j) + other.get(i, j);
                if (sum != 0) {
                    result.set(i, j, sum);
                }
            }
        }
    }

    /**
     * Facilitates matrix multiplication for the times method.
     * Omits value from resulting sparse matrix if entry is 0.
     * Optimized for BandMatrix multiplication with a regular Matrix.
     *
     * @param result The empty result matrix to be populated.
     * @param other The matrix to be multiplied with our Matrix instance.
     */
    @Override
    public void performMultiplication(MatrixInterface result, MatrixInterface other) {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < other.getColumns(); j++) {
                int sum = 0;
                for (int k = 0; k < this.size; k++) {
                    int value = this.get(i, k);
                    if (value != 0) {
                        sum += value * other.get(k, j);
                    }
                }
                if (sum != 0) {
                    result.set(i, j, sum);
                }
            }
        }
    }

}