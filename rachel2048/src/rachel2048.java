/**
 * @(#)rachel2048.java
 *
 * rachel2048 application
 *
 * @author 
 * @version 1.00 2015/2/26
 */
 
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
 import java.io.*;
 
public class rachel2048 implements MouseListener, KeyListener {
    
    public static void main(String[] args) throws Exception {
    	
    	new rachel2048();
    }
    
    
    boolean tracing=false;
   
    JLabel scoreLabel= new JLabel();
    int score=0;
    JFrame frame=new JFrame("2048");
    JFrame rulesFrame=new JFrame("2048: Rules");
    Font numberFont= new Font("Arial", Font.PLAIN, 25);
    Color grey=new Color(235, 230, 222);
    JPanel playPanel=new JPanel();
    JPanel menuPanel= new JPanel();
    JButton backButton= new JButton();
    JPanel panel=new JPanel(new BorderLayout());
    JButton rulesButton= new JButton();
    JButton playButton= new JButton();
    JButton exitButton= new JButton();
    int dim;
    
    JLabel[][] playGrid;
    boolean[][] empty;
    
    rachel2048() 
    {
    	Color greyBrown= new Color(192, 176, 143);
   		JLabel titleLabel= new JLabel();
   		Font arial20= new Font("Arial", Font.PLAIN, 20); 
   		Font arial16=new Font("Arial", Font.PLAIN, 16);	 
    	
    	JLabel menuLabel= new JLabel("2048 Game");
    	rulesButton.setText("Rules");
    	playButton.setText("Play");
    	exitButton.setText("Exit");
    	rulesButton.addMouseListener(this);
    	playButton.addMouseListener(this);
    	exitButton.addMouseListener(this);
    	
    	menuPanel.setLayout(new GridBagLayout());
    	GridBagConstraints gbc= new GridBagConstraints();
    	gbc.gridx=5;
    	gbc.gridy=10;
    	menuPanel.add(menuLabel, gbc);
    	gbc.gridy=20;
    	menuPanel.add(playButton, gbc);
    	gbc.gridy=30;
    	menuPanel.add(rulesButton, gbc);
    	gbc.gridy=40;
    	menuPanel.add(exitButton, gbc);
    	menuPanel.setBackground(greyBrown);
    	
    	frame.add(menuPanel);
    	
    	titleLabel.setSize(20,10);
    	titleLabel.setFont(arial20);
    	titleLabel.setText("2048");
    	titleLabel.setBackground(greyBrown);
    	titleLabel.setOpaque(true);
    	
    	scoreLabel.setSize(20,10);
    	scoreLabel.setFont(arial16);
    	scoreLabel.setText(""+score);
    	scoreLabel.setBackground(greyBrown);
    		
    	JPanel scorePanel=new JPanel();
    	scorePanel.setLayout(new BorderLayout());
    	scorePanel.add(titleLabel, BorderLayout.EAST);
    	scorePanel.add(scoreLabel, BorderLayout.WEST);
    	scoreLabel.setBackground(greyBrown);
    	scoreLabel.setOpaque(true);	
    	
    	panel.add(scorePanel, BorderLayout.NORTH);
    	panel.add(playPanel, BorderLayout.CENTER);
    	panel.addKeyListener(this);
    	    	
    	frame.setSize(300,400);
    	frame.addKeyListener(this);
    	frame.setVisible(true);
    	frame.setLocationRelativeTo(null);

    	frame.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e) {System.exit(0);}});
        JPanel rulesPanel= new JPanel();
        rulesPanel.setLayout(new BorderLayout());
        JTextArea rulesArea= new JTextArea();
        JLabel rulesLabel= new JLabel("Rules:");
        rulesPanel.setBackground(greyBrown);
        
        rulesArea.setLineWrap(true);
		rulesArea.setWrapStyleWord(true);
		rulesArea.setEditable(false);
		rulesArea.setText("Use your arrow keys to move the tiles. When two tiles with the same number touch, they merge into one. The goal is to create the biggest tile and earn the highest score you can!");
    	rulesPanel.add(rulesLabel, BorderLayout.NORTH);
    	rulesPanel.add(rulesArea, BorderLayout.CENTER);
    	
    	rulesFrame.add(rulesPanel);
    	rulesFrame.setSize(120,200);
    	rulesFrame.setVisible(false);
    	rulesFrame.setLocationRelativeTo(frame);
    	rulesFrame.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e) {rulesFrame.setVisible(false);}});
        
    }
    

    
    public void displayGrid()
    {
    	boolean worked=false;
    	dim=0;
    	while(worked==false)
    	{
    		String inputValue = JOptionPane.showInputDialog("Input the size of board you want to play: ");
    		try
	    	{
	    		dim= Integer.parseInt(inputValue);	
	    		worked=true;
	    		if(dim==1)
	    		{
	    			worked=false;
	    		}
	    	}
	    	catch(NumberFormatException e)
	    	{
	    		
	    	}	
    	}
    	System.out.println(dim);
    	
    	panel.setVisible(true);
    	empty= new boolean[dim][dim];
    	playGrid= new JLabel[dim][dim];
    	for(int x=0; x<playGrid.length; x++)
    	{
    		for(int y=0; y<playGrid[x].length; y++)
    		{
    			playGrid[x][y]= new JLabel();
    			playGrid[x][y].setHorizontalAlignment( SwingConstants.CENTER );
    			empty[x][y]=true;
    		}
    	}
    	
    	playPanel.setLayout(new GridLayout(playGrid.length,playGrid.length));
    	playPanel.addKeyListener(this);
    	for(int x=0; x<playGrid.length; x++)
    	{
    		if(tracing)System.out.print(4+x);
    		for(int y=0; y<playGrid[x].length; y++)
    		{
    			if(tracing)System.out.println(y);
    			playPanel.add(playGrid[x][y]); //adding each grid tile
    			playGrid[x][y].setBorder(BorderFactory.createLineBorder(Color.black));
    		}
    	}
    	
    	for(int x=0; x<playGrid.length; x++)
    	{
    		for(int y=0; y<playGrid[x].length; y++)
    		{
    			playGrid[x][y].setSize(50,50);
    			playGrid[x][y].setBackground(grey);
    			playGrid[x][y].setOpaque(true);
    			playGrid[x][y].setFont(numberFont);
    			playGrid[x][y].addKeyListener(this);
    			playGrid[x][y].setText("");
    			frame.validate();
    			frame.repaint();
    		//	if(tracing)playGrid[x][y].setText(""+x+", "+y);
    		}
    	}
    	startGame();
    }
    public void startGame()
    {
    	spawnNumber();
    	spawnNumber();
    } //done
    public void spawnNumber()
    {
    	int random=(int)((Math.random()*10)+1);
    	int newNumber;
    	
    	if(random<=9)
    		newNumber=2;
    	else
    		newNumber=4;
    	boolean found=false;
    	int x,y;	
    	do
    	{
    		x=(int)((Math.random()*playGrid.length));
    		y=(int)((Math.random()*playGrid.length));
    		
    		if(empty[y][x]==true)
    		{
    			found=true;
    		}	
    	}while(found==false);
    	
    	if(tracing)System.out.println("New number is at: " +y+x);
    	playGrid[y][x].setText(""+newNumber);
    	empty[y][x]=false;
    	playGrid[y][x].setBackground(nextColor(newNumber));
    	checkIfEnded();
    } //done 
    public void checkIfEnded()
    {
    	boolean ended=true;
    	
    	for(int x=0; x<playGrid.length; x++)
    	{
    		for(int y=0; y<playGrid.length; y++)
    		{
    			String number= playGrid[x][y].getText();
    			if(number.equals(""))
    			{
    				ended=false;
    			}
    			else
    			{
    				try
    				{
    					if(playGrid[x+1][y].getText().equals(number))
    					{
    						ended=false;
    					}
    				}
    				catch(ArrayIndexOutOfBoundsException e)
    				{
    					
    				}
    				
    				try
    				{
    					if(playGrid[x-1][y].getText().equals(number))
    					{
    						ended=false;
    					}
    				}
    				catch(ArrayIndexOutOfBoundsException e)
    				{
    					
    				}
    				
    				try
    				{
    					if(playGrid[x][y+1].getText().equals(number))
    					{
    						ended=false;
    					}
    				}
    				catch(ArrayIndexOutOfBoundsException e)
    				{
    					
    				}
    				
    				try
    				{
    					if(playGrid[x][y-1].getText().equals(number))
    					{
    						ended=false;
    					}
    				}
    				catch(ArrayIndexOutOfBoundsException e)
    				{
    					
    				}
    				
    			}
    			
    		}
    	}
    	
    	if(ended==true)
    	{
    		JOptionPane.showMessageDialog(null, "Oh no! You have no more moves. Click Ok to return to menu.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    		frame.remove(playPanel);
    		panel.setVisible(false);
    		frame.add(menuPanel);
    		
    	}
    }  
    public void mouseClicked(MouseEvent e)
    {
    	if(e.getSource().equals(playButton))
    	{
    		frame.remove(menuPanel);
    		frame.add(panel);
    		try
    		{
    			for(int x=0; x<playGrid.length; x++)
    			{
	    			for(int y=0; y<playGrid.length; y++)
	    			{
	    				playPanel.remove(playGrid[x][y]);
	    			}
				}
    		}
    		
    		catch(NullPointerException ex)
    		{
    			
    		}
    		
    		displayGrid();
    	}
    	
    	if(e.getSource().equals(exitButton))
    	{
    		System.exit(0);
    	}
    	
    	if(e.getSource().equals(rulesButton))
    	{
    		rulesFrame.setVisible(true);
    	}
    }
    public void keyPressed(KeyEvent e)
    {
    	int n= playGrid.length;
    	
    	if(tracing)System.out.println("keyPressed running");
    	if(e.getKeyCode()==KeyEvent.VK_DOWN)
    	{
    		boolean change=false;
    		boolean moved=false;
    		
    		do
    		{
    			moved=false;	
    			for(int y=n-1; y>0; y--)
    			{
	    			for(int x=n-1; x>=0; x--)
	    			{
	    				if(tracing)System.out.println(y+","+x);
	    				if(empty[y][x]==true)
	    				{
	    					if(tracing)System.out.println("Box " + y + "," + x + " is empty");
	    					if(empty[y-1][x]==false)
	    					{
	    						if(tracing)System.out.println("Box above it is not empty");
	    						playGrid[y][x].setText(playGrid[y-1][x].getText());
	    						playGrid[y-1][x].setText("");
	    						int temp= Integer.parseInt(playGrid[y][x].getText());
	    						playGrid[y][x].setBackground(nextColor(temp));
	    						playGrid[y-1][x].setBackground(grey);
	    						empty[y][x]=false;
	    						empty[y-1][x]=true;
	    						change=true;
	    						moved=true;
	    					}
	    				}
	    			}
				}
    			
    		}while(moved==true);
    		
    		
    		for(int x=n-1; x>=0; x--)
    		{
    			for(int y=n-1; y>0; y--)
    			{
    				if(empty[y][x]==false)
    				{
    					if(empty[y-1][x]==false)
    					{
    						if(playGrid[y][x].getText().equals(playGrid[y-1][x].getText()))
    						{
    							playGrid[y-1][x].setText(""+nextNumber(playGrid[y][x].getText()));
    							playGrid[y][x].setText("");
    							playGrid[y][x].setBackground(grey);
    							int temp= Integer.parseInt(playGrid[y-1][x].getText());
	    						playGrid[y-1][x].setBackground(nextColor(temp));
    							empty[y-1][x]=false;
    							empty[y][x]=true;	
    							change=true;
    							y=n-1;
    						}
    							
    					}
    				}
    			}
    		}
    		
    		do
    		{
    			moved=false;	
    			for(int y=n-1; y>0; y--)
    			{
	    			for(int x=n-1; x>=0; x--)
	    			{
	    				if(tracing)System.out.println(y+","+x);
	    				if(empty[y][x]==true)
	    				{
	    					if(tracing)System.out.println("Box " + y + "," + x + " is empty");
	    					if(empty[y-1][x]==false)
	    					{
	    						if(tracing)System.out.println("Box above it is not empty");
	    						playGrid[y][x].setText(playGrid[y-1][x].getText());
	    						int temp= Integer.parseInt(playGrid[y][x].getText());
	    						playGrid[y][x].setBackground(nextColor(temp));
	    						playGrid[y-1][x].setText("");
	    						playGrid[y-1][x].setBackground(grey);
	    						empty[y][x]=false;
	    						empty[y-1][x]=true;
	    						change=true;
	    						moved=true;
	    					}
	    				}
	    			}
				}
    			
    		}while(moved==true);
    		if(change)
    			spawnNumber();
    	}
    	
    	if(e.getKeyCode()==KeyEvent.VK_UP)
    	{
    		boolean change=false;
    		boolean movedUp=false;
    		
    		do
    		{
    			movedUp=false;	
    			for(int y=0; y<n-1; y++)
    			{
	    			for(int x=0; x<n; x++)
	    			{
	    				if(tracing)System.out.println(y+","+x);
	    				if(empty[y][x]==true)
	    				{
	    					if(tracing)System.out.println("Box " + y + "," + x + " is empty");
	    					if(empty[y+1][x]==false)
	    					{
	    						if(tracing)System.out.println("Box below it is not empty");
	    						playGrid[y][x].setText(playGrid[y+1][x].getText());
	    						playGrid[y+1][x].setText("");
	    						int temp= Integer.parseInt(playGrid[y][x].getText());
	    						playGrid[y][x].setBackground(nextColor(temp));
	    						playGrid[y+1][x].setBackground(grey);
	    						empty[y][x]=false;
	    						empty[y+1][x]=true;
	    						change=true;
	    						movedUp=true;
	    					}
	    				}
	    			}
				}
    			
    		}while(movedUp==true);
    		
    		
    		for(int x=n-1; x>=0; x--)
    		{
    			for(int y=0; y<n-1; y++)
    			{
    				if(empty[y][x]==false)
    				{
    					if(empty[y+1][x]==false)
    					{
    						if(playGrid[y][x].getText().equals(playGrid[y+1][x].getText()))
    						{
    							playGrid[y+1][x].setText(""+nextNumber(playGrid[y][x].getText()));
    							playGrid[y][x].setText("");
    							int temp= Integer.parseInt(playGrid[y+1][x].getText());
	    						playGrid[y+1][x].setBackground(nextColor(temp));
	    						playGrid[y][x].setBackground(grey);
    							empty[y+1][x]=false;
    							empty[y][x]=true;	
    							change=true;
    							y=0;
    						}
    							
    					}
    				}
    			}
    		}
    		
    		do
    		{
    			movedUp=false;	
    			for(int y=0; y<n-1; y++)
    			{
	    			for(int x=0; x<n; x++)
	    			{
	    				if(tracing)System.out.println(y+","+x);
	    				if(empty[y][x]==true)
	    				{
	    					if(tracing)System.out.println("Box " + y + "," + x + " is empty");
	    					if(empty[y+1][x]==false)
	    					{
	    						if(tracing)System.out.println("Box below it is not empty");
	    						playGrid[y][x].setText(playGrid[y+1][x].getText());
	    						playGrid[y+1][x].setText("");
	    						int temp= Integer.parseInt(playGrid[y][x].getText());
	    						playGrid[y][x].setBackground(nextColor(temp));
	    						playGrid[y+1][x].setBackground(grey);
	    						empty[y][x]=false;
	    						empty[y+1][x]=true;
	    						change=true;
	    						movedUp=true;
	    					}
	    				}
	    			}
				}
    			
    		}while(movedUp==true);
    		if(change)
    			spawnNumber();
    			
    	}
    	
    	if(e.getKeyCode()==KeyEvent.VK_RIGHT)
    	{
    		boolean change=false;
    		boolean movedRight=false;
    		
    		do
    		{
    			movedRight=false;	
    			for(int y=n-1; y>=0; y--)
    			{
	    			for(int x=n-1; x>0; x--)
	    			{
	    				if(tracing)System.out.println(y+","+x);
	    				if(empty[y][x]==true)
	    				{
	    					if(tracing)System.out.println("Box " + y + "," + x + " is empty");
	    					if(empty[y][x-1]==false)
	    					{
	    						if(tracing)System.out.println("Box to the left is not empty");
	    						playGrid[y][x].setText(playGrid[y][x-1].getText());
	    						playGrid[y][x-1].setText("");
	    						int temp= Integer.parseInt(playGrid[y][x].getText());
	    						playGrid[y][x].setBackground(nextColor(temp));
	    						playGrid[y][x-1].setBackground(grey);
	    						empty[y][x]=false;
	    						empty[y][x-1]=true;
	    						change=true;
	    						movedRight=true;
	    					}
	    				}
	    			}
				}
    			
    		}while(movedRight==true);
    		
    		
    		for(int y=0; y<n; y++)
    		{
    			for(int x=n-1; x>0; x--)
    			{
    				if(empty[y][x]==false)
    				{
    					if(empty[y][x-1]==false)
    					{
    						if(playGrid[y][x].getText().equals(playGrid[y][x-1].getText()))
    						{
    							playGrid[y][x-1].setText(""+nextNumber(playGrid[y][x].getText()));
    							playGrid[y][x].setText("");
    							int temp= Integer.parseInt(playGrid[y][x-1].getText());
	    						playGrid[y][x-1].setBackground(nextColor(temp));
	    						playGrid[y][x].setBackground(grey);
    							empty[y][x-1]=false;
    							empty[y][x]=true;	
    							change=true;
    							x=n-1;
    						}
    							
    					}
    				}
    			}
    		}
    		
    		do
    		{
    			movedRight=false;	
    			for(int y=n-1; y>=0; y--)
    			{
	    			for(int x=n-1; x>0; x--)
	    			{
	    				if(tracing)System.out.println(y+","+x);
	    				if(empty[y][x]==true)
	    				{
	    					if(tracing)System.out.println("Box " + y + "," + x + " is empty");
	    					if(empty[y][x-1]==false)
	    					{
	    						if(tracing)System.out.println("Box to the left is not empty");
	    						playGrid[y][x].setText(playGrid[y][x-1].getText());
	    						playGrid[y][x-1].setText("");
	    						int temp= Integer.parseInt(playGrid[y][x].getText());
	    						playGrid[y][x].setBackground(nextColor(temp));
	    						playGrid[y][x-1].setBackground(grey);
	    						empty[y][x]=false;
	    						empty[y][x-1]=true;
	    						change=true;
	    						movedRight=true;
	    					}
	    				}
	    			}
				}
    			
    		}while(movedRight==true);
    		if(change)
    			spawnNumber();
    	}
    	
    	if(e.getKeyCode()==KeyEvent.VK_LEFT)
    	{
    		boolean change=false;
    		boolean movedLeft=false;
    		
    		do
    		{
    			movedLeft=false;	
    			for(int y=0; y<n; y++)
    			{
	    			for(int x=0; x<n-1; x++)
	    			{
	    				if(tracing)System.out.println(y+","+x);
	    				if(empty[y][x]==true)
	    				{
	    					if(tracing)System.out.println("Box " + y + "," + x + " is empty");
	    					if(empty[y][x+1]==false)
	    					{
	    						if(tracing)System.out.println("Box to the right is not empty");
	    						playGrid[y][x].setText(playGrid[y][x+1].getText());
	    						playGrid[y][x+1].setText("");
	    						int temp= Integer.parseInt(playGrid[y][x].getText());
	    						playGrid[y][x].setBackground(nextColor(temp));
	    						playGrid[y][x+1].setBackground(grey);
	    						empty[y][x]=false;
	    						empty[y][x+1]=true;
	    						change=true;
	    						movedLeft=true;
	    					}
	    				}
	    			}
				}
    			
    		}while(movedLeft==true);
    		
    		
    		for(int y=n-1; y>=0; y--)
    		{
    			for(int x=0; x<n-1; x++)
    			{
    				if(empty[y][x]==false)
    				{
    					if(empty[y][x+1]==false)
    					{
    						if(playGrid[y][x].getText().equals(playGrid[y][x+1].getText()))
    						{
    							playGrid[y][x+1].setText(""+nextNumber(playGrid[y][x].getText()));
    							playGrid[y][x].setText("");
    							int temp= Integer.parseInt(playGrid[y][x+1].getText());
	    						playGrid[y][x+1].setBackground(nextColor(temp));
	    						playGrid[y][x].setBackground(grey);
    							empty[y][x+1]=false;
    							empty[y][x]=true;	
    							change=true;
    							x=n-1;
    						}
    							
    					}
    				}
    			}
    		}
    		
    		do
    		{
    			movedLeft=false;	
    			for(int y=0; y<n; y++)
    			{
	    			for(int x=0; x<n-1; x++)
	    			{
	    				if(tracing)System.out.println(y+","+x);
	    				if(empty[y][x]==true)
	    				{
	    					if(tracing)System.out.println("Box " + y + "," + x + " is empty");
	    					if(empty[y][x+1]==false)
	    					{
	    						if(tracing)System.out.println("Box to the right is not empty");
	    						playGrid[y][x].setText(playGrid[y][x+1].getText());
	    						playGrid[y][x+1].setText("");
	    						int temp= Integer.parseInt(playGrid[y][x].getText());
	    						playGrid[y][x].setBackground(nextColor(temp));
	    						playGrid[y][x+1].setBackground(grey);
	    						empty[y][x]=false;
	    						empty[y][x+1]=true;
	    						change=true;
	    						movedLeft=true;
	    					}
	    				}
	    			}
				}
    			
    		}while(movedLeft==true);
    		if(change)
    			spawnNumber();
    	}
    }
    public Color nextColor(int currentNumber)
    {
    	if(tracing)System.out.println("nextColor is running");
    	Color nextColor= Color.WHITE;
    	switch (currentNumber)
    	{
    		case 2: 
    			nextColor= new Color(232,226,209);
    			break;
    		case 4:
    			nextColor= new Color(222,211,177);
    			break;
    		case 8:
    			nextColor= new Color(247,183,109);
    			break;
    		case 16:
    			nextColor= new Color(245,153,49);
    			break;
    		case 32:
    			nextColor= new Color(247,118,89);
    			break;
    		case 64:
    			nextColor= new Color(247,71,34);
    			break;
    		case 128:
    			nextColor= new Color(247,225,114);
    			break;
    		case 256:
    			nextColor= new Color(247,223,90);
    			break;
    		case 512:
    			nextColor= new Color(247,221,72);
    			break;
    		case 1024:
    			nextColor= new Color(247,225,57);
    			break;
    		case 2048:
    			nextColor= new Color(247,230,57);
    			break;
    		default:
    			nextColor= new Color(0,0,0,0);
    	}
    	return(nextColor);	
    			
    }
    public int nextNumber(String text)
    {
    	text.trim();
    	int currentNumber=Integer.parseInt(text);
    	int nextNumber=currentNumber*2;
    	score+=nextNumber;
    	scoreLabel.setText(""+score);
    	return nextNumber;
    } //done
    
    
    public void mousePressed(MouseEvent e)
    {
    	
    }
    public void mouseReleased(MouseEvent e)
    {
    	
    }
    public void mouseEntered(MouseEvent e)
    {
    	
    }
    public void mouseExited(MouseEvent e)
    {
    	
    }
    public void keyTyped(KeyEvent e)
    {
    	
    }
    public void keyReleased(KeyEvent e)
    {
    	
    }
}
