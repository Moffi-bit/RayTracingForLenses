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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String file = "FOCFITS.fits";
			
			write(readOrEdit(file, true));
		} catch (FitsException e) {
			System.out.println("Fits exception: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO exception: " + e.getMessage());
		}
	}
	
	public static Fits readOrEdit(String file, boolean edit) throws FitsException, IOException {
		Fits fitsFile = new Fits(file);
		
		ImageHDU hdu = (ImageHDU) fitsFile.getHDU(0);
		float[][] imageState = (float[][]) hdu.getKernel();
		
		if (edit) {
			for (int i = 0; i < imageState.length; i++) {
				for (int j = 0; j < imageState[i].length; j++) {
					imageState[i][j] += 10f;
				}
			}
			hdu.rewrite();
		}
		
		fitsFile.close();
		
//		TableHDU thdu = (TableHDU) fitsFile.getHDU(1);
//		thdu.setElement(3, 0, "NewName");
//		thdu.rewrite();
		return fitsFile;
	}
	
	public static void write(Fits file) throws FitsException, IOException {
		Fits fresh = new Fits();
		
		ImageHDU hdu = (ImageHDU) file.getHDU(0);
		fresh.addHDU(FitsFactory.hduFactory(hdu.getKernel()));
		
		BufferedDataOutputStream out = new BufferedDataOutputStream(new FileOutputStream(new File("testimg.fits")));     
		fresh.write(out);
		out.close();
		fresh.close();
	}

}
