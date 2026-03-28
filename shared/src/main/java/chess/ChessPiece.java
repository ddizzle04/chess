package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private PieceType type;

    private boolean isInBounds(int row, int col){
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        switch (type) {
            case KNIGHT: {
                int currentRow = myPosition.getRow();
                int currentCol = myPosition.getColumn();

                int[][] knightMoves = {
                        {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                        {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
                };
                for (int[] shift : knightMoves) {
                    int newRow = currentRow + shift[0];
                    int newCol = currentCol + shift[1];
                    if (isInBounds(newRow, newCol)) {
                        ChessPosition newPosition = new ChessPosition(newRow, newCol);
                        ChessPiece pieceAtDestination = board.getPiece(newPosition);

                        if (pieceAtDestination == null) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                        } else if (pieceAtDestination.getTeamColor() != this.pieceColor) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                        }
                    }
                }
                break;
            }
            case KING: {
                int currentRow = myPosition.getRow();
                int currentCol = myPosition.getColumn();

                int[][] kingMoves = {
                        {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                        {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
                };

                for (int[] shift : kingMoves) {
                    int newRow = currentRow + shift[0];
                    int newCol = currentCol + shift[1];

                    if (isInBounds(newRow, newCol)) {
                        ChessPosition newPosition = new ChessPosition(newRow, newCol);
                        ChessPiece pieceAtDestination = board.getPiece(newPosition);

                        if (pieceAtDestination == null) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                        } else if (pieceAtDestination.getTeamColor() != this.pieceColor) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                        }
                    }
                }
                break;
            }
            case ROOK: {
                int currentRow = myPosition.getRow();
                int currentCol = myPosition.getColumn();

                int[][] rookDirections = {
                        {1, 0}, {-1, 0}, {0,1}, {0,-1}
                };

                for (int[] direction: rookDirections) {
                    int rowChange = direction[0];
                    int colChange = direction[1];

                    int newRow = currentRow + rowChange;
                    int newCol = currentCol + colChange;

                    while (isInBounds(newRow, newCol)) {
                        ChessPosition newPosition = new ChessPosition(newRow, newCol);
                        ChessPiece pieceAtDestination = board.getPiece(newPosition);

                        if (pieceAtDestination == null) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                        } else if (pieceAtDestination.getTeamColor() != this.pieceColor) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                            break;
                        } else {
                            break;
                        }

                        newRow += rowChange;
                        newCol += colChange;
                    }
                }
                break;
            }
            case BISHOP: {
                int currentRow = myPosition.getRow();
                int currentCol = myPosition.getColumn();

                int[][] bishopDirections = {
                        {1, 1}, {1, -1}, {-1, 1}, {-1,-1}
                };

                for (int[] direction: bishopDirections) {
                    int rowChange = direction[0];
                    int colChange = direction[1];

                    int newRow = currentRow + rowChange;
                    int newCol = currentCol + colChange;

                    while (isInBounds(newRow, newCol)) {
                        ChessPosition newPosition = new ChessPosition(newRow, newCol);
                        ChessPiece pieceAtDestination = board.getPiece(newPosition);

                        if (pieceAtDestination == null) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                        } else if (pieceAtDestination.getTeamColor() != this.pieceColor) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                            break;
                        } else {
                            break;
                        }

                        newRow += rowChange;
                        newCol += colChange;
                    }
                }
                break;
            }
            case QUEEN: {
                int currentRow = myPosition.getRow();
                int currentCol = myPosition.getColumn();

                int[][] queenDirections = {
                        {1, 0}, {-1, 0}, {0,1}, {0,-1},
                        {1, 1}, {1, -1}, {-1, 1}, {-1,-1}
                };

                for (int[] direction: queenDirections) {
                    int rowChange = direction[0];
                    int colChange = direction[1];

                    int newRow = currentRow + rowChange;
                    int newCol = currentCol + colChange;

                    while (isInBounds(newRow, newCol)) {
                        ChessPosition newPosition = new ChessPosition(newRow, newCol);
                        ChessPiece pieceAtDestination = board.getPiece(newPosition);

                        if (pieceAtDestination == null) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                        } else if (pieceAtDestination.getTeamColor() != this.pieceColor) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                            break;
                        } else {
                            break;
                        }

                        newRow += rowChange;
                        newCol += colChange;
                    }
                }
                break;
            }
            case PAWN: {
                int currentRow = myPosition.getRow();
                int currentCol = myPosition.getColumn();

                int direction;
                int startRow;
                int promotionRow;

                if (pieceColor == ChessGame.TeamColor.WHITE) {
                    direction = 1;
                    startRow = 2;
                    promotionRow = 8;
                } else {
                    direction = -1;
                    startRow = 7;
                    promotionRow = 1;
                }

                int oneStepRow = currentRow + direction;
                if (isInBounds(oneStepRow, currentCol)) {
                    ChessPosition oneStepPosition = new ChessPosition(oneStepRow, currentCol);

                    if (board.getPiece(oneStepPosition) == null) {
                        if (oneStepRow == promotionRow) {
                            moves.add(new ChessMove(myPosition, oneStepPosition, PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, oneStepPosition, PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, oneStepPosition, PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, oneStepPosition, PieceType.KNIGHT));
                        } else {
                            moves.add(new ChessMove(myPosition, oneStepPosition, null));
                        }
                        int twoStepRow = currentRow + (2 * direction);
                        if (currentRow == startRow && isInBounds(twoStepRow, currentCol)) {
                            ChessPosition twoStepPosition = new ChessPosition(twoStepRow, currentCol);

                            if (board.getPiece(twoStepPosition) == null) {
                                moves.add(new ChessMove(myPosition, twoStepPosition, null));
                            }
                        }
                    }
                }

                int[][] captureMoves = {
                        {direction, -1},
                        {direction, 1}
                };

                for (int[] shift : captureMoves) {
                    int newRow = currentRow + shift[0];
                    int newCol = currentCol + shift[1];

                    if (isInBounds(newRow, newCol)) {
                        ChessPosition newPosition = new ChessPosition(newRow, newCol);
                        ChessPiece pieceAtDestination = board.getPiece(newPosition);

                        if (pieceAtDestination != null && pieceAtDestination.getTeamColor() != this.pieceColor) {
                            if (newRow == promotionRow) {
                                moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                                moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                                moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                                moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                            } else {
                                moves.add(new ChessMove(myPosition, newPosition, null));
                            }
                        }
                    }
                }
            }
                break;
        }
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}