package grcar;

public class Complex {

	private double real;
	private double img;
	
	public Complex(double real, double img) {
		this.real = real;
		this.img = img;
	}
	
//	public Complex sum(Complex c){
//		return new Complex(real + c.real, img + c.img);
//	}
//	
//	public Complex sum(Double d){
//		return new Complex(real + d, img);
//	}
//	
//	public Complex div(Double d){
//		return new Complex(real / d, img / d);
//	}
	
	@Override
	public String toString() {
		if(img > 0)
			return real + " + " + img + "i" ;
		return real + " - " + Math.abs(img) + "i" ;
	}
	
	public Complex conjugate() {
		return new Complex(real,-img); 
	}
	
}
