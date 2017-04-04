package edu.uwec.cs.hamlingl8131.assingment5.gametree;

public class MiniMax {
	
	private int maxLevel;

	public MiniMax(int i) {
		this.maxLevel = i;
	}

	public TwoPlayerGameBoard generateNextMove(TwoPlayerGameBoard currentBoard) {
		TwoPlayerGameBoard nextMove = null;
		TwoPlayerGameBoard child;
		double bestChildEval = -Double.MAX_VALUE;
		double childEval;
		
		while (currentBoard.hasMoreChildren()) {
			child = currentBoard.nextChild();
			childEval = recursiveMiniMaxAlphaBeta(child, 1, -Double.MAX_VALUE, Double.MAX_VALUE);
			
			if (childEval > bestChildEval) {
				nextMove = child;
				bestChildEval = childEval;
			}
		}
		return nextMove;
	}

	/**
	 * Uses recursive algorithm to evaluate best move from all of its results
	 * @param currentBoard evaluates the current board move to be evaluated
	 * @param currentLevel is the level in the tree in relation to the original move
	 * @param alpha lower bound on the alpha beta tree
	 * @param beta upper bound on alpha beta tree 
	 * @param maxValue returns evaluation of current move
	 * @return
	 */
	private double recursiveMiniMaxAlphaBeta(TwoPlayerGameBoard currentBoard, int currentLevel, double alpha, double beta) {
		if (currentLevel == maxLevel || currentBoard.isDraw() || currentBoard.isComputerWinner() || currentBoard.isUserWinner()) {
			return currentBoard.staticEvaluation();
		}
				
		TwoPlayerGameBoard child;

		if (currentLevel % 2 == 0) {
			
			do {
				child = currentBoard.nextChild();
				alpha = Math.max(alpha, recursiveMiniMaxAlphaBeta(child, currentLevel + 1, alpha, beta));
			} while (currentBoard.hasMoreChildren() && alpha < beta); 
			
			return alpha;
			
		} else {
			
			do {
				child = currentBoard.nextChild();
				beta = Math.min(beta, recursiveMiniMaxAlphaBeta(child, currentLevel + 1, alpha, beta));
			} while (currentBoard.hasMoreChildren() && alpha < beta);
			
			return beta;
		}
	}

	public void setMaxLevel(int i) {
		this.maxLevel = i;
	}
}
