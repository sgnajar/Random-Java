import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class aminoQuiz extends JFrame {
	private static final long serialVersionUID = 3794059922116115530L;
	private JLabel label1 = new JLabel();
	private JLabel label2 = new JLabel();
	private JLabel myTimer = new JLabel();
	private JTextField tf = new JTextField();
	private JFrame frame = new JFrame();
	private JProgressBar progBar = new JProgressBar() ;
	private boolean cancel = false;
	private int numQues = 0;
	private int numTimes = 0;
	private String s1 = null;
	private int score = 0;
	boolean loop = true;
	Random random = new Random();
	private int TrueRes = 0;
	private int FalseRes = 0; 

	public static final String[] oneLetterAA = 
		{"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V" };
	public static final String[] threeLetterAA = 
		{"alanine","arginine","asparagine","aspartic acid","cysteine","glutamine","glutamic acid","glycine","histidine","isoleucine", 
		"leucine","lysine","methionine","phenylalanine","proline","serine","threonine","tryptophan","tyrosine","valine"};
	
	private JPanel getBottomPanel() {
		JPanel myPanel = new JPanel(new GridBagLayout());
		myPanel.setLayout(new GridBagLayout());

		// ---- Start Button --------
		JButton startButton = new JButton("Start");
		startButton.setToolTipText("Click here");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				{					
					String bigList[] = new String[61];
					for (int i=0; i < bigList.length; i++) {
						bigList[i] = Integer.toString(i);
					}
					numQues = Integer.parseInt ((String) JOptionPane.showInputDialog(frame, "Pick a number of questions", "Set no. questions", 
							JOptionPane.QUESTION_MESSAGE, null, bigList, "Titan"));

					//label1.setText("Time left: " + numTimes);
					cancel = false;
					startButton.setEnabled(false);
					new Thread (new startActionRunnable()).start();

					updateTextField();
				}
			}
		});

		// ----  Cancel Button ----------------
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setMnemonic('C');
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int response = JOptionPane.showConfirmDialog(frame, "Are you sure?","Confirm Cancel",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION){
					cancel = true;
					//System.out.println("Yes button clicked");
					JOptionPane.showMessageDialog(frame, "Your total score is: " + score + " point");

				}else if (response == JOptionPane.NO_OPTION ){
					//System.out.println("No button clicked");						//Not exactly working yet .. if yes do sth if not go back 
					//Test continue
					
				}else if (response == JOptionPane.CLOSED_OPTION){
					//System.out.println("JOptionPane closed");
				}
				updateTextField();
			}
		});

		// ---- Restart Button ------------
		JButton reStartButton = new JButton("Restart");
		reStartButton.setMnemonic('R');
		reStartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reStartButton.setEnabled(true);

				new aminoQuiz();
				dispose();
				updateTextField();
			}
		});
		
		// ---- Exit Button ------------
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				JDialog.setDefaultLookAndFeelDecorated(true);
				int response = JOptionPane.showConfirmDialog(frame, "Do you want to quit the quiz?", "Confirm Exit", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION){
					//System.out.println("No button clicked");
				}else if (response == JOptionPane.YES_OPTION ){
					if(e.getSource() == exitButton)System.exit(0);
					//System.out.println("Yes button clicked");
				}else if (response == JOptionPane.CLOSED_OPTION){
					//System.out.println("JOptionPane closed");
				}
				updateTextField();
			}
		});
		
		// ---- adding the button to myPanel ----------
		myPanel.add(startButton);
		myPanel.add(cancelButton);
		myPanel.add(reStartButton);
		myPanel.add(exitButton);
		myPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		progBar = new JProgressBar(0 /*,.getLengthOfTask()*/);
		progBar.setValue(0);
		progBar.setStringPainted(true);
		return myPanel;
	}

	
	// ----- a helper method to update the display -----
	private void updateTextField() {
		label1.setText("Number of questions left: " + numQues);
		label2.setText("What is the single letter for '" + "_______" + "' ?");
		myTimer.setText("Time Left: ");
		validate();
	}
	// ---- method for loading from a file -------------------
	private void loadFromFile() {
		JFileChooser chooseFile = new JFileChooser();
		if (chooseFile.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		if (chooseFile.getSelectedFile() == null)
			return;
		File selectedFile = chooseFile.getSelectedFile();

		try {
			BufferedReader myReader = new BufferedReader(new FileReader(selectedFile));
			String LineOnFile = myReader.readLine();
			if (LineOnFile == null || myReader.readLine() != null)
				throw new Exception("WRONG FILE FORMAT WAS SELECTED, TRY ANOTHER FILE");
			StringTokenizer strToken = new StringTokenizer(LineOnFile);
			if (strToken.countTokens() != 1)
				throw new Exception("WRONG FILE FORMAT WAS SELECTED, TRY ANOTHER FILE");
			try {
				this.score = Integer.parseInt(strToken.nextToken());
			} catch (Exception error) {
				throw new Exception("WRONG FILE FORMAT WAS SELECTED, TRY ANOTHER FILE");
			}
			updateTextField();
			myReader.close(); // ?
		} catch (Exception error) {
			error.printStackTrace();
			JOptionPane.showMessageDialog(this, error.getMessage(), "Error reading file", JOptionPane.ERROR_MESSAGE);
		}
	}
	// ---- method for saving to a file ------------
	private void saveToFile() {
		JFileChooser chooseFile = new JFileChooser();
		if (chooseFile.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		if (chooseFile.getSelectedFile() == null)
			return;

		File selectedFile = chooseFile.getSelectedFile();
		if (chooseFile.getSelectedFile().exists()) {
			String warningMessage = chooseFile.getSelectedFile()
					+ ". \nThis file already exist. Do you want to overwrite it?";

			if (JOptionPane.showConfirmDialog(this, warningMessage) != JOptionPane.YES_OPTION)
				return;
		}
		try {
			BufferedWriter myWriter = new BufferedWriter(new FileWriter(selectedFile));
			myWriter.write(this.score + "\n");
			myWriter.flush();
			myWriter.close();
		} catch (Exception error) {
			error.printStackTrace();
			JOptionPane.showMessageDialog(this, error.getMessage(), "Error writing file", JOptionPane.ERROR_MESSAGE);
		}
	}
	// ---- method to create a menu bar and adding stuff to it -----
	private JMenuBar myMenuBar() {
		JMenuBar myMenu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		myMenu.add(fileMenu);

		JMenuItem newQuiz = new JMenuItem("New");
		newQuiz.setMnemonic('N');
		fileMenu.add(newQuiz);
		
		JMenuItem openMenu = new JMenuItem("Open");
		openMenu.setMnemonic('O');
		fileMenu.add(openMenu);

		openMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFromFile();
			}
		});

		JMenuItem saveMenu = new JMenuItem("Save");
		fileMenu.add(saveMenu);
		saveMenu.setMnemonic('S');

		saveMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveToFile();
			}
		});
		
		JMenuItem exitMenu = new JMenuItem("Exit");
		fileMenu.add(exitMenu);
		exitMenu.setMnemonic('E');
		
 		exitMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				int response = JOptionPane.showConfirmDialog(frame, "Do you want to quit the quiz?", "Confirm Exit", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION){
					//System.out.println("No button clicked");
				}else if (response == JOptionPane.YES_OPTION ){
					if(e.getSource() == exitMenu)System.exit(0);
					//System.out.println("Yes button clicked");
				}else if (response == JOptionPane.CLOSED_OPTION){
					//System.out.println("JOptionPane closed");
				}
				updateTextField();
			}
		});

		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.setMnemonic('S');
		myMenu.add(settingsMenu);
		
		JMenuItem duration = new JMenuItem ("Duration");
		duration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String askTime =  JOptionPane.showInputDialog("Please set the test time"); 
				numQues = Integer.parseInt(askTime);
				updateTextField();
			}
		});
		
		JMenuItem Shortcuts = new JMenuItem ("Shortcuts");
		Shortcuts.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed (ActionEvent arg0) {
				updateTextField();
			}
		});
		
		settingsMenu.add(duration);
