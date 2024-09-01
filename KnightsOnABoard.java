/**
 * test comment
 */

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class KnightsOnABoard {
	public static void main(String[] args) throws FileNotFoundException{
		KnightsOnABoard game = new KnightsOnABoard();
		Scanner scnr = new Scanner(System.in);

		File validatedFile = new File("TEMPVAL");

		while (true) {
			System.out.print("Please enter the name of a valid file: ");
			String fileName = "TEMPVAL";
			try {
				fileName = scnr.nextLine();
			} catch (NoSuchElementException e) {
				System.exit(0);
			}
			File inputFile = new File(fileName);
			validatedFile = game.validateFile(inputFile, scnr);
			if (game.validateData(validatedFile)) {
				break;
			} else {
				System.out.println("File has invalid data.");
				continue;
			}
		}

		int[][] board = new int[8][8];
		board = game.populateBoard(validatedFile);
		System.out.println();

		if (game.cannotCapture(board)) {
			System.out.println("No knights can capture any other knight.");
		} else {
			System.out.println("There is at least one knight which can capture another knight.");
		}
	}

	public File validateFile(File inputFile, Scanner scnr) {
		if (inputFile.exists()) {
			return inputFile;
		} else {
			System.out.println("File does not exist!");
			System.out.print("Please enter the name of a valid file: ");
			String fileName = "TEMPVAL";
			try {
				fileName = scnr.nextLine();
			} catch (NoSuchElementException e) {
				System.exit(0);
			}
			File newFile = new File(fileName);
			return validateFile(newFile, scnr);
			}
		}

	public boolean validateData(File inputFile) throws FileNotFoundException {

		Scanner fileScnr;
		ArrayList<String> lines = new ArrayList<String>();

		try {
			fileScnr = new Scanner(inputFile);
			while (fileScnr.hasNextLine()) {
			String currentLine = fileScnr.nextLine();
			lines.add(currentLine);
				}
			} catch (FileNotFoundException e) {
				return false;
			}
		
		fileScnr.close();
		boolean eightRows = lines.size() == 8;

		if (eightRows) {
			try {
				Scanner fileScnrTwo = new Scanner(inputFile);
				while (fileScnrTwo.hasNextLine()) {
					String currentRow = fileScnrTwo.nextLine();
					String[] numbers = currentRow.split("\\s+");
					if (numbers.length != 8) {
						fileScnrTwo.close();
						return false;
					}
					for (String number : numbers) {
						try {
							Integer.parseInt(number);
						} catch (NumberFormatException e) {
							fileScnrTwo.close();
							return false;
						}
					}
				}
				fileScnrTwo.close();
				return true;
			} catch (FileNotFoundException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	public int[][] populateBoard(File inputFile) throws FileNotFoundException {
		int[][] board = new int[8][8];
		Scanner fileScnr = new Scanner(inputFile);
		boolean invalidVals = false;

		// Fill in board array
		int i = 0;
		while (fileScnr.hasNextLine()) {
			String currentRow = fileScnr.nextLine();
			String[] rowStr = currentRow.split("\\s+");

			int[] rowInt = new int[8];
			int j = 0;
			for (String numString : rowStr) {
				int numInt = Integer.parseInt(numString);
				if (numInt > 1) {
					invalidVals = true;
					rowInt[j] = 1;
				} else if (numInt < 0) {
					invalidVals = true;
					rowInt[j] = 0;
				} else {
					rowInt[j] = numInt;
				}

				j++;
			}

			board[i] = rowInt;
			i++;
			}
		
		fileScnr.close();

		printBoard(board, invalidVals);

		return board;
		}

		public void printBoard(int[][] chessBoard, boolean invalidVals) {
			System.out.println();
			if (invalidVals) {
				System.out.println("Positive values greater than 1 were detected and will be converted to 1 and/or that negative values smaller than 0 were detected and will be converted to 0");
				System.out.println("");
				System.out.println("");
			}
			System.out.println("The board looks as follows:");
			System.out.println();
			for (int[] row : chessBoard) {
				for (int number : row) {
					System.out.print(number + " ");
				}
				System.out.println();
			}
		}

		public boolean cannotCapture(int[][] chessBoard) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; ++j) {
					if (chessBoard[i][j] == 1) {
						try {
							if (chessBoard[i - 2][j - 1] == 1) {
								return false;
							}
						} catch (IndexOutOfBoundsException e) {
						}

						try {
							if (chessBoard[i - 2][j + 1] == 1) {
								return false;
							}
						} catch (IndexOutOfBoundsException e) {
						}

						try {
							if (chessBoard[i + 2][j - 1] == 1) {
								return false;
							}
						} catch (IndexOutOfBoundsException e) {
						}

						try {
							if (chessBoard[i + 2][j + 1] == 1) {
								return false;
							}
						} catch (IndexOutOfBoundsException e) {
						}

						try {
							if (chessBoard[i - 1][j - 2] == 1) {
								return false;
							}
						} catch (IndexOutOfBoundsException e) {
						}

						try {
							if (chessBoard[i - 1][j + 2] == 1) {
								return false;
							}
						} catch (IndexOutOfBoundsException e) {
						}

						try {
							if (chessBoard[i + 1][j - 2] == 1) {
								return false;
							}
						} catch (IndexOutOfBoundsException e) {
						}

						try {
							if (chessBoard[i + 1][j + 2] == 1) {
								return false;
							}
						} catch (IndexOutOfBoundsException e) {
						}
					}
				}
			}
			return true;
		}
	}