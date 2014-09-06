package grcar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grcar {

	public static double[][] getGrcarMatrix(int n){
		int k = 3;
		double [][] matrix = new double[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(i == j){
					matrix[i][j] = 1 ;
				} else if(i == (j + 1)){
					matrix[i][j] = -1;
				} else if(j > i && j <= (i + k)){
					matrix[i][j] = 1;
				}else {
					matrix[i][j] = 0;
				}
			}
		}
		return matrix;
	}
	
	public static void printMatrix(double[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				if(matrix[i][j] < 0){
					System.out.printf(" %.5f", matrix[i][j]);
				} else {
					System.out.printf("  %.5f" , matrix[i][j]);
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
			if(mat[0][i]!=0){
				result += mat[0][i] * Math.pow(-1, i) * determinant(temp);				
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
//		int n = 15;
//		printMatrix(getGrcarMatrix(n));
//		long start= System.currentTimeMillis();
//		System.out.println(determinant(traspose(getGrcarMatrix(n))));
//		long end= System.currentTimeMillis();
//		System.out.println((end-start));
//		System.out.println("-------------------");
//		start= System.currentTimeMillis();
//		System.out.println(determinant(getGrcarMatrix(n)));
//		end= System.currentTimeMillis();
//		System.out.println((end-start));
//		System.out.println(roots(2, 3, 4)[0]);
//		System.out.println(roots(2, 3, 4)[1]);
//		double[][] matrix = new double[2][2];
//		matrix[0][0] = 1.2565;
//		matrix[0][1] = 1.0185;
//		matrix[1][0] = -1.0826;
//		matrix[1][1] = 1.4064;
//		System.out.println(eigMatrix2(matrix)[0]);
//		System.out.println(eigMatrix2(matrix)[1]);
//		double[] b = {0.71637,0.62091,0.62556};
//		System.out.println(norm2(b));
//		double[][] x = {{0.70016,0.33394,0.78501},{0.48998,0.19624,0.92100}};
//		double[][] y = {{0.712111},{0.047847},{0.650948}};
//		printMatrix(matrixprod(x,y));
//		double[] x = {0.81400,0.16056,0.28492};
//		double[] y = {0.41281,0.46494,0.28367};
//		double[] x = {0,1};
//		double[] y = {1,0};
//		System.out.println(prodInt(x,y));
//		double[] x = {3, 2, 4};
//		for(double d : prodVectEsc(x, 2))
//			System.out.print(d + " ");
//		double[][] x = {{0.70016,0.33394,0.78501},{0.48998,0.19624,0.92100}};
//		double[] r = getColumn(x, 2);
//		for(double d : r){
//			System.out.print(d + " ");
//		}
//		double[][] aux = { {1,2,3,1},{1,1,2,2},{2,1,1,2},{4,1,3,1 }};
//		auxQR(aux);
		int n = 5;
		double[][] m = getGrcarMatrix(n);
		System.out.println("GRCAR " + n + "x" + n);
		printMatrix(m);
		System.out.println();
		Map<String, double[][]> qr = QR(m);
		System.out.println("Q = ");
		printMatrix(qr.get("Q"));
		System.out.println();
		System.out.println("R = ");
		printMatrix(qr.get("R"));
		System.out.println();
		System.out.println("Q * R =");
		printMatrix(matrixprod(qr.get("Q"),qr.get("R")));
	}
	
	private static double[][] traspose(double[][] mat) {
		double [][] aux = new double[mat[0].length][mat.length];
		for (int i = 0; i < mat[0].length; i++)
			for (int j = 0; j < mat.length; j++)
				aux[i][j] = mat[j][i];
		return aux;				
	}
	
	private static Complex[] roots(double a, double b, double c){
		// No puede ser a = 0 pues la matriz grcar tiene inversa siempre
		Complex[] res = new Complex[2];
		double x = (b * b) - (4 * a * c);
		if(x > 0){
			res[0] = new Complex(((-b + Math.sqrt(x))/2*a),0);
			res[1] = new Complex(((-b - Math.sqrt(x))/2*a),0);
		} else {
			res[0] = new Complex(-b/(2*a),Math.sqrt(Math.abs(x))/(2*a));
			res[1] = res[0].conjugate();
		}
		return res;
	}
	
	private static Complex[] eigMatrix2(double[][] m){
		if(m.length != 2 || m[0].length != 2){
			// ver excepción
			return null;
		}
		return roots(1,-m[0][0] -m[1][1], determinant(m));
	}
	
	private static double norm2(double[] v){
		double aux = 0;
		for(double x : v){
			aux += x*x;
		}
		return Math.sqrt(aux);
	}
	
	private static double[][] matrixprod(double[][] m1, double[][] m2) {
		double[][] res = new double[m1.length][m2[0].length];
		if (m1[0].length != m2.length) {
			// ver excepción
			return null;
		}
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[0].length; j++) {
				double aux = 0;
				for(int k = 0; k < m1[0].length; k++){
					aux += (m1[i][k] * m2[k][j]);
				}
				res[i][j] = aux;
			}
		}
		return res;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * 
	 * A = 0.81400   0.16056   0.28492
	 * B = 0.41281   0.46494   0.28367
	 * B * A' = dot(A,B) = dot(B,A)
	 */
	// Que los vectores estén en una fila y no en una columna
	private static double prodInt(double[] x, double[] y){
		double[][] auxx = new double[1][x.length];
		double[][] auxy = new double[1][y.length];
		auxx[0]= x;
		auxy[0]= y;
		return matrixprod(auxy,traspose(auxx))[0][0];
	}
	
	private static double[] prodVectEsc(double[] v, double x){
		double[] res = new double[v.length];
		for(int i = 0; i < v.length; i++){
			res[i] = v[i] * x; 
		}
		return res;
	}
	
	private static double[] getColumn(double[][] m, int n){
		double[] res = new double[m.length];
		for(int i = 0; i < m.length; i++){
			res[i] = m[i][n];
		}
		return res;
	}
	
	private static Map<String, double[][]> QR(double[][] m){
		Map<String,double[][]> res = new HashMap<String,double[][]>();
		double[][] Q = new double[m.length][m.length];
		double[][] R = new double[m.length][m[0].length];
		List<double[]> q = new ArrayList<double[]>();
		for(int k = 0; k < m[0].length; k++){
			double[] v = getColumn(m,k);
			double[] e = v;
			for(int n = 0; n < q.size(); n++){
				double p = prodInt(v, q.get(n));
				e = sumVec(e,prodVectEsc(q.get(n), -p ));
				R[n][k] = p;
			}
			double norm2e = norm2(e);
			R[k][k] = norm2e;
			e = prodVectEsc(e, 1 / norm2e);
			q.add(e);
			for(int i = 0; i < e.length; i++)
				Q[i][k] = e[i];
		}
		res.put("Q", Q);
		res.put("R", R);
		return res;
	}
	
	private static double[] sumVec(double[] v1, double[] v2){
		double[] res = new double[v1.length];
		// Ver salir por no tener la misma dim
		for(int i = 0; i < v1.length; i++){
			res[i] = v1[i] + v2[i];
		}
		return res;
	}
	
}
