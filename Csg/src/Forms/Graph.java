package Forms;

import java.awt.*;

import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import javax.imageio.ImageIO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Graph {

    private JFrame frame;
    private static JFrame info;	
    private JPanel rectPanel;
    private boolean btnCreatingRectangle, btnUnion, btnInter, btnMove, btnResize, btnInfo ,hold= false;
    private int startX, startY, endX, endY = -1;
    private static int IndicePanel=0;
    Shap oldshape = null;
    private Shap selectedShape1, selectedShape2 = null, old=null;
    public List<Shap> shaps = new ArrayList<Shap>();
	protected boolean corner;

    public Graph() {
    	
    	int choice = JOptionPane.showConfirmDialog(null, "Do you want to load from the latest version of the project?", "Loading choice", JOptionPane.YES_NO_OPTION);
        
        // Si l'utilisateur choisit de charger à partir du fichier de sérialisation
        if (choice == JOptionPane.YES_OPTION) {
            // Désérialiser le fichier
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("shapes.ser"));
                shaps = (List<Shap>) ois.readObject();
                ois.close();
            } catch (FileNotFoundException e) {
                // Le fichier de sérialisation n'existe pas encore
                e.printStackTrace();
            } catch (IOException e) {
                // Erreur de lecture du fichier
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // La classe des objets sérialisés n'a pas été trouvée
                e.printStackTrace();
            }
        }

        initialize();
    }
    
    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);  //size of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar toolBar = new JToolBar();
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        
        int xOffset = frame.getX() + frame.getWidth();
		int yOffset = frame.getY();
		info = new JFrame();
		info.setBounds(xOffset, yOffset, 300, 200);
		info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		info.setVisible(true); 
        
// ------------------------------- btn Info -------------------------------
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
// ------------------------------- btn Info -------------------------------
        
// ---------------------------- btn create rectangle ----------------------------
                
        JButton creatRectBtn = new JButton("create");
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

        /* FOR CERLCE
        JButton creatCercBtn = new JButton("New Cercle");
        creatCercBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	resetBool();
                btnCreatingCercle = true;
                rectPanel.removeMouseListener(mouseListener);
                rectPanel.addMouseListener(mouseListener);
            }
        });
        toolBar.add(creatCercBtn);
        */
// ---------------------------- btn resize ----------------------------
     JButton resizeBtn = new JButton("resize");

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
        JButton unionBtn = new JButton("union");

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
        JButton interBtn = new JButton("inter");

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
        JButton moveBtn = new JButton("move");

        moveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 resetBool();
            	 btnMove = true;
            	 rectPanel.removeMouseListener(mouseListener);
                 rectPanel.addMouseListener(mouseListener);
            }
        });
        toolBar.add(moveBtn);
// --------------------------------- btn Move ---------------------------------
        
// --------------------------------- btn Save ---------------------------------
        JButton saveBtn = new JButton("save");

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    serializeShapes("shapes.ser");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        toolBar.add(saveBtn);
// --------------------------------- btn Save ---------------------------------

// --------------------- Panel for printing rectangles ------------------------
        rectPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
        
               for (Shap shap : shaps) {
                   shap.draw(g);
               }
               if (btnCreatingRectangle && startX != -1 && startY != -1) {
            	    int width = Math.abs(endX - startX);
            	    int height = Math.abs(endY - startY);
            	    int x = Math.min(startX, endX);
            	    int y = Math.min(startY, endY);
            	    g.drawRect(x, y, width, height);
            	}
            }
        };
        frame.getContentPane().add(rectPanel, BorderLayout.CENTER);   
// --------------------- Panel for printing rectangles ------------------------

