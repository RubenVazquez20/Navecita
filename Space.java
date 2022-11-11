import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;



public class Space extends JApplet
    implements KeyListener, FocusListener, MouseListener {
  // (Note: MouseListener is implemented only so that
  // the applet can request the input focus when
  // the user clicks on it.)
  // AQUI SE DECLARAN VARIABLES---------------------------------------
  public int p1x, p2x, p3x, p1y, p2y, p3y, t, centroidx, centroidy, centerx, centery, angulo, dx, dy, sx, sy;
  public int[] px = new int[8];
  public int[] py = new int[8];
  public int[] pxT = new int[8];
  public int[] pyT = new int[8];
  
  
  public int[][] matrix = { { -6, -4, 1 }, { -3, 0, 1 }, { -1, 4, 1 }, { 0, 8, 1 }, { 1, 4, 1 }, { 3, 0, 1 },
  { 6, -4, 1 }, { 0, -6, 1 } };
  public int[][] matrixB = { { 1, 0, dx }, { 0, 1, dy }, { 0, 0, 1 } };
  public int[][] matrixS = { { sx, 0, 0 }, { 0, sy, 0 }, { 0, 0, 1 } };
  
  ArrayList<List<Integer>> nueva = new ArrayList<List<Integer>>();
  List<Integer> values = new ArrayList<Integer>();


  boolean focussed = false; // True when this applet has input focus.

  DisplayPanel canvas; // The drawing surface on which the applet draws,
                       // belonging to a nested class DisplayPanel, which
                       // is defined below.

  public void init() {
    angulo = 270;
    dx = (int) (10 * Math.cos(angulo * Math.PI / 180));
    dy = (int) (10 * Math.sin(angulo * Math.PI / 180));
    sx = 1;
    sy = 1;


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
    centroidx = (int) centroidx / px.length;
    System.out.print(centroidx);

    for (int i = 0; i < py.length; i++) {
      centroidy += py[i];
    }
    centroidy = (int) centroidy / py.length;
    System.out.print(centroidy);

    for (int i = 0; i < px.length; i++) {
      pxT[i] = centerx + px[i];
    }

    for (int i = 0; i < py.length; i++) {
      pyT[i] = centery + py[i];
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

      int width = getSize().width; // Width of the applet.
      int height = getSize().height; // Height of the applet.
      g.drawRect(0, 0, width - 1, height - 1);
      g.drawRect(1, 1, width - 3, height - 3);
      g.drawRect(2, 2, width - 5, height - 5);

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

  public void translate(int arrtemp[]){

    List<Integer> salida = new ArrayList<Integer>();
    salida.clear();
    for(int i = 0; i < matrixB.length; i++){
      int suma = 0;
      for(int j = 0; j < matrixB[0].length; j++){
        // System.out.println(matrixB[i][j]);

        suma += matrixB[i][j] * arrtemp[j];
      }
      salida.add(suma);
    }
    nueva.add(salida);
  
  }

  public void scale(int arrtemp[]){

    List<Integer> salida = new ArrayList<Integer>();
    salida.clear();
    for(int i = 0; i < matrixS.length; i++){
      int suma = 0;
      for(int j = 0; j < matrixS[0].length; j++){
        // System.out.println(matrixB[i][j]);

        suma += matrixS[i][j] * arrtemp[j];
      }
      salida.add(suma);
    }
    nueva.add(salida);
  }

  public void keyTyped(KeyEvent evt) {
  
    char ch = evt.getKeyChar(); // The character typed.

    if (ch == 'e' || ch == 'E') {
      sx +=1;
      sy +=1;
      matrixS[0][0] = sx;
      matrixS[1][1] = sy;
      for(int i = 0; i < matrix.length; i++){
        int[] temp = new int[3];
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
        pxT[i] = centerx + px[i];
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = centery + py[i];
      }

      for(int i = 0; i< matrix.length; i++){
        for(int j = 0; j < matrix[0].length; j++){
          matrix[i][j] = nueva.get(i).get(j);
        }
      }

      nueva.clear();

      canvas.repaint();
      
    }
    // squareColor = Color.blue;
    else if (ch == 'd' || ch == 'D') {
      sx = sx / 2;
      sy = sy / 2;
      matrixS[0][0] = sx;
      matrixS[1][1] = sy;
      for(int i = 0; i < matrix.length; i++){
        int[] temp = new int[3];
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
        pxT[i] = centerx + px[i];
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = centery + py[i];
      }

      for(int i = 0; i< matrix.length; i++){
        for(int j = 0; j < matrix[0].length; j++){
          matrix[i][j] = nueva.get(i).get(j);
        }
      }

      nueva.clear();

      canvas.repaint();
 
    }

    // else if (ch == 'R' || ch == 'r') {
    // squareColor = Color.red;
    // canvas.repaint();
    // }
    // else if (ch == 'K' || ch == 'k') {
    // squareColor = Color.black;
    // canvas.repaint();
    // }

  }// end keyTyped()
  
  public void keyPressed(KeyEvent evt) {
    int key = evt.getKeyCode(); // keyboard code for the key that was pressed

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
      else if(angulo == 0 || angulo ==360){
        matrixB[0][2] = 0;
        matrixB[1][2] = -10;
      }
      else{
        matrixB[0][2] = (int) (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (int) (10 * Math.sin(angulo * Math.PI / 180));
      }     

      
      for(int i = 0; i < matrix.length; i++){
        int[] temp = new int[3];
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
        pxT[i] = centerx + px[i];
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = centery + py[i];
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
        matrixB[0][2] = 10;
        matrixB[1][2] = 0;
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
        matrixB[0][2] = (int) (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (int) (10 * Math.sin(angulo * Math.PI / 180));
      }     

      
      for(int i = 0; i < matrix.length; i++){
        int[] temp = new int[3];
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
        pxT[i] = centerx + px[i];
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = centery + py[i];
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
        matrixB[0][2] = (int) (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (int) (10 * Math.sin(angulo * Math.PI / 180)) * -1;
      }
      else if(angulo == 270){
        matrixB[0][2] = (int) (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (int) (10 * Math.sin(angulo * Math.PI / 180)) * -1;
      }
      else if(angulo == 180){
        matrixB[0][2] = -10;
        matrixB[1][2] = 0;
      }
      else if(angulo == 0 || angulo ==360){
        matrixB[0][2] = 10;
        matrixB[1][2] = 0;
      }
      else{
        matrixB[0][2] = (int) (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (int) (10 * Math.sin(angulo * Math.PI / 180));
      }     

      
      for(int i = 0; i < matrix.length; i++){
        int[] temp = new int[3];
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
        pxT[i] = centerx + px[i];
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = centery + py[i];
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
        matrixB[0][2] = (int) (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (int) (10 * Math.sin(angulo * Math.PI / 180));
      }
      else if(angulo == 270){
        matrixB[0][2] = (int) (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (int) (10 * Math.sin(angulo * Math.PI / 180));
      }
      else if(angulo == 180){
        matrixB[0][2] = 10;
        matrixB[1][2] = 0;
      }
      else if(angulo == 0 || angulo ==360){
        matrixB[0][2] = -10;
        matrixB[1][2] = 0;
      }
      else{
        matrixB[0][2] = (int) (10 * Math.cos(angulo * Math.PI / 180));
        matrixB[1][2] = (int) (10 * Math.sin(angulo * Math.PI / 180));
      }     

      
      for(int i = 0; i < matrix.length; i++){
        int[] temp = new int[3];
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
        pxT[i] = centerx + px[i];
      }

      for (int i = 0; i < py.length; i++) {
        pyT[i] = centery + py[i];
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
