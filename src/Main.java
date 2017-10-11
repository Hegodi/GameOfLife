////////////////////////////////////////////////////////////////////////////////
////                                                                        ////
////  Game of life                                                          ////
////                                                                        ////
////  Diego Gonzalez                                                        ////
////  April 2016                                                            ////
////                                                                        ////
////////////////////////////////////////////////////////////////////////////////
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Main {
    public static void main(String args[]) {
        Window window = new Window(740,555);
    }
}

class Window extends JFrame implements ActionListener ,MouseListener{
    
    int iter, alive;
    Pantalla pantalla;
    JButton Btn_start;
    JButton Btn_stop;
    JButton Btn_init;
    JButton Btn_next;
    JButton Btn_load;
    JComboBox Cbox_size;
    JComboBox Cbox_seed;
    JComboBox Cbox_speed;
    JComboBox Cbox_load;
    JTextField Txt_iter;
    JLabel labelIter;
    JLabel labelAlive;
    Timer timer;
    boolean blocked;

    public Window(int w, int h) {

        iter = 0;
        alive = 0;
        this.setBounds(0,0,w,h);
        pantalla = new Pantalla(10,10,500,500);
        pantalla.addMouseListener(this);

        Btn_start = new JButton("Start");
        Btn_start.setBounds(520,480,100,25);
        Btn_start.addActionListener(this);

        Btn_stop = new JButton("Stop");
        Btn_stop.setBounds(625,480,100,25);
        Btn_stop.addActionListener(this);

        Btn_next = new JButton("Next");
        Btn_next.setBounds(520,450,100,25);
        Btn_next.addActionListener(this);
 
        Btn_init = new JButton("Initialize");
        Btn_init.setBounds(625,200,100,25);
        Btn_init.addActionListener(this);

        Btn_load = new JButton("Load");
        Btn_load.setBounds(625,300,100,25);
        Btn_load.addActionListener(this);

        String[] sizeStrings = {"Small", "Normal", "Large"};
        Cbox_size = new JComboBox(sizeStrings);
        Cbox_size.setSelectedIndex(0);
        Cbox_size.setBounds(625,100,100,25);
        JLabel lbl1 = new JLabel("Size :");
        lbl1.setBounds(520,100,100,25);

        labelIter = new JLabel("Iterations: 0");
        labelIter.setBounds(520,5,200,30);
        
        labelAlive = new JLabel("Alive: 0");
        labelAlive.setBounds(520,35,200,30);

        String[] seedString = {"Empty", "Random"};
        Cbox_seed = new JComboBox(seedString);
        Cbox_seed.setSelectedIndex(0);
        Cbox_seed.setBounds(625,130,100,25);
        JLabel lbl2 = new JLabel("Seed :");
        lbl2.setBounds(520,130,100,25);

        String[] speedString = {"Slow", "Normal", "Fast", "Very fast"};
        Cbox_speed = new JComboBox(speedString);
        Cbox_speed.setSelectedIndex(1);
        Cbox_speed.setBounds(625,160,100,25);
        JLabel lbl3 = new JLabel("Speed :");
        lbl3.setBounds(520,160,100,25);

        String[] loadString = {"Test"};
        Cbox_load = new JComboBox(speedString);
        Cbox_load.setSelectedIndex(1);
        Cbox_load.setBounds(520,300,100,25);

        Btn_stop.setEnabled(false);
      
        blocked = false;

        this.setLayout(null);
        this.add(pantalla);
        this.add(Btn_start);
        this.add(Btn_stop);
        this.add(Btn_init);
        this.add(Btn_next);
        this.add(Btn_load);
        this.add(Cbox_size);
        this.add(lbl1);
        this.add(Cbox_seed);
        this.add(lbl2);
        this.add(Cbox_speed);
        this.add(lbl3);
        this.add(Cbox_load);
        this.add(labelIter);
        this.add(labelAlive);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
        this.setVisible(true);
        pantalla.init(20,20);
    }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource()==Btn_start) {
      System.out.printf("Boton Start\n");
      Btn_start.setEnabled(false);
      Btn_stop.setEnabled(true);
      Btn_next.setEnabled(false);
      Btn_init.setEnabled(false);
      blocked = true;
      int speed = 1000;
      String speedtxt = (String)Cbox_speed.getSelectedItem();
      if (speedtxt == "Slow") speed = 1000;
      if (speedtxt == "Normal") speed = 500;
      if (speedtxt == "Fast") speed = 200;
      if (speedtxt == "Very fast") speed = 10;
      timer = new Timer(speed, new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
          labelIter.setText("Iteration: " + iter);
          iter++;
          int na = pantalla.update();
          labelAlive.setText("Alive: " + na);
          pantalla.refresh();
        }
      });
      timer.start();

    } else if (e.getSource()==Btn_stop) {
      System.out.printf("Boton Stop\n");
      Btn_start.setEnabled(true);
      Btn_stop.setEnabled(false);
      Btn_next.setEnabled(true);
      Btn_init.setEnabled(true);
      blocked = false;
      timer.stop();

    } else if (e.getSource()==Btn_next) {
      System.out.printf("Boton next\n");
      iter++;
      int na = pantalla.update();
      pantalla.refresh();
      labelAlive.setText("Alive: " + na);
      labelIter.setText("Iteration: " + iter);

    } else if (e.getSource()==Btn_init) {
      System.out.printf("Boton init\n");
      String size = (String)Cbox_size.getSelectedItem();
      String seed = (String)Cbox_seed.getSelectedItem();
      int n = 2;;
      if (size == "Small") n = 20;
      if (size == "Normal") n = 50;
      if (size == "Large") n = 100;
      pantalla.init(n, n);

      if (seed == "Random") {
        pantalla.randomInit();
      }
      
      pantalla.refresh();
      iter = 0;
      int na = 0;
      labelAlive.setText("Alive: " + na);
      labelIter.setText("Iteration: " + iter);
      blocked = false;
      
    }
  }

  public void mousePressed(MouseEvent e) {
    if (!blocked) {
      pantalla.set(e.getX(),e.getY());
      pantalla.refresh();
    }
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
  }

}