// ================================= Mouse Dragged =================================
        rectPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (btnMove && selectedShape1 != null) {
                    
                    // Calcul du décalage par rapport à la position initiale de la souris
                    int deltaX = e.getX() - startX;
                    int deltaY = e.getY() - startY;
                    // Déplacement de la forme en fonction du décalage
                    selectedShape1.move(deltaX, deltaY);
                    
                    startX = e.getX();
                    startY = e.getY();
                    
                    rectPanel.repaint();
                }
                
                else if (btnCreatingRectangle) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (startX != -1 && startY != -1) {
                            endX = e.getX();
                            endY = e.getY();
                            rectPanel.repaint();
                        }
                    }
                }
        
                else if (btnInfo && selectedShape1 != null) {
       	        		 if (SwingUtilities.isLeftMouseButton(e)) {
       	                	//System.out.println("test corner activé");
       	                	
       	                	for (int i = shaps.size() - 1; i >= 0; i--) {
       		                     Shap shape = shaps.get(i);
       		                     
       		                     int deltaX = e.getX() - startX;
       		                     int deltaY = e.getY() - startY;
       		                     
      		                     boolean dx=true,dy=true,dw = true,dh =true;
       		                     
       		                     
       		                     switch(shape.isTouchInfoCorner(e.getX(), e.getY())) {
       		                     
       		                     case 1:
       		                    	 corner = true;
       		                    	 System.out.println("1 bouge");
       		                    	 selectedShape1.setco(deltaX,deltaY,dx,dy,dw,dh);
       		                    	 rectPanel.repaint();
       		                    	 hold=true;
       		                    	 
       		                    	 break;
       		                     case 2:
       		                    	 corner = true;
       		                    	 System.out.println("2");
       		                    	selectedShape1.setco(deltaX,deltaY,dx,false,false,dh);
      		                    	 rectPanel.repaint();
      		                    	 hold=true;
       		                    	 break;
       		                     case 3:
       		                    	 corner = true;
       		                    	 System.out.println("3");
       		                    	selectedShape1.setco(deltaX,deltaY,dx,false,dw,dh);
      		                    	 rectPanel.repaint();
      		                    	 hold=true;
       		                    	 break;
       		                     case 4:
       		                    	 corner = true;
       		                    	 System.out.println("4");
       		                    	selectedShape1.setco(deltaX,deltaY,false,dy,dw,dh);
      		                    	 rectPanel.repaint();
      		                    	 hold=true;
       		                    	 break;
       		                     case 5:
       		                    	 corner = true;
       		                    	 System.out.println("5");
       		                    	selectedShape1.setco(deltaX,deltaY,dx,false,false,dh);
      		                    	 rectPanel.repaint();
      		                    	 hold=true;
       		                    	 break;
       		                     case 6:
       		                    	 corner = true;
       		                    	 System.out.println("6");
       		                    	selectedShape1.setco(deltaX,deltaY,false,false,dw,dh);
      		                    	 rectPanel.repaint();
      		                    	 hold=true;
       		                    	 break;
       		                     case 7:
       		                    	 corner = true;
       		                    	 System.out.println("7");
       		                    	selectedShape1.setco(deltaX,deltaY,false,dy,dw,dh);
      		                    	 rectPanel.repaint();
      		                    	 hold=true;
       		                    	 break;
       		                     case 8:
       		                    	 corner = true;
       		                    	 System.out.println("8");
       		                    	selectedShape1.setco(deltaX,deltaY,false,false,dw,false);
      		                    	 rectPanel.repaint();
      		                    	 hold=true;
       		                    	 break;
       		                     }
       	                	}
       	                	
       	                }
                	}
            }
        });
                
                
            
 
// ================================= Mouse Dragged =================================

