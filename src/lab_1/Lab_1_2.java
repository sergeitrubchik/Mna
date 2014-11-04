package lab_1;

public class Lab_1_2 {
	public static double[] polinom = {0};
	
	static double[] multPolinoms(double[] first, double[] second){//перемножение 2-х полиномов
    	double[] res = new double[first.length+ second.length-1];
    	for(int i =0; i< res.length; i++){
    		res[i] = 0;
    	}
    	for(int i =0; i< first.length; i++){
    		for(int j = 0; j < second.length; j++){
    			res[i+j]+=first[i]*second[j];
    		}
    	}
    	return res;
    }
	
	static double[] multPolinomWitnNumber(double[] first, double number){//
    	double[] res = new double[first.length];
    	for(int i =0; i< first.length; i++){    		
    			res[i]=first[i]*number;    		
    	}
    	return res;
    }
	
	static double[] sumPolinoms(double[] first, double[] second){//сумма 2-х полиномов
		double[] result;
		if(first.length >= second.length){
			result = new double[first.length];
			for(int i = 0; i< second.length; i++){
				result[i] = first[i]+second[i];
			}
			for(int i = second.length; i < first.length; i++){
				result[i] = first[i];
			}
			
		}
		else {
			result = new double[second.length];
			for(int i = 0; i < first.length; i++){
				result[i] = first[i]+second[i];
			}
			for(int i = first.length; i < second.length; i++){
				result[i] = second[i];
			}
			
		}
    	return result;
    }
	
    public static void makePolinom(double[] x, double[] f_x) {
        double[] temp = new double[2];
        temp[1] = 1;
        
        for (int i = 0; i < x.length; i++) {
        	double p[]= {1};
            int k[] = new int[i+1];
            for (int j = 0; j < i+1; j++) {
                k[j] = j;
            }
            for (int j = 0; j < i; j++) {
                temp[0] = -x[j];
                p  = multPolinoms(p, temp);
            }
            double razn = razdel_razn(f_x, k, x);
            p = multPolinomWitnNumber(p,razn);
            polinom = sumPolinoms(polinom, p);
        }
    }

    public static double razdel_razn(double[] f_x, int[] k, double[] x) {
        if (k.length == 1) {
            return f_x[k[0]];
        }
        if (k.length == 2) {
            return (f_x[k[0]] - f_x[k[1]]) / (x[k[0]] - x[k[1]]);
        }

        int[] k1 = new int[k.length - 1];
        int[] k2 = new int[k.length - 1];
        for (int i = 0; i < k.length - 1; i++) {
            k1[i] = k[i];
            k2[i] = k[i + 1];
        }
        return (razdel_razn(f_x, k1, x) - razdel_razn(f_x, k2, x)) / (x[k[0]] - x[k[k.length - 1]]);
    }
    public static double P(double x,int n){
    	double res = 0;
    	res+= polinom[0];
    	for(int i = 1; i < n; i++){
    		res+=polinom[i]*Math.pow(x, i);
    	}
    	return res;
    }
    
    
    public static double f(double x){
		//return Math.tan(x) - x + 1;
		return Math.cos(x-1)- Math.pow(x,3)+3*x-1;
	}
    public static void main(String args[]){
    	int n = 3;
    	double a = 0;
    	double b = 1.05;
    	
    	double[] x = new double[n+1];
    	double[] f_x = new double[n+1];
    	System.out.println("Узлы интерполяции(для обратной функции):");
    	for(int i = 0; i <= n; i++){
    		f_x[i] = a+i*(b-a)/n;
    		x[i] = f(f_x[i]);
    		System.out.format("%9.6f%n", x[i]);
    	}
    	System.out.println("Значения обратной функции в узлах:");
    	for(int i = 0 ; i <=n; i++){
    		System.out.format("%9.6f%n", f_x[i]);
    	}
    	
    	makePolinom(x, f_x);
    	double result = P(0,n);
    	System.out.println("Искомый корень уравнения:"+result);
    	
    	System.out.println("Значение функции в точке:"+f(result));
    	
    	System.out.println("Коэффициенты полинома:");
    	for(int i = 0; i< polinom.length; i++){
    		System.out.format("%9.6f%n", polinom[i]);
    	}
    	System.out.println("Значения полинома в узлах:");
    	for(int i = 0; i< polinom.length; i++){
    		System.out.format("%9.6f%n", P(x[i],n));
    	}
    	
    	
    	
    	
    	
    }

}
