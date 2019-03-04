import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
/* //import java.awt.*;

 
public class Programme{

	public static void main(String[] args) {
		JFrame frame = new JFrame("FrameDemo");
		
		JPanel p = new JPanel();
		JButton but = new JButton("x");
		

		frame.add(p);
		frame.setVisible(true);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		p.add(but);

	}
}

*/

public class Fenetre extends JFrame{
	public Fenetre(){
		setTitle("Fenetre1");
		setSize(960,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void paint(Graphics g){
		g.drawRect(300, 100, 300, 50);
		g.setColor(Color.RED);
		g.fillRect(301, 101, 299, 49);
	}


	public static void main(String[] args){
		Fenetre f1 = new Fenetre();
		f1.paint(null);
	}
}