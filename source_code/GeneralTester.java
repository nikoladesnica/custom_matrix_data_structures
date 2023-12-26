package assignmenttwo;

/**
 * This is a general tester class that initiates tests for BandMatrix, Matrix, and SquareMatrix.
 * The separate Tester classes for the above matrices use assertions.
 *
 * @author Nikola Desnica
 * @version 1.0
 */
public class GeneralTester {

    /**
     * The main method for starting all the tests.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        BandMatrixTester bandMatrixTester = new BandMatrixTester();
        bandMatrixTester.runTests();

        MatrixTester matrixTester = new MatrixTester();
        matrixTester.runTests();

        SquareMatrixTester squareMatrixTester = new SquareMatrixTester();
        squareMatrixTester.runTests();

        System.out.println();
        System.out.println("All tests passed!");
    }
}
