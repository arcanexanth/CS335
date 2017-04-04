package edu.uwec.cs.hamlingl8131.assingment5.gametree;

public class ComputerThinkingThread implements Runnable{
	private GamePlayer player;
	private boolean proceed;
	
	public ComputerThinkingThread(GamePlayer player) {
		this.player = player;
		this.proceed = true;
	}

	@Override
	public void run() {
		TwoPlayerGameBoard nextBoard = this.player.getMiniMax().generateNextMove(this.player.getBoard());
		
		if (this.proceed) {
			player.computerDone(nextBoard);
		}
	}

	public void cancel() {
		this.proceed = false;
	}
}
