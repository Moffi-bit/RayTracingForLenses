package fits;

import java.io.IOException;
import java.util.Arrays;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.ImageHDU;

public class ProcessingFits {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Fits f = new Fits("FOCFITS.fits");
			ImageHDU hdu = (ImageHDU) f.getHDU(0);
			float[][] image = (float[][]) hdu.getKernel();
			System.out.println(image[1023][0]);
		} catch (FitsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
