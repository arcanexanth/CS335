package edu.uwec.cs.hamlingl8131.assignment3.seam.carving;

public class SeamDemo {

	
	
	public static void main(String[] args) {
	
		String path;
		UWECImage image;
		Seam seam;
		String out = null;
		
		//edit path to choose your picture //
		path = "/home/george/Pictures/cat.jpeg";
		image = new UWECImage(path);
		seam = new Seam();
		
		image.openNewDisplayWindow();
		for(int i = 0; i < image.getWidth()/4; i++)
			seam.verticalSeamShrink(image);
		for(int i = 0; i < image.getHeight()/4; i++)
			seam.horizontalSeamShrink(image);
	}
}
