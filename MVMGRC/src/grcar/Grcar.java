package grcar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grcar {

	private static final double E = 0.0001;
	private static final long MAX_ITERATIONS = 10000;
	private static final int CHECK_FREQUENCY = 1;

	/**
	 * Consigna A. Obtener la matriz Grcar de NxN
	 * @param n : Dimensión de la Matriz Grcar a obtener
	 * @return Matriz Grcar double[n][n]
	 */
	public static double[][] getGrcarMatrix(int n) {
		int k = 3;
		double[][] matrix = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					matrix[i][j] = 1;
				} else if (i == (j + 1)) {
					matrix[i][j] = -1;
				} else if (j > i && j <= (i + k)) {
					matrix[i][j] = 1;
				} else {
					matrix[i][j] = 0;
				}
			}
		}
		return matrix;
	}

	/**
	 * Optimización del método matrixprod para el caso especial
	 * con m1 = R y m2 = q de la descomposición QR.
	 * @param r : Matriz R de la descomposición QR.
	 * @param q : Matriz Q de la descomposición QR.
	 * @return Matriz R * Q
	 */
	public static double[][] matrixRprodQ(double[][] r, double[][] q) {
		double[][] res = new double[r.length][q[0].length];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[0].length; j++) {
				double aux = 0;
				for (int k = i; k < r[0].length && k <= 2*(Math.floor(j/2)+1); k++) {
					aux += (r[i][k] * q[k][j]);
				}
				res[i][j] = aux;
			}
		}
		return res;
	}

	/**
	 * Descomposición QR de la matriz m.
	 * @param m : Matriz double[][]
	 * @return Mapa que contiene las matrices Q y R de la descomposición QR de la matriz m.
	 * <"Q", Matriz Q>
	 * <"R", Matrix R>
	 */
	public static Map<String, double[][]> QR(double[][] m) {
		Map<String, double[][]> res = new HashMap<String, double[][]>();
		double[][] Q = new double[m.length][m.length];
		double[][] R = new double[m.length][m[0].length];
		List<double[]> q = new ArrayList<double[]>();
		for (int k = 0; k < m[0].length; k++) {
			double[] v = Vector.getColumn(m, k);
			double[] e = v;
			for (int n = 0; n < q.size(); n++) {
				double p = Vector.prodInt(v, q.get(n));
				e = Vector.sumVec(e, Vector.prodVectEsc(q.get(n), -p));
				R[n][k] = p;
			}
			double norm2e = Vector.norm2(e);
			R[k][k] = norm2e;
			e = Vector.prodVectEsc(e, 1 / norm2e);
			q.add(e);
			for (int i = 0; i < e.length; i++)
				Q[i][k] = e[i];
		}
		res.put("Q", Q);
		res.put("R", R);
		return res;
	}

	/**
	 * Método para el cálculo de autovalores de la matriz m
	 * @param m : Matriz double[][]
	 * @return Lista con los autovalores (reales y complejos) de la matriz m
	 */
	public static List<Complex> eig(double[][] m) {
		List<Complex> eigs = new ArrayList<Complex>();
		if (m.length == 2 && m[0].length == 2)
			return Complex.roots(1, -m[0][0] - m[1][1], Vector.determinant(m));
		double [] dets = new double[(m.length)/2];
		double[][] T = m;
		boolean flag = false;
		for (int k = 0; k < MAX_ITERATIONS && !flag; k++) {
			Map<String, double[][]> qr = QR(T);
//			T = matrixprod(matrixprod(traspose(qr.get("Q")),T),qr.get("Q"));
			T = matrixRprodQ(qr.get("R"), qr.get("Q"));
//			T = matrixprod(qr.get("R"), qr.get("Q"));
			if( k % (m.length*CHECK_FREQUENCY) == 0){
				double[][] current = new double[2][2];
				flag = true;
				for (int i = 0; i < m.length - m.length % 2; i += 2) {
					current[0][0] = T[i][i];
					current[0][1] = T[i][i + 1];
					current[1][0] = T[i + 1][i];
					current[1][1] = T[i + 1][i + 1];
					double currentdet = Vector.determinant(current);
					if(Math.abs(dets[i/2]-currentdet) > E){
						flag = false;
					}
					dets[i/2]=currentdet;
				}
			}
		}
		for (int i = 0; i < m.length - m.length % 2; i += 2) {
			double[][] aux = new double[2][2];
			aux[0][0] = T[i][i];
			aux[0][1] = T[i][i + 1];
			aux[1][0] = T[i + 1][i];
			aux[1][1] = T[i + 1][i + 1];
			eigs.addAll(eig(aux));
		}
		if (m.length % 2 == 1) {
			eigs.add(new Complex(T[T.length - 1][T.length - 1], 0));
		}
		return eigs;
	}

	public static void main(String[] args) {
		long init = System.currentTimeMillis();
		int n = 10;
		System.out.println("GRCAR " + n + "x" + n);
		double[][] grcar = getGrcarMatrix(n);
		System.out.println("\nAutovalores");
		List<Complex> eigs = eig(grcar);
		for (Complex e : eigs) {
			System.out.println(e);
		}
		long end = System.currentTimeMillis();
		System.out.println("\nTiempo transcurrido: " + (end - init) + " milisegundos");
	}
}
