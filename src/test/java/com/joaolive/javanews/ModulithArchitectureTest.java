package com.joaolive.javanews;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModulithArchitectureTest {
	// Initializes the application module model based on the main application class
	ApplicationModules modules = ApplicationModules.of(JavanewsApplication.class);

	@Test
	void verifyModularStructure() {
		// Executes structural validation. The test fails if any module accesses
		// another module's internal package in violation of the defined boundaries
		modules.verify();
		// Outputs the module structure to the console for inspection and debugging purposes
		modules.forEach(System.out::println);
	}

	@Test
	void generateArchitectureDocumentation() {
		//Generate visual documentation of the architecture
		new Documenter(modules)
			.writeModulesAsPlantUml()
			.writeIndividualModulesAsPlantUml();
	}
}
