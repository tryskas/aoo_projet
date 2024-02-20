package Forms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    private JFrame frame;
    private boolean creatingRectangle = false;
    private int startX = -1, startY = -1, endX = -1, endY = -1;
    private JPanel rectPanel;
    private List<Rectangle> rectangles = new ArrayList<>();
    
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Graph window = new Graph();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Graph() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar toolBar = new JToolBar();
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        
// ---------------------------- btn create rectangle ----------------------------
        JButton creatRectBtn = new JButton("New Rectangle");
        creatRectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creatingRectangle = true;
                rectPanel.addMouseListener(mouseListener);
            }
        });
        toolBar.add(creatRectBtn);
// ---------------------------- btn create rectangle ----------------------------

//// ---------------------------- TO DO ----------------------------

        JButton unionBtn = new JButton("Union");
        toolBar.add(unionBtn);

        JButton interBtn = new JButton("Inter");
        toolBar.add(interBtn);
// ---------------------------- TO DO ----------------------------

        // Panel pour afficher les rectangles
        rectPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                for (Rectangle rectangle : rectangles) {
                    g.setColor(Color.GREEN);
                    g.fillRect((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
                }
            }
        };
        frame.getContentPane().add(rectPanel, BorderLayout.CENTER);
        
        
    }

    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (creatingRectangle) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (startX == -1 && startY == -1) {
                        startX = e.getX();
                        startY = e.getY();
                    } else {
                        endX = e.getX();
                        endY = e.getY();
                        creatingRectangle = false;
                        
                        //ajoute un rectangle a la liste.
                        rectangles.add(new Rectangle(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY)));
                        rectPanel.removeMouseListener(this);
                        
                        rectPanel.repaint(); // Rafraîchit l'affichage du panneau pour afficher le nouveau rectangle
                        System.out.println("Rectangle créé avec les coordonnées : (" + startX + ", " + startY + ") et (" + endX + ", " + endY + ")");
                        startX=-1; startY=-1;
                    }
                }
            }
        }
    };
    
}
