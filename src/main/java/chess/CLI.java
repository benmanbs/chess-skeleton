package chess;

import chess.moves.Move;
import chess.pieces.Piece;

import java.io.*;
import java.util.List;

/**
 * This class provides the basic CLI interface to the Chess game.
 * 
 * TODO
 * 1. move checkmate and stalemate checking logic to GameState
 * 2. add legal draw cases where there are still moves (i.e. only two kings left)
 * 3. tests tests tests
 * 
 */
public class CLI {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final String SAVED_GAMES_LOCATION = System.getProperty("user.dir") + "/__SAVED-GAMES__";

    private final BufferedReader inReader;
    private final PrintStream outStream;

    private GameState gameState = null;

    public CLI(InputStream inputStream, PrintStream outStream) {
        this.inReader = new BufferedReader(new InputStreamReader(inputStream));
        this.outStream = outStream;
        writeOutput("Welcome to Chess!");
    }

    /**
     * Write the string to the output
     * @param str The string to write
     */
    private void writeOutput(String str) {
        this.outStream.println(str);
    }

    /**
     * Retrieve a string from the console, returning after the user hits the 'Return' key.
     * @return The input from the user, or an empty-length string if they did not type anything.
     */
    private String getInput() {
        try {
            this.outStream.print("> ");
            return inReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from input: ", e);
        }
    }

    void startEventLoop() {
        writeOutput("Type 'help' for a list of commands.");
        doNewGame();

        while (true) {
            showBoard();
        	if(gameState.listAllMoves().isEmpty()){
        		if(gameState.checkIfInCheck(gameState.getCurrentPlayer()))
        			writeOutput("The game is over.  Congrats to " + (gameState.getCurrentPlayer()==Player.Black?"White":"Black") + ".");
        		else
        			writeOutput("The game is over. Stalemate");
        		System.exit(0);
        	}
            writeOutput(gameState.getCurrentPlayer() + "'s Move");

            String input = getInput();
            if (input == null) {
                break; // No more input possible; this is the only way to exit the event loop
            } else if (input.length() > 0) {
                if (input.equals("help")) {
                    showCommands();
                } else if (input.equals("new")) {
                    doNewGame();
                } else if (input.equals("quit")) {
                    writeOutput("Goodbye!");
                    System.exit(0);
                } else if (input.equals("board")) {
                    writeOutput("Current Game:");
                } else if (input.equals("list")) {
                	//List all moves.
                    showMoves(gameState.listAllMoves());
                } else if (input.startsWith("move")) {
                    String[] command = input.split(" ");
                    boolean moved = gameState.executeMove(new Move(new Position(command[1]),new Position(command[2])));
                    if(!moved) {
                    	writeOutput("Illegal move.");
                    } else {
                        if(gameState.checkIfInCheck(gameState.getCurrentPlayer()))
                        	writeOutput("Check");
                    }
                } else if (input.startsWith("save")) {
                    String name = input.split(" ")[1];
                    doWriteGameToFile(name);
                } else if (input.startsWith("load")) {
                    String name = input.split(" ")[1];
                    doCreateGameFromFile(name);
                } else {
                    writeOutput("I didn't understand that.  Type 'help' for a list of commands.");
                }
            }
        }
    }

    private void doNewGame() {
        gameState = new GameState();
        gameState.reset();
    }

    private void doWriteGameToFile(String name) {
        //Create saves directory if it doesn't exist
        new File(SAVED_GAMES_LOCATION).mkdirs();

        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(SAVED_GAMES_LOCATION + "/" + name + ".txt"), "utf-8"));
            writer.write("player:" + gameState.getCurrentPlayer() + "\n");
            writer.write(getBoardAsString(false));
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {}
        }
    }

    private void doCreateGameFromFile(String name) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(SAVED_GAMES_LOCATION + "/" + name + ".txt"));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            //strip out player
            boolean black = false;
            if(line.startsWith("player")) {
                black = line.contains("Black");
                line = reader.readLine();
            }

            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            gameState = new GameState(sb.toString());
            if(black) {
                gameState.changePlayer();
            }
        } catch(FileNotFoundException e){
            writeOutput("Invalid game name provided");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {reader.close();} catch (Exception ex) {}
        }
    }

    private void showBoard() {
        writeOutput(getBoardAsString(true));
    }

    private void showCommands() {
        writeOutput("Possible commands: ");
        writeOutput("    'help'                       Show this menu");
        writeOutput("    'quit'                       Quit Chess");
        writeOutput("    'new'                        Create a new game");
        writeOutput("    'board'                      Show the chess board");
        writeOutput("    'list'                       List all possible moves");
        writeOutput("    'move <colrow> <colrow>'     Make a move");
    }
    
    private void showMoves(List<Move> moves) {
    	for(Move m : moves) {
    		writeOutput(m.toString());
    	}
    }

    /**
     * Display the board for the user(s)
     */
    String getBoardAsString(boolean labels) {
        StringBuilder builder = new StringBuilder();

        if(labels) {
            builder.append(NEWLINE);
            printColumnLabels(builder);
        }
        for (int i = Position.MAX_ROW; i >= Position.MIN_ROW; i--) {
            printSeparator(builder, labels);
            printSquares(i, builder, labels);
        }

        printSeparator(builder, labels);
        if(labels) {
            printColumnLabels(builder);
        }

        return builder.toString();
    }


    private void printSquares(int rowLabel, StringBuilder builder, boolean labels) {
        if(labels) {
            builder.append(rowLabel).append(" ");
        }

        builder.append("| ");

        for (char c = Position.MIN_COLUMN; c <= Position.MAX_COLUMN; c++) {
            Piece piece = gameState.getPieceAt(String.valueOf(c) + rowLabel);
            char pieceChar = piece == null ? ' ' : piece.getIdentifier();
            builder.append(pieceChar).append(" | ");
        }
        if(labels) {
            builder.append(rowLabel);
        } else {
            builder.deleteCharAt(builder.length()-1);
        }
        builder.append(NEWLINE);
    }

    private void printSeparator(StringBuilder builder, boolean labels) {
        if(labels) {
            builder.append("  ");
        }
        builder.append("+---+---+---+---+---+---+---+---+").append(NEWLINE);
    }

    private void printColumnLabels(StringBuilder builder) {
        builder.append("   ");
        for (char c = Position.MIN_COLUMN; c <= Position.MAX_COLUMN; c++) {
            builder.append(" ").append(c).append("  ");
        }

        builder.append(NEWLINE);
    }

    public static void main(String[] args) {
        CLI cli = new CLI(System.in, System.out);
        cli.startEventLoop();
    }
}