// ================================= Mouse Relaese =================================

        rectPanel.addMouseListener(new MouseAdapter() {
        	 @Override
             public void mouseReleased(MouseEvent e) {
                 if (btnMove) {
                     selectedShape1 = null;
                     startX = -1;
                     startY = -1;
                 }
                 
                 else if (btnCreatingRectangle) {
                     if (startX != -1 && startY != -1) {
                         endX = e.getX();
                         endY = e.getY();
                         btnCreatingRectangle = false;
                          
                        // Ajouter le rectangle à la liste et redessiner le panneau
                         Shap shap = new Shap();
                         shap.addRectangle(new Rectangle(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY)));
                         shaps.add(shap);
                             
                         rectPanel.repaint();
                         System.err.println("Rectangle créé avec les coordonnées : (" + startX + ", " + startY + ") et (" + endX + ", " + endY + ")");
                         startX = -1;
                         startY = -1;
                         endX = -1;
                         endY = -1;
                     }
                 }
                 else if (btnInfo) {
                	 if(hold==true) {
	                	 selectedShape1.removeRectangle();
	                	 selectedShape1=null;
	                	 rectPanel.repaint();
	                	 hold=false;
	                	 oldshape=null;
                	 }
                 }
                 
                 serializeShapes("shapes.ser");
             }
        });
// ================================= Mouse Relaese =================================

// ================================= Mouse Pressed =================================
        rectPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (btnMove && SwingUtilities.isLeftMouseButton(e)) {
                	System.err.println("Moving");
                    if (selectedShape1 == null) {
                        for (int i = shaps.size() - 1; i >= 0; i--) {
                            Shap shape = shaps.get(i);
                            if (shape.isTouch(e.getX(), e.getY())) {
                                selectedShape1 = shape;
                                
                                // Stockage de la position initiale de la souris par rapport au coin supérieur gauche de la forme
                                startX = e.getX();
                                startY = e.getY();
                                
                                System.err.println("Selected id= " + shape.getId());
                                break;
                            }
                        }
                    }
                }
                
                if (btnCreatingRectangle && SwingUtilities.isLeftMouseButton(e)) {
                    startX = e.getX();
                    startY = e.getY();
                }
                if (btnInfo && SwingUtilities.isLeftMouseButton(e)) {
                    startX = e.getX();
                    startY = e.getY();
                }
            }
        });
    }
// ================================= Mouse Pressed =================================

// ================================= Click Mouse =====================================
    MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
        	
        	 
            
// ---------------------------- function create cercle ----------------------------
            /* FOR CERLCE
            if (btnCreatingCercle) {
            	
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (startX == -1 && startY == -1) {
                        startX = e.getX();
                        startY = e.getY();
                    } else {
                        endX = e.getX();
                        endY = e.getY();
                        btnCreatingCercle = false;
                        
                        //ajoute un rectangle a la liste.
                        Shap shap = new Shap();
                        int rayon = (int) Math.sqrt(Math.pow(Math.abs(endX-startX), 2) + Math.pow(Math.abs(endY-startY), 2));
                        shap.addCercle(new Cercle(startX, startY, rayon));
                        shaps.add(shap);
                        
                        rectPanel.removeMouseListener(this);
                        rectPanel.repaint();
                        serializeShapes("shapes.ser");
                        System.err.println("Cercle créé avec les coordonnées : (" + startX + ", " + startY + ") et de rayon :" + (int) Math.sqrt(Math.pow(Math.abs(endX-startX), 2) + Math.pow(Math.abs(endY-startY), 2)));
                        startX=-1; startY=-1;
                    }
                }
            } 
            */      
// ---------------------------- function create cercle ----------------------------
            
// --------------------------------- function Union ----------------------------------
            if (btnUnion) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                	
                	// 1) select 2 shaps
                    selectShap(e.getX(), e.getY(), true);
                	
                    // 2) do an inter
                    if (selectedShape1 != null && selectedShape2 != null) {
                        	
                	   Shap unionResult = union(selectedShape1, selectedShape2);
                	   
                	   shaps.remove(selectedShape1);
                       shaps.remove(selectedShape2);
                       shaps.add(unionResult);
                       
                       System.err.println("New shap id= " + unionResult.getId());
                       selectedShape1 = null;
                       selectedShape2 = null;
                       rectPanel.repaint();
                       serializeShapes("shapes.ser");
                   }
                }
            }
