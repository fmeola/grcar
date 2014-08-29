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
	
	public static void main(String[] args) {
		printMatrix(getGrcarMatrix(10));
	}
}
