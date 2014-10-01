package grcar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grcar {

	private static final double E = 0.0001;
	private static final long MAX_ITERATIONS = 10000;
	private static final int CHECK_FREQUENCY = 1;

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

	private static double[][] traspose(double[][] mat) {
		double[][] aux = new double[mat[0].length][mat.length];
		for (int i = 0; i < mat[0].length; i++)
			for (int j = 0; j < mat.length; j++)
				aux[i][j] = mat[j][i];
		return aux;
	}

	private static List<Complex> roots(double a, double b, double c) {
		List<Complex> res = new ArrayList<Complex>();
		double x = (b * b) - (4 * a * c);
		if (x > 0) {
			res.add(0, new Complex(((-b + Math.sqrt(x)) / 2 * a), 0));
			res.add(1, new Complex(((-b - Math.sqrt(x)) / 2 * a), 0));
		} else {
			res.add(0, new Complex(-b / (2 * a), Math.sqrt(Math.abs(x))
					/ (2 * a)));
			res.add(1, res.get(0).conjugate());
		}
		return res;
	}

	private static double norm2(double[] v) {
		double aux = 0;
		for (double x : v) {
			aux += x * x;
		}
		return Math.sqrt(aux);
	}

	private static double[][] matrixprod(double[][] m1, double[][] m2) {
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
	
	private static double[][] matrixRprodQ(double[][] r, double[][] q) {
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

	private static double prodInt(double[] x, double[] y) {
		double[][] auxx = new double[1][x.length];
		double[][] auxy = new double[1][y.length];
		auxx[0] = x;
		auxy[0] = y;
		return matrixprod(auxy, traspose(auxx))[0][0];
	}

	private static double[] prodVectEsc(double[] v, double x) {
		double[] res = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			res[i] = v[i] * x;
		}
		return res;
	}

	private static double[] getColumn(double[][] m, int n) {
		double[] res = new double[m.length];
		for (int i = 0; i < m.length; i++) {
			res[i] = m[i][n];
		}
		return res;
	}

	private static Map<String, double[][]> QR(double[][] m) {
		Map<String, double[][]> res = new HashMap<String, double[][]>();
		double[][] Q = new double[m.length][m.length];
		double[][] R = new double[m.length][m[0].length];
		List<double[]> q = new ArrayList<double[]>();
		for (int k = 0; k < m[0].length; k++) {
			double[] v = getColumn(m, k);
			double[] e = v;
			for (int n = 0; n < q.size(); n++) {
				double p = prodInt(v, q.get(n));
				e = sumVec(e, prodVectEsc(q.get(n), -p));
				R[n][k] = p;
			}
			double norm2e = norm2(e);
			R[k][k] = norm2e;
			e = prodVectEsc(e, 1 / norm2e);
			q.add(e);
			for (int i = 0; i < e.length; i++)
				Q[i][k] = e[i];
		}
		res.put("Q", Q);
		res.put("R", R);
		return res;
	}

	private static double[] sumVec(double[] v1, double[] v2) {
		double[] res = new double[v1.length];
		for (int i = 0; i < v1.length; i++) {
			res[i] = v1[i] + v2[i];
		}
		return res;
	}

	public static List<Complex> eig(double[][] m) {
		List<Complex> eigs = new ArrayList<Complex>();
		if (m.length == 2 && m[0].length == 2)
			return roots(1, -m[0][0] - m[1][1], determinant(m));
		double [] dets = new double[(m.length)/2];
		double[][] T = m;
		boolean flag = false;
		for (int k = 0; k < MAX_ITERATIONS && !flag; k++) {
			Map<String, double[][]> qr = QR(T);
			T = matrixRprodQ(qr.get("R"), qr.get("Q"));
			
			if( k % (m.length*CHECK_FREQUENCY) == 0){
				double[][] current = new double[2][2];
				flag = true;
				for (int i = 0; i < m.length - m.length % 2; i += 2) {
					current[0][0] = T[i][i];
					current[0][1] = T[i][i + 1];
					current[1][0] = T[i + 1][i];
					current[1][1] = T[i + 1][i + 1];
					double currentdet = determinant(current);
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
		eigs.sort(new Comparator<Complex>() {
			@Override
			public int compare(Complex o1, Complex o2) {
				return (int) Math.signum(o1.getReal() - o2.getReal());
			}
		});
		for (Complex e : eigs) {
			System.out.println(e);
		}
		long end = System.currentTimeMillis();
		System.out.println("\nTiempo requerido: " + (end - init) / 1000
				+ " segundos");
	}
}
