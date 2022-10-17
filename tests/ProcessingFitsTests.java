package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import fits.ProcessingFits;
import nom.tam.fits.FitsException;

public class ProcessingFitsTests {

	@Test
	public void constructorTests() {
		System.out.println(testHelper("constructorTests"));
		ProcessingFits process = new ProcessingFits("foc.fits", true, 10f);
		System.out.println(process);

		assertTrue(process.toString().equals(
				"Fits' file: foc.fits\nWriting to a new file: true\nEditing value: 10.0"));
	}

	@Test
	public void readTests() throws FitsException, IOException {
		System.out.println(testHelper("readTests"));
		float value = 10f;
		ProcessingFits process = new ProcessingFits("foc.fits", true, value);
		for (int i = 0; i < 10; i++) {
			if (i == 0);
			else {
				process.changeFile("foc" + (value * i) + ".fits");
			}
			float[][] initialState = process.read();
			float[][] changedState = process.editAndWrite();
			assertFalse(compareImageHDUS(initialState, changedState));
			System.out.println("Read Test #" + i + ": PASSED\nFirst cell for initial state: "
					+ initialState[0][0] + "\nFirst cell for changed state: "
					+ changedState[0][0]);
		}
	}

	private boolean compareImageHDUS(float[][] initial, float[][] changed) {
		for (int i = 0; i < initial.length; i++) {
			for (int j = 0; j < initial[i].length; j++) {
				if (initial[i][j] == changed[i][j])
					;
				else {
					return false;
				}
			}
		}

		return true;
	}

	private String testHelper(String methodName) {
		String temp = "", addition = "Start of ";
		int separatorLength = methodName.length() + addition.length() + 1;

		for (int i = 0; i < separatorLength; i++) {
			temp += "-";
		}

		return temp + "\n" + addition + methodName + ".\n" + temp;
	}

}
