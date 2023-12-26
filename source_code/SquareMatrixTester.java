package assignmenttwo;

/**
 * This is a tester class for the SquareMatrix class.
 * We test all methods and a lot of edge cases.
 *
 * @author Nikola Desnica
 * @version 3.0
 */
public class SquareMatrixTester {

    /**
     * Runs all the test methods for the SquareMatrix class.
     */
    public void runTests() {
        testConstructors();
        testPopulateMatrix();
        testGetters();
        testGetAndSet();
        testValidation();
        testEquals();
        testToArrayAndToString();
        testIdentityMatrix();
        testAddition();
        testMultiplication();
        System.out.println("All SquareMatrix tests passed!");
    }

    /**
     * Tests the constructors of the SquareMatrix class.
     */
    public static void testConstructors() {
        SquareMatrix sqMatrix = new SquareMatrix(5);

        assert sqMatrix.getSize() == 5 : "Failed on testConstructors (size-based constructor)";

        int[][] array = {{1, 2, 3, 4, 5}, {4, 5, 6, 7, 8}, {7, 8, 9, 10, 11}, {10, 11, 12, 13, 14}, {13, 14, 15, 16, 17}};
        SquareMatrix fromArray = new SquareMatrix(array);

        assert fromArray.getSize() == 5 : "Failed on testConstructors (array-based constructor)";
        assert fromArray.get(1, 1) == 5 : "Failed on testConstructors (array content)";

        // Let's add an assertion for a non-square array
        try {
            int[][] nonSquare = {{1, 2, 3, 4, 5}, {4, 5, 6, 7, 8}};
            SquareMatrix nonSquareMatrix = new SquareMatrix(nonSquare);
            assert false : "Constructor didn't throw an exception for a non-square array";
        } catch (IllegalArgumentException e) {
            // This is expected.
        }
    }

    /**
     * Tests the populateMatrix method of the SquareMatrix class.
     */
    public static void testPopulateMatrix() {
        SquareMatrix sqMatrix = new SquareMatrix(5);

        int[][] array = {{1, 2, 3, 4, 5}, {4, 5, 6, 7, 8}, {7, 8, 9, 10, 11}, {10, 11, 12, 13, 14}, {13, 14, 15, 16, 17}};
        sqMatrix.populateMatrix(array);
        assert sqMatrix.get(2, 2) == 9 : "Failed on testPopulateMatrix";
    }

    /**
     * Tests the getter methods of the SquareMatrix class.
     */
    private void testGetters() {
        SquareMatrix sqMatrix = new SquareMatrix(5);

        assert sqMatrix.getSize() == 5 : "GetSize failed";
        assert sqMatrix.getRows() == 5 : "GetRows failed.";
        assert sqMatrix.getColumns() == 5 : "GetColumns failed.";
    }

    /**
     * Tests the get and set methods of the SquareMatrix class.
     */
    private void testGetAndSet() {
        SquareMatrix sqMatrix = new SquareMatrix(5);
        sqMatrix.set(0, 0, 10);

        assert sqMatrix.get(0, 0) == 10 : "Set or Get method failed.";

        sqMatrix.set(3, 4, 20);

        assert sqMatrix.get(3, 4) == 20 : "Set or Get method failed.";
    }

