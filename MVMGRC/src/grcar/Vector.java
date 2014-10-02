package grcar;

/**
 * Clase auxiliar para el manejor de matrices double[][] y vectores double[]. *
 */
public class Vector {

	/**
	 * Suma Vectorial
	 * @param v1 : Vector double[]
	 * @param v2 : Vector double[]
	 * @return Vector suma v1() + v2()
	 */
	public static double[] sumVec(double[] v1, double[] v2) {
		double[] res = new double[v1.length];
		for (int i = 0; i < v1.length; i++) {
			res[i] = v1[i] + v2[i];
		}
		return res;
	}
	
	/**
	 * Método para obtener la n-ésima columna de una matriz.
	 * @param m : Matriz double[]
	 * @param n : Índice n de la columna (0 =< n <= col(m))
	 * @return La n-ésima columna de la matriz m
	 */
	public static double[] getColumn(double[][] m, int n) {
		double[] res = new double[m.length];
		for (int i = 0; i < m.length; i++) {
			res[i] = m[i][n];
		}
		return res;
	}

	/**
	 * Producto entre vector V y escalar X
	 * @param v : Vector double[]
	 * @param x : Escalar double
	 * @return Vector x * v()
	 */
	public static double[] prodVectEsc(double[] v, double x) {
		double[] res = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			res[i] = v[i] * x;
		}
		return res;
	}
	
	/**
	 * Producto Interno entre vectores
	 * @param x : Vector double[]
	 * @param y : Vector double[]
	 * @return Producto Interno <x,y>
	 */
	public static double prodInt(double[] x, double[] y) {
		double[][] auxx = new double[1][x.length];
		double[][] auxy = new double[1][y.length];
		auxx[0] = x;
		auxy[0] = y;
		return matrixprod(auxy, traspose(auxx))[0][0];
	}

	/**
	 * Norma 2 de un vector
	 * @param v : Vector double[]
	 * @return Norma 2 del vector v
	 */
	public static double norm2(double[] v) {
		double aux = 0;
		for (double x : v) {
			aux += x * x;
		}
		return Math.sqrt(aux);
	}

	/**
	 * Producto entre matrices
	 * @param m1 : Matriz double[][]
	 * @param m2 : Matriz double[][]
	 * @return Matriz m1 * m2
	 */
	public static double[][] matrixprod(double[][] m1, double[][] m2) {
		double[][] res = new double[m1.length][m2[0].length];
		if (m1[0].length != m2.length) {
			return null;
		}
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[0].length; j++) {
				double aux = 0;
				for (int k = 0; k < m1[0].length; k++) {
					aux += (m1[i][k] * m2[k][j]);
				}
				res[i][j] = aux;
			}
		}
		return res;
	}
	
	/**
	 * Método del determinante.
	 * @param mat : Una matriz cuadrada double[][]
	 * @return El determinante de la matriz mat
	 */
	public static double determinant(double[][] mat) {
		double result = 0;
		if (mat.length == 2) {
			result = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];
			return result;
		}
		for (int i = 0; i < mat[0].length; i++) {
			double temp[][] = new double[mat.length - 1][mat[0].length - 1];
			for (int j = 1; j < mat.length; j++) {
				System.arraycopy(mat[j], 0, temp[j - 1], 0, i);
				System.arraycopy(mat[j], i + 1, temp[j - 1], i, mat[0].length
						- i - 1);
			}
			if (mat[0][i] != 0) {
				result += mat[0][i] * Math.pow(-1, i) * determinant(temp);
			}
		}
		return result;
	}

	/**
	 * Traspuesta de una matriz
	 * @param mat : Una matriz double[][]
	 * @returnLa traspuesta de la matriz mat
	 */
	public static double[][] traspose(double[][] mat) {
		double[][] aux = new double[mat[0].length][mat.length];
		for (int i = 0; i < mat[0].length; i++)
			for (int j = 0; j < mat.length; j++)
				aux[i][j] = mat[j][i];
		return aux;
	}
	
	/**
	 * Método para imprimir una matriz double[][]
	 * @param matrix : Matriz a imprimir en pantalla
	 */
	public static void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] < 0) {
					System.out.printf(" %.5f", matrix[i][j]);
				} else {
					System.out.printf("  %.5f", matrix[i][j]);
				}
			}
			System.out.println();
		}
	}
}
