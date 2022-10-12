package fits;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

public class ProcessingFits {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Fits f = new Fits("FOCFITS.fits");
		} catch (FitsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
