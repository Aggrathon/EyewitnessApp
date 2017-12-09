package aggrathon.eyewitnessapp.light;

import org.junit.Test;

import java.util.Random;

import aggrathon.eyewitnessapp.light.data.ExperimentData;

import static org.junit.Assert.*;

public class ExperimentDataUnitTest {

	@Test
	public void creationAndDeletion() {
		ExperimentData.clearInstance();
		assertNull("Clear Instance is not working", ExperimentData.getInstance());

		ExperimentData.createSimpleInstance();
		assertNotNull("Create Instance is not working", ExperimentData.getInstance());

		ExperimentData.clearInstance();
		assertNull("Clear Instance is not working", ExperimentData.getInstance());
	}

	@Test
	public void isInstanced() {
		ExperimentData.clearInstance();
		assertFalse("Is instanced is not working", ExperimentData.isInstanced());

		ExperimentData.createSimpleInstance();
		assertTrue("Is instanced is not working", ExperimentData.isInstanced());

		ExperimentData.clearInstance();
		assertFalse("Is instanced is not working", ExperimentData.isInstanced());

	}

	@Test
	public void testNormalisation() {
		statNormalisation(-0.05f);
		statNormalisation(0.2f);
		statNormalisation(0.4f);
		statNormalisation(0.6f);
		statNormalisation(0.8f);
		statNormalisation(1.05f);
	}

	public void statNormalisation(float target) {
		float a = 1;
		float b = 1;
		float marginOfError = 0.1f;
		Random rnd = new Random();
		int iterations = 1000;
		for (int i = 0; i < iterations; i++) {
			float stat = b / (a+b);
			if(rnd.nextFloat() > 2* target - stat) {
				a = a+1;
			}
			else {
				b = b+1;
			}
		}
		assertTrue("The normalization is wrong", Math.abs(target - b /(a+b)) < marginOfError);
	}

}