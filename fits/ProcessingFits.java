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

	private String file;
	private float value;

	public ProcessingFits(String file, float value) {
		this.file = file;
		this.value = value;
	}

	public String toString() {
		return "Fits' file: " + this.file + "\nEditing value: " + this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public void changeFile(String file) {
		this.file = file;
	}

	public float[][] read() throws FitsException, IOException {
		Fits fitsFile = new Fits(this.file);

		ImageHDU hdu = (ImageHDU) fitsFile.getHDU(0);
		float[][] imageState = (float[][]) hdu.getKernel();
		fitsFile.close();

		return imageState;
	}
	
	public void drawCircle() throws FitsException, IOException {
		Fits fits = new Fits(this.file);
		ImageHDU hdu = (ImageHDU) fits.getHDU(0);
		float[][] imageData = (float[][]) hdu.getKernel();
		int ic = imageData.length / 2, jc = imageData[0].length / 2;
		float radius = 10f;
		
		for (int i = 0; i < imageData.length; i++) {
			for (int j = 0; j < imageData[i].length; j++) {
				if (Math.sqrt(Math.pow(i - ic, 2) + (Math.pow(j - jc, 2))) < radius) {
					imageData[i][j] = 1;
				} else {
					imageData[i][j] = 0;
				}
			}
		}
		
		hdu.rewrite();
		
		BufferedDataOutputStream out = new BufferedDataOutputStream(
				new FileOutputStream(
						new File("foccircle.fits")));
		fits.write(out);
		out.close();
		fits.close();
	}
	
	public void edit() throws FitsException, IOException {
		Fits fits = new Fits(this.file);
		String copyFile = "foc" + this.value + ".fits";
		ImageHDU hdu = (ImageHDU) fits.getHDU(0);
		fits.addHDU(FitsFactory.hduFactory(hdu.getKernel()));
		
		BufferedDataOutputStream out = new BufferedDataOutputStream(new FileOutputStream(new File(copyFile)));
		fits.write(out);
		out.close();
		fits.close();
		
		Fits copy = new Fits(copyFile);
		ImageHDU copyHDU = (ImageHDU) copy.getHDU(0);
		float[][] copyState = (float[][]) copyHDU.getKernel();
		
		for (int i = 0; i < copyState.length; i++) {
			for (int j = 0; j < copyState[i].length; j++) {
				copyState[i][j] *= this.value;
			}
		}
		
		copyHDU.rewrite();
		copy.close();
	}
}
