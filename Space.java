import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;



public class Exam extends JApplet
    implements KeyListener, FocusListener, MouseListener {
  // (Note: MouseListener is implemented only so that
  // the applet can request the input focus when
  // the user clicks on it.)
  // AQUI SE DECLARAN VARIABLES---------------------------------------
  public double p1x, p2x, p3x, p1y, p2y, p3y, t, centroidx, centroidy, centerx, centery, angulo, dx, dy, sx, sy, rx, ry,aux;
  public double[] px = new double[8];
  public double[] py = new double[8];
  public int[] pxT = new int[8];
  public int[] pyT = new int[8];
  
  
  public double[][] matrix = { { -6, -4, 1 }, { -3, 0, 1 }, { -1, 4, 1 }, { 0, 8, 1 }, { 1, 4, 1 }, { 3, 0, 1 },
  { 6, -4, 1 }, { 0, -6, 1 } };
  public double[][] matrixB = { { 1, 0, dx }, { 0, 1, dy }, { 0, 0, 1 } };
  public double[][] matrixS = { { sx, 0, 0 }, { 0, sy, 0 }, { 0, 0, 1 } };
  public double[][] matrixR = { {rx ,-ry, 0 }, { ry, rx, 0 }, { 0, 0, 1 } };


  
  
  ArrayList<List<Double>> nueva = new ArrayList<List<Double>>();
  List<Double> values = new ArrayList<Double>();


  boolean focussed = false; // True when this applet has input focus.

  DisplayPanel canvas; // The drawing surface on which the applet draws,
                       // belonging to a nested class DisplayPanel, which
                       // is defined below.

  public void init() {
    angulo = 270;
    aux = 0;
    dx = (10 * Math.cos(angulo * Math.PI / 180));
    dy = (10 * Math.sin(angulo * Math.PI / 180));
    sx=1;
    sy=1;
    rx = Math.cos(angulo * Math.PI / 180);
    ry = Math.sin(angulo * Math.PI / 180);


    System.out.print(values);
    // solo son variables
    // AQUI SE INICIALIZAN VARIABLES---------------------------------------

    setSize(800, 600); // esto mueve a la figura al iniciar jaja pero tambien son los boundaries de

    centerx = getSize().width / 2;
    centery = getSize().height / 2;

    for (int i = 0; i < matrix.length; i++) {
      px[i] = matrix[i][0];
    }

    for (int i = 0; i < matrix.length; i++) {
      py[i] = matrix[i][1];
    }

    // Centroides
    for (int i = 0; i < px.length; i++) {
      centroidx += px[i];
    }
    centroidx = centroidx / px.length;
    System.out.print(centroidx);

    for (int i = 0; i < py.length; i++) {
      centroidy += py[i];
    }
    centroidy = centroidy / py.length;
    System.out.print(centroidy);

    for (int i = 0; i < px.length; i++) {
      pxT[i] = (int)(centerx + px[i]);
    }

    for (int i = 0; i < py.length; i++) {
      pyT[i] = (int)(centery + py[i]);
    }

    canvas = new DisplayPanel(); // Create drawing surface and
    setContentPane(canvas); // install it as the applet's content pane.

    canvas.setBackground(Color.white); // Set the background color of the canvas.

    canvas.addFocusListener(this); // Set up the applet to listen for events
    canvas.addKeyListener(this); // from the canvas.
    canvas.addMouseListener(this);

  } // end init();

  class DisplayPanel extends JPanel {
    // An object belonging to this nested class is used as
    // the content pane of the applet. It displays the
    // moving square on a white background with a border
    // that changes color depending on whether this
    // component has the input focus or not.

    public void paintComponent(Graphics g) {

      super.paintComponent(g); // Fills the panel with its
                               // background color, which is white.

      /*
       * Draw a 3-pixel border, colored cyan if the applet has the
       * keyboard focus, or in light gray if it does not.
       */

      if (focussed)
        g.setColor(Color.cyan);
      else
        g.setColor(Color.lightGray);

      double width = getSize().width; // Width of the applet.
      double height = getSize().height; // Height of the applet.
      g.drawRect(0, 0, (int)width - 1, (int)height - 1);
      g.drawRect(1, 1, (int)width - 3, (int)height - 3);
      g.drawRect(2, 2, (int)width - 5, (int)height - 5);

      /* Draw the square. */
      // aqui es donde ddebemos formar la figura
      g.setColor(new Color(255, 0, 0));
      g.setColor(new Color(0, 0, 0));

      g.fillPolygon(pxT, pyT, 8);

      /* If the applet does not have input focus, print a message. */

      if (!focussed) {
        g.setColor(Color.magenta);
        g.drawString("Click to activate", 7, 20);
      }

    } // end paintComponent()

  } // end nested class DisplayPanel

  // ------------------- Event handling methods ----------------------

  public void focusGained(FocusEvent evt) {
    // The applet now has the input focus.
    focussed = true;
    canvas.repaint(); // redraw with cyan border
  }

  public void focusLost(FocusEvent evt) {
    // The applet has now lost the input focus.
    focussed = false;
    canvas.repaint(); // redraw without cyan border
  }

  public void translate(double arrtemp[]){

    List<Double> salida = new ArrayList<Double>();
    salida.clear();
    for(int i = 0; i < matrixB.length; i++){
      double suma = 0;
      for(int j = 0; j < matrixB[0].length; j++){
        // System.out.println(matrixB[i][j]);
        suma += matrixB[i][j] * arrtemp[j];
      }
      salida.add(suma);
    }
    nueva.add(salida);
  
  }

  public void scale(double arrtemp[]){

    List<Double> salida = new ArrayList<Double>();
    salida.clear();
    for(int i = 0; i < matrixS.length; i++){
      double suma = 0;
      for(int j = 0; j < matrixS[0].length; j++){
        // System.out.println(matrixB[i][j]);

        suma += matrixS[i][j] * arrtemp[j];
      }
      salida.add(suma);
    }
    nueva.add(salida);
  }

  public void rotate(double arrtemp[]){

    List<Double> salida = new ArrayList<Double>();
    salida.clear();
    for(int i = 0; i < matrixS.length; i++){
      double suma = 0;
      for(int j = 0; j < matrixR[0].length; j++){
        // System.out.println(matrixB[i][j]);7
            suma += matrixR[i][j] * arrtemp[j];      
        // suma += matrixR[i][j] * arrtemp[j];
      }
      salida.add(suma);
    }
    nueva.add(salida);
  }

  public void keyTyped(KeyEvent evt) {
  
    char ch = evt.getKeyChar(); // The character typed.

    if (ch == 'r' || ch == 'R') {
      sx +=.1;
      sy +=.1;
      // Para verificar que solo crece hasta un 200%
      if(sx<2){
        matrixS[0][0] = 1.1;
        matrixS[1][1] = 1.1;
        for(int i = 0; i < matrix.length; i++){
          double[] temp = new double[3];
          for(int j = 0; j < matrix[0].length; j++){
            temp[j] = matrix[i][j];
          }
          scale(temp);
        }


        for (int i = 0; i < nueva.size(); i++) {
          px[i] = nueva.get(i).get(0);
        }

        for (int i = 0; i < nueva.size(); i++) {
          py[i] = nueva.get(i).get(1);
        }

        for (int i = 0; i < px.length; i++) {
          pxT[i] = (int)(centerx + px[i]);
        }

        for (int i = 0; i < py.length; i++) {
          pyT[i] = (int)(centery + py[i]);
        }

        for(int i = 0; i< matrix.length; i++){
          for(int j = 0; j < matrix[0].length; j++){
            matrix[i][j] = nueva.get(i).get(j);
          }
        }

        nueva.clear();

        canvas.repaint();

      }
      else{
        sx = 2;
        sy = 2;
      }


      
            
    }
    else if (ch == 'f' || ch == 'F') {
      sx -= .1;
      sy -= .1;
      // Para verificar que no este por debajo del 100%
      if(sx>1){
        matrixS[0][0] = .9;
        matrixS[1][1] = .9;
        for(int i = 0; i < matrix.length; i++){
          double[] temp = new double[3];
          for(int j = 0; j < matrix[0].length; j++){
            temp[j] = matrix[i][j];
          }
          scale(temp);
        }


        for (int i = 0; i < nueva.size(); i++) {
          px[i] = nueva.get(i).get(0);
        }

        for (int i = 0; i < nueva.size(); i++) {
          py[i] = nueva.get(i).get(1);
        }

        for (int i = 0; i < px.length; i++) {
          pxT[i] = (int)(centerx + px[i]);
        }

        for (int i = 0; i < py.length; i++) {
          pyT[i] = (int)(centery + py[i]);
        }

        for(int i = 0; i< matrix.length; i++){
          for(int j = 0; j < matrix[0].length; j++){
            matrix[i][j] = nueva.get(i).get(j);
          }
        }
      

        nueva.clear();

        canvas.repaint();
      }
      else{
        sx = 1;
        sy = 1;
      }

    }

    // Rotation
    else  if (ch == 'e' || ch == 'E') {
      angulo += 10;
      aux = -9.5;
      if(angulo == 360){
        angulo = 0;
      }
      rx = Math.cos(aux * Math.PI / 180);
      ry = Math.sin(aux * Math.PI / 180);
      matrixR[0][0] = rx;
      matrixR[1][1] = rx;
      System.out.print(angulo);

        matrixR[0][1] = ry * -1;
        matrixR[1][0] = ry;

        for(int i = 0; i < matrix.length; i++){
          double[] temp = new double[3];
          for(int j = 0; j < matrix[0].length; j++){
            temp[j] = matrix[i][j];
          }
          rotate(temp);
        }


        for (int i = 0; i < nueva.size(); i++) {
          px[i] = nueva.get(i).get(0);
        }

        for (int i = 0; i < nueva.size(); i++) {
          py[i] = nueva.get(i).get(1);
        }

        for (int i = 0; i < px.length; i++) {
          pxT[i] = (int)(centerx + px[i]);
        }

        for (int i = 0; i < py.length; i++) {
          pyT[i] = (int)(centery + py[i]);
        }

        for(int i = 0; i< matrix.length; i++){
          for(int j = 0; j < matrix[0].length; j++){
            matrix[i][j] = nueva.get(i).get(j);
          }
        }
      

        nueva.clear();

        canvas.repaint();

      
      }

      else  if (ch == 'd' || ch == 'D') {
        angulo -= 10;
        aux = 9.5;
        if(angulo == 0){
          angulo = 360;
        }
        rx = Math.cos(aux * Math.PI / 180);
        ry = Math.sin(aux * Math.PI / 180);
        matrixR[0][0] = rx;
        matrixR[1][1] = rx;

        matrixR[0][1] = ry * -1;
        matrixR[1][0] = ry;

        for(int i = 0; i < matrix.length; i++){
          double[] temp = new double[3];
          for(int j = 0; j < matrix[0].length; j++){
            temp[j] = matrix[i][j];
          }
          rotate(temp);
        }


        for (int i = 0; i < nueva.size(); i++) {
          px[i] = nueva.get(i).get(0);
        }

        for (int i = 0; i < nueva.size(); i++) {
          py[i] = nueva.get(i).get(1);
        }

        for (int i = 0; i < px.length; i++) {
          pxT[i] = (int)(centerx + px[i]);
        }

        for (int i = 0; i < py.length; i++) {
          pyT[i] = (int)(centery + py[i]);
        }

        for(int i = 0; i< matrix.length; i++){
          for(int j = 0; j < matrix[0].length; j++){
            matrix[i][j] = nueva.get(i).get(j);
          }
        }
      

        nueva.clear();

        canvas.repaint();

      
      }

  }// end keyTyped()
  
  public void keyPressed(KeyEvent evt) {
    int key = evt.getKeyCode(); // keyboard code for the key that was pressed
    System.out.println(angulo);
    if (key == KeyEvent.VK_LEFT) {
      
      if(angulo == 90){
        matrixB[0][2] = -10;
        matrixB[1][2] = 0;
      }
      

      else if(angulo == 270){
        matrixB[0][2] = 10;
        matrixB[1][2] = 0;
      }
      else if(angulo == 180){
        matrixB[0][2] = 0;
        matrixB[1][2] = 10;
      }
      else if(angulo > 0 && angulo < 90){
        matrixB[0][2] = -10;
        matrixB[1][2] = -10;
      }
      else if(angulo > 90 && angulo < 180){
        matrixB[0][2] = -10;
        matrixB[1][2] = 10;
      }
      else if(angulo > 180 && angulo < 270){
        matrixB[0][2] = 10;
        matrixB[1][2] = 10;
      }
      else if(angulo > 270 && angulo < 360){
        matrixB[0][2] = 10;
        matrixB[1][2] = -10;
      }
      else if(angulo == 0 || angulo ==360){
        matrixB[0][2] = 0;
        matrixB[1][2] = -10;
      }
      else{
        matrixB[0][2] = (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (10 * Math.sin(angulo * Math.PI / 180));
      }     

      
      for(int i = 0; i < matrix.length; i++){
        double[] temp = new double[3];
        for(int j = 0; j < matrix[0].length; j++){
          temp[j] = matrix[i][j];
        }
        translate(temp);
      }


      for (int i = 0; i < nueva.size(); i++) {
        px[i] = nueva.get(i).get(0);
      }

      for (int i = 0; i < nueva.size(); i++) {
        py[i] = nueva.get(i).get(1);
      }

      for (int i = 0; i < px.length; i++) {
        pxT[i] = (int)(centerx + px[i]);
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = (int)(centery + py[i]);
      }

      for(int i = 0; i< matrix.length; i++){
        for(int j = 0; j < matrix[0].length; j++){
          matrix[i][j] = nueva.get(i).get(j);
        }
      }

      nueva.clear();

      canvas.repaint();
    }
    else if (key == KeyEvent.VK_RIGHT) {
      if(angulo == 90){
        matrixB[0][2] = 10;//dx
        matrixB[1][2] = 0;
      }
      else if(angulo > 0 && angulo < 90){
        matrixB[0][2] = 10;
        matrixB[1][2] = 10;
      }
      else if(angulo > 90 && angulo < 180){
        matrixB[0][2] = 10;
        matrixB[1][2] = -10;
      }
      else if(angulo > 180 && angulo < 270){
        matrixB[0][2] = -10;
        matrixB[1][2] = -10;
      }
      else if(angulo > 270 && angulo < 360){
        matrixB[0][2] = -10;
        matrixB[1][2] = 10;
      }
      else if(angulo == 270){
        matrixB[0][2] = -10;
        matrixB[1][2] = 0;
      }
      else if(angulo == 180){
        matrixB[0][2] = 0;
        matrixB[1][2] = -10;
      }
      else if(angulo == 0 || angulo ==360){
        matrixB[0][2] = 0;
        matrixB[1][2] = 10;
      }
      else{
        matrixB[0][2] = (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (10 * Math.sin(angulo * Math.PI / 180));
      }     

      
      for(int i = 0; i < matrix.length; i++){
        double[] temp = new double[3];
        for(int j = 0; j < matrix[0].length; j++){
          temp[j] = matrix[i][j];
        }
        translate(temp);
      }


      for (int i = 0; i < nueva.size(); i++) {
        px[i] = nueva.get(i).get(0);
      }

      for (int i = 0; i < nueva.size(); i++) {
        py[i] = nueva.get(i).get(1);
      }

      for (int i = 0; i < px.length; i++) {
        pxT[i] = (int)(centerx + px[i]);
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = (int)(centery + py[i]);
      }

      for(int i = 0; i< matrix.length; i++){
        for(int j = 0; j < matrix[0].length; j++){
          matrix[i][j] = nueva.get(i).get(j);
        }
      }

      nueva.clear();

      canvas.repaint();
     } 
    else if (key == KeyEvent.VK_UP) {
      if(angulo == 90){
        matrixB[0][2] = (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (10 * Math.sin(angulo * Math.PI / 180)) * -1;
      }
      else if(angulo == 270){
        matrixB[0][2] = (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (10 * Math.sin(angulo * Math.PI / 180)) * -1;
      }
      else if(angulo == 180){
        matrixB[0][2] = -10;
        matrixB[1][2] = 0;
      }
      else if(angulo == 0 || angulo ==360){
        matrixB[0][2] = 10;
        matrixB[1][2] = 0;
      }
      else if(angulo > 0 && angulo < 90){
        matrixB[0][2] = 10;
        matrixB[1][2] = -10;
      }
      else if(angulo > 90 && angulo < 180){
        matrixB[0][2] = -10;
        matrixB[1][2] = -10;
      }
      else if(angulo > 180 && angulo < 270){
        matrixB[0][2] = -10;
        matrixB[1][2] = 10;
      }
      else if(angulo > 270 && angulo < 360){
        matrixB[0][2] = 10;
        matrixB[1][2] = 10;
      }
      else{
        matrixB[0][2] = (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (10 * Math.sin(angulo * Math.PI / 180));
      }     

      
      for(int i = 0; i < matrix.length; i++){
        double[] temp = new double[3];
        for(int j = 0; j < matrix[0].length; j++){
          temp[j] = matrix[i][j];
        }
        translate(temp);
      }


      for (int i = 0; i < nueva.size(); i++) {
        px[i] = nueva.get(i).get(0);
      }

      for (int i = 0; i < nueva.size(); i++) {
        py[i] = nueva.get(i).get(1);
      }

      for (int i = 0; i < px.length; i++) {
        pxT[i] = (int)(centerx + px[i]);
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = (int)(centery + py[i]);
      }

      for(int i = 0; i< matrix.length; i++){
        for(int j = 0; j < matrix[0].length; j++){
          matrix[i][j] = nueva.get(i).get(j);
        }
      }

      nueva.clear();

      canvas.repaint();

    } 
    else if (key == KeyEvent.VK_DOWN) {
    if(angulo == 90){
        matrixB[0][2] = (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (10 * Math.sin(angulo * Math.PI / 180));
      }
      else if(angulo == 270){
        matrixB[0][2] = (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (10 * Math.sin(angulo * Math.PI / 180));
      }
      else if(angulo == 180){
        matrixB[0][2] = 10;
        matrixB[1][2] = 0;
      }
      else if(angulo == 0 || angulo ==360){
        matrixB[0][2] = -10;
        matrixB[1][2] = 0;
      }
      else if(angulo > 0 && angulo < 90){
        matrixB[0][2] = -10;
        matrixB[1][2] = 10;
      }
      else if(angulo > 90 && angulo < 180){
        matrixB[0][2] = 10;
        matrixB[1][2] = 10;
      }
      else if(angulo > 180 && angulo < 270){
        matrixB[0][2] = 10;
        matrixB[1][2] = -10;
      }
      else if(angulo > 270 && angulo < 360){
        matrixB[0][2] = -10;
        matrixB[1][2] = -10;
      }
      else{
        matrixB[0][2] = (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (10 * Math.sin(angulo * Math.PI / 180));
      }     

      
      for(int i = 0; i < matrix.length; i++){
        double[] temp = new double[3];
        for(int j = 0; j < matrix[0].length; j++){
          temp[j] = matrix[i][j];
        }
        translate(temp);
      }


      for (int i = 0; i < nueva.size(); i++) {
        px[i] = nueva.get(i).get(0);
      }

      for (int i = 0; i < nueva.size(); i++) {
        py[i] = nueva.get(i).get(1);
      }

      for (int i = 0; i < px.length; i++) {
        pxT[i] = (int)(centerx + px[i]);
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = (int)(centery + py[i]);
      }

      for(int i = 0; i< matrix.length; i++){
        for(int j = 0; j < matrix[0].length; j++){
          matrix[i][j] = nueva.get(i).get(j);
        }
      }

      nueva.clear();

      canvas.repaint();


  } // end Translation

}

  public void keyReleased(KeyEvent evt) {
    // // empty method, required by the KeyListener Interface
  }

  public void mousePressed(MouseEvent evt) {
    // Request that the input focus be given to the
    // canvas when the user clicks on the applet.
    canvas.requestFocus();
  }

  public void mouseEntered(MouseEvent evt) {
  } // Required by the

  public void mouseExited(MouseEvent evt) {
  } // MouseListener

  public void mouseReleased(MouseEvent evt) {
  } // interface.

  public void mouseClicked(MouseEvent evt) {
  }

} // end class KeyboardAndFocusDemo
