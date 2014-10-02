package grcar;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase auxiliar creada para el manejo de valores complejos.
 */
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
	
	/**
	 * Fórmula Resolvente Cuadrática
	 * @param a Coeficiente de grado 2
	 * @param b	Coeficiente de grado 1
	 * @param c Coeficiente de grado 0
	 * @return Las raíces del polinomnio de grado 2 ax^2 + bx + c (Reales y Complejas).
	 */
	public static List<Complex> roots(double a, double b, double c) {
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
}
