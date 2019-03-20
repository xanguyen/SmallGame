import javax.swing.JFrame;

public class Prog{
	
	public static void main(String[] args) {
		int npair = 0;
		double[] xs = new double[npair];
		double[] ys = new double[npair];
		double[] vxs = new double[npair];
		double[] vys = new double[npair];

		for (int i=0; i<npair/2; i++) {
			xs[i] = 30.0 + Math.random() * 20;
			xs[npair/2+i] = 560.0 - Math.random() * 20;
			ys[i] = 30.0 + Math.random() * 20;
			ys[npair/2+i] = 30.0 + Math.random() * 20;
			vxs[i] = -0.5 + Math.random()*2;
			vxs[npair/2+i] = 0.5 - Math.random() * 2;
			vys[i] = vys[npair/2+i] = 2.0;
		}

		second s = new second(xs,ys, vxs,vys,0);
		JFrame f = new JFrame();
		f.setResizable(false);
		f.add(s);
		f.setVisible(true);
		f.setSize(600,300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Petit jeu");
	}
}