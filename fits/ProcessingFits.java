package fits;

import java.io.IOException;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.ImageHDU;

public class ProcessingFits {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String file = "FOCFITS.fits";
			read(file);
		} catch (FitsException e) {
			System.out.println("Fits exception: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO exception: " + e.getMessage());
		}
	}
	
	public static void read(String file) throws FitsException, IOException {
		Fits f = new Fits(file);
		ImageHDU hdu = (ImageHDU) f.getHDU(0);
		float[][] image = (float[][]) hdu.getKernel();
		System.out.println(image[100][23]);
	}

}
