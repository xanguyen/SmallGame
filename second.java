import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class second extends JPanel implements ActionListener, KeyListener {
	Timer t = new Timer(15,this);
	double[] x, y , vx , vy;
	double[] xobs, yobs, lenobs, heiobs;
	int numObs = 5, DEAD = 0, D_R = 0, D_U = 1, D_L = 2, D_D = 3, lifeChampi = 1, BILLES = 0, CHAMPI = 1, bullet=0;
	int[] life, direction;
	double changeSens = 1.0 , xchmapi = 30.0, ychampi = 30.0, vxchampi = 0.0, vychampi = 0.0, bvx = 0.0, bx, by;
	String lastSurvivor = "";
	Graphics2D g2;
	boolean reloading = false;

	public second(double[] xs, double[] ys, double[] v1, double[] v2){
		this.x = xs; 
		this.y = ys;
		this.vx = v1;
		this.vy = v2;
		life = new int[x.length];
		xobs = new double[numObs];
		yobs = new double[numObs];
		lenobs = new double[numObs];
		heiobs = new double[numObs];
		direction = new int[numObs];
		for(int i=0; i<x.length; i++) {
			life[i] = 1;
		}
		for(int i=0; i<numObs; i++) {
			xobs[i] = Math.random() * 500.0;
			yobs[i] = Math.random() * 250.0;
			lenobs[i] = 18.0;
			heiobs[i] = 28.0;
			direction[i] = (int) (Math.random() * 4.0);
		}
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

	}

	public void paintComponent(Graphics g){
		
		Toolkit.getDefaultToolkit().sync();
		super.paintComponent(g);

		g2 = (Graphics2D) g;
		
		/*
		g2.setColor(Color.RED);										//DESSINE LES OBSTACLES/RECTANGLES ROUGES
		for (int i=0; i<numObs; i++) {
			g2.fillRect((int)xobs[i], (int)yobs[i], (int)lenobs[i], (int)heiobs[i]);
		}
		*/


		BufferedImage[] imgObs = new BufferedImage[numObs];
		try {
    		for(int i = 0; i< numObs; i++){
    			imgObs[i] = ImageIO.read(new File("minimechant.png"));			//DESSINE UNE IMAGE DE MECHANT
    			g2.drawImage(imgObs[i], (int) xobs[i], (int) yobs[i], null);
    		}
		} catch (IOException e) {
			System.out.println("exeption");
		}



		BufferedImage img = null;
		try {
    		if(vxchampi >= 0){
    			img = ImageIO.read(new File("mushroom_resize_R.gif"));			//DESSINE UNE IMAGE CHOISIE
    		}
    		else{
    			img = ImageIO.read(new File("mushroom_resize_L.gif"));
    		}
		} catch (IOException e) {
			System.out.println("exeption");
		}
		g2.drawImage(img, (int)xchmapi,(int)ychampi,null);

		Ellipse2D[] circle = new Ellipse2D[x.length];				//DESSINE LES BOULES BLEUES
		for (int i=0; i<x.length ; i++) {
			circle[i] = new Ellipse2D.Double(x[i],y[i],12,12);
			g2.setColor(Color.BLUE);
			g2.fill(circle[i]);
		}

		if(bullet==1){
			by = (int) ychampi + 10;
			bx = (int) xchmapi + 20;
			bvx = 1.5;
			bullet++;
		}
		else if(bullet == -1){
			by = (int) ychampi + 10;
			bx = (int) xchmapi;
			bvx = -1.5;
			bullet--;
		}
		if(bullet != 0){
			Ellipse2D balle = new Ellipse2D.Double(bx,by,10,10);
			g2.setColor(Color.RED);
			g2.fill(balle);
		}
				
	}

	public double abs(double x){
		if(x>=0){
			return x;
		}
		return -x;
	}

	public void actionPerformed(ActionEvent e){


		if(isInObstacle(xchmapi, ychampi, CHAMPI)){				//POUR LE CHAMPIGNON
			lifeChampi = DEAD;
		}
		if(lifeChampi == DEAD){
			vxchampi = vychampi = 0.0;
		}
		else{
			if(xchmapi<0 || xchmapi > 570){
				vxchampi = -vxchampi * 0.75;
				if (xchmapi<0) {
					xchmapi = 0;
				}
				else{
					xchmapi = 570;
				}
			}
			if(ychampi<0 || ychampi >246){
				vychampi = -vychampi*0.75;
				vxchampi = 0.997 * vxchampi;
				if(ychampi<0){
					ychampi = 0;
				}
				else{
					ychampi = 246;
				}
			}
			if(abs(vxchampi) < 0.1 && ychampi>246){
				vxchampi = 0.0;
				ychampi = 246;
			}
			else{
				vychampi += 0.00981 * 1.2;
			}

			xchmapi += vxchampi;
			ychampi += vychampi;
		}


		for (int i = 0; i<x.length; i++) {		//POUR LES BILLES BLEUES
			if(isInObstacle(x[i], y[i], BILLES)){
				life[i]=DEAD;
			}
			if (life[i]== DEAD){
				vy[i] = vx[i] = 0.0;
			}
			else {
				if(x[i]<0 || x[i] > 580){
					vx[i] = -vx[i] * 0.75;
					if (x[i]<0) {
						x[i] = 0;
					}
					else{
						x[i] = 580;
					}
				}
				if(y[i]<0){
					vy[i] = -vy[i] * 0.75;
					vx[i] = 0.997 * vx[i];
					y[i] = 0;
				}
				if(y[i]>256){
					vy[i] = -vy[i] * 0.75;
					vx[i] = 0.997 * vx[i];
					y[i]=256;
				}
				if(abs(vx[i]) < 0.1 && y[i]>256){
					vx[i] = 0.0;
					y[i]=256;
				}
				else{
					vy[i] += 0.00981 * 1.2;
				}

				x[i] += vx[i];
		
				y[i] += vy[i];
			}
		}


		for(int i=0; i<numObs; i++) {			//POUR LES OBSTOCLES/RECTANGLES ROUGES
			if(direction[i] == D_R){
				if(xobs[i]+lenobs[i]<600){
					xobs[i] += 1;
				}
				else{
					direction[i] = D_U;
					yobs[i] -= 1;
				}
			}
			else if(direction[i] == D_U){
				if(yobs[i]>0){
					yobs[i] -= 1;
				}
				else{
					direction[i] = D_L;
					xobs[i] -= 1;
				}
			}
			else if(direction[i] == D_L){
				if(xobs[i]>0){
					xobs[i] -= 1;
				}
				else{
					direction[i] = D_D;
					yobs[i] += 1;
				}
			}
			else{
				if(yobs[i]+heiobs[i]<271){
					yobs[i] += 1;
				}
				else{
					direction[i] = D_R;
					xobs[i] += 1;
				}
			}

			changeSens = Math.random();			//POUR FAIRE CHANGER LES RECTANGLES DE DIRECTION
			if(changeSens<0.001){
				if(direction[i]==D_D){
					direction[i] = D_R;
				}
				else{
					direction[i] += 1;
				}
			}
		}
		if(bullet !=0){
			bx += bvx;
			if(bx>600 || bx<0){
				reloading = false;
			}
			else{
				reloading = true;
			}
		}
		killEnemy();
		repaint();

		if(!theresASurvivor()){
			System.out.println("Les Monstres gagnent ! \nLe dernier survivant était : " + lastSurvivor);
			t.stop();
		}
		if(numObs==0){
			System.out.println("You win !");
			t.stop();
		}
	}

	public void up(){
		vychampi = -1;
	}

	public void down(){
		vychampi = 1;
	}
	public void right(){
		vxchampi = 1;
	}
	public void left(){
		vxchampi = -1;
	}
	public void shoot(){
		if(!reloading && lifeChampi!=DEAD){
			if(vxchampi<0){
				bullet = -1;
			}
			else{
				bullet = 1;
			}
		}
	}

	public void keyPressed(KeyEvent e){
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_SPACE){shoot();}
		if(code == KeyEvent.VK_UP){up();}
		if(code == KeyEvent.VK_DOWN){down();}
		if(code == KeyEvent.VK_RIGHT){right();}
		if(code == KeyEvent.VK_LEFT){left();}
	}

	public boolean isInObstacle(double x, double y, int format){
		if(format == BILLES){
			for (int i=0; i<numObs; i++) {
				if(x>xobs[i]-5 && x<xobs[i]+lenobs[i]-5 && y>yobs[i]-5 && y<yobs[i]+heiobs[i]-5){
					return true;
				}
			}
		}
		else if(format == CHAMPI){
			for (int i=0; i<numObs; i++) {
				if(x>xobs[i]-19 && x<xobs[i]+lenobs[i]-5 && y>yobs[i]-19 && y<yobs[i]+heiobs[i]-5){
					return true;
				}
			}
		}
		return false;

	}

	public void killEnemy(){
		for(int i = 0; i<numObs; i++){
			if(bx>xobs[i]-5 && bx<xobs[i]+lenobs[i]-5 && by>yobs[i]-5 && by<yobs[i]+heiobs[i]-5){
				depopEnemy(i);
				numObs--;
				bx = 620;
				bvx = 0;
			}
		}
	}

	public void depopEnemy(int index){
		double[] newXobs = new double[numObs-1];
		double[] newYobs = new double[numObs-1];
		double[] newLenobs = new double[numObs-1];
		double[] newHeiobs = new double[numObs-1];
		int count = 0;

		for(int i = 0; i<numObs; i++){
			if(i != index){
				newXobs[count] = xobs[i];
				newYobs[count] = yobs[i];
				newLenobs[count] = lenobs[i];
				newHeiobs[count] = heiobs[i];
				count++;
			}
		}
		xobs = newXobs;
		yobs = newYobs;
		lenobs = newLenobs;
		heiobs = newHeiobs;
	}

	public boolean theresASurvivor(){
		if(lifeChampi != DEAD){
			lastSurvivor = "Le Champignon !";
			return true;
		}
		for(int i=0; i<life.length; i++){
			if(life[i] != DEAD){
				lastSurvivor = "Une boule !";
				return true;
			}
		}
		return false;
	}

	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
}
