package checkersUI;

import checkers.Board;
import checkers.CheckerPosition;
import checkers.Coordinate;
import checkers.GameSearch;
import checkers.Move;
import checkers.MoveIter;
import checkers.MoveJump;
import checkers.MoveList;
import checkers.MoveNormal;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

public class CheckersFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
    CheckersField pan;
    private CheckerPosition multipleJumpsChecker = null;
    ArrayList<Board> boardHistory = new ArrayList();
    private int clickedHere = 0;
    int FromPawnIndex = 0;
    int ToPawnIndex = 0;
    private static final int FROM = 1;
    private static final int TO = 2;
    private static final int FROM_MULTIPLE = 3;
    private static final int TO_MULTIPLE = 4;
    private static final int COMPUTER_THINKS = 5;
    private static final int NOT_STARTED = 6;
    private int userColor = 2;
    private int computerColor = 1;
    private Coordinate from;
    private int thinkDepth = 2;
    private boolean alreadyMoved;
    private boolean moving;
    private int nbrBacks = 0;
    private int nbrBack = 0;
    private int nbrForward = 0;
    private boolean isBack = false;
    String output = "";
    int currentPositionInBoradHistory = 0;
    private boolean isForward = false;
    static int algorithm = 1;
    JMenuBar menuBar;

    public CheckersFrame() {
        Toolkit var1 = this.getToolkit();
        this.setTitle("Checkers");
        this.setDefaultCloseOperation(3);
        this.setAlwaysOnTop(true);
        this.pan = new CheckersField();
        Dimension var2 = var1.getScreenSize();
        this.setSize(605, 650);
        this.setResizable(false);
        this.setLocationRelativeTo((Component) null);
        this.createMenu();
        this.add(this.pan);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setVisible(true);
    }

    public void createMenu() {
        this.menuBar = new JMenuBar();
        JMenu var1 = new JMenu("Algorithm");
        ButtonGroup var2 = new ButtonGroup();
        JRadioButtonMenuItem var3 = new JRadioButtonMenuItem("MiniMax");
        var3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                System.out.println("miniMax");
                CheckersFrame.algorithm = 1;
            }
        });
        /*JRadioButtonMenuItem var4 = new JRadioButtonMenuItem("MiniMaxAB");
        var4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                System.out.println("miniMaxAB");
                CheckersFrame.algorithm = 2;
            }
        });*/
        var2.add(var3);
        //var2.add(var4);
        if (algorithm == 1) {
            var3.setSelected(true);
            //    var4.setSelected(false);
        } else {
            var3.setSelected(false);
            //   var4.setSelected(true);
        }

        var1.add(var3);
        //var1.add(var4);
        this.menuBar.add(var1);
        JMenu var5 = new JMenu("Levels");
        this.menuBar.add(var5);
        ButtonGroup var6 = new ButtonGroup();
        JRadioButtonMenuItem var7 = new JRadioButtonMenuItem("HardLevel");
        var7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                System.out.println("hardLevel");
            }
        });
        JRadioButtonMenuItem var8 = new JRadioButtonMenuItem("MediumLevel");
        var8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                System.out.println("mediumLevel");
            }
        });
        JRadioButtonMenuItem var9 = new JRadioButtonMenuItem("EasyLevel");
        var9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                System.out.println("easyLevel");
            }
        });
        var6.add(var7);
        var6.add(var8);
        var6.add(var9);
        var5.add(var7);
        var5.add(var8);
        var5.add(var9);
        if (this.thinkDepth == 8) {
            var7.setSelected(true);
            var8.setSelected(false);
            var9.setSelected(false);
        } else if (this.thinkDepth == 5) {
            var7.setSelected(false);
            var8.setSelected(true);
            var9.setSelected(false);
        } else if (this.thinkDepth == 2) {
            var7.setSelected(false);
            var8.setSelected(false);
            var9.setSelected(true);
        }

        JMenu var10 = new JMenu("Options");
        this.menuBar.add(var10);

        JMenuItem var12 = new JMenuItem("Backward", (Icon) null);
        var12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                System.out.println("Do backward ");
                System.out.println("The Current Position when i will Back " + CheckersFrame.this.currentPositionInBoradHistory);
                if (CheckersFrame.this.currentPositionInBoradHistory - ++CheckersFrame.this.nbrBack >= 0) {
                    CheckersFrame.this.isBack = true;
                    CheckersFrame.this.pan.boardO = (Board) CheckersFrame.this.boardHistory.get(CheckersFrame.this.currentPositionInBoradHistory - CheckersFrame.this.nbrBack);
                    CheckersFrame.this.currentPositionInBoradHistory -= CheckersFrame.this.nbrBack;
                    CheckersFrame.this.pan.repaint();
                    CheckersFrame.this.nbrBack = 0;
                } else {
                    CheckersFrame.this.nbrBack = 0;
                }

            }
        });
        JMenuItem var13 = new JMenuItem("Forward", (Icon) null);
        var13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                System.out.println("Do forward ");
                if (CheckersFrame.this.currentPositionInBoradHistory + ++CheckersFrame.this.nbrForward < CheckersFrame.this.boardHistory.size()) {
                    CheckersFrame.this.pan.boardO = (Board) CheckersFrame.this.boardHistory.get(CheckersFrame.this.currentPositionInBoradHistory + CheckersFrame.this.nbrForward);
                    CheckersFrame.this.currentPositionInBoradHistory += CheckersFrame.this.nbrForward;
                    CheckersFrame.this.nbrForward = 0;
                } else {
                    CheckersFrame.this.nbrForward = 0;
                }

                CheckersFrame.this.pan.repaint();
            }
        });
        JMenuItem var14 = new JMenuItem("Reset board", (Icon) null);
        var14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                System.out.println("Do resetBoard ");
                CheckersFrame.this.pan.pawns.clear();
                CheckersFrame.this.pan.boardO.initialize();
                CheckersFrame.this.boardHistory.clear();
                CheckersFrame.this.currentPositionInBoradHistory = 0;
                CheckersFrame.this.isBack = false;
                CheckersFrame.this.pan.repaint();
            }
        });

        JMenuItem var15 = new JMenuItem("help", (Icon) null);
        var15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                System.out.println("Do help ");
                Board var2 = null;
                if (CheckersFrame.algorithm == 1) {
                    var2 = GameSearch.minimax(CheckersFrame.this.pan.boardO, CheckersFrame.this.thinkDepth, CheckersFrame.this.userColor);
                } else {
                    /* var2 = GameSearch.minimaxAB(CheckersFrame.this.pan.boardO, CheckersFrame.this.thinkDepth, CheckersFrame.this.userColor, GameSearch.minusInfinityBoard(), GameSearch.plusInfinityBoard());
                     */
                }

                Move var3 = var2.getHistory().first().getNext();
                CheckersFrame.this.pan.bestMoves.add(var3.getChecker().getPosition().get());
                CheckersFrame.this.pan.bestMoves.add(var3.getDestination().get());
                CheckersFrame.this.pan.repaint();
            }
        });
        var10.add(var12);
        var10.add(var13);
        var10.add(var15);
        var10.add(var14);

        this.setJMenuBar(this.menuBar);
    }

    public static void main(String[] var0) {
        new CheckersFrame();
    }

    public void mouseClicked(MouseEvent var1) {
        int var2 = 0;

        int var3;
        for (var3 = 0; var3 < this.pan.allBoardPoints.size(); ++var3) {
            if (var1.getX() > (int) ((Point) this.pan.allBoardPoints.get(var3)).getX() && var1.getX() < (int) (((Point) this.pan.allBoardPoints.get(var3)).getX() + 75.0D) && var1.getY() - 40 < (int) (((Point) this.pan.allBoardPoints.get(var3)).getY() + 75.0D) && var1.getY() - 40 > (int) ((Point) this.pan.allBoardPoints.get(var3)).getY()) {
                var2 = var3 + 1;
                break;
            }
        }

        for (var3 = 0; var3 < this.pan.pawns.size(); ++var3) {
            if (var1.getX() > (int) ((Pawn) this.pan.pawns.get(var3)).point.getX() && var1.getX() < (int) (((Pawn) this.pan.pawns.get(var3)).point.getX() + 75.0D) && var1.getY() - 27 < (int) (((Pawn) this.pan.pawns.get(var3)).point.getY() + 75.0D) && var1.getY() - 27 > (int) ((Pawn) this.pan.pawns.get(var3)).point.getY()) {
                this.clickedHere = var3;
                break;
            }
        }

        MoveList var5 = GameSearch.findAllValidMoves(this.pan.boardO, this.userColor);
        this.pan.possibleMoves.clear();

        for (int var4 = 0; var4 < var5.size(); ++var4) {
            if (var2 - 1 >= 0 && this.pan.boardO.getChecker(new Coordinate(var2)) != null && var5.get(var4).getChecker().getPosition() == this.pan.boardO.getChecker(new Coordinate(var2)).getPosition()) {
                this.pan.possibleMoves.add(var5.get(var4).getDestination().get());
                this.pan.repaint();
            }
        }

        if (var1.getX() > 690 && var1.getX() < 744 && var1.getY() - 27 > 530 && var1.getY() - 27 < 584) {
            this.pan.pawns.clear();
            this.pan.boardO.initialize();
            this.boardHistory.clear();
            this.currentPositionInBoradHistory = 0;
            this.isBack = false;
            this.pan.repaint();
        }

    }

    public void mousePressed(MouseEvent var1) {
    }

    public void mouseReleased(MouseEvent var1) {
        if (this.alreadyMoved) {
            this.FromPawnIndex = this.clickedHere + 1;

            for (int var2 = 0; var2 < this.pan.allBoardPoints.size(); ++var2) {
                if (var1.getX() > (int) ((Point) this.pan.allBoardPoints.get(var2)).getX() && var1.getX() < (int) (((Point) this.pan.allBoardPoints.get(var2)).getX() + 75.0D) && var1.getY() - 27 < (int) (((Point) this.pan.allBoardPoints.get(var2)).getY() + 75.0D) && var1.getY() - 27 > (int) ((Point) this.pan.allBoardPoints.get(var2)).getY()) {
                    this.ToPawnIndex = var2 + 1;
                    break;
                }
            }

            if (this.clickedHere >= 0) {
                this.moveUser(new Coordinate(((Pawn) this.pan.pawns.get(this.clickedHere)).posindex), new Coordinate(this.ToPawnIndex));
            }

            this.pan.newBoard = true;
            this.pan.repaint();
            this.clickedHere = -48;
            this.setCursor(0);
            this.alreadyMoved = false;
            this.moving = false;
        }

    }

    public void mouseEntered(MouseEvent var1) {
    }

    public void mouseExited(MouseEvent var1) {
    }

    public void mouseDragged(MouseEvent var1) {
        this.alreadyMoved = true;
        this.setCursor(1);
        this.pan.possibleMoves.clear();
        this.pan.bestMoves.clear();
        if (!this.moving) {
            for (int var2 = 0; var2 < this.pan.pawns.size(); ++var2) {
                if (var1.getX() > (int) ((Pawn) this.pan.pawns.get(var2)).point.getX() && var1.getX() < (int) (((Pawn) this.pan.pawns.get(var2)).point.getX() + 75.0D) && var1.getY() - 27 < (int) (((Pawn) this.pan.pawns.get(var2)).point.getY() + 75.0D) && var1.getY() - 27 > (int) ((Pawn) this.pan.pawns.get(var2)).point.getY()) {
                    this.clickedHere = var2;
                    break;
                }
            }
        }

        if (this.clickedHere >= 0) {
            this.pan.newBoard = false;
            this.moving = true;
            ((Pawn) this.pan.pawns.get(this.clickedHere)).setP(new Point(var1.getX() - 37, var1.getY() - 40 - 37));
            this.pan.repaint();
        }

    }

    public void mouseMoved(MouseEvent var1) {
    }

    public void moveUser(Coordinate var1, Coordinate var2) {
        this.pan.turn = "your turn";
        Move var3 = this.validateUserMove(var1, var2);
        if (var3 == null) {
            System.out.println(" The Move is not Valid ");
            this.outputText("Invalid move.");
        } else {
            boolean var4;
            int var5;
            if (var3.isJump()) {
                if (this.isBack) {
                    var4 = false;
                    var5 = this.currentPositionInBoradHistory;
                    this.removeBordsAfter(this.currentPositionInBoradHistory + 1);
                    this.isBack = false;
                    this.currentPositionInBoradHistory = this.boardHistory.size() + 1;
                } else if (this.boardHistory.size() == 0) {
                    this.currentPositionInBoradHistory = 0;
                    this.boardHistory.add(this.pan.boardO);
                }

                this.pan.boardO = GameSearch.executeUserJump(var3, this.pan.boardO);
                this.multipleJumpsChecker = this.pan.boardO.getChecker(var3.getDestination());
                if (this.mandatoryJump(this.multipleJumpsChecker, this.pan.boardO)) {
                    this.outputText("A multiple jump must be completed.");
                } else {
                    this.computerMoves();
                }
            } else if (GameSearch.existJump(this.pan.boardO, this.userColor)) {
                this.outputText("Invalid move. If you can jump, you must.");
            } else {
                if (this.isBack) {
                    var4 = false;
                    var5 = this.currentPositionInBoradHistory;
                    this.removeBordsAfter(this.currentPositionInBoradHistory + 1);
                    this.isBack = false;
                    this.currentPositionInBoradHistory = this.boardHistory.size() + 1;
                } else if (this.boardHistory.size() == 0) {
                    this.currentPositionInBoradHistory = 0;
                    this.boardHistory.add(this.pan.boardO);
                }

                this.pan.boardO = GameSearch.executeMove(var3, this.pan.boardO);
                this.pan.user_move = var3.toString();
                this.computerMoves();
            }
        }

    }

    public Move validateUserMove(Coordinate var1, Coordinate var2) {
        Object var3 = null;
        CheckerPosition var4 = this.pan.boardO.getChecker(var1);
        if (var4 == null) {
        }

        if (var4 != null) {
            if (this.userColor == 2) {
                if (var4.getColor() == 2) {
                    if (var4.getValue() == 3) {
                        if (Math.abs(var1.row() - var2.row()) == 1) {
                            if (GameSearch.validKingMove(var1, var2, this.pan.boardO)) {
                                var3 = new MoveNormal(var4, var2);
                            }
                        } else if (GameSearch.validKingJump(var1, var2, this.pan.boardO)) {
                            var3 = new MoveJump(var4, var2);
                        }
                    } else if (var1.row() - var2.row() == 1) {
                        if (GameSearch.validWhiteMove(var1, var2, this.pan.boardO)) {
                            var3 = new MoveNormal(var4, var2);
                        }
                    } else if (GameSearch.validWhiteJump(var1, var2, this.pan.boardO)) {
                        var3 = new MoveJump(var4, var2);
                    }
                }
            } else if (var4.getColor() == 1) {
                if (var4.getValue() == -3) {
                    if (Math.abs(var1.row() - var2.row()) == 1) {
                        if (GameSearch.validKingMove(var1, var2, this.pan.boardO)) {
                            var3 = new MoveNormal(var4, var2);
                        }
                    } else if (GameSearch.validKingJump(var1, var2, this.pan.boardO)) {
                        var3 = new MoveJump(var4, var2);
                    }
                } else if (var2.row() - var1.row() == 1) {
                    if (GameSearch.validBlackMove(var1, var2, this.pan.boardO)) {
                        var3 = new MoveNormal(var4, var2);
                    }
                } else if (GameSearch.validBlackJump(var1, var2, this.pan.boardO)) {
                    var3 = new MoveJump(var4, var2);
                }
            }
        }

        return (Move) var3;
    }

    private void outputText(String s) {
        output = "\n>>> " + s;
        System.out.println("" + (output));
    }

    private boolean mandatoryJump(CheckerPosition var1, Board var2) {
        MoveList var3 = new MoveList();
        var1.findValidJumps(var3, var2);
        return var3.size() != 0;
    }

    public void computerMoves() {
        this.pan.turn = " Computer turn ";
        MoveList var1 = GameSearch.findAllValidMoves(this.pan.boardO, this.computerColor);
        if (var1.size() == 0) {
            JOptionPane.showMessageDialog(this, "\nCongratulations!You win\n", "Checkers", 1);
            this.outputText("You win.");
        } else {
            this.pan.boardO.getHistory().reset();
            Board var2 = null;
            if (algorithm == 2) {
                var2 = GameSearch.minimaxAB(this.pan.boardO, this.thinkDepth, this.computerColor, GameSearch.minusInfinityBoard(), GameSearch.plusInfinityBoard());
            }

            if (algorithm == 1) {
                var2 = GameSearch.minimax(this.pan.boardO, this.thinkDepth, this.computerColor);
            }

            Move var3 = var2.getHistory().first();
            this.pan.boardO = GameSearch.executeMove(var3, this.pan.boardO);
            if (!this.isBack && !this.isForward) {
                this.boardHistory.add(this.pan.boardO);
                this.currentPositionInBoradHistory = this.boardHistory.size() - 1;
            }

            MoveIter var4 = this.pan.boardO.getHistory().getIterator();
            String var5 = "";

            while (var4.hasNext()) {
                var5 = var5 + var4.next();
                if (var4.hasNext()) {
                    var5 = var5 + " , ";
                }
            }

            this.pan.computer_move = var5;
            int var6 = var5.indexOf("(");
            int var7 = var5.indexOf(")");
            String[] var8 = var5.substring(var6 + 1, var7).split("-");
            this.outputText("the computer make this move : " + var5);
            var1 = GameSearch.findAllValidMoves(this.pan.boardO, this.userColor);
            if (var1.size() == 0) {
                JOptionPane.showMessageDialog(this, "Computer wins!", "Checkers", 1);
                this.outputText("Sorry. The computer wins.");
            }
        }

    }

    public void keyTyped(KeyEvent var1) {
    }

    public void keyPressed(KeyEvent var1) {
    }

    public void keyReleased(KeyEvent var1) {
    }

    private void removeBordsAfter(int var1) {
        int var2 = this.boardHistory.size();

        for (int var3 = var2 - 1; var3 >= var1; --var3) {
            this.boardHistory.remove(var3);
        }

    }

}