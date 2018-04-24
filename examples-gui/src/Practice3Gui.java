import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Practice3Gui extends JFrame {

	private static final long serialVersionUID = 3794059922116115530L;

	private JTextField aTextField = new JTextField();
	private JTextArea bTextArea = new JTextArea();
	private JTextField cTextField = new JTextField();
//	private JLabel enterName = new JLabel();
//	private CardLayout cardLayout = new CardLayout();
//	private JOptionPane optionPane = new JOptionPane();
	public String setTime = null;

	private JFrame frame = new JFrame();
	

	// private JButton doubleButton = new JButton("Double or Nothing");
	private int numDollars = 10;
	
	private Random random = new Random();

	// ----- Method for adding desired button ------------
	private JPanel getBottomPanel() {
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new GridLayout(0, 1));

		// ---- First Button (Double or Nothing) -----------
		JButton doubleButton = new JButton("Double or nothing");
		doubleButton.setMnemonic('D');
		doubleButton.setToolTipText("Click here");
		doubleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (random.nextFloat() < 0.5)
					numDollars = numDollars * 2;
				else
					numDollars = 0;
				updateTextField();
			}
		});

		// ---- Second Button (Add a Dollar) -----------
		JButton addButton = new JButton("Add a dollar");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println("First button got click");
				numDollars++;
				updateTextField();
			}
		});

		// ---- Third Button (Remove a Dollar) -----------
		JButton removeButton = new JButton("Remove a dollar");
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println("Second button got click");
				numDollars = numDollars - 1;
				updateTextField();
			}
		});

		// ---- Fourth Button (Reset Game) -----------
		JButton resetButton = new JButton("Reset Game");
		resetButton.setMnemonic('R');
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println("Reset button got click");
				numDollars = 10;
				updateTextField();
			}
		});
		// ---- Fifth Button ($10 Bonus) -----------
		JButton bonusButton = new JButton("$10 Bonus");
		bonusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println("Bonus button got click");
				numDollars = numDollars + 10;
				updateTextField();
			}
		});
		// ---- Sixth Button (Cancel) -----------
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

		
		// ---- Seventh Button (Start Over) -----------
		JButton startOverButton = new JButton("Start Over");
		startOverButton.setMnemonic('S');
		startOverButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.out.println("Cancel button got click");

				updateTextField();
			}
		});

		// ---- adding the button to myPanel ----------
		myPanel.add(doubleButton);
		myPanel.add(addButton);
		myPanel.add(removeButton);
		myPanel.add(bonusButton);
		myPanel.add(resetButton);
		myPanel.add(cancelButton);
		myPanel.add(startOverButton);

		return myPanel;
	}
	
	// ----- a helper method to update the display -----
	private void updateTextField() {
		aTextField.setText("You have $" + numDollars);
		bTextArea.setText("Your start point was $10.\nYour time is: " + setTime); //+ setTime
		cTextField.setText("HI");
		validate();
	}

	// ----- practice method/ double or nothing
	public Practice3Gui() {
		super("Biological knowledge test");
		setLocationRelativeTo(null);
		setSize(500, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottomPanel(), BorderLayout.EAST);
		getContentPane().add(aTextField, BorderLayout.CENTER);
		getContentPane().add(bTextArea, BorderLayout.SOUTH);
		setJMenuBar(myMenuBar());
		updateTextField();
		setVisible(true);
	}

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
				this.numDollars = Integer.parseInt(strToken.nextToken());
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

	// ---- method for save to file
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
			myWriter.write(this.numDollars + "\n");
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

		JMenu optionsMenu = new JMenu("Options");
		optionsMenu.setMnemonic('O');
		myMenu.add(optionsMenu);
		
		JMenuItem duration = new JMenuItem ("Duration");
		duration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String timeMessage =  JOptionPane.showInputDialog("Please set the test time"); 
				updateTextField();
				//String setTime = timeMessage.getText();
				//how to pass variable from actionlistner to frame
			}
		});
		optionsMenu.add(duration);
 
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
	// -----------
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

	// ---- main method ----------
	public static void main(String[] args) {
		//String fn  =  JOptionPane.showInputDialog("Please enter your name: ");
		//JOptionPane.showMessageDialog(null, "Ok, " + fn + ". Are you ready to test your biological knowledge?");
		//JOptionPane.showMessageDialog(null, "The answer is " + fn);

		new Practice3Gui();

		//JOptionPane.showMessageDialog(null, "The answer is " + fn);
		//JOptionPane.showInputDialog("Question 1");
		//String result = "Well done!";
		//JOptionPane.showMessageDialog(null, result);
//JFrame.DISPOSE_ON_CLOSE;
	}
}