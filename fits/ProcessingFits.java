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
	private boolean write;
	private float value;

	public ProcessingFits(String file, boolean write, float value) {
		this.file = file;
		this.write = write;
		this.value = value;
	}

	public String toString() {
		return "Fits' file: " + this.file + "\nWriting to a new file: "
				+ this.write + "\nEditing value: " + this.value;
	}
	
	public void changeWrite() {
		this.write = !this.write;
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

	public float[][] editAndWrite() throws FitsException, IOException {
		// TODO: More efficient algorithm of editing/writing.
		Fits fits = new Fits(this.file);
		// New Fits object
		Fits fresh = new Fits();

		ImageHDU hdu = (ImageHDU) fits.getHDU(0);
		fresh.addHDU(FitsFactory.hduFactory(hdu.getKernel()));
		float[][] imageState = (float[][]) hdu.getKernel();
		
		String newFile = "foc" + (imageState[0][0] + this.value) + ".fits";
		
		for (int i = 0; i < imageState.length; i++) {
			for (int j = 0; j < imageState[i].length; j++) {
				if (imageState[i][j] + this.value < 0) {
					imageState[i][j] = 0;
				} else {
					imageState[i][j] += this.value;
				}
			}
		}

		hdu.rewrite();

		// Write new values to a new file.
		if (this.write) {
			BufferedDataOutputStream out = new BufferedDataOutputStream(
					new FileOutputStream(
							new File(newFile)));
			fresh.write(out);
			out.close();
			fresh.close();
			
			// Keep the original values of fits
			for (int i = 0; i < imageState.length; i++) {
				for (int j = 0; j < imageState[i].length; j++) {
					imageState[i][j] -= this.value;
				}
			}

			hdu.rewrite();
			fits.close();
			
			fresh = new Fits(newFile);
			ImageHDU freshHDU = (ImageHDU) fresh.getHDU(0);
			imageState = (float[][]) freshHDU.getKernel();
			fresh.close();
		}
		
		return imageState;
	}
}
