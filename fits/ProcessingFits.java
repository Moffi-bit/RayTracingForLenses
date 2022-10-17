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
			String file = "foc.fits";
			
			writeAndEdit(read(file), true);
		} catch (FitsException e) {
			System.out.println("Fits exception: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO exception: " + e.getMessage());
		}
	}
	
	public static Fits read(String file) throws FitsException, IOException {
		Fits fitsFile = new Fits(file);
		
//		ImageHDU hdu = (ImageHDU) fitsFile.getHDU(0);
//		float[][] imageState = (float[][]) hdu.getKernel();
//		System.out.println(imageState[0][106]);
		
		return fitsFile;
	}
	
	public static void writeAndEdit(Fits file, boolean edit) throws FitsException, IOException {
		Fits fresh = new Fits();
		
		ImageHDU hdu = (ImageHDU) file.getHDU(0);
		fresh.addHDU(FitsFactory.hduFactory(hdu.getKernel()));
		float[][] imageState = (float[][]) hdu.getKernel();
		
		if (edit) {
			for (int i = 0; i < imageState.length; i++) {
				for (int j = 0; j < imageState[i].length; j++) {
					imageState[i][j] += 10f;
				}
			}
			
			hdu.rewrite();
		} 
		
		// Can change "foc.fits" to another name to create a new file with edited values
		BufferedDataOutputStream out = new BufferedDataOutputStream(new FileOutputStream(new File("foc.fits")));     
		fresh.write(out);
		out.close();
		fresh.close();
	}
}