// --------------------------------- function Union ----------------------------------
            
// --------------------------------- function Inter ----------------------------------
            else if (btnInter) {
            	if (SwingUtilities.isLeftMouseButton(e)) {
            		
            		// 1) select 2 shaps
                    selectShap(e.getX(), e.getY(), true);
                	
                    // 2) do an inter
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
	                 
	                 for (int i = shaps.size() - 1; i >= 0; i--) {
	                     Shap shape = shaps.get(i);
	                     if (shape.isTouch(e.getX(), e.getY())) {
	                         selectedShape1 = shape;
	                         System.err.println("Resize");
	                         System.err.println("Selected id= " + shape.getId());
	                         for (Rectangle rect : shape.getRectangles()) {
	                             System.err.println("X1 = " + rect.getX() + " Y1 = " + rect.getY() + " X2 = " + (rect.getX()+rect.getWidth()) + " Y2 = " + (rect.getY()+rect.getHeight()) );
	                         }
	                         //rectPanel.repaint();
	                         //serializeShapes("shapes.ser");
	                         System.err.println("test :");
	                         break;
	                     }
	                 }
	             }
	         }
//--------------------------------- function Resize ----------------------------------
            
//--------------------------------- function info ----------------------------------
	         else if (btnInfo) {
	        	 List<Object> maListe = new ArrayList<>();
	        	 if (btnInfo && selectedShape1 != null) {
   	        		 if (SwingUtilities.isLeftMouseButton(e)) {
   	        			
	   	                	//System.out.println("test corner activé");
	   	                	
	   	                	for (int i = shaps.size() - 1; i >= 0; i--) {
	   		                     Shap shape = shaps.get(i);
	   		                     switch(shape.isTouchInfoCorner(e.getX(), e.getY())) {
	   		                     case 1:
	   		                    	 corner = true;
	   		                    	 System.out.println("1");
	   		                    	 break;
	   		                     case 2:
	   		                    	 corner = true;
	   		                    	 System.out.println("2");
	   		                    	 break;
	   		                     case 3:
	   		                    	 corner = true;
	   		                    	 System.out.println("3");
	   		                    	 break;
	   		                     case 4:
	   		                    	 corner = true;
	   		                    	 System.out.println("4");
	   		                    	 break;
	   		                     case 5:
	   		                    	 corner = true;
	   		                    	 System.out.println("5");
	   		                    	 break;
	   		                     case 6:
	   		                    	 corner = true;
	   		                    	 System.out.println("6");
	   		                    	 break;
	   		                     case 7:
	   		                    	 corner = true;
	   		                    	 System.out.println("7");
	   		                    	 break;
	   		                     case 8:
	   		                    	 corner = true;
	   		                    	 System.out.println("8");
	   		                    	 break;
	   		                     }
	   	                	}
	   	                	
   	        			}
   	        		 
	        	 }
	        	 
	             if (SwingUtilities.isLeftMouseButton(e)) {
	            	 int compteur=0;
	            	 System.out.println("oldshape avant avant"+oldshape);
	            	 if(oldshape==null) {
	            		 System.out.println("oldshape avant "+oldshape);
	            		 
		            	 for (int i = shaps.size() - 1; i >= 0; i--) {
		            		 Shap shape = shaps.get(i);
		            		 
		            		 if (shape.isTouch(e.getX(), e.getY())) {
		            			 if(shape!=null) {
			            			 ajouterTexte(shape.toString());
			            		 }
		            			 selectedShape1 = shape;
		            			 //System.err.println(shape);
		            			 maListe.add(selectedShape1);
		            			 
		            			 oldshape= shape;
		            			 
		            			 Graphics g = rectPanel.getGraphics();
		                         shape.selectdraw(g,true,rectPanel);

		            		 }
		            	 }
		            	 System.out.println("oldshape après "+oldshape);
	            	 }
	            	 
	            	 if((maListe.size())==0) {
	            		 if(corner==false) {
	            			 if(selectedShape1!=null) {
		            			 selectedShape1.removeRectangle();
			            		 ajouterTexte("Rien");
			            		 System.out.println("Aucune forme detecté");
			            		 System.out.println(oldshape);
			            		 rectPanel.revalidate();
		            			 rectPanel.repaint();
		            			 oldshape=null;
		            			 corner=false;
		            			 selectedShape1=null;
	            			 }
	            		 }else {
	            			 corner=false;
	            		 }
	            	 }
	             }
	         }
	         
