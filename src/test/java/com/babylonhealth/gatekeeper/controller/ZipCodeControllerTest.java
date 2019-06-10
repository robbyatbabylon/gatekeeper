package com.babylonhealth.gatekeeper.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.babylonhealth.bamsspring.security.kong.KongSecurityAutoConfiguration;
import com.babylonhealth.bamsspring.security.kong.web.KongWebSecurityAutoConfiguration;
import com.babylonhealth.gatekeeper.controller.zipCode.ZipCodeController;
import com.babylonhealth.gatekeeper.service.zipCode.ZipCode;
import com.babylonhealth.gatekeeper.service.zipCode.ZipCodeFilter;
import com.babylonhealth.gatekeeper.service.zipCode.ZipCodeService;
import com.google.common.collect.Lists;
import java.nio.charset.Charset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(
    controllers = {ZipCodeController.class},
    includeFilters = @Filter(classes = EnableWebSecurity.class))
@Import({KongWebSecurityAutoConfiguration.class, KongSecurityAutoConfiguration.class})
class ZipCodeControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ZipCodeService zipCodeService;

  @Value("classpath:files/responseAllZips.json")
  private Resource responseAllZips;

  @Test
  void getZipCodes() throws Exception {

    String sponsor = "SPONSOR";
    String state = "michigan";

    String body = String.format("{ \"sponsor_code\": \"%s\", \"state\": \"%s\" }", sponsor, state);

    ZipCodeFilter zipCodeFilter = new ZipCodeFilter();
    zipCodeFilter.setSponsorCode(sponsor);
    zipCodeFilter.setState(state);

    String expectedRes =
        StreamUtils.copyToString(responseAllZips.getInputStream(), Charset.defaultCharset());

    given(zipCodeService.getZipCodes(zipCodeFilter)).willReturn(buildZipCodes());

    this.mockMvc
        .perform(get("/v1/zipCodes").contentType(MediaType.APPLICATION_JSON).content(body))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedRes, true));
  }

  @Test
  void getAllZipCodes() throws Exception {

    String expectedRes =
        StreamUtils.copyToString(responseAllZips.getInputStream(), Charset.defaultCharset());

    given(zipCodeService.getAllZipCodes()).willReturn(buildZipCodes());

    this.mockMvc
        .perform(get("/v1/zipCodes/all"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedRes, true));
  }

  @Test
  void getAllZipCodesForSponsorCode() throws Exception {
    String sponsor = "SPONSOR";

    String expectedRes =
        StreamUtils.copyToString(responseAllZips.getInputStream(), Charset.defaultCharset());

    given(zipCodeService.getAllZipCodes(sponsor)).willReturn(buildZipCodes());

    this.mockMvc
        .perform(get(String.format("/v1/zipCodes/sponsorCode/%s/all", sponsor)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedRes, true));
  }

  @Test
  void checkIfZipCodeIsInSponsor() throws Exception {
    String sponsor = "SPONSOR";
    String zipCode = "78723";

    given(zipCodeService.isZipCodeInList(sponsor, zipCode)).willReturn(true);

    this.mockMvc
        .perform(get(String.format("/v1/zipCodes/sponsorCode/%s/zipCode/%s", sponsor, zipCode)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json("{ \"passed\": true }", true));
  }

  @Test
  void checkIfZipCodeIsInAnySponsor() throws Exception {
    String zipCode = "78723";

    given(zipCodeService.isZipCodeInList(zipCode)).willReturn(true);

    this.mockMvc
        .perform(get(String.format("/v1/zipCodes/zipCode/%s", zipCode)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json("{ \"passed\": true }", true));
  }

  @Test
  void getZipCodesForSponsorAndState() throws Exception {
    String sponsor = "SPONSOR";
    String state = "michigan";

    String expectedRes =
        StreamUtils.copyToString(responseAllZips.getInputStream(), Charset.defaultCharset());

    given(zipCodeService.getZipCodesForState(sponsor, state)).willReturn(buildZipCodes());

    this.mockMvc
        .perform(get(String.format("/v1/zipCodes/sponsorCode/%s/state/%s", sponsor, state)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedRes, true));
  }

  @Test
  void getZipCodesForState() throws Exception {
    String state = "michigan";

    String expectedRes =
        StreamUtils.copyToString(responseAllZips.getInputStream(), Charset.defaultCharset());

    given(zipCodeService.getZipCodesForState(state)).willReturn(buildZipCodes());

    this.mockMvc
        .perform(get(String.format("/v1/zipCodes/state/%s", state)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expectedRes, true));
  }

  @Test
  void badRequestForEmptyBody() throws Exception {

    ZipCodeFilter zipCodeFilter = new ZipCodeFilter();

    given(zipCodeService.getZipCodes(zipCodeFilter)).willReturn(buildZipCodes());

    this.mockMvc
        .perform(get("/v1/zipCodes").contentType(MediaType.APPLICATION_JSON).content(""))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  private List<ZipCode> buildZipCodes() {
    return Lists.newArrayList(
        new ZipCode("Texas", "78723"),
        new ZipCode("Texas", "78665"),
        new ZipCode("Michigan", "48616"),
        new ZipCode("Michigan", "48362"),
        new ZipCode("Florida", "32043"),
        new ZipCode("Florida", "32202"));
  }
}
