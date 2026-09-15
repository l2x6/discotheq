/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.quarkus.test.junit.QuarkusTest;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.l2x6.discotheq.api.DiscoClient;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoveryScope.KnownDiscoveryScope;
import org.l2x6.discotheq.api.model.MajorVersion;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class MajorVersionsBuilderTest {

    private static final Duration TIMEOUT = Duration.ofSeconds(30);
    private static final DiscoClient CLIENT = DiscoClient.of("https://api.foojay.io");

    @Test
    void majorVersionsWithDefaults() {
        final ApiResponse<MajorVersion> response = call(CLIENT.majorVersions());

        assertValid(response);
        assertThat(response.result())
                .hasSizeGreaterThanOrEqualTo(22)
                .allSatisfy(version -> {
                    assertThat(version.earlyAccessOnly()).isFalse();
                    assertThat(version.releaseStatus()).isEqualTo("ga");
                    assertThat(version.versions()).isNotEmpty();
                });
    }

    @Test
    void majorVersionsWithExplicitAndScopeOptions() {

        assertMajorVersionsWithExplicitAndScopeOptions(call(CLIENT.majorVersions()
                .notEa()
                .ga()
                .maintained()
                .includeBuild()
                .discoveryScopeId(List.of(KnownDiscoveryScope.PUBLIC))
                .notIncludeVersions()));

        assertMajorVersionsWithExplicitAndScopeOptions(call(CLIENT.majorVersions()
                .ea(false)
                .ga(true)
                .maintained(true)
                .includeBuild(true)
                .discoveryScopeId(List.of(KnownDiscoveryScope.PUBLIC))
                .matchAll()
                .includeVersions(false)));
    }

    private static ApiResponse<MajorVersion> call(MajorVersionsBuilder builder) {
        return builder.call().await().atMost(TIMEOUT);
    }

    private static void assertMajorVersionsWithExplicitAndScopeOptions(ApiResponse<MajorVersion> response) {
        assertValid(response);
        assertThat(response.result())
                .hasSize(7)
                .allSatisfy(version -> {
                    assertThat(version.earlyAccessOnly()).isFalse();
                    assertThat(version.releaseStatus()).isEqualTo("ga");
                    assertThat(version.maintained()).isTrue();
                    assertThat(version.versions()).isNullOrEmpty();
                });
    }

    private static void assertValid(ApiResponse<MajorVersion> response) {
        assertThat(response).isNotNull();
        assertThat(response.message()).isNotNull();
        assertThat(response.result())
                .allSatisfy(version -> {
                    assertThat(version.majorVersion()).isPositive();
                    assertThat(version.termOfSupport()).isNotBlank();
                    assertThat(version.maintained()).isNotNull();
                    assertThat(version.earlyAccessOnly()).isNotNull();
                    assertThat(version.releaseStatus()).isNotBlank();
                });
    }
}
