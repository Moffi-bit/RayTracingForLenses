package fits;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.FitsFactory;
import nom.tam.fits.ImageHDU;
import nom.tam.util.BufferedDataOutputStream;

public class ProcessingFits {
	
	private static final String file = "FOCFITS.fits";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			float[][] imageData = read(file);
			write(imageData);
		} catch (FitsException e) {
			System.out.println("Fits exception: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO exception: " + e.getMessage());
		}
	}
	
	public static float[][] read(String file) throws FitsException, IOException {
		Fits f = new Fits(file);
		ImageHDU hdu = (ImageHDU) f.getHDU(0);
		float[][] image = (float[][]) hdu.getKernel();
		System.out.println(image[100][23]);
		f.close();
		
		return image;
	}
	
	public static void write(float[][] imageData) throws FitsException, IOException {
		Fits f = new Fits();
		f.addHDU(FitsFactory.hduFactory(imageData));
		
		BufferedDataOutputStream out = new BufferedDataOutputStream(new FileOutputStream(new File("testimg.fits")));     
		f.write(out);
		out.close();
		f.close();
	}

}