//		settingsMenu.add(Shortcuts);

 		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		myMenu.add(helpMenu);

		JMenuItem About = new JMenuItem("About");
		About.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				updateTextField();
			}
		});
		helpMenu.add(About);

		return myMenu;
	}
	//-----------------------------------------------------------
	private void getUserInput()
	{
		String userInput = tf.getText();
		//System.out.println("User input + " + userInput + " "+ userInput.length());
		
		while( userInput == null || userInput.length() == 0) 
		{

			userInput =  tf.getText();
			
			if(userInput != null ){
				userInput = userInput.toUpperCase();
			}
			//System.out.println("The letter entered is: " + userInput);
		}
			if (userInput.toString().equals(oneLetterAA[rndPos3aa]))
			{
					//score++;
					TrueRes++;
				}else if (userInput != oneLetterAA[rndPos3aa])
				{
					FalseRes++;
				}
			numQues = numQues - 1;
		}	

		
	//	Thread.sleep(1000);

	int rndPos3aa;
	private void setUp()
	{
		label1.setText("Number of questions left: " + numQues);
		rndPos3aa= random.nextInt(threeLetterAA.length);
		s1 = threeLetterAA[rndPos3aa];
		label2.setText("What is the single letter for '" + s1 + "' ?");
	}
	
		private void tryAgainMenu()
		{
			String stringArray[] = {"OK" , "Report", "Try again"};
			int act = JOptionPane.showOptionDialog(frame, "Time out or cancelled, Check your report", "Select an Option",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, stringArray[0]);
			
			if (act == JOptionPane.YES_OPTION)
			{
				JOptionPane.showMessageDialog(frame, "The summary of this test is: " 
						+ "\nTrue Answers: " + TrueRes + " point" + "\nFalse Answers: " + FalseRes + " point");
			}else if (act == JOptionPane.NO_OPTION )
			{
				JOptionPane.showMessageDialog(frame, "The summary of this test is: " 
						+ "\nTrue Answers: " + TrueRes + " point" + "\nFalse Answers: " + FalseRes + " point");
				
			}else if (act == JOptionPane.CLOSED_OPTION)
			{
				
			}
				
			try 
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run ()
					{
						if (numQues == 0)
							loop = false;
					}
				});
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	private class startActionRunnable implements Runnable 
	{
		public void run()
		{
			//label1.setText(Double.toString(numTimes));
			long start = System.currentTimeMillis();
			long end = start + numQues * 1000;
			long timeLeft = ((end-start)/1000);
			
			try {
				for (int i = numTimes; i>0; i--)
				{
					Thread.sleep(1000);
				}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			
			while (!cancel && numQues > 0)
			{
				setUp();
				getUserInput();
				tf.setText("");

				timeLeft =((end-start)/1000);
				//label1.setText(Double.toString(timeLeft));
				if (timeLeft <= 0)
				{
					break;
				}
				
				try 
				{
					Thread.sleep(300);
				}
				catch (InterruptedException e)
				{
					return;
				}
			}
			//label1.setText("Time left: 0");
				tryAgainMenu();	
			}
		
		}	
	 

	// -------------------------------------------------------------
	// ----- Constructor---------------------------------------
	public aminoQuiz() {
		super("Aminoacid test");
		//setLocationRelativeTo(null);
		setLocationByPlatform(true);
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout Layout = new GridBagLayout();
		getContentPane().setLayout(Layout);
		GridBagConstraints gbc = new GridBagConstraints();

//		gbc.gridx = 15;
		gbc.gridy = 2;
		gbc.weightx = 4;
		gbc.weighty = 1.0;
//		gbc.gridheight = 2;
//		gbc.gridwidth = 0;
//		gbc.ipadx = 1;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		getContentPane().add(label1, gbc);
		
//		gbc.gridx = 0;
		gbc.gridy = 15;
		gbc.weightx = 15;
		gbc.weighty = 1.0;
//		gbc.gridheight = 2;
//		gbc.gridwidth = 0;
//		gbc.ipadx = 1;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		getContentPane().add(label2, gbc);
		
//		gbc.gridx = 0;
		gbc.gridy = 20;
		gbc.weightx = 9;
		gbc.weighty = 1.0;
//		gbc.gridheight = 2;
//		gbc.gridwidth = 0;
//		gbc.ipadx = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_END;
		getContentPane().add(getBottomPanel(), gbc);
		
		gbc.gridy = 15;
		gbc.weightx = 10;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LINE_END;
		getContentPane().add(tf, gbc);
		setJMenuBar(myMenuBar());
		updateTextField();
		setVisible(true);
		//new Thread(new startActionRunnable()).start();
	}
	//=======================================================	
	// ---- main method ----------
	public static void main(String[] args) 
	{
		new aminoQuiz();
	}

}