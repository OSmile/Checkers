package checkersUI;

import checkers.Board;
import checkers.Coordinate;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CheckersField extends JPanel{
        ImageIcon blackPawn;
        ImageIcon whitePawn;
        ImageIcon board;
        ImageIcon blackKing;
        ImageIcon whiteKing;
        ImageIcon availableMove;

        boolean newBoard = true;
        ArrayList<Pawn> pawns = new ArrayList();
        ArrayList<Point> allBoardPoints = new ArrayList();
        ArrayList<Integer> possibleMoves = new ArrayList();
        ArrayList<Integer> bestMoves = new ArrayList(); //With help
        Board boardInit = new Board();
        String turn = "Lets do it! ";
        String user_move = "";
        String computer_move = "";

        // Init all fields
        public CheckersField() {
            this.initAllPositions();
            this.blackPawn = new ImageIcon(this.getClass().getResource("/picture/black_pawn.png"));
            this.whitePawn = new ImageIcon(this.getClass().getResource("/picture/white_pawn.png"));
            this.board = new ImageIcon(this.getClass().getResource("/picture/board.png"));
            this.availableMove = new ImageIcon(this.getClass().getResource("/picture/available.png"));
            this.whiteKing = new ImageIcon(this.getClass().getResource("/picture/white_pawn_king.png"));
            this.blackKing = new ImageIcon(this.getClass().getResource("/picture/black_pawn_king.png"));
            this.boardInit.initialize();
        }

        protected void paintComponent(Graphics component) {
            super.paintComponent(component);
            this.board.paintIcon(this, component, 0, 0);

            if (this.newBoard) {
                this.drawBoard();
            }

            this.drawPawns(component);
            this.drawPossibleMoves(component);
            this.drawBestMoves(component);
        }

        private void initAllPositions() {
            int line = 0;

            for(int i = 0; i < 32; i++) {
                Point blackPoint = new Point(5, 5);
                if (i != 0 && i % 4 == 0) {
                    line++;
                }
                if (line % 2 == 0) {
                    blackPoint.x = i % 4 * 75 * 2 + 5;
                } else {
                    blackPoint.x = i % 4 * 75 * 2 + 5 + 75;
                }
                blackPoint.y = line * 75 + 5;
                allBoardPoints.add(blackPoint);
            }
        }

        public void drawPawns(Graphics pawn) {

            for (Pawn nextPawn : pawns) {
                nextPawn.image.paintIcon(this, pawn, (int) nextPawn.point.getX(), (int) nextPawn.point.getY());
            }
        }


        public void drawBoard() {
            pawns.clear();

            for(int i = 1; i < 33; i++) {
                Coordinate coord = new Coordinate(i);
                int color = 0;

                if (this.boardInit.getChecker(coord) != null) {
                    color = this.boardInit.getChecker(coord).getColor();
                }

                if (color == 2) {
                    Pawn pawn;
                    if (this.boardInit.getChecker(coord).isKing()) {
                        pawn = new Pawn(allBoardPoints.get(i - 1), whiteKing);
                    } else {
                        pawn = new Pawn(allBoardPoints.get(i - 1), whitePawn);
                    }

                    pawn.index = i;
                    this.pawns.add(pawn);
                }

                if (color == 1) {
                    Pawn pawn;
                    if (this.boardInit.getChecker(coord).isKing()) {
                        pawn = new Pawn(allBoardPoints.get(i - 1), blackKing);
                    } else {
                        pawn = new Pawn(allBoardPoints.get(i - 1), blackPawn);
                    }

                    pawn.index = i;
                    this.pawns.add(pawn);
                }
            }

        }

        private void drawBestMoves(Graphics bestMoves) {
            for (Integer bestMove : this.bestMoves) {
                this.availableMove.paintIcon(this, bestMoves,
                        (int) this.allBoardPoints.get(bestMove - 1).getX() + 5,
                        (int) this.allBoardPoints.get(bestMove - 1).getY() + 5);
            }

        }

        public void drawPossibleMoves(Graphics possibleMove) {
            for (Integer move : this.possibleMoves) {
                this.availableMove.paintIcon(this, possibleMove,
                        (int) this.allBoardPoints.get(move - 1).getX() + 5,
                        (int) this.allBoardPoints.get(move - 1).getY() + 5);
            }

        }
}