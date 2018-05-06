import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The mancala board itself
 * @author sukhvirsingh
 *
 */
public class Board extends JFrame implements ChangeListener{

	private Model model;
	private ArrayList<Pit> smallPits;
	private ArrayList<Pit> largePits;
	private Color color;
	private boolean player2turn;
	private boolean stonesNumSelected;
	private boolean colorSelected;
	
	/**
	 * Constructs a new board
	 * @param m model to use
	 */
	protected Board(Model m){
		model = m;
		smallPits = new ArrayList<Pit>(12);
		largePits = new ArrayList<Pit>(2);
		player2turn = false;
		
		stonesNumSelected = false;
		colorSelected = false;
		
		makeStartScreen();
		drawBoard();
		
		//Mouse listener to detect which pit user clicks on
		MouseListener listener = new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				Point2D point = new Point2D.Double(e.getX(), e.getY());
				int i = 0;
				if(point.getX()>100 && point.getX()<900){
					//if player picked one of other player's pits
					if((!player2turn && point.getY() <= 175) || (player2turn && point.getY() > 175)){
						JDialog wrongTurn = new JDialog();
						wrongTurn.setTitle("Wrong row.");
						wrongTurn.setModal(true);
						wrongTurn.setSize(200, 50);
						wrongTurn.setResizable(false);
						wrongTurn.setLayout(new FlowLayout());
						wrongTurn.add(new JLabel("Not Your Turn."));
						wrongTurn.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						wrongTurn.setVisible(true);
						
					}
					else{
						//top half of small pits
						if(point.getY() <= 175){
							if(point.getX()>100 && point.getX()<233){
								i = 0;
							}
							else if(point.getX()>233 && point.getX()<366){
								i = 1;
							}
							else if(point.getX()>366 && point.getX()<499){
								i = 2;
							}
							else if(point.getX()>499 && point.getX()<632){
								i = 3;
							}
							else if(point.getX()>632 && point.getX()<765){
								i = 4;
							}
							else if(point.getX()>765 && point.getX()<898){
								i = 5;
							}
							model.updateModel(i);
							
							//swtich turns
							if(player2turn){
								player2turn = false;
							}
							else if(!player2turn){
								player2turn = true;
							}
						}
						//bottom half of small pits
						if(point.getY() > 175){
							if(point.getX()>100 && point.getX()<233){
								i = 6;
							}
							else if(point.getX()>233 && point.getX()<366){
								i = 7;
							}
							else if(point.getX()>366 && point.getX()<499){
								i = 8;
							}
							else if(point.getX()>499 && point.getX()<632){
								i = 9;
							}
							else if(point.getX()>632 && point.getX()<765){
								i = 10;
							}
							else if(point.getX()>765 && point.getX()<898){
								i = 11;
							}
							model.updateModel(i);
							
							//switch turns
							if(player2turn){
								player2turn = false;
							}
							else if(!player2turn){
								player2turn = true;
							}
						}
					}
				}
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		};
		this.addMouseListener(listener);	//add listener to this board
	}
	
	/**
	 * Makes the start screen that user selects 
	 *  number of stones and board color from
	 */
	public void makeStartScreen(){
		//start screen
		JDialog startScreen = new JDialog();		//JDialog waits for the user input
		startScreen.setTitle("Mancala");
		startScreen.setModal(true);
		startScreen.setSize(600, 175);
		startScreen.setResizable(false);
		startScreen.setLayout(new FlowLayout());
		
		//ask for stones
		JPanel stonesSelectPanel = new JPanel();
		JLabel selectLabel = new JLabel("Select how many stones you would like:");
		stonesSelectPanel.add(selectLabel);
		//three stones
		JButton threeStonesButton = new JButton("3");
		threeStonesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				model.setNumOfStones(3);
				stonesNumSelected = true;
			}
		});
		//four stones
		JButton fourStonesButton = new JButton("4");
		fourStonesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				model.setNumOfStones(4);
				stonesNumSelected = true;
			}
		});
		stonesSelectPanel.add(threeStonesButton);
		stonesSelectPanel.add(fourStonesButton);
		
		//color panel
		JPanel colorSelectPanel = new JPanel();
		JLabel colorSelectLabel = new JLabel("Select the color of the board:");
		colorSelectPanel.add(colorSelectLabel);	
		JButton redButton = new JButton("Red");
		redButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setColor(Color.RED);
				colorSelected = true;
			}
		});	
		JButton greenButton = new JButton("Green");
		greenButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setColor(Color.GREEN);
				colorSelected = true;
			}
		});	
		JButton blueButton = new JButton("Blue");
		blueButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setColor(Color.BLUE);
				colorSelected = true;
			}
		});
		JButton blackButton = new JButton("Black");
		blackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setColor(Color.BLACK);
				colorSelected = true;
			}
		});
		colorSelectPanel.add(redButton);
		colorSelectPanel.add(greenButton);
		colorSelectPanel.add(blackButton);
		colorSelectPanel.add(blueButton);
		
		JPanel startButtonPanel = new JPanel();
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(colorSelected && stonesNumSelected){
					startScreen.dispose();
				}
			}
		});
		startButtonPanel.add(startButton);
		
		startScreen.add(stonesSelectPanel);
		startScreen.add(colorSelectPanel);
		startScreen.add(startButtonPanel);
		
		startScreen.setVisible(true);
		startScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Draws the board using the model
	 */
	public void drawBoard(){
		
		setSize(1000,350);
		setLayout(new BorderLayout(10, 0));		//border layout with 10 horizontal padding between section
		setResizable(false);
		
		//left pit
		Pit leftPit = new LargePit(model.getLargePits()[0],color);
		largePits.add(leftPit);
		add(leftPit, BorderLayout.WEST);

		//center panel of 12 small pits
		JPanel smallPitsPanel = new JPanel();
		smallPitsPanel.setLayout(new GridLayout(2,6));
		for(int i=0; i<12; i++){
			Pit p = new SmallPit(model.getSmallPits()[i], color);
			smallPits.add(p);		//add pit to array
			smallPitsPanel.add(p);	//add pit to panel
		}
		add(smallPitsPanel, BorderLayout.CENTER);
		
		//right pit
		Pit rightPit = new LargePit(model.getLargePits()[1],color);
		largePits.add(rightPit);
		add(rightPit, BorderLayout.EAST);
		
		/*
		//undoBtn
		JButton undoBtn = new JButton("Undo :" + this.model.getUndoCounter());
		undoBtn.setPreferredSize(new Dimension(80, 35));
	
		undoBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				if(Board.this.model.getUndoCounter() != 0) {
					Board.this.model.undo();
					
						undoBtn.setText("Undo : " + Board.this.model.getUndoCounter());
					}
			}
		
		});
		add(undoBtn, BorderLayout.SOUTH);
		

		JPanel undoPanel = new JPanel();
		undoPanel.add(undoBtn);
		
		add(undoPanel, BorderLayout.SOUTH);
		*/
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Sets the board color to c
	 * @param c new board color
	 */
	public void setColor(Color c){
		color = c;
	}
	
	/**
	 * To get the small pits
	 * @return smallPits
	 */
	public ArrayList<Pit> getSmallPits(){
		return smallPits;
	}
	
	/**
	 * To get the large pits
	 * @return largePits
	 */
	public ArrayList<Pit> getLargePits(){
		return largePits;
	}

	/**
	 * Repaints the board upon a change in the model
	 */
	public void stateChanged(ChangeEvent e) {
		drawBoard();
	}
	
}
