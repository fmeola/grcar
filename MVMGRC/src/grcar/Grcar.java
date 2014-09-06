package grcar;

public class Grcar {

	public static int[][] getGrcarMatrix(int n){
		int k = 3;
		int [][] matrix = new int[n][n];
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
	
	public static void printMatrix(int[][] matrix){
		int n = matrix.length;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(matrix[i][j] == -1){
					System.out.print(" " + matrix[i][j]);
				} else {
					System.out.print("  " + matrix[i][j]);
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
		System.out.println(roots(2, 3, 4)[0]);
		System.out.println(roots(2, 3, 4)[1]);
		double[][] matrix = new double[2][2];
		matrix[0][0] = 1.2565;
		matrix[0][1] = 1.0185;
		matrix[1][0] = -1.0826;
		matrix[1][1] = 1.4064;
		System.out.println(eigMatrix2(matrix)[0]);
		System.out.println(eigMatrix2(matrix)[1]);
	}

//	private static int[][] traspose(int[][] mat) {
//		int [][] aux = new int[mat.length][mat[0].length];
//		for (int i = 0; i < mat[0].length; i++)
//			for (int j = 0; j < mat.length; j++)
//				aux[i][j]=mat[j][i];
//		return aux;				
//	}
	
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
		return roots(1,-m[0][0] -m[1][1], determinant(m));
	}
}
