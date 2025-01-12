package io.mosip.kernel.otpmanager.test.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io.mosip.kernel.otpmanager.controller.OtpGeneratorController;
import io.mosip.kernel.otpmanager.dto.OtpGeneratorResponseDto;
import io.mosip.kernel.otpmanager.service.impl.OtpGeneratorServiceImpl;
import io.mosip.kernel.otpmanager.test.OtpmanagerTestBootApplication;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = OtpmanagerTestBootApplication.class)
public class OtpGeneratorControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private OtpGeneratorServiceImpl service;
	
	@InjectMocks
    private OtpGeneratorController otpGeneratorController;
	
	 @Before
	    public void setup() {
		  this.mockMvc = MockMvcBuilders.standaloneSetup(otpGeneratorController).build();

	    }

	@WithUserDetails("individual")
	@Test
	public void testOtpGenerationController() throws Exception {
		String otp = "3214";
		OtpGeneratorResponseDto dto = new OtpGeneratorResponseDto();
		dto.setOtp(otp);
		given(service.getOtp(Mockito.any())).willReturn(dto);
		String json = "{\"key\":\"123456789\"}";
		mockMvc.perform(post("/otp/generate").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.response.otp", is("3214")));
	}

}