    /**
     * Tests validation checks in get and set methods of the SquareMatrix class.
     */
    private void testValidation() {
        SquareMatrix sqMatrix = new SquareMatrix(5);
        sqMatrix.set(0, 0, 3);

        try {
            sqMatrix.get(6, 2);
            assert false : "Expected an exception for invalid indices in get().";
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            sqMatrix.set(-1, 2, 10);
            assert false : "Expected an exception for invalid indices in set().";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests the equals method of the SquareMatrix class.
     */
    private void testEquals() {
        SquareMatrix sqMatrix1 = new SquareMatrix(5);
        sqMatrix1.set(0, 0, 10);
        sqMatrix1.set(0,1, 7);
        sqMatrix1.set(3, 4, -2);

        SquareMatrix sqMatrix2 = new SquareMatrix(5);
        sqMatrix2.set(0, 0, 10);
        sqMatrix2.set(0,1, 7);
        sqMatrix2.set(3, 4, -2);

        assert sqMatrix1.equals(sqMatrix2) : "Equals method failed.";

        sqMatrix2.set(3, 4, 2);
        assert !sqMatrix1.equals(sqMatrix2) : "Equals method failed.";
    }

    /**
     * Tests the toArray and toString methods of the SquareMatrix class.
     */
    private void testToArrayAndToString() {
        SquareMatrix sqMatrix = new SquareMatrix(5);
        sqMatrix.set(0, 0, -5);
        sqMatrix.set(3, 4, 12);
        int[][] array = sqMatrix.toArray();

        assert array[0][0] == -5 : "ToArray method failed.";
        assert array[3][4] == 12 : "ToArray method failed.";
        assert sqMatrix.toString().contains("-5") : "ToString method failed.";
        assert sqMatrix.toString().contains("12") : "ToString method failed.";
    }

    /**
     * Tests the identityMatrix method of the SquareMatrix class.
     */
    public static void testIdentityMatrix() {
        SquareMatrix identity = SquareMatrix.identityMatrix(5);

        assert identity.get(0, 0) == 1
                && identity.get(1, 1) == 1
                && identity.get(2, 2) == 1
                && identity.get(3, 3) == 1
                && identity.get(4, 4) == 1: "Failed on testIdentityMatrix (diagonal)";
        assert identity.get(0, 1) == 0
                && identity.get(1, 0) == 0
                && identity.get(2, 1) == 0 : "Failed on testIdentityMatrix (off-diagonal)";
    }

    /**
     * Tests the addition operation (plus method) of the SquareMatrix class.
     * Edge cases the professor mentioned are considered (full matrix and all zero matrix).
     */
    public static void testAddition() {
        int[][] array = {{1, 2, 3, 4, 5}, {4, 5, 6, 7, 8}, {7, 8, 9, 10, 11}, {10, 11, 12, 13, 14}, {13, 14, 15, 16, 17}};
        SquareMatrix sqMatrix1 = new SquareMatrix(array);
        SquareMatrix sqMatrix2 = new SquareMatrix(array);
        Matrix result = sqMatrix1.plus(sqMatrix2);

        assert result.get(0, 0) == 2
                && result.get(1, 1) == 10
                && result.get(2, 2) == 18
                && result.get(3, 3) == 26
                && result.get(4, 4) == 34: "Failed on testAddition";

        SquareMatrix sqMatrix3 = new SquareMatrix(5);
        Matrix result2 = sqMatrix1.plus(sqMatrix3);

        assert result2.get(0, 0) == 1
                && result2.get(1, 1) == 5
                && result2.get(2, 2) == 9
                && result2.get(3, 3) == 13
                && result2.get(4, 4) == 17: "Failed on addition with all zero matrix";
    }

    /**
     * Tests the multiplication operation (times method) of the SquareMatrix class.
     * Edge cases the professor mentioned are considered (full matrix and all zero matrix).
     */
    public static void testMultiplication() {
        int[][] array = {{1, 2, 3, 4, 5}, {4, 5, 6, 7, 8}, {7, 8, 9, 10, 11}, {10, 11, 12, 13, 14}, {13, 14, 15, 16, 40}};
        SquareMatrix sqMatrix1 = new SquareMatrix(array);
        SquareMatrix sqMatrix2 = new SquareMatrix(array);
        Matrix result = sqMatrix1.times(sqMatrix2);

        assert result.get(0, 0) == 135
                && result.get(1, 1) == 270
                && result.get(2, 2) == 435
                && result.get(3, 3) == 630
                && result.get(4, 4) == 2166: "Failed on testTimes";

        SquareMatrix sqMatrix3 = new SquareMatrix(5);
        Matrix result2 = sqMatrix1.times(sqMatrix3);

        assert result2.get(0, 0) == 0
                && result2.get(1, 1) == 0
                && result2.get(2, 2) == 0
                && result2.get(3, 3) == 0
                && result2.get(4, 4) == 0: "Failed on addition with all zero matrix";
    }
}
