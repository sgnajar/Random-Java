import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class myProtQuizBackup extends JFrame {
	private static final long serialVersionUID = 3794059922116115530L;
	private JTextField aTextField = new JTextField();
	private JTextArea bTextArea = new JTextArea();
	private JTextField cTextField = new JTextField();
	private JLabel label1 = new JLabel();
	private JLabel label2 = new JLabel();
	private JFrame frame = new JFrame();
	private int report;
	// ----- Method for adding desired button ------------
	private JPanel getBottomPanel() {
		JPanel myPanel = new JPanel(new BorderLayout());
		
		
		myPanel.setLayout(new GridLayout(1, 2, 1, 1));

		// ---- First Button (Double or Nothing) --------
		JButton startButton = new JButton("Start");
		//startButton.setMnemonic('S');
		startButton.setToolTipText("Click here");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				updateTextField();
			}
		});
		
		// ----  Second Button (Pause) ----------------
		JButton pauseButton = new JButton ("Pause");
		pauseButton.setMnemonic('P');
		pauseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				updateTextField();

			}
		});

		// ----  Third Button (Cancel) ----------------
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setMnemonic('C');
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int response = JOptionPane.showConfirmDialog(frame, "Are you sure?","Cancel",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION){
					//System.out.println("Yes button clicked");
					//Give REPORT
				}else if (response == JOptionPane.NO_OPTION ){
					//System.out.println("No button clicked");						//Not exactly working yet .. if yes do sth if not go back 
					//Test continue
				//}else if (response == JOptionPane.CANCEL_OPTION ){
					//System.out.println("Cancel button clicked");
				}else if (response == JOptionPane.CLOSED_OPTION){
					//System.out.println("JOptionPane closed");
				}
				updateTextField();
			}
		});
		
		// ---- Fourth Button (Start Over) ------------
		JButton startOverButton = new JButton("Restart");
		startOverButton.setMnemonic('R');
		startOverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println("Cancel button got click");

				updateTextField();
			}
		});
		
		// ---- adding the button to myPanel ----------
		myPanel.add(startButton);
		myPanel.add(pauseButton);
		myPanel.add(cancelButton);
		myPanel.add(startOverButton);
		//myPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//////-----------
		GridBagLayout Layout = new GridBagLayout();
		getContentPane().setLayout(Layout);
		GridBagConstraints gbc = new GridBagConstraints();
		//myPanel.add(getBottomPanel(),gbc.SOUTH);
		//================
		
		return myPanel;
	}
	
	///==============================================

	//======================================================
	// ----- a helper method to update the display -----
	private void updateTextField() {
		//bTextArea.setText("Point: \nTime: ");
		label1.setText("Time Left: " + " s");
		label2.setText("What is the Single letter for (aminoacid) ?" );

		validate();
	}
	// ------- load form file ---------------------------------------
	// -------------------------------------------------------------
	// ---- method for load from file
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
				this.report = Integer.parseInt(strToken.nextToken());
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
	// -------------------------------------------------------------
	// ---- method for save to file
	// ------- save form file ---------------------------------------
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
			myWriter.write(this.report + "\n");
			myWriter.flush();
			myWriter.close();
		} catch (Exception error) {
			error.printStackTrace();
			JOptionPane.showMessageDialog(this, error.getMessage(), "Error writing file", JOptionPane.ERROR_MESSAGE);
		}
	}
	// -------------------------------------------------------------
	// ---- method to create a menu bar and adding stuff to it -----
	// ------- Menu bar ---------------------------------------
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
				int response = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Exit", 
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
				int userTime = Integer.parseInt(askTime);
				System.out.println(userTime);
				updateTextField();
				cTextField.setText(askTime);

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
		settingsMenu.add(Shortcuts);

 
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
	// -------------------------------------------------------------
	// ----- Constructor---------------------------------------
	public myProtQuizBackup() {
		super("Aminoacid test");
		//setLocationRelativeTo(null);
		setLocationByPlatform(true);
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel myPanel = new JPanel(new BorderLayout());
		GridBagLayout Layout = new GridBagLayout();
		getContentPane().setLayout(Layout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill=GridBagConstraints.HORIZONTAL;
		
		
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridheight = 2;
		gbc.gridwidth = 0;
		gbc.ipadx = 1;
		getContentPane().add(label1, gbc);
		
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridheight = 2;
		gbc.gridwidth = 0;
		gbc.ipadx = 1;
		getContentPane().add(label2, gbc);
//		
//		getContentPane().add(label2, gbc.CENTER);
//		getContentPane().add(bTextArea, gbc.EAST);
		
		
		
		
		gbc.anchor = GridBagConstraints.SOUTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		
		getContentPane().add(getBottomPanel(), gbc);
//		myPanel.add(getBottomPanel(),gbc.SOUTH);
		
		setJMenuBar(myMenuBar());
		updateTextField();
		setVisible(true);
	}
	
//=======================================================	
		public static final String[] oneLetterAA = 
			{
			"A","R", "N", "D", "C", "Q", "E", 
			"G",  "H", "I", "L", "K", "M", "F", 
			"P", "S", "T", "W", "Y", "V" 
			};
		public static final String[] threeLetterAA = 
			{
			"alanine","arginine", "asparagine", 
			"aspartic acid", "cysteine",
			"glutamine",  "glutamic acid",
			"glycine" ,"histidine","isoleucine",
			"leucine",  "lysine", "methionine", 
			"phenylalanine", "proline", 
			"serine","threonine","tryptophan", 
			"tyrosine", "valine"
			};

//		final long start = System.currentTimeMillis();
//		Scanner input1 = new Scanner(System.in);
//		int score = 0;
//		Random random = new Random();
//		long testTime = 10000; //instead of userTime*1000
//		final long end = start + testTime;
//		boolean loop = true;
//		{		
//		while (loop && System.currentTimeMillis() < end)
//		{
//			//System.out.println(start + ", " + System.currentTimeMillis() + ", " + end);
//			int n1 = random.nextInt(threeLetterAA.length);
//			String s1 = threeLetterAA[n1];
//					
//			System.out.println("What is the letter that assigned to '"+s1 +"'?");
//			System.out.print("Your answer is: ");		
//			String userinput = input1.nextLine();
//			userinput = userinput.toUpperCase(); 
//			System.out.println("The letter you entered is: " + userinput);
//		
//					
//			for (int i=0; i < oneLetterAA.length; i++);
//			{
//				if (userinput.equals(oneLetterAA[n1]))
//				{
//					score++;
//					System.out.println("\tCorrect!" + " Your score is " + score);
//				}
////				else if (userinput2==quit) 
////				{
////					loop = false;
////				}
//				
//				else 
//				{
//					System.out.println("\tWRONG LETTER ENTERED\n" + "The test ends with score of "+ score);
//					loop = false;
//								
//				}
//					
//			}
//			final long end2 = System.currentTimeMillis();
//			System.out.println("Took: " + ((end2-start)/1000f) + " seconds");
//		}
//		}
//=======================================================	

	// ---- main method ----------
	public static void main(String[] args) {
		new myProtQuiz();
		}
}