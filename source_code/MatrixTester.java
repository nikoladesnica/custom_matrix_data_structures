package assignmenttwo;

/**
 * This is a tester class for the Matrix class.
 * We test all methods and a lot of edge cases.
 *
 * @author Nikola Desnica
 * @version 2.0
 */
public class MatrixTester {

    /**
     * Runs all the test methods for the Matrix class.
     */
    public void runTests() {
        testConstructor();
        testGetters();
        testGetAndSet();
        testValidation();
        testEquals();
        testToArrayAndToString();
        testAddition();
        testMultiplication();
        System.out.println("All Matrix tests passed!");
    }

    /**
     * Tests the constructor of the Matrix class to ensure proper initialization.
     */
    private void testConstructor() {
        Matrix matrix = new Matrix(5, 6);

        assert matrix.getRows() == 5 : "Constructor failed for rows.";
        assert matrix.getColumns() == 6 : "Constructor failed for columns.";
    }

    /**
     * Tests the getter methods (getRows and getColumns) of the Matrix class.
     */
    private void testGetters() {
        Matrix matrix = new Matrix(5, 6);

        assert matrix.getRows() == 5 : "GetRows failed.";
        assert matrix.getColumns() == 6 : "GetColumns failed.";
    }

    /**
     * Tests the get and set methods of the Matrix class to ensure proper
     * value retrieval and assignment.
     */
    private void testGetAndSet() {
        Matrix matrix = new Matrix(5, 6);
        matrix.set(0, 0, 10);

        assert matrix.get(0, 0) == 10 : "Set or Get method failed.";

        matrix.set(3, 4, 20);

        assert matrix.get(3, 4) == 20 : "Set or Get method failed.";
    }

    /**
     * Tests the validation checks in get and set methods of the Matrix class.
     * Ensures exceptions are thrown for invalid indices.
     */
    private void testValidation() {
        Matrix matrix = new Matrix(5, 6);
        matrix.set(0, 0, 5);

        try {
            matrix.get(6, 2);
            assert false : "Expected an exception for invalid indices in get().";
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            matrix.set(-1, 2, 10);
            assert false : "Expected an exception for invalid indices in set().";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests the equals method of the Matrix class to ensure proper comparison
     * of Matrix objects.
     */
    private void testEquals() {
        Matrix matrix1 = new Matrix(5, 6);
        matrix1.set(0, 0, 10);
        matrix1.set(0,1, 5);
        matrix1.set(4, 5, 7);

        Matrix matrix2 = new Matrix(5, 6);
        matrix2.set(0, 0, 10);
        matrix2.set(0,1, 5);
        matrix2.set(4, 5, 7);

        assert matrix1.equals(matrix2) : "Equals method failed.";

        matrix2.set(4, 5, 2);
        assert !matrix1.equals(matrix2) : "Equals method failed.";
    }

    /**
     * Tests the toArray and toString methods of the Matrix class. Validates
     * the conversion of matrix to array representation and string representation.
     */
    private void testToArrayAndToString() {
        Matrix matrix = new Matrix(5, 6);
        matrix.set(0, 0, 10);
        matrix.set(3, 4, 15);
        int[][] array = matrix.toArray();

        assert array[0][0] == 10 : "ToArray method failed.";
        assert array[3][4] == 15 : "ToArray method failed.";
        assert matrix.toString().contains("10") : "ToString method failed.";
        assert matrix.toString().contains("15") : "ToString method failed.";
    }

    /**
     * Tests the addition operation (plus method) of the Matrix class.
     * Ensures matrices are added correctly.
     */
    private void testAddition() {
        Matrix matrix1 = new Matrix(5, 6);
        matrix1.set(0, 0, 10);

        Matrix matrix2 = new Matrix(5, 6);
        matrix2.set(0, 0, 15);

        Matrix result = matrix1.plus(matrix2);

        assert result.get(0, 0) == 25 : "Matrix addition failed.";
    }

    /**
     * Tests the multiplication operation (times method) of the Matrix class.
     * Ensures matrices are multiplied correctly.
     */
    private void testMultiplication() {
        Matrix matrix1 = new Matrix(5, 6);
        matrix1.set(0, 0, 10);

        Matrix matrix2 = new Matrix(6, 5);
        matrix2.set(0, 0, 15);

        Matrix result = matrix1.times(matrix2);

        assert result.get(0, 0) == 150 : "Matrix multiplication failed.";
    }

}
