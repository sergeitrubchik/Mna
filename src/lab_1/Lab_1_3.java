package lab_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Lab_1_3 {
	static int n;
	static double[][] a;
	static double[][] s;
	static double[] d;
	static double[] y;
	static double[] res;
	static double[] f;
	static boolean flag = true;
	
	static double[] x;
	static double[] f_x;
	static double[] P_x;


	public static void findMatrixS(){
		d[0] = Math.signum(a[0][0]);
		s[0][0] = Math.sqrt(Math.abs(a[0][0]));
		if(s[0][0]< 1e-5) {flag = false; s[0][0] = 0; return;}
		for(int j = 2; j<=n; j++) s[0][j-1] = a[0][j-1]/(s[0][0]*d[0]);
		for(int i = 2; i<=n; i++){
			double sum = 0;
			int b = i-1;
			for(int k = 1; k<=b;k++) sum+=(s[k-1][i-1]*s[k-1][i-1])*d[k-1];
			d[i-1] = Math.signum(a[i-1][i-1]-sum);
			s[i-1][i-1] = Math.sqrt(Math.abs(a[i-1][i-1]-sum));
			if(s[i-1][i-1] < 1e-5) {flag = false;s[i-1][i-1] = 0; return;}
			for(int j = i+1; j<=n; j++){
				sum = 0;
				b = i-1;
				for(int k = 1; k<=b;k++) sum+=(s[k-1][i-1]*s[k-1][j-1])*d[k-1];
				s[i-1][j-1] = (a[i-1][j-1] - sum)/(s[i-1][i-1]*d[i-1]);
			}
		}
	}
	
	public static void findVectorY(){
		y[0] = f[0]/(s[0][0]*d[0]);
		for(int i = 2; i<=n; ++i){
			double sum = 0;
			int in = i-1;
			for(int k = 1; k<=in;k++) sum+=(s[k-1][i-1]*y[k-1]*d[k-1]);
			y[i-1] = (f[i-1] - sum)/(s[i-1][i-1]*d[i-1]);
		}
		
	}

	public static void findResult(){
		res[n-1] = y[n-1]/s[n-1][n-1];
		for(int i = n-1; i>=1; --i){
			double sum = 0;
			for(int j = i+1; j<=n;j++) sum+=(s[i-1][j-1]*res[j-1]);
			res[i-1] = (y[i-1] - sum)/s[i-1][i-1];
		}
		
	}
		
	public static double findScalarProduct(int i,int j,int N){//находжение скалярного произведения функций  базиса с номерами i и j
		double scalarProduct = 0;
		for( int k = 0; k < N; k++){
			scalarProduct+=Math.pow(x[k],i)*Math.pow(x[k],j);
		}
		return scalarProduct;
	}
	
	public static double findScalarProduct(int i,int N){//находжение скалярного произведения bcисходной функции и функции  базиса с номером i
		double scalarProduct = 0;
		for( int k = 0; k < N; k++){
			scalarProduct+= f(x[k])*Math.pow(x[k],i);
		}
		return scalarProduct;
	}
	
	public static double f(double x){//вычисление значения функции
		return Math.pow(Math.E, x)+ Math.pow(Math.E, -x);
	}
	
	public static double P(double x,int n, double[] polinom){//вычисление значения полинома в точке
    	double res = 0;
    	res+= polinom[0];
    	for(int i = 1; i < n; i++){
    		res+=polinom[i]*Math.pow(x, i);
    	}
    	return res;
    }
	
  public static void main(String[] args) throws FileNotFoundException{
	 n = 6;
	 double h = 0.1;
	 a = new double[n][n];
	 s = new double[n][n];
	 d = new double[n];
     y = new double[n];
     f = new double[n];
     res = new double[n];
     
	 double start = -1.5;
	 double end = 1.5;
	 int N = (int)((end - start)/h)+1;
	 x = new double[N];
	 f_x  = new double[N];
	 int index = -1;
	 
	 while (start <= (end+1e-3)){//создаем массив узлов и вычисляем значения функции в них
		 ++index;
		 x[index] = start;
		 start+=h;
		 f_x[index] = f(x[index]); 
	 }
	 //создаем матрицу 
	 for(int i = 0; i < n; i++){
    	 for(int j = i; j < n; j++){
    		 a[i][j] = findScalarProduct(i,j,N);
    		 a[j][i] = a[i][j];
    	 }
     }
	 
	 for(int i = 0; i < n; i++){//заполняем нулями, т.к. в дальнейшем будем находить только верхний треугольник
    	 for(int j = 0; j < n; j++){
    		 s[i][j] = 0;    		 
    	 }
     }
	 
	 for(int i = 0; i< n; i++){//столбец свободных коэффициентов
    	 f[i] =  findScalarProduct(i,N);
     }   
     
     findMatrixS();//находим матрицу S
    
     if(flag == false) {
    	 System.out.println("Нет решений");    	
         return ;
     }
     
     findVectorY();//находим вектор у
     findResult();//находим результат

     System.out.println("Коэффициенты полинома:  ");
     for(int i = 0; i < n; ++i){
    	 System.out.format("%7.4f  ", res[i]);        
     }
     
     System.out.println("\nМатрица:");
     for(int i = 0; i < n; i++){
    	 for(int j = 0; j < n; j++){
    		 System.out.format("%7.4f   ", a[i][j]);    		 
    	 }
    	 System.out.println();
     }
     System.out.println("Точки:");
     for(int i = 0; i < N; ++i){
    	 System.out.format("%7.4f%n", x[i]);        
     }
     System.out.println("\n\nЗначения полинома в точках:\n");
     for(int i = 0; i < N; ++i){
    	  System.out.format("%10.7f%n", P(x[i],n,res));
     }
    
    
    	 
     
     
 
  
  
}
}

