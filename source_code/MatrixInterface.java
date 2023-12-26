package assignmenttwo;

/**
 * Represents a matrix interface with basic operations for getting and setting values,
 * validating operations, performing addition and multiplication, and converting to an array.
 *
 * @author Nikola Desnica (UNI: ndd2131)
 * @version 3.0
 */
public interface MatrixInterface {

    /**
     * Returns the number of rows in the matrix.
     *
     * @return the number of rows
     */
    int getRows();

    /**
     * Returns the number of columns in the matrix.
     *
     * @return the number of columns
     */
    int getColumns();

    /**
     * Retrieves the value at the specified row and column position.
     *
     * @param i the row position
     * @param j the column position
     * @return the value at the specified position
     */
    int get(int i, int j);


    void set(int i, int j, int value);

    /**
     * Validates whether the given matrix is compatible for addition with this matrix.
     * Implementing classes should throw an exception if matrices are not compatible.
     *
     * @param other the matrix to validate compatibility with
     */
    void validateAddition(MatrixInterface other);

    /**
     * Validates whether the given matrix is compatible for multiplication with this matrix.
     * Implementing classes should throw an exception if matrices are not compatible.
     *
     * @param other the matrix to validate compatibility with
     */
    void validateMultiplication(MatrixInterface other);

    public Matrix plus(MatrixInterface other);

    public Matrix times(MatrixInterface other);

    /**
     * Helps in performing the matrix addition operation. It takes the result matrix
     * and another matrix to add to the current matrix.
     *
     * @param result the matrix where the addition result will be stored
     * @param other  the matrix to be added to the current matrix
     */
    void performAddition(MatrixInterface result, MatrixInterface other);

    /**
     * Helps in performing the matrix multiplication operation. It takes the result matrix
     * and another matrix to multiply with the current matrix.
     *
     * @param result the matrix where the multiplication result will be stored
     * @param other  the matrix to be multiplied with the current matrix
     */
    void performMultiplication(MatrixInterface result, MatrixInterface other);

    /**
     * Converts the matrix to a 2D array representation.
     *
     * @return a 2D array representation of the matrix
     */
    int[][] toArray();

    /**
     * Returns a string representation of the matrix.
     *
     * @return the string representation
     */
    @Override
    String toString();
}