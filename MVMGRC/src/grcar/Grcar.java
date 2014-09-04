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
	
	public static int determinant(int[][] mat) {
		int result = 0;
		if (mat.length == 2) {
			result = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];
			return result;
		}
		for (int i = 0; i < mat[0].length; i++) {
			int temp[][] = new int[mat.length - 1][mat[0].length - 1];
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
		int n= 15;
		long start= System.currentTimeMillis();
		System.out.println(determinant(traspose(getGrcarMatrix(n))));
		long end= System.currentTimeMillis();
		System.out.println((end-start));
		System.out.println("-------------------");
		start= System.currentTimeMillis();
		System.out.println(determinant(getGrcarMatrix(n)));
		end= System.currentTimeMillis();
		System.out.println((end-start));
	}

	private static int[][] traspose(int[][] mat) {
		int [][] aux = new int[mat.length][mat[0].length];
		for (int i = 0; i < mat[0].length; i++)
			for (int j = 0; j < mat.length; j++)
				aux[i][j]=mat[j][i];
		return aux;				
	}
}
