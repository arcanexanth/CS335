package edu.uwec.cs.hamlingl8131.assignment3.seam.carving;

public class Seam {
	
	private final int pause;
	
	public Seam (){
		this(0);
	}
	
	public Seam(final int pause) {
		//Wanted to implement this but didn't make it happen//
		this.pause = pause;
	}
	
	//Shrink vertically//
	public void verticalSeamShrink(UWECImage image){
		int[][] energy = calculateEnergy(image);
		int[][] paths = findPaths(energy);
		int[] seam = findSeam(paths);
	
		for(int j = 0; j < image.getHeight(); j++){
			image.setRGB(seam[j], j, 255, 00, 00);
		}
		image.repaintCurrentDisplayWindow();
		try{
			Thread.sleep(pause);
		}catch (Exception e){
			System.err.println("Couldn't pause repaint.");
		}
		image.switchImage(image.removeSeam(seam));
		image.repaintCurrentDisplayWindow();
	}
	
	//Shrink horizontally //
	public void horizontalSeamShrink(UWECImage image){
		UWECImage rotatedImage = image.transpose();
		int[][] energy = calculateEnergy(rotatedImage);
		int[][] paths = findPaths(energy);
		int[] seam = findSeam(paths);
		
		for(int i = 0; i < image.getWidth(); i++){
			image.setRGB(i, seam[i], 255, 0, 0);
		}
		image.repaintCurrentDisplayWindow();
		try{
			Thread.sleep(pause);
		}catch(Exception e){
			System.err.println("Couldn't pause repaint.");
		}
		
		image.switchImage(rotatedImage.removeSeam(seam).transpose());
		image.repaintCurrentDisplayWindow();
	}
	
	//Locate seam for removal//
	private int[] findSeam(int[][] paths) {
		
		final int height = paths[0].length;
		final int width = paths.length;
		final int top = height-1;
		int[] seam = new int[height];
		
		seam[top] = 0;
		
		//Find min //
		for(int j = 0; j < width; j++)
			if(paths[j][top] < paths[seam[top]][top])
				seam[top]=j;
		
		//follow seam//
		for(int i = seam.length-2; i >= 0; i--){
			seam[i] = seam[i+1] > 0 ? seam[i+1]-1 : seam[i+1];
			for(int j = seam[i+1]; j <=seam[i+1]+1 && j < width; j++)
				if(paths[j][i] < paths[seam[i]][i])
					seam[i] = j;	
		}
		return seam;
	}

	//Find energy values//
	public int[][] calculateEnergy(final UWECImage image){
		final int height = image.getHeight();
		final int width = image.getWidth();
		
		int[][] energy = new int[width][height];
		
		/* Energy map */
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++ ){
				int deltaRed;
				int deltaGreen;
				int deltaBlue;
				
				int prevI = (i+width-1)%width;
				int nextI = (i+1)%width;
				
				deltaRed = image.getRed(prevI, j) - image.getRed(nextI, j);
				deltaGreen = image.getGreen(prevI, j) - image.getGreen(nextI, j);
				deltaBlue = image.getBlue(prevI, j) - image.getBlue(nextI, j);
				
				energy[i][j] = (deltaRed * deltaRed) + (deltaGreen * deltaGreen)
								+ (deltaBlue * deltaBlue);
			}
		}
		return energy;
	}
	
	//find path energies//
	private int[][] findPaths(int[][] energy) {
		final int height = energy[0].length;
		final int width = energy.length;
		int[][] paths = new int[width][height];
		
		for(int i = 0; i < width; i++){
			paths[i][0] = energy[i][0];
		}
		//check to see if it's on the left side or top//
		for(int j = 1; j < height; j++){
			paths[0][j] = paths[0][j-1] < paths[1][j-1]?
						 paths[0][j-1] : paths[1][j-1];
			paths[width-1][j] = paths[width-1][j-1] < paths[width-2][j-1]?
								paths[width-1][j-1] : paths[width-2][j-1];
			paths[0][j] += energy[0][j];
			paths[width-1][j] += energy[width-1][j];
			
			//find find paths for non-edge pixels//
			for(int i = 1; i < width-1; i++){
				int pixelAboveLeft = 0;
				int pixelAbove = 0;
				int pixelAboveRight = 0;
				
				pixelAboveLeft = paths[i-1][j-1];
				pixelAbove = paths[i][j-1];
				pixelAboveRight = paths[i+1][j-1];
				
				paths[i][j] = pixelAbove < pixelAboveLeft ? pixelAbove : pixelAboveLeft;
				paths[i][j] = paths[i][j] < pixelAboveRight ? paths[i][j] : pixelAboveRight;
				paths[i][j] += energy[i][j];
			}
		}
		return paths;
	}
}
