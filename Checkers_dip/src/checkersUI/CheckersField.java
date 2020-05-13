package checkersUI;

import checkers.Board;
import checkers.Coordinate;
import java.awt.Graphics;
import java.awt.Point;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class CheckersField extends JPanel{
        static final String BLACK_PAWN = "/picture/black_pawn.png";
        static final String WHITE_PAWN = "/picture/white_pawn.png";
        static final String BOARD = "/picture/board1.png";
        static final String WHITE_KING = "/picture/white_pawn_king.png";
        static final String BLACK_KING = "/picture/black_pawn_king.png";
        static final String GREEN_INDICATOR = "/picture/available.png";
        ImageIcon black_pawn;
        ImageIcon white_pawn;
        ImageIcon board;
        ImageIcon black_king;
        ImageIcon white_king;
        ImageIcon availableMove;
        boolean newBoard = true;
        ArrayList<Point> blackPositions = new ArrayList();
        ArrayList<Point> whitePositions = new ArrayList();
        ArrayList<Pawn> pawns = new ArrayList();
        ArrayList<Point> allBoardPoints = new ArrayList();
        ArrayList<Integer> possibleMoves = new ArrayList();
        ArrayList<Integer> bestMoves = new ArrayList(); //With help
        Board boardO = new Board();
        String turn = "your turn";
        String user_move = "";
        String computer_move = "";
        int theme = 1;

        public CheckersField() {
            this.initAllpositions();
            this.black_pawn = new ImageIcon(this.getClass().getResource("/picture/black_pawn.png"));
            this.white_pawn = new ImageIcon(this.getClass().getResource("/picture/white_pawn.png"));
            this.board = new ImageIcon(this.getClass().getResource("/picture/board.png"));
            this.availableMove = new ImageIcon(this.getClass().getResource("/picture/available.png"));
            this.white_king = new ImageIcon(this.getClass().getResource("/picture/white_pawn_king.png"));
            this.black_king = new ImageIcon(this.getClass().getResource("/picture/black_pawn_king.png"));
            this.boardO.initialize();
        }

        protected void paintComponent(Graphics var1) {
            super.paintComponent(var1);
            this.board.paintIcon(this, var1, 0, 0);

            if (this.newBoard) {
                this.drawBoard(var1);
            }

            this.drawPawns(var1);
            this.drawPossibleMoves(var1);
            this.drawBestMoves(var1);
        }

        public void drawInitBoard(Graphics var1) {
            Iterator var2 = this.allBoardPoints.iterator();

            while(var2.hasNext()) {
                Point var3 = (Point)var2.next();
                PrintStream var10000 = System.out;
                double var10001 = var3.getX();
                var10000.println(var10001 + " " + var3.getY());
                this.black_pawn.paintIcon(this, var1, (int)var3.getX(), (int)var3.getY());
            }

        }

        private void initAllpositions() {
            int var1 = 0;

            for(int var2 = 0; var2 < 32; ++var2) {
                Point var3 = new Point(5, 5);
                if (var2 != 0 && var2 % 4 == 0) {
                    ++var1;
                }

                if (var1 % 2 == 0) {
                    var3.x = var2 % 4 * 75 * 2 + 5;
                    var3.y = var1 * 75 + 5;
                } else {
                    var3.x = var2 % 4 * 75 * 2 + 5 + 75;
                    var3.y = var1 * 75 + 5;
                }

                this.allBoardPoints.add(var3);
            }

        }

        public void drawPawnAtPosition(Graphics var1, int var2, int var3) {
            Pawn var4;
            if (var3 == 1) {
                var4 = new Pawn((Point)this.allBoardPoints.get(var2), this.black_pawn);
                var4.posindex = var2;
                this.pawns.add(var4);
            } else {
                var4 = new Pawn((Point)this.allBoardPoints.get(var2), this.white_pawn);
                var4.posindex = var2;
                this.pawns.add(var4);
            }

        }

        public void drawPawns(Graphics var1) {
            Iterator var2 = this.pawns.iterator();

            while(var2.hasNext()) {
                Pawn var3 = (Pawn)var2.next();
                var3.image.paintIcon(this, var1, (int)var3.point.getX(), (int)var3.point.getY());
            }

        }

        public void drawBoard(Graphics var1) {
            this.pawns.clear();

            for(int var2 = 1; var2 < 33; ++var2) {
                Coordinate var3 = new Coordinate(var2);
                int var4 = 0;
                if (this.boardO.getChecker(var3) != null) {
                    var4 = this.boardO.getChecker(var3).getColor();
                }

                Pawn var5;
                if (var4 == 2) {
                    var5 = null;
                    if (this.boardO.getChecker(var3).isKing()) {
                        var5 = new Pawn((Point)this.allBoardPoints.get(var2 - 1), this.white_king);
                    } else {
                        var5 = new Pawn((Point)this.allBoardPoints.get(var2 - 1), this.white_pawn);
                    }

                    var5.posindex = var2;
                    this.pawns.add(var5);
                }

                if (var4 == 1) {
                    var5 = null;
                    if (this.boardO.getChecker(var3).isKing()) {
                        var5 = new Pawn((Point)this.allBoardPoints.get(var2 - 1), this.black_king);
                    } else {
                        var5 = new Pawn((Point)this.allBoardPoints.get(var2 - 1), this.black_pawn);
                    }

                    var5.posindex = var2;
                    this.pawns.add(var5);
                }
            }

        }

        private void drawBestMoves(Graphics var1) {
            for(int var2 = 0; var2 < this.bestMoves.size(); ++var2) {
                this.availableMove.paintIcon(this, var1, (int)((Point)this.allBoardPoints.get((Integer)this.bestMoves.get(var2) - 1)).getX() + 5, (int)((Point)this.allBoardPoints.get((Integer)this.bestMoves.get(var2) - 1)).getY() + 5);
            }

        }

        public void drawPossibleMoves(Graphics var1) {
            for(int var2 = 0; var2 < this.possibleMoves.size(); ++var2) {
                this.availableMove.paintIcon(this, var1, (int)((Point)this.allBoardPoints.get((Integer)this.possibleMoves.get(var2) - 1)).getX() + 5, (int)((Point)this.allBoardPoints.get((Integer)this.possibleMoves.get(var2) - 1)).getY() + 5);
            }

        }

        public Pawn getPawnOfPosition(int var1) {
            Iterator var2 = this.pawns.iterator();

            Pawn var3;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                var3 = (Pawn)var2.next();
            } while(var3.posindex != var1);

            return var3;
        }
}