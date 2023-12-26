package assignmenttwo;

/**
 * This is a tester class for the BandMatrix class.
 * We test all methods and a lot of edge cases.
 *
 * @author Nikola Desnica
 * @version 5.0
 */
public class BandMatrixTester {

    /**
     * Executes all the test methods.
     */
    public void runTests() {
        testConstructor();
        testGetters();
        testGetAndSet();
        testValidation();
        testToArrayAndToString();
        testAddition();
        testMultiplication();
        System.out.println("All BandMatrix tests passed!");
    }

    /**
     * Tests the constructors of the BandMatrix class.
     */
    private void testConstructor() {
        BandMatrix matrix = new BandMatrix(5);

        assert matrix.getSize() == 5 : "Constructor failed with single argument.";

        matrix = new BandMatrix(5, 2);

        assert matrix.getSize() == 5 : "Constructor failed with two arguments.";
    }

    /**
     * Tests getter methods of the BandMatrix class.
     */
    private void testGetters() {
        BandMatrix matrix = new BandMatrix(5);

        assert matrix.getRows() == 5 : "GetRows failed.";
        assert matrix.getColumns() == 5 : "GetColumns failed.";
        assert matrix.getSize() == 5 : "GetSize failed.";
    }

    /**
     * Tests the get and set methods of the BandMatrix class, including checks for band symmetry.
     */
    private void testGetAndSet() {
        BandMatrix matrix = new BandMatrix(6);
        matrix.set(0, 0, 10);

        assert matrix.get(0, 0) == 10 : "Set or Get method failed for main diagonal.";
        assert matrix.get(3,3) == 10 : "Set method failed to set entire band.";

        matrix.set(0, 1, 15);
        matrix.set(0, 2, 20);

        assert matrix.get(3, 5) == 20 : "Set or Get method failed for off-diagonal element.";
        assert matrix.get(5, 3) == 20 : "Set or Get failed to maintain band symmetry.";
    }

    /**
     * Tests the validation checks in the BandMatrix class,
     * such as boundary checks and maintaining band symmetry.
     * These are the edge cases of set that would ruin the BandMatrix structure.
     */
    private void testValidation() {
        BandMatrix matrix = new BandMatrix(5);
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

        try {
            matrix.set(0, 2, 10);
            assert false : "Expected an exception for breaking band symmetry by inserting band.";
        }
        catch (IllegalArgumentException e) {
            // expected
        }

        matrix.set(0, 1, 10);
        matrix.set(0, 2, 20);

        try {
            matrix.set(0, 1, 0);
            assert false : "Expected an exception for breaking band symmetry by band.";
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests the conversion of BandMatrix to a 2D array and its string representation.
     */
    private void testToArrayAndToString() {
        BandMatrix matrix = new BandMatrix(5);
        matrix.set(0, 0, 10);
        matrix.set(0,1, 5);
        matrix.set(0, 2, 1);
        int[][] array = matrix.toArray();

        assert array[0][2] == 1 : "ToArray method failed.";
        assert array[1][0] == 5 : "ToArray method failed.";
        assert matrix.toString().contains("10") : "ToString method failed.";
        assert matrix.toString().contains("5") : "ToString method failed.";
        assert matrix.toString().contains("1") : "ToString method failed.";
    }

    /**
     * Tests the addition of two BandMatrices and also addition with a SquareMatrix.
     */
    private void testAddition() {
        BandMatrix matrix1 = new BandMatrix(5, 1);
        matrix1.set(1, 2, 10);

        BandMatrix matrix2 = new BandMatrix(5, 1);
        matrix2.set(1, 2, 15);

        BandMatrix result1 = matrix1.plus(matrix2);

        assert result1.get(1, 2) == 25 : "Matrix addition failed.";
        assert result1.get(2, 1) == 25 : "Matrix addition failed.";
        assert result1.get(4, 3) == 25 : "Matrix addition failed.";

        SquareMatrix matrix3 = new SquareMatrix(5);
        matrix3.set(1,2,15);
        SquareMatrix result2 = matrix1.plus(matrix3);

        assert result2.get(1,2) == 25 : "Matrix addition with SquareMatrix failed";
    }

    /**
     * Tests the multiplication of two BandMatrices and also multiplication with a regular Matrix.
     */
    private void testMultiplication() {
        BandMatrix matrix1 = new BandMatrix(6);
        matrix1.set(0, 0, 1);
        matrix1.set(0, 1, 2);
        matrix1.set(0, 2, 3);

        BandMatrix matrix2 = new BandMatrix(6);
        matrix2.set(0, 0, 1);
        matrix2.set(0, 1, 2);
        matrix2.set(0, 2, 3);

        Matrix result1 = matrix1.times(matrix2);

        assert result1.get(3, 3) == 27 : "Matrix multiplication with BandMatrix failed.";

        Matrix matrix3 = new Matrix(6, 3);
        for (int i = 0; i < matrix3.getRows(); i++) {
            for (int j = 0; j < matrix3.getColumns(); j++) {
                matrix3.set(i, j, i + j);
            }
        }
        Matrix result2 = matrix1.times(matrix3);

        assert result2.get(0,0) == 8 : "Matrix multiplication with Matrix failed";

    }

}
