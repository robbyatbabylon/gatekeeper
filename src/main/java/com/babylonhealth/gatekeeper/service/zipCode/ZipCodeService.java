package com.babylonhealth.gatekeeper.service.zipCode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZipCodeService {
  //  private final ZipcodesRepository zipcodesRepository;
  //  private final ConsumerNetworkService consumerNetworkService;
  private final List<Sponsors> sponsors;

  public ZipCodeService() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    this.sponsors =
        objectMapper.readValue(
            pathToString("files/zipcodes.json"), new TypeReference<List<Sponsors>>() {});
  }

  public List<ZipCode> getZipCodes(ZipCodeFilter zipCodeFilter) {
    return sponsors.stream()
        .filter(
            s ->
                !zipCodeFilter.getSponsorCode().isPresent()
                    || s.getSponsorCode().equalsIgnoreCase(zipCodeFilter.getSponsorCode().get()))
        .flatMap(s -> s.getZipCodes().stream())
        .filter(
            z ->
                !zipCodeFilter.getZipCode().isPresent()
                    || z.getZipCode().equals(zipCodeFilter.getZipCode().get()))
        .filter(
            z ->
                !zipCodeFilter.getState().isPresent()
                    || z.getState().equalsIgnoreCase(zipCodeFilter.getState().get()))
        .collect(Collectors.toList());
  }

  public List<ZipCode> getAllZipCodes(String sponsorCode) {
    return this.sponsors.stream()
        .filter(s -> s.getSponsorCode().equalsIgnoreCase(sponsorCode))
        .findFirst()
        .map(Sponsors::getZipCodes)
        .orElse(Collections.emptyList());
  }

  public List<ZipCode> getAllZipCodes() {
    return this.sponsors.stream()
        .flatMap(s -> s.getZipCodes().stream())
        .collect(Collectors.toList());
  }

  public Boolean isZipCodeInList(String sponsorCode, String zipCode) {
    Optional<Sponsors> sponsor = getSponsor(sponsorCode);

    if (sponsor.isPresent()) {
      return sponsor.get().getZipCodes().stream().anyMatch(z -> z.getZipCode().equals(zipCode));
    } else {
      return false;
    }
  }

  public Boolean isZipCodeInList(String zipCode) {
    return this.sponsors.stream()
        .flatMap(s -> s.getZipCodes().stream())
        .collect(Collectors.toList())
        .stream()
        .anyMatch(z -> z.getZipCode().equals(zipCode));
  }

  public List<ZipCode> getZipCodesForState(String sponsorCode, String state) {
    Optional<Sponsors> sponsor = getSponsor(sponsorCode);

    if (sponsor.isPresent()) {
      return sponsor.get().getZipCodes().stream()
          .filter(z -> z.getState().equalsIgnoreCase(state))
          .collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  public List<ZipCode> getZipCodesForState(String state) {
    return this.sponsors.stream()
        .flatMap(s -> s.getZipCodes().stream())
        .collect(Collectors.toList())
        .stream()
        .filter(z -> z.getState().equalsIgnoreCase(state))
        .collect(Collectors.toList());
  }

  private Optional<Sponsors> getSponsor(String sponsorCode) {
    return this.sponsors.stream()
        .filter(s -> s.getSponsorCode().equalsIgnoreCase(sponsorCode))
        .findFirst();
  }

  private String pathToString(String path) throws IOException {
    return StreamUtils.copyToString(
        new ClassPathResource(path).getInputStream(), Charset.defaultCharset());
  }
}
