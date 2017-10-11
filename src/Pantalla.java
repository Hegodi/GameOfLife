////////////////////////////////////////////////////////////////////////////////
////                                                                        ////
////  Diego Gonzalez                                                        ////
////  April 2016                                                            ////
////                                                                        ////
////////////////////////////////////////////////////////////////////////////////

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pantalla extends Canvas{
 
  private int nx, ny;
  private int szc;
  int dimX, dimY;

  private Color colorFondo;
  private boolean[][] grid;
  private boolean[][] tmp;

  Pantalla(int x0, int y0, int _dimX, int _dimY) {
    dimX = _dimX;
    dimY = _dimY;
    this.setBounds(x0, y0, dimX, dimY);
    nx = 0;
    ny = 0;
    colorFondo = new Color(0,0,0);
    setVisible(true);
  }

  public void init(int _nx, int _ny) {
    nx = _nx;
    ny = _ny;
    Dimension D = getSize();
    if ( (D.width % nx != 0) || (D.height % ny != 0)) {
      System.out.println("ERROR: (D.width % nx != 0) || (D.height % ny != 0) \n\n"); 
    }
    int szcX = D.width/nx;
    if (szcX == 0) {
      System.out.println("ERROR: szX == 0\n\n");
    }

    int szcY = D.height/nx;
    if ( (szcY == 0) || (szcY != szcX) ){
      System.out.println("ERROR: szcX != szcY \n\n");
    }

    nx += 2;
    ny += 2;
    szc = szcX;

    grid = new boolean[nx][ny];
    tmp = new boolean[nx][ny];
    Graphics g = this.getGraphics();
    dibuja(g);
  }

  public void reset(double min, double max) {
    for (int i=0; i<ny; i++) {
      for (int j=0; j<nx; j++) {
        grid[i][j] = false;
      }
    }

  }

  public void dibuja(Graphics gT) {
    Dimension D = getSize();
    BufferedImage buffer = new BufferedImage(D.width, D.height, 
                                             BufferedImage.TYPE_INT_ARGB);

    Graphics g = buffer.createGraphics();


    int x, y;
    g.setColor(new Color(0,0,0));
    g.fillRect(0, 0, D.width, D.height);

    g.setColor(new Color(55,55,55));
    y = 0;
    for (int i=1; i<ny-1 ; i++, y+=szc) g.drawLine(0, y, dimX, y); 
    x = 0;
    for (int j=1; j<nx-1 ; j++, x+=szc) g.drawLine(x, 0, x, dimY); 

    g.setColor(new Color(255,0,0));
    y = 0;
    for (int i=1; i<ny-1 ; i++, y+=szc) {
      x = 0;
      for (int j=1; j<nx-1 ; j++, x+=szc) {
        if (grid[i][j]) g.fillRect(y,x,szc,szc);
      }
    }
    gT.drawImage(buffer, 0, 0, this);
  }

  public void paint(Graphics g) {
   dibuja(g);	
  }
  
  public void refresh() {
    Graphics g = getGraphics();
    dibuja(g);
  }

  public int countNeighbours(int i, int j) {
    int nn = 0;
    if (grid[i-1][j  ]) nn++;
    if (grid[i-1][j-1]) nn++;
    if (grid[i-1][j+1]) nn++;
    if (grid[i  ][j+1]) nn++;
    if (grid[i  ][j-1]) nn++;
    if (grid[i+1][j  ]) nn++;
    if (grid[i+1][j-1]) nn++;
    if (grid[i+1][j+1]) nn++;
    return nn;
  }

  public int update() {
    int na = 0;
    for (int i=1; i<ny-1 ; i++) {
      for (int j=1; j<nx-1 ; j++) {
        int nn = countNeighbours(i,j);
        if (grid[i][j]) {
          if (nn < 2) tmp[i][j] = false;
          else if (nn > 3) tmp[i][j] = false;
          else {tmp[i][j] = true; na++; }
        } else {
          if (nn == 3) {tmp[i][j] = true; na++;}
          else tmp[i][j] = false;
        }
      }
    }
    boolean[][] kk = grid;
    grid = tmp;
    tmp = kk;
    return na;

  }

  public void set(int x, int y) {
    int i = x/szc+1;
    int j = y/szc+1;
    if (grid[i][j]) grid[i][j] = false;
    else grid[i][j] = true;
  }

  public void randomInit() {
    int num = nx*nx/4;
    for (int k=0; k<num; k++) {
      int i = (int) (ny*Math.random());
      int j = (int) (nx*Math.random());
      grid[i][j] = true; 
    }
  }
}
