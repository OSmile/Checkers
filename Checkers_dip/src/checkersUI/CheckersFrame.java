package checkersUI;

import checkers.*;

import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CheckersFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
    CheckersField pan;
    ArrayList<Board> boardHistory = new ArrayList();
    private int clickedHere = 0;
    int fromPawnIndex = 0;
    int toPawnIndex = 0;

    private int userColor = 2;
    private int thinkDepth = 2;
    private boolean alreadyMoved;
    private boolean moving;
    private int useBackward = 0;
    private int useForward = 0;
    private boolean isBack = false;
    String output = "";
    int currentPositionInBoardHistory = 0;
    static int algorithm = 2;
    JMenuBar menuBar;

    public CheckersFrame() {
        this.setTitle("Checkers Dip");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.pan = new CheckersField();
        this.setSize(605, 650);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.createMenu();
        this.add(this.pan);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setVisible(true);
    }

    // Creation of menuBar
    public void createMenu() {
        this.menuBar = new JMenuBar();
        JMenu algorithm = new JMenu("Algorithm");
        ButtonGroup algorithmGroup = new ButtonGroup(); // Will be radio button

        JRadioButtonMenuItem minimaxButton = new JRadioButtonMenuItem("MiniMax");
        minimaxButton.addActionListener(var1 -> {
            System.out.println("miniMax");
            CheckersFrame.algorithm = 1;
        });
        JRadioButtonMenuItem minimaxABButton = new JRadioButtonMenuItem("MiniMaxAB");
        minimaxABButton.addActionListener(algorithm1 -> {
            System.out.println("miniMaxAB");
            CheckersFrame.algorithm = 2;
        });

        // Add to the group to check only one
        algorithmGroup.add(minimaxButton);
        algorithmGroup.add(minimaxABButton);

        if (CheckersFrame.algorithm == 1) {
            minimaxButton.setSelected(true);
            minimaxABButton.setSelected(false);
        } else {
            minimaxButton.setSelected(false);
            minimaxABButton.setSelected(true);
        }

        algorithm.add(minimaxButton);
        algorithm.add(minimaxABButton);

        // Add algo to menuBar
        menuBar.add(algorithm);

        // Levels added
        JMenu levels = new JMenu("Levels");
        menuBar.add(levels);

        ButtonGroup levelsGroup = new ButtonGroup();

        JRadioButtonMenuItem hardLevel = new JRadioButtonMenuItem("HardLevel");
        hardLevel.addActionListener(var1 -> System.out.println("HardLevel was chosen"));

        JRadioButtonMenuItem mediumLevel = new JRadioButtonMenuItem("MediumLevel");
        mediumLevel.addActionListener(var1 -> System.out.println("MediumLevel was chosen"));

        JRadioButtonMenuItem easyLevel = new JRadioButtonMenuItem("EasyLevel");
        easyLevel.addActionListener(var1 -> System.out.println("EasyLevel was chosen"));

        // Add levels to the group to check only one
        levelsGroup.add(hardLevel);
        levelsGroup.add(mediumLevel);
        levelsGroup.add(easyLevel);

        levels.add(hardLevel);
        levels.add(mediumLevel);
        levels.add(easyLevel);

        if (this.thinkDepth == 8) {
            hardLevel.setSelected(true);
            mediumLevel.setSelected(false);
            easyLevel.setSelected(false);
        } else if (this.thinkDepth == 5) {
            hardLevel.setSelected(false);
            mediumLevel.setSelected(true);
            easyLevel.setSelected(false);
        } else if (this.thinkDepth == 2) {
            hardLevel.setSelected(false);
            mediumLevel.setSelected(false);
            easyLevel.setSelected(true);
        }


        JMenu help = new JMenu("Help");
        this.menuBar.add(help);

        JMenuItem backward = new JMenuItem("Backward", null);
        backward.addActionListener(var1 -> {
            System.out.println("Backward ");
            System.out.println("Current position of that game " + CheckersFrame.this.currentPositionInBoardHistory);
            if (CheckersFrame.this.currentPositionInBoardHistory - ++CheckersFrame.this.useBackward >= 0) {
                CheckersFrame.this.isBack = true;
                CheckersFrame.this.pan.boardInit = CheckersFrame.this.boardHistory.get
                        (CheckersFrame.this.currentPositionInBoardHistory - CheckersFrame.this.useBackward);
                CheckersFrame.this.currentPositionInBoardHistory -= CheckersFrame.this.useBackward;
                CheckersFrame.this.pan.repaint();
            }
            CheckersFrame.this.useBackward = 0;

        });
        JMenuItem forward = new JMenuItem("Forward", null);
        forward.addActionListener(var1 -> {
            System.out.println("Forward ");
            if (CheckersFrame.this.currentPositionInBoardHistory + ++CheckersFrame.this.useForward < CheckersFrame.this.boardHistory.size()) {
                CheckersFrame.this.pan.boardInit = CheckersFrame.this.boardHistory.get
                        (CheckersFrame.this.currentPositionInBoardHistory + CheckersFrame.this.useForward);
                CheckersFrame.this.currentPositionInBoardHistory += CheckersFrame.this.useForward;
            }
            CheckersFrame.this.useForward = 0;
            CheckersFrame.this.pan.repaint();
        });

        JMenuItem resetBoard = new JMenuItem("Reset board", null);
        resetBoard.addActionListener(var1 -> {
            System.out.println("ResetBoard ");
            CheckersFrame.this.pan.pawns.clear();
            CheckersFrame.this.pan.boardInit.initialize();
            CheckersFrame.this.boardHistory.clear();
            CheckersFrame.this.currentPositionInBoardHistory = 0;
            CheckersFrame.this.isBack = false;
            CheckersFrame.this.pan.repaint();
        });

        JMenuItem helpingMove = new JMenuItem("Help with move", null);
        helpingMove.addActionListener(var1 -> {
            System.out.println("Help with ur moving");
            Board tempBoard = null;
            if (CheckersFrame.algorithm == 1) {
                tempBoard = Algorithm.minimax(CheckersFrame.this.pan.boardInit, CheckersFrame.this.thinkDepth, CheckersFrame.this.userColor);
            }  /* algorithmGroup = GameSearch.minimaxAB(CheckersFrame.this.pan.boardO, CheckersFrame.this.thinkDepth, CheckersFrame.this.userColor, GameSearch.minusInfinityBoard(), GameSearch.plusInfinityBoard());
             */

            assert tempBoard != null;
            Move move = tempBoard.getHistory().first().getNext();
            CheckersFrame.this.pan.bestMoves.add(move.getChecker().getPosition().get());
            CheckersFrame.this.pan.bestMoves.add(move.getDestination().get());
            CheckersFrame.this.pan.repaint();
        });
        help.add(backward);
        help.add(forward);
        help.add(helpingMove);
        help.add(resetBoard);

        this.setJMenuBar(this.menuBar);
    }

    public static void main(String[] args) {
        new CheckersFrame();
    }

    public void mouseClicked(MouseEvent e) {
        int testClick = 0;

        for (int i = 0; i < this.pan.allBoardPoints.size(); i++) {
            if (e.getX() > (int) this.pan.allBoardPoints.get(i).getX() && e.getX()
                    < (int) (this.pan.allBoardPoints.get(i).getX() + 75.0D) && e.getY() - 40
                    < (int) (this.pan.allBoardPoints.get(i).getY() + 75.0D) && e.getY() - 40
                    > (int) this.pan.allBoardPoints.get(i).getY()) {
                testClick = i + 1;
                break;
            }
        }

        for (int i = 0; i < this.pan.pawns.size(); i++) {
            if (e.getX() > (int) this.pan.pawns.get(i).point.getX() && e.getX()
                    < (int) (this.pan.pawns.get(i).point.getX() + 75.0D) && e.getY() - 27
                    < (int) (this.pan.pawns.get(i).point.getY() + 75.0D) && e.getY() - 27
                    > (int) this.pan.pawns.get(i).point.getY()) {
                this.clickedHere = i;
                break;
            }
        }

        MoveList normalMoves = GameRules.findAllValidMoves(pan.boardInit, userColor);
        pan.possibleMoves.clear();

        for (int i = 0; i < normalMoves.size(); i++) {
            if (testClick - 1 >= 0 && pan.boardInit.getChecker(new Coordinate(testClick)) != null
                    && normalMoves.get(i).getChecker().getPosition() == pan.boardInit.getChecker(new Coordinate(testClick)).getPosition()) {
                pan.possibleMoves.add(normalMoves.get(i).getDestination().get());
                pan.repaint();
            }
        }

        if (e.getX() > 690 && e.getX() < 744 && e.getY() - 27 > 530 && e.getY() - 27 < 584) {
            pan.pawns.clear();
            pan.boardInit.initialize();
            boardHistory.clear();
            currentPositionInBoardHistory = 0;
            isBack = false;
            pan.repaint();
        }

    }

    public void mouseReleased(MouseEvent e) {
        if (alreadyMoved) {
            fromPawnIndex = clickedHere + 1;

            for (int i = 0; i < this.pan.allBoardPoints.size(); i++) {
                if (e.getX() > (int) this.pan.allBoardPoints.get(i).getX() && e.getX()
                        < (int) (this.pan.allBoardPoints.get(i).getX() + 75.0D) && e.getY() - 27
                        < (int) (this.pan.allBoardPoints.get(i).getY() + 75.0D) && e.getY() - 27
                        > (int) this.pan.allBoardPoints.get(i).getY()) {
                    toPawnIndex = i + 1;
                    break;
                }
            }

            if (clickedHere >= 0) {
                moveUser(new Coordinate(pan.pawns.get(clickedHere).index), new Coordinate(toPawnIndex));
            }

            pan.newBoard = true;
            pan.repaint();
            clickedHere = -48;
            setCursor(0);
            alreadyMoved = false;
            moving = false;
        }

    }

    public void mouseDragged(MouseEvent e) {
        alreadyMoved = true;
        setCursor(1);
        pan.possibleMoves.clear();
        pan.bestMoves.clear();
        if (!moving) {
            for (int i = 0; i < this.pan.pawns.size(); i++) {
                if (e.getX() > (int) pan.pawns.get(i).point.getX() && e.getX()
                        < (int) (pan.pawns.get(i).point.getX() + 75.0D) && e.getY() - 27
                        < (int) (pan.pawns.get(i).point.getY() + 75.0D) && e.getY() - 27
                        > (int) pan.pawns.get(i).point.getY()) {
                    this.clickedHere = i;
                    break;
                }
            }
        }

        if (clickedHere >= 0) {
            pan.newBoard = false;
            moving = true;
            pan.pawns.get(clickedHere).setPoint(new Point(e.getX() - 37, e.getY() - 40 - 37));
            pan.repaint();
        }

    }

    public void moveUser(Coordinate from, Coordinate to) {
        pan.turn = "Ur move";
        Move move = validateUserMove(from, to);
        if (move == null) {
            System.out.println(" This move is not valid. Try harder ");
            outputText("Invalid move.");
        } else {
            if (move.isJump()) {
                if (isBack) {
                    removeBoardsAfter(currentPositionInBoardHistory + 1);
                    isBack = false;
                    currentPositionInBoardHistory = boardHistory.size() + 1;
                } else if (boardHistory.size() == 0) {
                    currentPositionInBoardHistory = 0;
                    boardHistory.add(pan.boardInit);
                }

                pan.boardInit = GameRules.executeUserJump(move, pan.boardInit);
                CheckerPosition multipleJumpsChecker = pan.boardInit.getChecker(move.getDestination());
                if (requiredJump(multipleJumpsChecker, pan.boardInit)) {
                    outputText("A multiple jump must be done ");
                } else {
                    computerMoves();
                }
            } else if (GameRules.existJump(pan.boardInit, userColor)) {
                this.outputText("Invalid move. If you can jump, you must.");
            } else {
                if (isBack) {
                    removeBoardsAfter(currentPositionInBoardHistory + 1);
                    isBack = false;
                    currentPositionInBoardHistory = boardHistory.size() + 1;
                } else if (boardHistory.size() == 0) {
                    currentPositionInBoardHistory = 0;
                    boardHistory.add(pan.boardInit);
                }

                pan.boardInit = GameRules.executeMove(move, this.pan.boardInit);
                pan.user_move = move.toString();
                computerMoves();
            }
        }

    }

    public Move validateUserMove(Coordinate from, Coordinate to) {
        Move move = null;
        CheckerPosition checker = this.pan.boardInit.getChecker(from);

        if (checker != null) {
            if (userColor == 2) {
                if (checker.getColor() == 2) {
                    if (checker.getValue() == 3) {
                        if (Math.abs(from.row() - to.row()) == 1) {
                            if (GameRules.validKingMove(from, to, pan.boardInit)) {
                                move = new OnlyMove(checker, to);
                            }
                        } else if (GameRules.validKingJump(from, to, pan.boardInit)) {
                            move = new MoveJump(checker, to);
                        }
                    } else if (from.row() - to.row() == 1) {
                        if (GameRules.validWhiteMove(from, to, pan.boardInit)) {
                            move = new OnlyMove(checker, to);
                        }
                    } else if (GameRules.validWhiteJump(from, to, pan.boardInit)) {
                        move = new MoveJump(checker, to);
                    }
                }
            } else if (checker.getColor() == 1) {
                if (checker.getValue() == -3) {
                    if (Math.abs(from.row() - to.row()) == 1) {
                        if (GameRules.validKingMove(from, to, pan.boardInit)) {
                            move = new OnlyMove(checker, to);
                        }
                    } else if (GameRules.validKingJump(from, to, pan.boardInit)) {
                        move = new MoveJump(checker, to);
                    }
                } else if (to.row() - from.row() == 1) {
                    if (GameRules.validBlackMove(from, to, pan.boardInit)) {
                        move = new OnlyMove(checker, to);
                    }
                } else if (GameRules.validBlackJump(from, to, pan.boardInit)) {
                    move = new MoveJump(checker, to);
                }
            }
        }

        return move;
    }

    private void outputText(String s) {
        output = "\n>>> " + s;
        System.out.println("" + (output));
    }

    private boolean requiredJump(CheckerPosition checker, Board board) {
        MoveList moveList = new MoveList();
        checker.findValidJumps(moveList, board);
        return moveList.size() != 0;
    }

    public void computerMoves() {
        this.pan.turn = " Computer think ";
        int computerColor = 1;
        MoveList normalMoves = GameRules.findAllValidMoves(pan.boardInit, computerColor);
        if (normalMoves.size() == 0) {
            JOptionPane.showMessageDialog(this, "\nCongratulations!You win\n",
                    "Checkers Dip", JOptionPane.INFORMATION_MESSAGE);
            outputText("You win.");
        } else {
            pan.boardInit.getHistory().reset();
            Board board = null;
            if (algorithm == 2) {
                board = Algorithm.minimaxAB(pan.boardInit, thinkDepth, computerColor, GameRules.allBlackKingsOnABoard(), GameRules.allWhiteKingsOnABoard());
            }

            if (algorithm == 1) {
                board = Algorithm.minimax(pan.boardInit, thinkDepth, computerColor);
            }

            assert board != null;
            Move move = board.getHistory().first();
            pan.boardInit = GameRules.executeMove(move, pan.boardInit);
            boolean isForward = false;
            if (!this.isBack && !isForward) {
                boardHistory.add(pan.boardInit);
                currentPositionInBoardHistory = boardHistory.size() - 1;
            }

            MoveIter iterator = pan.boardInit.getHistory().getIterator();
            StringBuilder moves = new StringBuilder();

            while (iterator.hasNext()) {
                moves.append(iterator.next());
                if (iterator.hasNext()) {
                    moves.append(" , ");
                }
            }

            pan.computer_move = moves.toString();

            outputText(" Computer's move : " + moves);
            normalMoves = GameRules.findAllValidMoves(pan.boardInit, userColor);
            if (normalMoves.size() == 0) {
                JOptionPane.showMessageDialog(this, "Computer wins!",
                        "Checkers Dip", JOptionPane.INFORMATION_MESSAGE);
                outputText("Computer wins.");
            }
        }

    }

    private void removeBoardsAfter(int count) {
        int cut = boardHistory.size();

        if (cut > count) {
            boardHistory.subList(count, cut).clear();
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }
}