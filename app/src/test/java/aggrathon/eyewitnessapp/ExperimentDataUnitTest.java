package aggrathon.eyewitnessapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExperimentDataUnitTest {

	@Test
	public void creationAndDeletion() {
		ExperimentData.clearInstance();
		assertNull("Clear Instance is not working", ExperimentData.getInstance());

		ExperimentData.createInstance();
		assertNotNull("Create Instance is not working", ExperimentData.getInstance());

		ExperimentData.clearInstance();
		assertNull("Clear Instance is not working", ExperimentData.getInstance());
	}

	@Test
	public void isInstanced() {
		ExperimentData.clearInstance();
		assertFalse("Is instanced is not working", ExperimentData.isInstanced());

		ExperimentData.createInstance();
		assertTrue("Is instanced is not working", ExperimentData.isInstanced());

		ExperimentData.clearInstance();
		assertFalse("Is instanced is not working", ExperimentData.isInstanced());

	}

}