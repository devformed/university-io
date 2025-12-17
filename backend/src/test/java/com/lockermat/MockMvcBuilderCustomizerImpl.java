package com.lockermat;

import org.springframework.boot.webmvc.test.autoconfigure.MockMvcBuilderCustomizer;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

/**
 * @author Anton Gorokh
 */
@Component
public class MockMvcBuilderCustomizerImpl implements MockMvcBuilderCustomizer {

	@Override
	public void customize(ConfigurableMockMvcBuilder<?> builder) {
		builder.defaultRequest(
				MockMvcRequestBuilders.get("/")
						.with(SecurityMockMvcRequestPostProcessors.jwt())
		);
	}
}
