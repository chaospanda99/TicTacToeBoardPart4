import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener
{
    // setting up ALL the variables
    JFrame window = new JFrame("Kenneth's Tic Tac Toe Game");

    JMenuBar mnuMain = new JMenuBar();
    JMenuItem   mnuNewGame = new JMenuItem("  New Game"), 
    mnuGameTitle = new JMenuItem("|Tic Tac Toe|  "),
    mnuStartingPlayer = new JMenuItem(" Starting Player"),
    mnuExit = new JMenuItem("    Quit");

    JButton btnEmpty[] = new JButton[1000];

    JPanel  pnlNewGame = new JPanel(),
    pnlNorth = new JPanel(),
    pnlSouth = new JPanel(),
    pnlTop = new JPanel(),
    pnlBottom = new JPanel(),
    pnlPlayingField = new JPanel();
    JPanel radioPanel = new JPanel();

    private JRadioButton SelectX = new JRadioButton("User Plays Hioh", false);
    private  JRadioButton SelectO = new JRadioButton("User Plays Pioh", false);
    private ButtonGroup radioGroup;
    private  String startingPlayer= "";
    final int X = 800, Y = 480, color = 190; // size of the game window
    private boolean inGame = false;
    private boolean win = false;
    private boolean btnEmptyClicked = false;
    private boolean setTableEnabled = false;
    private String message;
    private Font font = new Font("Rufscript", Font.BOLD, 100);
    private int remainingMoves = 1;
    private int boardsize=3;

    //===============================  GUI  ========================================//
    public GUI() //This is the constructor
    {
        //Setting window properties:
        boardsize = Integer.parseInt(
           JOptionPane.showInputDialog("Please enter Board size"));
        
        
        window.setSize(X, Y);
        window.setLocation(300, 180);
        window.setResizable(true);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

        //------------  Sets up Panels and text fields  ------------------------//
        // setting Panel layouts and properties
        pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER));

        pnlNorth.setBackground(new Color(70, 70, 70));
        pnlSouth.setBackground(new Color(color, color, color));

        pnlTop.setBackground(new Color(color, color, color));
        pnlBottom.setBackground(new Color(color, color, color));

        pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));

        radioPanel.setBackground(new Color(color, color, color));
        pnlBottom.setBackground(new Color(color, color, color));
        radioPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Who Goes First?"));

        // adding menu items to menu bar
        mnuMain.add(mnuGameTitle);
        mnuGameTitle.setEnabled(false);
        mnuGameTitle.setFont(new Font("Purisa",Font.BOLD,18));
        mnuMain.add(mnuNewGame);
        mnuNewGame.setFont(new Font("Purisa",Font.BOLD,18));
        mnuMain.add(mnuStartingPlayer);
        mnuStartingPlayer.setFont(new Font("Purisa",Font.BOLD,18));
        mnuMain.add(mnuExit);
        mnuExit.setFont(new Font("Purisa",Font.BOLD,18));//---->Menu Bar Complete

        // adding X & O options to menu
        SelectX.setFont(new Font("Purisa",Font.BOLD,18));
        SelectO.setFont(new Font("Purisa",Font.BOLD,18));
        radioGroup = new ButtonGroup(); // create ButtonGroup
        radioGroup.add(SelectX); // add plain to group
        radioGroup.add(SelectO);
        radioPanel.add(SelectX);
        radioPanel.add(SelectO);

        // adding Action Listener to all the Buttons and Menu Items
        mnuNewGame.addActionListener(this);
        mnuExit.addActionListener(this);
        mnuStartingPlayer.addActionListener(this);

        // setting up the playing field
        pnlPlayingField.setLayout(new GridLayout(boardsize, boardsize, 2, 2));
        pnlPlayingField.setBackground(Color.blue);
        for(int x=1; x <= boardsize*boardsize; ++x)   
        {
            btnEmpty[x] = new JButton();
            btnEmpty[x].setBackground(new Color(220, 220, 220));
            btnEmpty[x].addActionListener(this);
            pnlPlayingField.add(btnEmpty[x]);
            btnEmpty[x].setEnabled(setTableEnabled);
        }

        // adding everything needed to pnlNorth and pnlSouth
        pnlNorth.add(mnuMain);
        BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);

        // adding to window and Showing window
        window.add(pnlNorth, BorderLayout.NORTH);
        window.add(pnlSouth, BorderLayout.CENTER);
        window.setVisible(true);
    }// End GUI

    // ===========  Start Action Performed  ===============//
    public void actionPerformed(ActionEvent click)  
    {
        // get the mouse click from the user
        Object source = click.getSource();

        // check if a button was clicked on the gameboard
        for(int currentMove=1; currentMove <= boardsize*boardsize; ++currentMove) 
        {
            if(source == btnEmpty[currentMove] && remainingMoves < boardsize*boardsize+1)  
            {
                btnEmptyClicked = true;
                BusinessLogic.GetMove(currentMove, remainingMoves, font, 
                    btnEmpty, startingPlayer);              
                btnEmpty[currentMove].setEnabled(false);
                pnlPlayingField.requestFocus();
                ++remainingMoves;
            }
        }

        // if a button was clicked on the gameboard, check for a winner
        if(btnEmptyClicked) 
        {
            inGame = true;
            CheckWin();
            btnEmptyClicked = false;
        }

        // check if the user clicks on a menu item
        if(source == mnuNewGame)    
        {
            System.out.println(startingPlayer);
            BusinessLogic.ClearPanelSouth(pnlSouth,pnlTop,pnlNewGame,
                pnlPlayingField,pnlBottom,radioPanel);
            if(startingPlayer.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please Select a Starting Player", 
                    "Oops..", JOptionPane.ERROR_MESSAGE);
                BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
            }
            else
            {
                if(inGame)  
                {
                    int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                            " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                        , "New Game?" ,JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.YES_OPTION)    
                    {
                        inGame = false;
                        startingPlayer = "";
                        setTableEnabled = false;
                    }
                    else
                    {
                        BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                    }
                }
                // redraw the gameboard to its initial state
                if(!inGame) 
                {
                    RedrawGameBoard();
                }
            }       
        }       
        // exit button
        else if(source == mnuExit)  
        {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", 
                    "Quit" ,JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
        // select X or O player 
        else if(source == mnuStartingPlayer)  
        {
            if(inGame)  
            {
                JOptionPane.showMessageDialog(null, "Cannot select a new Starting "+
                    "Player at this time.nFinish the current game, or select a New Game "+
                    "to continue", "Game In Session..", JOptionPane.INFORMATION_MESSAGE);
                BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
            }
            else
            {
                setTableEnabled = true;
                BusinessLogic.ClearPanelSouth(pnlSouth,pnlTop,pnlNewGame,
                    pnlPlayingField,pnlBottom,radioPanel);

                SelectX.addActionListener(new RadioListener());
                SelectO.addActionListener(new RadioListener());
                radioPanel.setLayout(new GridLayout(2,1));

                radioPanel.add(SelectX);
                radioPanel.add(SelectO);
                pnlSouth.setLayout(new GridLayout(2, 1, 2, 1));
                pnlSouth.add(radioPanel);
                pnlSouth.add(pnlBottom);
            }
        }
        pnlSouth.setVisible(false); 
        pnlSouth.setVisible(true);  
    }// End Action Performed

    // ===========  Start RadioListener  ===============//  
    private class RadioListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event) 
        {
            JRadioButton theButton = (JRadioButton)event.getSource();
            if(theButton.getText().equals("User Plays Hioh")) 
            {
                startingPlayer = "Hioh";
            }
            if(theButton.getText().equals("User Plays Pioh"))
            {
                startingPlayer = "Pioh";
            }

            // redisplay the gameboard to the screen
            pnlSouth.setVisible(false); 
            pnlSouth.setVisible(true);          
            RedrawGameBoard();
        }
    }// End RadioListener
    /*
    ----------------------------------
    Start of all the other methods. |
    ----------------------------------
     */
    private void RedrawGameBoard()  
    {
        BusinessLogic.ClearPanelSouth(pnlSouth,pnlTop,pnlNewGame,
            pnlPlayingField,pnlBottom,radioPanel);
        BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);       

        remainingMoves = 1;

        for(int x=1; x <= boardsize*boardsize; ++x)   
        {
            btnEmpty[x].setText("");
            btnEmpty[x].setEnabled(setTableEnabled);
        }

        win = false;        
    }

    private void CheckWin() 
    {   CheckHorizontal1();
        CheckHorizontal2();
        CheckVertical1();
        CheckVertical2();
        CheckDiagonal1();
        CheckDiagonals();
        CheckDiagonal2();
        CheckDiagonals2();
    }   

    private void CheckDiagonal1(){

        int count=0;

        for (int j=4; j<=8; j+=4){
            if (btnEmpty[1].getText().equals("Hioh") && 
            btnEmpty[1+j].getText().equals("Hioh")){
                count++;
            }

        }

        if (count==2){
            JOptionPane.showMessageDialog(null, "Hioh Wins");
            if(inGame)  
            {
                int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                        " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                    , "New Game?" ,JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION)    
                {
                    inGame = false;
                    startingPlayer = "";
                    setTableEnabled = false;
                }
                else
                {
                    BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                }
                 if(!inGame) 
                {
                    RedrawGameBoard();
                }
            }
        }

        else{
            count=0;
        }

    }

    private void CheckDiagonals(){

        int count=0;

        for (int j=2; j<=4; j+=2){
            if (btnEmpty[3].getText().equals("Hioh") && 
            btnEmpty[3+j].getText().equals("Hioh")){
                count++;
            }

        }

        if (count==2){
            JOptionPane.showMessageDialog(null, "Hioh Wins");
            if(inGame)  
            {
                int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                        " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                    , "New Game?" ,JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION)    
                {
                    inGame = false;
                    startingPlayer = "";
                    setTableEnabled = false;
                }
                else
                {
                    BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                }
                 if(!inGame) 
                {
                    RedrawGameBoard();
                }
            }
        }

        else{
            count=0;
        }

    }

    private void CheckDiagonals2(){

        int count=0;

        for (int j=2; j<=4; j+=2){
            if (btnEmpty[3].getText().equals("Pioh") && 
            btnEmpty[3+j].getText().equals("Pioh")){
                count++;
            }

        }

        if (count==2){
            JOptionPane.showMessageDialog(null, "Pioh Wins");
            if(inGame)  
            {
                int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                        " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                    , "New Game?" ,JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION)    
                {
                    inGame = false;
                    startingPlayer = "";
                    setTableEnabled = false;
                }
                else
                {
                    BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                }
                 if(!inGame) 
                {
                    RedrawGameBoard();
                }
            }
        }

        else{
            count=0;
        }

    }

    private void CheckHorizontal1(){

        int count=0;
        for (int x=1; x<=boardsize*boardsize; x++){
            if (x%boardsize==1){
                for (int j=1; j<boardsize;j++){
                    if (btnEmpty[x].getText().equals("Hioh") && 
                    btnEmpty[x+j].getText().equals("Hioh")){
                        count++;
                    }

                }
                
            }

        }
        if (count==boardsize-1){
                    JOptionPane.showMessageDialog(null, "Hioh Wins");
                    if(inGame)  
                    {
                        int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                                " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                            , "New Game?" ,JOptionPane.YES_NO_OPTION);
                        if(option == JOptionPane.YES_OPTION)    
                        {
                            inGame = false;
                            startingPlayer = "";
                            setTableEnabled = false;
                        }
                        else
                        {
                            BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                        }
                         if(!inGame) 
                {
                    RedrawGameBoard();
                }
                    }
                }

                else{
                    count=0;
                }


    }

    private void CheckHorizontal2(){

        int count=0;
         for (int x=1; x<=boardsize*boardsize; x++){
            if (x%boardsize==1){
                for (int j=1; j<boardsize;j++){
                    if (btnEmpty[x].getText().equals("Pioh") && 
                    btnEmpty[x+j].getText().equals("Pioh")){
                        count++;
                    }

                }
                
            }

        }
        if (count==boardsize-1){
                    JOptionPane.showMessageDialog(null, "Pioh Wins");
                    if(inGame)  
                    {
                        int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                                " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                            , "New Game?" ,JOptionPane.YES_NO_OPTION);
                        if(option == JOptionPane.YES_OPTION)    
                        {
                            inGame = false;
                            startingPlayer = "";
                            setTableEnabled = false;
                        }
                        else
                        {
                            BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                        }
                         if(!inGame) 
                {
                    RedrawGameBoard();
                }
                    }
                }

                else{
                    count=0;
                }

    }

    private void CheckVertical2(){

        int count=0;
        for (int x=1; x<=3; x++){

            for (int j=3; j<x+6;j+=3){
                if (btnEmpty[x].getText().equals("Pioh") && 
                btnEmpty[x+j].getText().equals("Pioh")){
                    count++;
                }

            }

        }
        if (count==2){
            JOptionPane.showMessageDialog(null, "Pioh Wins");
            if(inGame)  
            {
                int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                        " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                    , "New Game?" ,JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION)    
                {
                    inGame = false;
                    startingPlayer = "";
                    setTableEnabled = false;
                }
                else
                {
                    BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                }
                 if(!inGame) 
                {
                    RedrawGameBoard();
                }
            }
        }

        else{
            count=0;
        }

    }

    private void CheckVertical1(){

        int count=0;
        for (int x=1; x<=boardsize; x++){

            for (int j=boardsize; j<x+boardsize*3; j+=boardsize){
                if (btnEmpty[x].getText().equals("Hioh") && 
                btnEmpty[x+j].getText().equals("Hioh")){
                    count++;
                }

            }

        }
        if (count==boardsize-1){
            System.out.println("Hioh Wins");
            if(inGame)  
            {
                int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                        " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                    , "New Game?" ,JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION)    
                {
                    inGame = false;
                    startingPlayer = "";
                    setTableEnabled = false;
                }
                else
                {
                    BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                }
                 if(!inGame) 
                {
                    RedrawGameBoard();
                }
            }
        }

        else{
            count=0;
        }
    }

    private void CheckDiagonal2(){
        int count=0;
        for (int j=4; j<=8; j+=4){
            if (btnEmpty[1].getText().equals("Pioh") && 
            btnEmpty[1+j].getText().equals("Pioh")){
                count++;
            }

        }

        if (count==2){
            JOptionPane.showMessageDialog(null, "Pioh Wins");
            if(inGame)  
            {
                int option = JOptionPane.showConfirmDialog(null, "If you start a new game," +
                        " your current game will be lost..." + "n" +"Are you sure you want to continue?"
                    , "New Game?" ,JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION)    
                {
                    inGame = false;
                    startingPlayer = "";
                    setTableEnabled = false;
                }
                else
                {
                    BusinessLogic.ShowGame(pnlSouth,pnlPlayingField);
                }
                 if(!inGame) 
                {
                    RedrawGameBoard();
                }
            }
        }

        else{
            count=0;
        }

    }

}