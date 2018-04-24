package cave;

import java.util.Scanner;

public class Test8 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("please enter a line: ");
		String test = input.nextLine();
		// System.out.println("you entered: " + test);

		switch (test) {
		case "start":
			System.out.println("machine started");

			int value = 0;
			Scanner input2 = new Scanner(System.in);
			do {
				System.out.println("enter an integer: ");
				value = input2.nextInt();
			} while (value != 5);

			System.out.println("Got 5!");
			break;
		case "stop":
			System.out.println("machine stopped");
			break;

		default:
			System.out.println("command not recognized");
		}
	}

}
