class MathSine extends Thread {

 public double deg, res;

 public MathSine(int degree) {
  deg = degree;
 }

 public void run() {
  double rads = Math.toRadians(deg);
  res = Math.sin(rads);
 }
}

class MathCosine extends Thread {

 public double deg, res;

 public MathCosine(int degree) {
  deg = degree;
 }

 public void run() {
  double rads = Math.toRadians(deg);
  res = Math.cos(rads);
 }
}

class MathTan extends Thread {

 public double deg, res;

 public MathTan(int degree) {
  deg = degree;
 }

 public void run() {
  double rads = Math.toRadians(deg);
  res = Math.tan(rads);
 }
}

public class MathFunc {

	public static void main (String [] args) {
	
		MathSine sine = new MathSine(45);
		MathCosine cosine = new MathCosine(60);
		MathTan tangent = new MathTan(30);
		
		sine.start();
		cosine.start();
		tangent.start();
		
		double z = sine.res + cosine.res + tangent.res;
		System.out.println("sin(45) + cos(60) + tan(30) = "+z);

	}

}