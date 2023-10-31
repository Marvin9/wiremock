/*
 * Copyright (C) 2023 Thomas Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tomakehurst.wiremock.verification;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.junit.Assert.assertThrows;

import com.github.tomakehurst.wiremock.AcceptanceTestBase;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DisabledRequestJournalTest extends AcceptanceTestBase {
  @BeforeEach
  void setupServerWithRequestJournalDisabled() {
    setupServer(WireMockConfiguration.options().disableRequestJournal());

    wm.stubFor(get("/one").willReturn(ok()));
    wm.stubFor(get("/two").willReturn(ok()));

    testClient.get("/two");
    testClient.get("/one");
  }

  @Test
  public void throwsErrorOnGetAllServerEvents() {
    assertThrows(RequestJournalDisabledException.class, () -> wm.getAllServeEvents());
  }

  @Test
  public void throwsErrorOnGetServedStub() {
    assertThrows(
        RequestJournalDisabledException.class,
        () -> wireMockServer.getServedStub(new UUID(1000, 1000)));
  }

  @Test
  public void throwsErrorOnRemoveServeEventsMatching() {
    assertThrows(
        RequestJournalDisabledException.class,
        () -> wireMockServer.removeServeEventsMatching(RequestPattern.ANYTHING));
  }

  @Test
  public void throwsErrorOnRemoteServeEventsForStubsMatchingMetadata() {
    assertThrows(
        RequestJournalDisabledException.class,
        () -> wireMockServer.removeServeEventsForStubsMatchingMetadata(null));
  }

  @Test
  public void returnMinusOneOnCountMatchedRequest() {
    VerificationResult result = wireMockServer.countRequestsMatching(RequestPattern.ANYTHING);

    Assertions.assertEquals(-1, result.getCount());
  }

  @Test
  void returnEmptyOnRequestMatching() {
    FindRequestsResult requestsResult =
        wireMockServer.findRequestsMatching(RequestPattern.ANYTHING);

    Assertions.assertEquals(0, requestsResult.getRequests().size());
  }
}
