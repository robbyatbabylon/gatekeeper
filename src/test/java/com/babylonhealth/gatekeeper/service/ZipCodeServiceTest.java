package com.babylonhealth.gatekeeper.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.babylonhealth.gatekeeper.service.zipcode.Sponsors;
import com.babylonhealth.gatekeeper.service.zipcode.ZipCode;
import com.babylonhealth.gatekeeper.service.zipcode.ZipCodeFilter;
import com.babylonhealth.gatekeeper.service.zipcode.ZipCodeService;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ZipCodeServiceTest {

  private ZipCodeService zipCodeService;

  @BeforeEach
  void setup() {
    List<Sponsors> sponsorsList =
        Lists.newArrayList(
            new Sponsors(
                "SPONSOR_A",
                Lists.newArrayList(
                    new ZipCode("Texas", "78723"),
                    new ZipCode("Texas", "78665"),
                    new ZipCode("Michigan", "48616"),
                    new ZipCode("Michigan", "48362"),
                    new ZipCode("Florida", "32043"),
                    new ZipCode("Florida", "32202"))),
            new Sponsors(
                "SPONSOR_B",
                Lists.newArrayList(
                    new ZipCode("Texas", "76712"),
                    new ZipCode("Texas", "76831"),
                    new ZipCode("Michigan", "48607"),
                    new ZipCode("Michigan", "48329"),
                    new ZipCode("Florida", "32561"),
                    new ZipCode("Florida", "32009"))));

    zipCodeService = new ZipCodeService(sponsorsList);
  }

  @Test
  public void getZipCodesReturnsForPasses() {
    ZipCodeFilter zipCodeFilter = new ZipCodeFilter();
    zipCodeFilter.setState("MICHIGAN");
    zipCodeFilter.setSponsorCode("SPONSOR_B");

    List<ZipCode> validZipCodes =
        Lists.newArrayList(new ZipCode("Michigan", "48607"), new ZipCode("Michigan", "48329"));

    assertThat(zipCodeService.getZipCodes(zipCodeFilter)).isEqualTo(validZipCodes);
  }

  @Test
  public void getAllZipCodesPasses() {
    String sponsor = "SPONSOR_B";

    List<ZipCode> validZipCodes =
        Lists.newArrayList(
            new ZipCode("Texas", "76712"),
            new ZipCode("Texas", "76831"),
            new ZipCode("Michigan", "48607"),
            new ZipCode("Michigan", "48329"),
            new ZipCode("Florida", "32561"),
            new ZipCode("Florida", "32009"));

    assertThat(zipCodeService.getAllZipCodes(sponsor)).isEqualTo(validZipCodes);
  }

  @Test
  public void getAllZipCodesReturnsEmptyListForWrongSponsor() {
    String sponsor = "SPONSOR_C";

    assertThat(zipCodeService.getAllZipCodes(sponsor)).isEqualTo(Lists.newArrayList());
  }

  @Test
  public void getAllZipCodesReturnsAllZipsWhenNoSponsorPresent() {
    List<ZipCode> validZipCodes =
        Lists.newArrayList(
            new ZipCode("Texas", "78723"),
            new ZipCode("Texas", "78665"),
            new ZipCode("Michigan", "48616"),
            new ZipCode("Michigan", "48362"),
            new ZipCode("Florida", "32043"),
            new ZipCode("Florida", "32202"),
            new ZipCode("Texas", "76712"),
            new ZipCode("Texas", "76831"),
            new ZipCode("Michigan", "48607"),
            new ZipCode("Michigan", "48329"),
            new ZipCode("Florida", "32561"),
            new ZipCode("Florida", "32009"));

    assertThat(zipCodeService.getAllZipCodes()).isEqualTo(validZipCodes);
  }

  @Test
  public void isZipCodeInListPassesForValidZipCode() {
    String zipCode = "76712";

    assertThat(zipCodeService.isZipCodeInList(zipCode)).isEqualTo(true);
  }

  @Test
  public void isZipCodeInListFailsForInvalidZipCode() {
    String zipCode = "76700";

    assertThat(zipCodeService.isZipCodeInList(zipCode)).isEqualTo(false);
  }

  @Test
  public void isZipCodeInListPassesForValidZipCodeInSponsor() {
    String sponsor = "SPONSOR_A";
    String zipCode = "48362";

    assertThat(zipCodeService.isZipCodeInList(sponsor, zipCode)).isEqualTo(true);
  }

  @Test
  public void isZipCodeInListFailsForValidZipCodeInWrongSponsor() {
    String sponsor = "SPONSOR_A";
    String zipCode = "32561";

    assertThat(zipCodeService.isZipCodeInList(sponsor, zipCode)).isEqualTo(false);
  }

  @Test
  public void getZipCodesForStatePassesWithoutSponsor() {
    String zipCode = "texas";

    List<ZipCode> validZipCodes =
        Lists.newArrayList(
            new ZipCode("Texas", "78723"),
            new ZipCode("Texas", "78665"),
            new ZipCode("Texas", "76712"),
            new ZipCode("Texas", "76831"));

    assertThat(zipCodeService.getZipCodesForState(zipCode)).isEqualTo(validZipCodes);
  }

  @Test
  public void getZipCodesForStatePassesWithSponsor() {
    String sponsor = "sponsor_a";
    String zipCode = "fLoRiDa";

    List<ZipCode> validZipCodes =
        Lists.newArrayList(new ZipCode("Florida", "32043"), new ZipCode("Florida", "32202"));

    assertThat(zipCodeService.getZipCodesForState(sponsor, zipCode)).isEqualTo(validZipCodes);
  }

  @Test
  public void getZipCodesForStateReturnsEmptyListForInvalidSponsor() {
    String sponsor = "sponsor_c";
    String zipCode = "fLoRiDa";

    assertThat(zipCodeService.getZipCodesForState(sponsor, zipCode))
        .isEqualTo(Lists.newArrayList());
  }

  @Test
  public void getZipCodesForStateReturnsEmptyListForInvalidState() {
    String sponsor = "sponsor_b";
    String zipCode = "New York";

    assertThat(zipCodeService.getZipCodesForState(sponsor, zipCode))
        .isEqualTo(Lists.newArrayList());
  }
}
