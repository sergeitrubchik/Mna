package lab_1;
//added to github
public class Lab_1_1 {
	
	public static double f(double x){
		return Math.tan(x) - x + 1;
	}
	
	public static double fi(double x){
		return Math.atan(x-1);
	}
	
	public static double proizv_f(double x){
		return 1/(Math.cos(x)*Math.cos(x)) - 1;
	}
	
	public static double next(double x){
		return (x - f(x)/proizv_f(x));
	}
	
	public static void main(String[] args){
		double a = -1.2;
		double b = -1.1;
		double c = 0 ;		
		int k = 1;
		while(b-a > 1e-2){
			c = (a+b)/2;
			System.out.println("k = "+ k+" a = "+ a+ " b = "+ b+"  f_a = "+f(a)+"  f_b = "+f(b)+"  c = "+(a+b)/2+"  f_c = "+f((a+b)/2)+" eps = "+(b-a));
			if(f(a)*f(c) < 0) b = c;
			else a = c;
			System.out.println(" eps = "+(b-a));			
			k++;
		}
		double result = (a+b)/2;
		System.out.println("Корень, найденный с помощью метода дихотомии: "+result);
		
		k=0;
		double x = result;
		double next_x = next(x);
		System.out.println("k = "+k+" next_x = "+ next_x+" eps = "+(Math.abs(x - next_x)));
		while(Math.abs(x - next_x)>1e-14){			
			x = next_x; 
			next_x = next(x);
			k++;
			System.out.println("k = "+k+" next_x = "+ next_x+" eps = "+(Math.abs(x - next_x)));
			
		}
		System.out.println("Корень, найденный с помощью метода Ньютона: "+next_x);
		
		x = result;
		next_x = fi(x);
		k = 0;
		System.out.println("k = "+k+" next_x = "+ next_x+" eps = "+(Math.abs(x - next_x)));
		while(Math.abs(x - next_x)>1e-14){
			x = next_x;
			next_x = fi(x);
			k++;
			System.out.println("k = "+k+" next_x = "+ next_x+" eps = "+(Math.abs(x - next_x)));
		}
		System.out.println("Корень, найденный с помощью метода простых итераций: "+next_x);
	}
	
}
