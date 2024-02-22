package Forms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    private JFrame frame;
    private JPanel rectPanel;
    
    private List<Shap> shaps = new ArrayList<>();
    
    private boolean btnCreatingRectangle = false;
    private boolean btnUnion = false;
    private boolean btnInter = false;
    private boolean btnMove = false;
    private boolean btnResize = false;
    private boolean btnInfo = false;
    
    private int startX = -1, startY = -1, endX = -1, endY = -1;
    private Shap selectedShape1, selectedShape2 = null;

    
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

    public void resetBool() {
    	this.btnCreatingRectangle = false;
    	this.btnInter = false;
    	this.btnUnion = false;
    	this.btnMove = false;
    	this.startX = -1;
    	this.startY = -1;
    	this.endX = -1;
    	this.endY = -1;
    	this.selectedShape1=null;
    	this.selectedShape2=null;
    }
    
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);  //size of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar toolBar = new JToolBar();
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
// ---------------------------- btn create rectangle ----------------------------
        JButton InfoBtn = new JButton("Info");
        InfoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBool();
                btnInfo = true;
                rectPanel.removeMouseListener(mouseListener);
                rectPanel.addMouseListener(mouseListener);
            }
        });
        toolBar.add(InfoBtn);
// ---------------------------- btn create rectangle ----------------------------      
// ---------------------------- btn create rectangle ----------------------------
        JButton creatRectBtn = new JButton("New Rectangle");
        creatRectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	resetBool();
                btnCreatingRectangle = true;
                rectPanel.removeMouseListener(mouseListener);
                rectPanel.addMouseListener(mouseListener);
            }
        });
        toolBar.add(creatRectBtn);
// ---------------------------- btn create rectangle ----------------------------

// ---------------------------- btn resize ----------------------------
JButton resizeBtn = new JButton("Resize");
resizeBtn.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        resetBool();
        btnResize = true;
        rectPanel.removeMouseListener(mouseListener);
        rectPanel.addMouseListener(mouseListener);
    }
});
toolBar.add(resizeBtn);
// ---------------------------- btn resize ----------------------------

// --------------------------------- btn Union ---------------------------------
        JButton unionBtn = new JButton("Union");
        unionBtn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		resetBool();
                btnUnion = true;
                rectPanel.removeMouseListener(mouseListener);
                rectPanel.addMouseListener(mouseListener);
            }
        });
        toolBar.add(unionBtn);   
// --------------------------------- btn Union ---------------------------------

// --------------------------------- btn Inter ---------------------------------
        JButton interBtn = new JButton("Inter");
        interBtn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		resetBool();
                btnInter = true;
                rectPanel.removeMouseListener(mouseListener);
                rectPanel.addMouseListener(mouseListener);
            }
        });
        toolBar.add(interBtn);
// --------------------------------- btn Inter ---------------------------------
        
// --------------------------------- btn Move ---------------------------------
        JButton moveBtn = new JButton("Move form");
        moveBtn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		resetBool();
                btnMove = true;
                rectPanel.removeMouseListener(mouseListener);
                rectPanel.addMouseListener(mouseListener);
            }
        });
        toolBar.add(unionBtn);   
        
        JButton btnMove_1 = new JButton("Move");
        toolBar.add(btnMove_1);
// --------------------------------- btn Move ---------------------------------

        // Panel for printing rectangles
        rectPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
        
               for (Shap shap : shaps) {
                   shap.draw(g);
               }
            }
        };
        frame.getContentPane().add(rectPanel, BorderLayout.CENTER);   
    }
    
    private Shap union(Shap shape1, Shap shape2) {
    	
        Shap unionResult = new Shap();
        
    	for (Rectangle rect : shape1.getRectangles()) {
            unionResult.addRectangle(rect);
        }

        for (Rectangle rect : shape2.getRectangles()) {
            unionResult.addRectangle(rect);
        }

        return unionResult;
    }
    
    private Shap inter(Shap shape1, Shap shape2) {
        Shap interResult = new Shap();
        
        for (Rectangle rect1 : shape1.getRectangles()) {
            for (Rectangle rect2 : shape2.getRectangles()) {
                Rectangle intersection = rect1.intersection(rect2);
                if (intersection.getWidth() > 0 && intersection.getHeight() > 0) {
                    interResult.addRectangle(intersection);
                }
            }
        }
        
        return interResult;
    }
    
    private MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	
// ---------------------------- function create rectangle ----------------------------

            if (btnCreatingRectangle) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (startX == -1 && startY == -1) {
                        startX = e.getX();
                        startY = e.getY();
                    } else {
                        endX = e.getX();
                        endY = e.getY();
                        btnCreatingRectangle = false;
                        
                        //ajoute un rectangle a la liste.
                        Shap shap = new Shap();
                        shap.addRectangle(new Rectangle(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY)));
                        shaps.add(shap);
                        
                        rectPanel.removeMouseListener(this);
                        
                        rectPanel.repaint();
                        System.err.println("Rectangle créé avec les coordonnées : (" + startX + ", " + startY + ") et (" + endX + ", " + endY + ")");
                        startX=-1; startY=-1;
                    }
                }
            }       
