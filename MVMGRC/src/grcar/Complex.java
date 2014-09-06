package grcar;

public class Complex {

	private double real;
	private double img;

	public Complex(double real, double img) {
		this.real = real;
		this.img = img;
	}

	@Override
	public String toString() {
		String realstring = String.format("%.5f", real);
		String imgstring = String.format("%.5f", Math.abs(img));
		if (img >= 0)
			return realstring + " + " + imgstring + "i";
		return realstring + " - " + imgstring + "i";
	}

	public Complex conjugate() {
		return new Complex(real, -img);
	}

	public double mod() {
		return Math.sqrt(real * real + img * img);
	}
	
	public double getReal(){
		return real;
	}
}