//--------------------------------- function info ----------------------------------
        }
    };
// ================================= Click Mouse =====================================
    
    public void resetBool() {
    	this.btnCreatingRectangle = false;
    	//this.btnCreatingCercle = false;
    	this.btnInter = false;
    	this.btnUnion = false;
    	this.btnMove = false;
    	this.btnResize = false;
    	this.startX = -1;
    	this.startY = -1;
    	this.endX = -1;
    	this.endY = -1;
    	this.selectedShape1=null;
    	this.selectedShape2=null;
    	this.old=null;
    }
    
    public void serializeShapes(String filename) {
    	System.err.println("start save");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(shaps);
            oos.close();
            System.err.println("save done !!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Shap union(Shap shape1, Shap shape2) {
    	
        Shap unionResult = new Shap();
        
    	for (Rectangle rect : shape1.getRectangles()) {
            unionResult.addRectangle(rect);
        }
    	/*for (Cercle cerc : shape1.getCercles()) { FOR CERLCE
    		unionResult.addCercle(cerc);
    	}
		*/
        for (Rectangle rect : shape2.getRectangles()) {
            unionResult.addRectangle(rect);
        }
        /*
        for (Cercle cerc : shape2.getCercles()) { FOR CERLCE
    		unionResult.addCercle(cerc);
    	}
    	*/
        return unionResult;
    }
    
    public Shap inter(Shap shape1, Shap shape2) {
        Shap interResult = new Shap();
        
        for (Rectangle rect1 : shape1.getRectangles()) {
            for (Rectangle rect2 : shape2.getRectangles()) {
                Rectangle intersect = rect1.intersection(rect2);
                if (intersect.getWidth() > 0 && intersect.getHeight() > 0) {
                    interResult.addRectangle(intersect);
                }
            }
        }
        
        return interResult;
    }
    
    public void selectShap(int Xpos, int Ypos, boolean isTwoSelection) {
    	if (selectedShape1 == null) {
    		for (int i = shaps.size() - 1; i >= 0; i--) {
                Shap shape = shaps.get(i);
                if (shape.isTouch(Xpos, Ypos)) {
                    selectedShape1 = shape;
                    System.err.println("Selected id= " + shape.getId());
                    break;
                }
            }
        } 
    	
    	// 2) select shap 2
    	if (isTwoSelection) {
    		 for (int i = shaps.size() - 1; i >= 0; i--) {
                 Shap shape = shaps.get(i);
                 if (shape.isTouch(Xpos, Ypos) && !shape.equals(selectedShape1)) {
                     selectedShape2 = shape;
                     System.err.println("Selected id= " + shape.getId());
                     break;
                 }
             }
    	}
    }
    
    public static void ajouterTexte(String texte) {
        if (info != null) {
            JTextArea textArea = null;
            for (Component component : info.getContentPane().getComponents()) {
                if (component instanceof JTextArea) {
                    textArea = (JTextArea) component;
                    break;
                }
            }

            if (textArea == null) {
                textArea = new JTextArea();
                info.add(textArea);
            }
            
            if (IndicePanel==10) {
            	IndicePanel=0;
            	info.getContentPane().removeAll();
                info.revalidate();
                info.repaint();
            }else{
            	IndicePanel=IndicePanel+1;
	            textArea.append(texte + "\n");

            }
        }
    }
   
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
}