// ---------------------------- function create rectangle ----------------------------
            
// --------------------------------- function Union ----------------------------------
            else if (btnUnion) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                	// 1) select shap 1
                	if (selectedShape1 == null) {
                		for (int i = shaps.size() - 1; i >= 0; i--) {
                            Shap shape = shaps.get(i);
                            if (shape.isTouch(e.getX(), e.getY())) {
                                selectedShape1 = shape;
                                System.err.println("Selected id= " + shape.getId());
                                break;
                            }
                        }
                    } 
                	
                	// 2) select shap 2
                    else {
                        for (int i = shaps.size() - 1; i >= 0; i--) {
                            Shap shape = shaps.get(i);
                            if (shape.isTouch(e.getX(), e.getY()) && !shape.equals(selectedShape1)) {
                                selectedShape2 = shape;
                                System.err.println("Selected id= " + shape.getId());
                                break;
                            }
                        }
                    }
                   // 3) do an inter
                   if (selectedShape1 != null && selectedShape2 != null) {
                        	
                	   Shap unionResult = union(selectedShape1, selectedShape2);
                	   
                	   shaps.remove(selectedShape1);
                       shaps.remove(selectedShape2);
                       shaps.add(unionResult);
                       
                       System.err.println("New shap id= " + unionResult.getId());
                       selectedShape1 = null;
                       selectedShape2 = null;
                       rectPanel.repaint();
                   }
                }
            }
// --------------------------------- function Union ----------------------------------
            
// --------------------------------- function Inter ----------------------------------
            else if (btnInter) {
            	if (SwingUtilities.isLeftMouseButton(e)) {
            		
            		// 1) select shap 1
                	if (selectedShape1 == null) {
                        for (int i = shaps.size() - 1; i >= 0; i--) {
                            Shap shape = shaps.get(i);
                            if (shape.isTouch(e.getX(), e.getY())) {
                                selectedShape1 = shape;
                                System.err.println("Selected id= " + shape.getId());
                                break;
                            }
                        }
                    } 
                	
                	// 2) select shap 2
                    else {
                        for (int i = shaps.size() - 1; i >= 0; i--) {
                            Shap shape = shaps.get(i);
                            if (shape.isTouch(e.getX(), e.getY()) && !shape.equals(selectedShape1)) {
                                selectedShape2 = shape;
                                System.err.println("Selected id= " + shape.getId());
                                break;
                            }
                        }
                    }
                	
                   // 3) do an inter
                   if (selectedShape1 != null && selectedShape2 != null) {
                        	
                	   Shap InterResult = inter(selectedShape1, selectedShape2);
                	   
                	   shaps.remove(selectedShape1);
                       shaps.remove(selectedShape2);
                       shaps.add(InterResult);
                       
                       System.err.println("New shap id= " + InterResult.getId());
                       selectedShape1 = null;
                       selectedShape2 = null;
                       rectPanel.repaint();
                   }
                }
            }
// --------------------------------- function Inter ----------------------------------
// --------------------------------- function Resize ----------------------------------
            else if (btnResize) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    System.err.println("Resize");
                    for (int i = shaps.size() - 1; i >= 0; i--) {
                        Shap shape = shaps.get(i);
                        if (shape.isTouch(e.getX(), e.getY())) {
                            selectedShape1 = shape;
                            System.err.println("Selected id= " + shape.getId());
                            for (Rectangle rect : shape.getRectangles()) {
                                System.err.println("X1 = " + rect.getX() + " Y1 = " + rect.getY() + " X2 = " + (rect.getX()+rect.getWidth()) + " Y2 = " + (rect.getY()+rect.getHeight()) );
                                rect.ChangeX(rect.getX()*2);
                                rect.ChangeY(rect.getY()*2);
                                rect.ChangHeight(rect.getHeight()*2);
                                rect.ChangeWidth(rect.getWidth()*2);
                                rectPanel.repaint();
                            }
                            break;
                        }
                        
                    }
                }
            }
// --------------------------------- function Resize ----------------------------------
// --------------------------------- function info ----------------------------------
else if (btnInfo) {
    if (SwingUtilities.isLeftMouseButton(e)) {
        System.err.println("Info :");
        for (int i = shaps.size() - 1; i >= 0; i--) {
            Shap shape = shaps.get(i);
            if (shape.isTouch(e.getX(), e.getY())) {
                selectedShape1 = shape;
                System.err.println("Selected id= " + shape.getId());
                for (Rectangle rect : shape.getRectangles()) {
                    System.err.println("X1 = " + rect.getX() + " Y1 = " + rect.getY() + " X2 = " + (rect.getX()+rect.getWidth()) + " Y2 = " + (rect.getY()+rect.getHeight()) );
                }
                break;
            }
            
        }

    }
}
// --------------------------------- function info ----------------------------------
        }
    };
}
