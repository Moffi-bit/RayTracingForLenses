package tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import fits.ProcessingFits;
import nom.tam.fits.FitsException;

public class ProcessingFitsTests {

	@Test
	public void constructorTests() {
		System.out.println(testHelper("constructorTests"));
		ProcessingFits process = new ProcessingFits("foc.fits", 10f);
		System.out.println(process);

		assertTrue(process.toString().equals(
				"Fits' file: foc.fits\nEditing value: 10.0"));
	}

//	@Test
//	public void drawCircleTests() throws FitsException, IOException {
//		ProcessingFits process = new ProcessingFits("foc.fits", true, 0f);
//		process.drawCircle();
//	}
	
	@Test
	public void editTests() throws FitsException, IOException {
		System.out.println(testHelper("editTests"));
		float value = 100f;
		ProcessingFits process = new ProcessingFits("foc.fits", value);
		process.edit();
		for (int i = 0; i < 10; i++) {
			if (i == 0);
			else {
				process.setValue((int)(value * i));
			}
			process.edit();
			System.out.println("Edit And Write Test #" + (i + 1) + ": PASSED.");
		}
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
