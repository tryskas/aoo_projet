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
    private boolean btnCreatingRectangle, btnUnion, btnInter, btnMove, btnInfo ,hold= false;
    private int startX, startY, endX, endY = -1;
    private int nbrRectCreer,selection=0;
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
       	        			 hold=true;
       	                	//System.out.println("test corner activé");
       	                	
       	                	
							   
							     
							   int deltaX = e.getX() - startX;
							   int deltaY = e.getY() - startY;
							     
							   
							    
							   selection = selectedShape1.isTouchInfoCorner(e.getX(), e.getY(),nbrRectCreer,selection);
							   System.out.println("selectino = " +selection);
							   System.out.println("SS "+selectedShape1.getId()+" "+selectedShape1+"");
							   selectedShape1.setco(selection,e.getX(),e.getY());
							   rectPanel.repaint();
       	                	
       	                	
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
                         nbrRectCreer=nbrRectCreer+1;
                         
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

            
//--------------------------------- function info ----------------------------------
	         else if (btnInfo) {
	        		List<Object> maListe = new ArrayList<>();
	        		if (btnInfo) {
	        			if (SwingUtilities.isLeftMouseButton(e)) {
	        				//System.out.println("test corner activé");
	        				for (int i = shaps.size() - 1; i >= 0; i--) {
	        						Shap shape = shaps.get(i);
	        						System.out.println("ITERATION NUMERO "+i);
	        						System.out.println("nbr rect créer = "+nbrRectCreer);
	        						boolean verification=shape.isTouch(e.getX(), e.getY());
	        						int verificationcorner=shape.isTouchInfoCorner(e.getX(), e.getY(),nbrRectCreer,selection);
	        						//System.out.println("verif = "+verification);
	        						System.out.println("verifcorner = "+verificationcorner);
	        						System.out.println("oldshape : "+oldshape);
	        						System.out.println("selectedshape : "+selectedShape1);
	        						
	        						if(oldshape==null) {
	        							if(selectedShape1==null){
	        								if(verification) {
		        								System.out.println("figure existe");
		        								oldshape= selectedShape1;
		        								selectedShape1 = shape;
		        								ajouterTexte(shape.toString());
		        								Graphics g = rectPanel.getGraphics();
		        								shape.selectdraw(g,true,rectPanel);
		        								break;
		        							}else {
		        								
		        							}
	        							}else {
	        								if(verificationcorner>0) {
		        								System.out.println("corner");
		        							}else {
		        								System.out.println("pas de corner = rien ");
		        								if(verification==true){
		        									resetselectinfo();
		        									break;
		        								}else {
		        									selectedShape1=null;
		        									System.out.println("JE SUIS ALA");
		        									
		        									break;
		        								}
		        							}
	        							}
	        						}else {
	        							if(selectedShape1==oldshape){
	        								selectedShape1=null;
	        								oldshape=null;
	        							}else {
	        								System.out.println("figure existe");
	        								oldshape= null;
	        								selectedShape1 = shape;
	        								ajouterTexte(shape.toString());
	        								Graphics g = rectPanel.getGraphics();
	        								shape.selectdraw(g,true,rectPanel);
	        								break;
	        							}
	        						}	
	        					}
	        				}
	        			}
	        	}
//--------------------------------- function info ----------------------------------
        }
    };
// ================================= Click Mouse =====================================
    public void resetselectinfo() {
		System.out.println("reset");
    	selectedShape1.removeRectangle();
		rectPanel.repaint();
		oldshape=selectedShape1;
		corner=false;
		selectedShape1=null;
		oldshape=null;
    }
    public void resetBool() {
    	this.btnCreatingRectangle = false;
    	//this.btnCreatingCercle = false;
    	this.btnInter = false;
    	this.btnUnion = false;
    	this.btnMove = false;
    	
    	this.startX = -1;
    	this.startY = -1;
    	this.endX = -1;
    	this.endY = -1;
    	
    	this.selectedShape2=null;
    	this.old=null;
    	
    	this.btnInfo = false;
    	this.selectedShape1=null;
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