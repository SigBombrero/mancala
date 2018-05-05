import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Model {

	private int[] smallPits;
	private int[] largePits;
	private int numOfStones;
	private ArrayList<ChangeListener> views;
	/*int undoCounter;
	boolean undoAllowed;
	boolean freeTurn;
	int [] data;
	static int Player = 1;
	int previousPlayer = 2;
	*/
	
	public Model(){
		smallPits = new int[12];
		largePits = new int[2];
		largePits[0] = 0;
		largePits[1] = 0;
		views = new ArrayList<ChangeListener>();
		//freeTurn = false;
		//undoAllowed = false;
		//undoCounter = 3;
	}
	
	public int getNumOfStones(){
		return numOfStones;
	}
	
	public void setNumOfStones(int n){
		numOfStones = n;
		for(int i=0; i<12; i++){
			smallPits[i] = numOfStones;
		}
	}
	
	/*
	public void undo(){
		if(!undoAllowd){
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "You are not allowed to undo!");
		}
		undo = true;
		undoCounter--;
		if(freeTurn){
			Player++;
			Player = ((player % 2) ! = 0) ? 1 : 0;
			freeTurn = false;
		}
		if(undoCounter ! = 0){
			
		}
		undoAllowed = false;
		updateModel();
		
	}*/
	
	public int[] getSmallPits(){
		return smallPits;
	}
	
	public int[] getLargePits(){
		return largePits;
	}
	
	/*
	public int getUndoCounter(){
		return undoCounter;
	}
	
	public int [] getData(){
		return data;
	}
	*/
	
	//i is the small pit user clicked
	public void updateModel(int i){
		int startPit = i;
		int count = smallPits[i];
		smallPits[i] = 0;
		while(count >  0){
			//top row
			if(i<=5){
				if(i==0 && startPit<6){
					largePits[0]++;
					i=6;
					count--;
				}
				else if(i==0 && startPit>=6){
					i=6;
					smallPits[i]++;
				}
				else{
					i--;
					smallPits[i]++;
				}
			}
			else{
				if(i==11 && startPit >= 6){
					largePits[1]++;
					i=5;
					count--;
				}
				else if(i==11 && startPit < 6){
					i=5;
					smallPits[i]++;
				}
				else{
					i++;
					smallPits[i]++;
				}
			}
			if(count ==1 && smallPits[i]==0){
				JDialog freeTurn = new JDialog();
				freeTurn.setTitle("Free Turn.");
				freeTurn.setModal(true);
				freeTurn.setSize(200, 50);
				freeTurn.setResizable(false);
				freeTurn.setLayout(new FlowLayout());
				freeTurn.add(new JLabel("You ended in an empty Mancala."));
				freeTurn.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				freeTurn.setVisible(true);
			}
			count--;
		}
		for(ChangeListener l: views){
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	public void attachView(ChangeListener c){
		views.add(c);
	}
	
	public ArrayList<ChangeListener> getViews(){
		return views;
	}
}
