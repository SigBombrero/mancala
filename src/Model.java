import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Holds all of the board data
 * @author sukhvir singh & edrees osman
 *
 */
public class Model {

    private int[] smallPits;
    private int[] largePits;
    private int numOfStones;
    private ArrayList<ChangeListener> views;
    private boolean gameEnd;
    private boolean player1won;
    private boolean player2won;
	/*int undoCounter;
	boolean undoAllowed;
	int [] data;
	static int Player = 1;
	int previousPlayer = 2;
	*/

    /**
     * Constructs a new model with 12 small pits and 2 large pits
     */
    public Model(){
        smallPits = new int[12];
        largePits = new int[2];
        largePits[0] = 0;
        largePits[1] = 0;
        views = new ArrayList<ChangeListener>();
        gameEnd = false;
        player1won = false;
        player2won = false;
        //undoAllowed = false;
        //undoCounter = 3;
    }

    /**
     * To get number of stones the user selected
     * @return
     */
    public int getNumOfStones(){
        return numOfStones;
    }

    /**
     * To set number of stones in each pit
     * @param n
     */
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

    /**
     * To get all the small pits
     * @return
     */
    public int[] getSmallPits(){
        return smallPits;
    }

    /**
     * To get all the large pits
     * @return
     */
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

    /**
     * Updates the model
     * @param i the small pit the user clicked on
     */
    public void updateModel(int i){
        int startPit = i;
        int count = smallPits[i];
        smallPits[i] = 0;
        if(!gameEnd){
            while(count >  0){
                //top row
                if(i<=5){
                    //if user reaches his own mancala on his turn, add a stone to his mancala
                    if(i==0 && startPit<6){
                        largePits[0]++;
                        i=6;
                        count--;

                        // if last stone is in your Mancala, take a free turn
                        if(count == 0) {
                            Board.setPlayer2turn();
                        }
                        if(count > 0) {
                            smallPits[i]++;
                            count--;
                        }
                    }
                    else if(i==0 && startPit>=6){
                        i=6;
                        smallPits[i]++;
                    }
                    else{
                        i--;
                        // if last stone is put into an empty pit on your side,
                        // capture stone as well as opponent's stones in opposite pit
                        if(count == 1 && smallPits[i] == 0) {
                            int capture = smallPits[i+6] + 1;
                            largePits[0] += capture;
                            smallPits[i+6] = 0;
                        } else {
                            smallPits[i]++;
                        }
                    }

                }
                //bottom row
                else{
                    //if user reaches his own mancala on his turn, add a stone to his mancala
                    if(i==11 && startPit >= 6){
                        largePits[1]++;
                        i=5;
                        count--;

                        // if last stone is in your Mancala, take a free turn
                        if(count == 0) {
                            Board.setPlayer2turn();
                        }
                        if(count > 0) {
                            smallPits[i]++;
                            count--;
                        }
                    }
                    else if(i==11 && startPit < 6){
                        i=5;
                        smallPits[i]++;
                    }
                    else{
                        i++;
                        // if last stone is put into an empty pit on your side,
                        // capture stone as well as opponent's stones in opposite pit
                        if(count == 1 && smallPits[i] == 0) {
                            int capture = smallPits[i-6] + 1;
                            largePits[1] += capture;
                            smallPits[i-6] = 0;
                        } else {
                            smallPits[i]++;
                        }

                    }

                }
                count--;

                //check if game has ended
                if(smallPits[0] == 0 && smallPits[1] == 0 && smallPits[2] == 0 && smallPits[3] == 0 && smallPits[4] == 0 && smallPits[5] == 0){
                    gameEnd = true;
                    //add the leftover stones in player 1's small pits to his mancala
                    for(int j=6; j<12; j++){
                        largePits[1] += smallPits[j];
                    }
                }
                if(smallPits[6] == 0 && smallPits[7] == 0 && smallPits[8] == 0 && smallPits[9] == 0 && smallPits[10] == 0 && smallPits[11] == 0){
                    gameEnd = true;
                    //add the leftover stones in player 2's small pits to his mancala
                    for(int j=0; j<6; j++){
                        largePits[0] += smallPits[j];
                    }
                }
            }
            //notify all views of state change
            for(ChangeListener l: views){
                l.stateChanged(new ChangeEvent(this));
            }

            if(gameEnd){
                if(largePits[0] > largePits[1]){
                    JDialog player2won = new JDialog();
                    player2won.setTitle("Winner");
                    player2won.setModal(true);
                    player2won.setSize(200, 50);
                    player2won.setResizable(false);
                    player2won.setLayout(new FlowLayout());
                    player2won.add(new JLabel("Player 2 has won."));
                    player2won.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    player2won.setVisible(true);
                }
                if(largePits[0] < largePits[1]){
                    JDialog player1won = new JDialog();
                    player1won.setTitle("Winner");
                    player1won.setModal(true);
                    player1won.setSize(200, 50);
                    player1won.setResizable(false);
                    player1won.setLayout(new FlowLayout());
                    player1won.add(new JLabel("Player 1 has won."));
                    player1won.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    player1won.setVisible(true);
                }
            }
        }
    }

    /**
     * Attach a view to this model
     * @param c
     */
    public void attachView(ChangeListener c){
        views.add(c);
    }

    /**
     * Get all views attached to this model
     * @return
     */
    public ArrayList<ChangeListener> getViews(){
        return views;
    }
}