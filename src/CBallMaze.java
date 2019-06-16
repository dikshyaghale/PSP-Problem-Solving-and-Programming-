/*
 * 
 *  Program:   Assignment 2: Application – Ball Maze
 *  Filename:  CBallMaze.java              
 *  @author:   © Dikshya Ghale(18406490)                       
 *  Course:    Bsc.Computing        
 *  Module:    CSY1020 Problem Solving & Programming       
 *  Tutor:     Kumar Lamichhane                                 
 *  @version:  2.0 Incorporates Artificial Intelligence!
 *  Date:      23/11/17      
 *   
 */ 


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class CBallMaze extends JFrame implements ActionListener

{
	//declaring the swing components
	private JButton jBMyAct, jBMyRun, jBReset, jBOptionOne, jBOptionTwo, 
					jBOptionThree, jBOptionFour, jBNull1, jBNull2, jBNull3,
					jBNull4, jBNull5, jBCompass, jBForward, jBBackward, 
					jBUpward, jBDownward, jBPause;
	private JPanel jPPanelMaze, jPPanelSide,
					jPBottomPanel;
	private JLabel jLOption, jLSquare, jLDirection, jLDigiTimer,
					jLColon1, jLColon2, jLsliderLbl;
	private JTextField jTFOptionField, jTFSquareField, jTFieldDirection,
					jTFieldHour, jTFieldMinute, jTFieldSec;
	private Border raisedbevel, loweredbevel,raisedetched,
					loweredetched,compound;
	private static JMenuBar jMMenu;
	private JSlider jSSlider;
	private JLabel[][] labelMaze = new JLabel[16][13];//initializing 2D array
	int moveX = 15;//initialize the position of goldenball in x-axis
	int moveY = 0;//initialize the position of goldenball in y-axis
	private int ticTock = 0;//initialize the variable for timer
	private Timer digitalTimer, fallTimer,ballRunTimer;
	private boolean clickOption2=false;
	private boolean clickOption3=false;
	Clip soundPlay;//for sound

	//these are declaring the images
	ImageIcon sandIcon = new ImageIcon("img/sand.jpg");
	ImageIcon goldenBall = new ImageIcon("img/sand60x60.png");
	ImageIcon whiteIcon = new ImageIcon("img/white32*32.jpg");
	ImageIcon goalIcon = new ImageIcon("img/sandstone.jpg");
	ImageIcon goldBallWhite = new ImageIcon("img/gold-ball.png");
	ImageIcon southImg = new ImageIcon("img/south.jpg");
	ImageIcon eastImg = new ImageIcon("img/east.jpg");
	ImageIcon northImg = new ImageIcon("img/north.jpg");
	ImageIcon westImg = new ImageIcon("img/west.jpg");
	ImageIcon resetImg = new ImageIcon("img/reset.png");
	ImageIcon actImg = new ImageIcon("img/step.png");
	ImageIcon runImg = new ImageIcon("img/run.png");


	public static void main(String[] args)
	{
		createInterface();//method calling

	}
	private static void createInterface()
	{
		CBallMaze frame = new CBallMaze();//creating new frame
		frame.setSize(755, 650);
		frame.setResizable(false);//can't resize the frame
		frame.setLocationRelativeTo(null);
		frame.setTitle("CBallMaze - Ball Maze Application");//setting the title of the frame

		ImageIcon icon = new ImageIcon("img/greenfoot.png");//setting the image of hte frame
		frame.setIconImage(icon.getImage());

		jMMenu = new JMenuBar();//creating new menubar
		frame.setJMenuBar(jMMenu);//adding Jmenu in frame
		JMenu scenario = new JMenu("Scenario");
		jMMenu.add(scenario);
		scenario.addSeparator();
		JMenuItem newJava = new JMenuItem("New Java Scenario");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save as");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);//it ends the system while pressing exit in menubar
				
			}
		});
		scenario.add(newJava);
		scenario.add(open);
		scenario.add(save);
		scenario.add(exit);
		
		JMenu edit = new JMenu("Edit");
		jMMenu.add(edit);
		edit.addSeparator();//this seperates the line between menu bar and menu items
		JMenuItem newc = new JMenuItem("New Class   Ctrl+N");
		JMenuItem importc = new JMenuItem("Import Class   Ctrl+I");
		JMenuItem delt = new JMenuItem("Delete Class");
		JMenuItem prefer = new JMenuItem("Preferences");
		edit.add(newc);
		edit.add(importc);
		edit.add(delt);
		edit.add(prefer);
		
		JMenu control = new JMenu("Controls");
		jMMenu.add(control);
		control.addSeparator();//this seperates the line between menu bar and menu items
		JMenuItem actf = new JMenuItem("Act        Ctrl+A");
		actf.setIcon(new ImageIcon("img/step.png"));
		JMenuItem runf = new JMenuItem("Run       Ctrl+t");
		runf.setIcon(new ImageIcon("img/run.png"));
		JMenuItem resetf = new JMenuItem("Reset    Ctrl+T");
		resetf.setIcon(new ImageIcon("img/reset.png"));
		JMenuItem world = new JMenuItem("Save the world");
		control.add(actf);
		control.add(runf);
		control.add(resetf);
		control.add(world);
		
		JMenu help = new JMenu("Help");
		jMMenu.add(help);
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e) 
			{//it shows the following message while clicking on "about'\" button
				JOptionPane.showMessageDialog(null, "Program:   Assignment 2: Application – Ball Maze\r\n" + 
						" *  Filename:  CBallMaze.java              \r\n" + 
						" *  @author:   © Dikshya Ghale(18406490)                       \r\n" + 
						" *  Course:    Bsc.Computing        \r\n" + 
						" *  Module:    CSY1020 Problem Solving & Programming       \r\n" + 
						" *  Tutor:     Kumar Lamichhane                                 \r\n" + 
						" *  @version:  2.0 Incorporates Artificial Intelligence!\r\n" + 
						" *  Date:      23/11/17 ");
				
			}
		});
		help.addSeparator();//this seperates the line between menu bar and menu items
		JMenuItem gallery = new JMenuItem("Greenfoot Gallery");

		help.add(about);
		help.add(gallery);
		
		frame.createGUI();//create graphical user interface
		frame.setVisible(true);

	}
	
	private void createGUI()
	
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container window = getContentPane();

		raisedbevel = BorderFactory.createRaisedBevelBorder();//for raised borders
		loweredbevel = BorderFactory.createRaisedBevelBorder();//this creates nice borders
		raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);//this makes borders properties great
		compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);//for compound borders
		loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);//to create loweredetched borderes
	

		jPPanelMaze = new JPanel();
		jPPanelMaze.setPreferredSize(new Dimension(580, 600));//giving size for the panel
		jPPanelMaze.setBorder(compound);//setting border as compund
		jPPanelMaze.setBackground(Color.WHITE);
		jPPanelMaze.setLayout(null);//this makes layout null

		jPPanelMaze.setLayout(new GridBagLayout());//set new layout
		GridBagConstraints gbc = new GridBagConstraints();//c

		jPPanelSide = new JPanel();
		jPPanelSide.setPreferredSize(new Dimension(200, 600));
		jPPanelSide.setBorder(compound);
		jPPanelSide.setLayout(null);
		
		jPBottomPanel = new JPanel();
		jPBottomPanel.setPreferredSize(new Dimension(680, 80));
		jPBottomPanel.setLayout(null);
		jPBottomPanel.setBorder(compound);

		jLOption = new JLabel("Option:");
		jLOption.setBounds(40, 10, 60, 20);
		jLOption.setFont(new Font("Serif", Font.BOLD, 15));
		jPPanelSide.add(jLOption);
		jTFOptionField = new JTextField();
		jTFOptionField.setBackground(Color.WHITE);
		jTFOptionField.setBorder(null);//this sets border to null
		jTFOptionField.setBounds(110, 10, 70, 20);
		jTFOptionField.setEditable(false);//it wont allow you to write anything at that field
		jPPanelSide.add(jTFOptionField);

		jLSquare = new JLabel("Square:");
		jLSquare.setFont(new Font("Serif", Font.BOLD, 15));
		jLSquare.setBounds(40, 40, 60, 20);
		jPPanelSide.add(jLSquare);
		jTFSquareField = new JTextField();
		jTFSquareField.setBounds(110, 40, 70, 20);
		jTFSquareField.setText(moveX + " , " + moveY);//setting the value of position of ball in square textfield
		jTFSquareField.setBorder(null);
		jTFSquareField.setBackground(Color.WHITE);
		jTFSquareField.setEditable(false);//making square field non editable
		jPPanelSide.add(jTFSquareField);

		jLDirection = new JLabel("Direction:");
		jLDirection.setBounds(40, 70, 80, 20);
		jLDirection.setFont(new Font("Serif", Font.BOLD, 15));
		jPPanelSide.add(jLDirection);
		jTFieldDirection = new JTextField();
		jTFieldDirection.setBounds(110, 70, 70, 20);
		jTFieldDirection.setBorder(null);
		jTFieldDirection.setBackground(Color.WHITE);
		jTFieldDirection.setEditable(false);
		jPPanelSide.add(jTFieldDirection);

		jLDigiTimer = new JLabel("DIGITAL TIMER");
		jLDigiTimer.setBounds(50, 110, 130, 30);
		jLDigiTimer.setFont(new Font("Serif", Font.BOLD, 17));
		jPPanelSide.add(jLDigiTimer);

		jTFieldHour = new JTextField();
		jTFieldHour.setBounds(50, 145, 35, 25);
		jTFieldHour.setBorder(compound);
		jTFieldHour.setEditable(false);
		jTFieldHour.setBackground(Color.BLACK);
		jTFieldHour.setForeground(Color.WHITE);
		jPPanelSide.add(jTFieldHour);

		jLColon1 = new JLabel(":");
		jLColon1.setBounds(90, 145, 10, 20);
		jPPanelSide.add(jLColon1);

		jTFieldMinute = new JTextField();
		jTFieldMinute.setBounds(95, 145, 35, 25);
		jTFieldMinute.setBorder(null);
		jTFieldMinute.setEditable(false);
		jTFieldMinute.setBackground(Color.BLACK);
		jTFieldMinute.setForeground(Color.WHITE);//making forgeground color white
		jPPanelSide.add(jTFieldMinute);

		jLColon2 = new JLabel(":");
		jLColon2.setBounds(135, 145, 10, 20);
		jPPanelSide.add(jLColon2);

		jTFieldSec = new JTextField();
		jTFieldSec.setBounds(140, 145, 35, 25);
		jTFieldSec.setBorder(null);
		jTFieldSec.setEditable(false);
		jTFieldSec.setBackground(Color.BLACK);
		jTFieldSec.setForeground(Color.WHITE);

		jPPanelSide.add(jTFieldSec);

		//creating timer so that the time starts to display in the timerField
		digitalTimer = new Timer(1000, new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				jTFieldHour.setText(Integer.toString(ticTock / 3600));//set hour in hour field
				jTFieldMinute.setText(Integer.toString(ticTock / 60));//set minute in minute field
				jTFieldSec.setText(Integer.toString(ticTock % 60));//set second in second field
				ticTock += 1;

			}
		});

		//this is the timer for movement of ball which falls automatically 
		fallTimer = new Timer(900, new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				//check if clickOption2 is true then ball falls down automatically but it doesn't fall in case of false condition.
				if(clickOption2==true) {
				if (labelMaze[moveX][moveY + 1].getIcon().equals(sandIcon)) 
				{
					labelMaze[moveX][moveY].setIcon(sandIcon);
					labelMaze[moveX][moveY + 1].setIcon(goldenBall);
					moveY +=1;
					jBCompass.setIcon(new ImageIcon("img/south.jpg"));//set new image in compass while ball falls down
					jTFieldDirection.setText("South");
					playGameSound("down.wav");//playsound while falling down
				  }
				}
			}
		});
		ballFallDown();//method calling
		
		//creating "^" button
		jBUpward = new JButton("^");
		jBUpward.setBounds(90, 190, 50, 30);//giving the position of x-axis,y-axis,width and height for a button
		jBUpward.setBackground(Color.LIGHT_GRAY);//setting background color to gray
		jBUpward.setBorder(raisedbevel);//setting the border
		jPPanelSide.add(jBUpward);//adding button to panel
		jBUpward.addActionListener(this);//reference to the object jBUpward

		//creating "<" button
		jBBackward = new JButton("<");//create new object of button
		jBBackward.setBounds(40, 220, 50, 30);//setting the position of x-axis, y-axis, width and height for a button
		jBBackward.setBackground(Color.LIGHT_GRAY);//setting background-color
		jBBackward.setBorder(raisedbevel);//setting the border
		jBBackward.addActionListener(this);//reference to the object JBBackward
		jPPanelSide.add(jBBackward);//add button in the panel

		//creating ">" button
		jBForward = new JButton(">");
		jBForward.setBounds(140, 220, 50, 30);
		jBForward.setBackground(Color.LIGHT_GRAY);
		jBForward.setBorder(raisedbevel);
		jBForward.addActionListener(this);
		jPPanelSide.add(jBForward);//add button to panel

		//creating "v" button
		jBDownward = new JButton("v");
		jBDownward.setBounds(90, 250, 50, 30);
		jBDownward.setBackground(Color.LIGHT_GRAY);
		jBDownward.setBorder(raisedbevel);
		jBDownward.addActionListener(this);
		jPPanelSide.add(jBDownward);

		//creating null buttons
		jBNull1 = new JButton();
		jBNull1.setBounds(40, 190, 50, 30);
		jBNull1.setEnabled(false);
		jBNull1.setBackground(Color.WHITE);
		jBNull1.setBorder(null);
		jBNull1.setBorder(loweredetched);
		jPPanelSide.add(jBNull1);

		jBNull2 = new JButton();
		jBNull2.setBounds(140, 190, 50, 30);
		jBNull2.setEnabled(false);
		jBNull2.setBackground(Color.WHITE);
		jBNull2.setBorder(null);
		jBNull2.setBorder(raisedetched);
		jPPanelSide.add(jBNull2);

		jBNull3 = new JButton();
		jBNull3.setBounds(90, 220, 50, 30);
		jBNull3.setEnabled(false);
		jBNull3.setBackground(Color.WHITE);
		jBNull3.setBorder(null);
		jBNull3.setBorder(raisedetched);
		jPPanelSide.add(jBNull3);

		jBNull4 = new JButton();
		jBNull4.setBounds(40, 250, 50, 30);
		jBNull4.setBackground(Color.WHITE);
		jBNull4.setEnabled(false);
		jBNull4.setBorder(null);//setting border to null
		jBNull4.setBorder(raisedetched);
		jPPanelSide.add(jBNull4);

		jBNull5 = new JButton();
		jBNull5.setBounds(140, 250, 50, 30);
		jBNull5.setBackground(Color.WHITE);
		jBNull5.setEnabled(false);
		jBNull5.setBorder(null);
		jBNull5.setBorder(raisedetched);
		jPPanelSide.add(jBNull5);

		jBOptionOne = new JButton("Option1");
		jBOptionOne.setBounds(33, 310, 80, 30);
		jBOptionOne.setBackground(Color.WHITE);
		jPPanelSide.add(jBOptionOne);
		jBOptionOne.addActionListener(this);

		jBOptionTwo = new JButton("Option2");
		jBOptionTwo.setBackground(Color.WHITE);
		jBOptionTwo.setBounds(115, 310, 80, 30);
		jPPanelSide.add(jBOptionTwo);
		jBOptionTwo.addActionListener(this);

		jBOptionThree = new JButton("Option3");
		jBOptionThree.setBounds(33, 350, 80, 30);
		jBOptionThree.setBackground(Color.WHITE);
		jPPanelSide.add(jBOptionThree);
		jBOptionThree.addActionListener(this);

		jBOptionFour = new JButton("Exit");
		jBOptionFour.setBackground(Color.WHITE);
		jBOptionFour.setBounds(115, 350, 80, 30);
		jPPanelSide.add(jBOptionFour);
		jBOptionFour.addActionListener(this);

		jBCompass = new JButton();
		jBCompass.setBounds(80, 395, 80, 80);
		jBCompass.setIcon(new ImageIcon("img/north.jpg"));
		jBCompass.addActionListener(this);
		jPPanelSide.add(jBCompass);
		

		jBMyAct = new JButton("Act");
		jBMyAct.setIcon(new ImageIcon("img/step.png"));//Adding image to button named Act
		jBMyAct.setBounds(20, 15, 80, 30);
		jBMyAct.addActionListener(this);//reference to object jBMyAct
		jPBottomPanel.add(jBMyAct);//adding button to panel

		jBMyRun = new JButton("Run");
		jBMyRun.setIcon(new ImageIcon("img/run.png"));//setting image to button named Run
		jBMyRun.setBounds(110, 15, 80, 30);
		jBMyRun.addActionListener(this);//reference to object jBMyRun
		jPBottomPanel.add(jBMyRun);//add button to panel
		jBMyRun.setEnabled(false);

		jBPause = new JButton("Pause");
		jBPause.setBounds(110, 15, 80, 30);
		jBPause.addActionListener(this);
		jPBottomPanel.add(jBPause);

		jBReset = new JButton("Reset");
		jBReset.setIcon(new ImageIcon("img/reset.png"));
		jBReset.setBounds(210, 15, 100, 30);
		jBReset.addActionListener(this);
		jPBottomPanel.add(jBReset);

		jSSlider = new JSlider(JSlider.HORIZONTAL, 10, 130, 30);//creating new slider in horizontal direction
		jSSlider.setBounds(540, 10, 150, 50);//giving the proper position height and width
		JLabel jLsliderLbl = new JLabel("Speed:");//adding new JLabel 
		jLsliderLbl.setFont(new Font("Serif", Font.BOLD, 17));//setting font
		jLsliderLbl.setBounds(490, 10, 70, 30);
		jPBottomPanel.add(jLsliderLbl);
		jSSlider.setMinorTickSpacing(8);//setting minor ticks for slider
		jSSlider.setPaintTicks(true);//make ticks visible
		jSSlider.setPaintLabels(true);//make labels visible
		jPBottomPanel.add(jSSlider);
		jPBottomPanel.add(jLsliderLbl);

		for (int x = 0; x < 16; x++) 
		{
			for (int y = 0; y < 13; y++) 
			{
				gbc.gridx = x;
				gbc.gridy = y;
				labelMaze[x][y] = new JLabel(sandIcon);//adding sand image in the label

				for (int a = 10; a < 16; a++)
				{
					labelMaze[a][1] = new JLabel(whiteIcon);//adding white image in the given position
					labelMaze[a][2] = new JLabel(whiteIcon);
				}
				for (int a = 6; a < 9; a++)
				{
					labelMaze[a][1] = new JLabel(whiteIcon);
					labelMaze[a][2] = new JLabel(whiteIcon);

				}
				for (int a = 2; a < 5; a++)
				{
					labelMaze[a][1] = new JLabel(whiteIcon);
					labelMaze[a][2] = new JLabel(whiteIcon);
					labelMaze[a][7] = new JLabel(whiteIcon);
					labelMaze[a][8] = new JLabel(whiteIcon);
				}
				for (int a = 0; a < 1; a++)
				{
					labelMaze[a][1] = new JLabel(whiteIcon);
					labelMaze[a][2] = new JLabel(whiteIcon);
					labelMaze[a][7] = new JLabel(whiteIcon);
					labelMaze[a][8] = new JLabel(whiteIcon);
				}
				for (int a = 0; a < 2; a++)
				{
					labelMaze[a][4] = new JLabel(whiteIcon);
					labelMaze[a][5] = new JLabel(whiteIcon);
				}
				for (int a = 3; a < 6; a++)
				{
					labelMaze[a][4] = new JLabel(whiteIcon);
					labelMaze[a][5] = new JLabel(whiteIcon);
					labelMaze[a][10] = new JLabel(whiteIcon);
					labelMaze[a][11] = new JLabel(whiteIcon);
				}
				for (int a = 7; a < 11; a++) 
				{
					labelMaze[a][4] = new JLabel(whiteIcon);
					labelMaze[a][5] = new JLabel(whiteIcon);
				}
				for (int a = 12; a < 16; a++)
				{
					labelMaze[a][4] = new JLabel(whiteIcon);
					labelMaze[a][5] = new JLabel(whiteIcon);
				}
				for (int a = 6; a < 12; a++)
				{
					labelMaze[a][7] = new JLabel(whiteIcon);
					labelMaze[a][8] = new JLabel(whiteIcon);
				}
				for (int a = 13; a < 16; a++)
				{
					labelMaze[a][7] = new JLabel(whiteIcon);
					labelMaze[a][8] = new JLabel(whiteIcon);
				}
				for (int a = 0; a < 2; a++)
				{
					labelMaze[a][10] = new JLabel(whiteIcon);
					labelMaze[a][11] = new JLabel(whiteIcon);
				}
				for (int a = 7; a < 16; a++)
				{
					labelMaze[a][10] = new JLabel(whiteIcon);
					labelMaze[a][11] = new JLabel(whiteIcon);
				}
				if (x == 15 && y == 0)
				{
					labelMaze[x][y] = new JLabel(goldenBall);//adding goldenball in position where x is equal to 15 and y is equal to 0.
				}
				if (x == 0 && y == 12) 
				{
					labelMaze[x][y] = new JLabel(goalIcon);//adding 
				}
				jPPanelMaze.add(labelMaze[x][y], gbc);
			}
		}

		window.add(jPPanelMaze, BorderLayout.LINE_START);//adding panel in the container 
		window.add(jPPanelSide, BorderLayout.LINE_END);//adding panel in the container
		window.add(jPBottomPanel, BorderLayout.PAGE_END);//adding panel in the container
	}
	
	//this method would help ball to move in specific paths only
		private void myAct()
		{
			if((moveX == 9 && moveY<3)
					|| (moveX==6 && moveY<6)
					|| (moveX==5 && moveY<9)
					|| (moveX==2 && moveY<12 ))
					moveDown();		
			else
					moveLeft();
			if(changeBall()==true)//check if the boolean changeBall is true or not
			{
				moveLeft();
				digitalTimer.stop();
			}
		
		}
		
		//this method lets ball move within timer
		private void finalRun()
		{
			ballRunTimer = new Timer(500, new ActionListener() //creating new timer
			{
				
				public void actionPerformed(ActionEvent e)
				{
					if(clickOption3==true)
					//if the method clickOption3 is true then the method myAct will be called	
					{
						
					myAct();
					
					}
				}
				
			});
			
			ballRunTimer.start();//the timer will start working
			
		}
		


	//creating this method makes the ball move towards left
	public void moveLeft() 
	{
		labelMaze[moveX - 1][moveY].setIcon(goldenBall);
		labelMaze[moveX][moveY].setIcon(sandIcon);
		moveX--;
		jTFSquareField.setText(moveX + " , " + moveY);//this shows the position of ball in the maze while moving
		jBCompass.setIcon(new ImageIcon("img/west.jpg"));
		jTFieldDirection.setText("West");
		
	}
	
	//creating this method makes the ball move towards right
	public void moveRight()
	{
		labelMaze[moveX + 1][moveY].setIcon(goldenBall);
		labelMaze[moveX][moveY].setIcon(sandIcon);
		moveX++;
		jTFSquareField.setText(moveX + " , " + moveY);
		jBCompass.setIcon(new ImageIcon("img/east.jpg"));//this shows the position of ball in maze while moving
		jTFieldDirection.setText("East");
	}

	//creating this method makes the ball move towards up
	public void moveUp()
	{
		labelMaze[moveX][moveY - 1].setIcon(goldenBall);
		labelMaze[moveX][moveY].setIcon(sandIcon);
		moveY--;
		jBCompass.setIcon(new ImageIcon("img/north.jpg"));
		jTFieldDirection.setText("North");
		jTFSquareField.setText(moveX + " , " + moveY);
	}

	//creating this method makes the ball move towards down
	public void moveDown()
	{
		labelMaze[moveX][moveY + 1].setIcon(goldenBall);
		labelMaze[moveX][moveY].setIcon(sandIcon);
		moveY++;
		jBCompass.setIcon(new ImageIcon("img/south.jpg"));
		jTFieldDirection.setText("South");
		jTFSquareField.setText((moveX + " , " + moveY));
		
	}

	//checks if there is image of sand or not in left side. 
	//If there is sand then ball moves and if there's no sand then ball doesn't move. 
	
	private boolean checkWhiteLeft()
	{
		if (moveX != 0 && labelMaze[moveX - 1][moveY].getIcon() == sandIcon)
			return true;
		else
			return false;
	}

	//checks if there is image of goal or not.
	// if there is goal then it'll return true value otherwise it will return false value
	private boolean checkGoal()
	{
		if (moveX != 0 && labelMaze[moveX - 1][moveY].getIcon() == goalIcon)
			return true;
		else
			return false;
	}

	//this method checks if there is sand image in right or not.
	// If there is image in right then it'll return true value otherwise it'll return false value.
	private boolean checkWhiteRight()
	{
		if (moveX != 15 && labelMaze[moveX + 1][moveY].getIcon() == sandIcon)
			return true;
		else
			return false;
	}

	//This method is created to check if there is goal or not.
	// If there is goal then the image of goal is replaced by goldenBall otherwise it'll remail same
	private boolean changeBall()
	{
		if (moveX == 0 && moveY == 12)
		{
			labelMaze[moveX][moveY].setIcon(goldBallWhite);//setting image in the goal
			playGameSound("ballSound.wav");//plays sound while changing the image in goal
			digitalTimer.stop();//this stops the timer after reaching the goal
			JOptionPane.showMessageDialog(null, "Congratulations");//displays message congratulations
			return true;
		} 
		else
			return false;
	}

	//this method checks whether there is sand image or not while moving down.
	// If there is sand then the ball will move, otherwise the ball won't move.
	private boolean checkWhiteDown() 
	{
		if (moveX != 0 && labelMaze[moveX][moveY + 1].getIcon() == sandIcon)
			return true;
		else
			return false;
	}

	//this method checks whether there is sand image or not while moving up.
	// If there is sand then the ball will move up, otherwise the ball won't move.
	private boolean checkWhiteUp()
	{
		if (moveY != 0 && labelMaze[moveX][moveY - 1].getIcon() == sandIcon)
			return true;
		else
			return false;
	}

	//this method makes the ball fall down automatically
	public void ballFallDown()
	{

		fallTimer.start();

	}

	//this method is created for the sound production
	public void playGameSound(String mySound) 
	{
		try 
		{
			//these are the codes which may cause exception
			play();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sound/" +mySound));
			soundPlay = AudioSystem.getClip();
			soundPlay.open(inputStream);
			soundPlay.start();
		}
		catch(Exception e)
		{
			//these are error handling code
			e.printStackTrace();
			play();
		}
	}
	
	public void play()
	{
		if(soundPlay!=null) 
		{
			soundPlay.stop();
			soundPlay.close();
			soundPlay = null;
		}
	}

	public void actionPerformed(ActionEvent event) 
	{
		
		//it returns the events associated with Option1
		if (event.getActionCommand().equals("Option1")) 
		{
			JOptionPane.showMessageDialog(null, "Use 4 buttons to control the ball");
			clickOption2=false;
			clickOption3=false;
			jTFOptionField.setText("1");
			jBMyRun.setEnabled(false);
			digitalTimer.start();
			jBMyAct.setEnabled(true);
			jBBackward.setEnabled(true);
			jBUpward.setEnabled(true);
			jBForward.setEnabled(true);
			jBDownward.setEnabled(true);
			
		}
		//it returns the events associated with Option2
		if (event.getActionCommand().equals("Option2"))
		{
			JOptionPane.showMessageDialog(null, "You're in level2");
			clickOption2=true;
			clickOption3=false;
			jTFOptionField.setText("2");
			jBMyRun.setEnabled(false);
			digitalTimer.start();
			//jBMyRun.setEnabled(true);
			jBMyAct.setEnabled(true);
			jBBackward.setEnabled(true);
			jBUpward.setEnabled(true);
			jBForward.setEnabled(true);
			jBDownward.setEnabled(true);
		}
		//it returns the events associated with Option3
		if (event.getActionCommand().equals("Option3"))
		{
			JOptionPane.showMessageDialog(null, "Use Run button for this level");
			clickOption3=true;
			clickOption2=false;
			jTFOptionField.setText("3");
			jBMyRun.setEnabled(true);
			jBMyAct.setEnabled(false);
			jBBackward.setEnabled(false);
			jBUpward.setEnabled(false);
			jBForward.setEnabled(false);
			jBDownward.setEnabled(false);
			digitalTimer.start();
			
		}
		//it returns the events which is associated with JButton jBbackward
		if (event.getSource() == jBBackward)
		{
			if (checkWhiteLeft() == true || checkGoal() == true) 
			{
				moveLeft();
				changeBall();
				digitalTimer.start();
			}
		}
		//it returns the events which is associated with JButton jBForward
		if (event.getSource() == jBForward)
		{
			if (checkWhiteRight() == true)
			{
				moveRight();
				digitalTimer.start();
			}
		}
		//it returns the events which is associated with JButton jBDownward
		if (event.getSource() == jBDownward)
		{
			if (checkWhiteDown() == true)
			{
				moveDown();
				digitalTimer.start();
			}
		}
		//it returns the events which is associated with JButton jBUpward
		if (event.getSource() == jBUpward)
		{
			if (checkWhiteUp() == true)
			{
				moveUp();
				digitalTimer.start();
			}
		}
		
		if (event.getSource() == jBMyAct)
		{

			myAct();//method calling
			digitalTimer.start();

		}
		
		if (event.getSource() == jBReset) //if you click on button reset
		{
			dispose();//destroy the current window
			createInterface();
			
		}
		
		if (event.getActionCommand().equals("Run"))
		{
			jBMyRun.setIcon(new ImageIcon("img/pause.png"));
			jBMyRun.setText("Pause");
			jBMyRun.setBounds(110, 15, 100, 30);
			finalRun();
			digitalTimer.start();
			playGameSound("left.wav");
			
		}
		
		if (event.getActionCommand().equals("Pause")) 
		{
			jBMyRun.setIcon(new ImageIcon("img/run.png"));
			jBMyRun.setText("Run");
			jBMyRun.setBounds(110, 15, 80, 30);
			ballRunTimer.stop();
			digitalTimer.stop();
			soundPlay.stop();
			changeBall();
		}
		
		if (event.getActionCommand().equals("Exit"))
		{
			System.exit(0);//it close the GUI
		}
	}

}
