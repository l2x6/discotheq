/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoveryScope.KnownDiscoveryScope;
import org.l2x6.discotheq.api.model.Distribution;

import static org.assertj.core.api.Assertions.assertThat;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.CLIENT;
import static org.l2x6.discotheq.api.builder.BuilderTestUtils.TIMEOUT;

@QuarkusTest
class DistributionsBuilderTest {

    @Test
    void distributionsWithDefaults() {
        final ApiResponse<Distribution> response = call(CLIENT.distributions());

        assertValid(response);
        assertThat(response.result()).allSatisfy(distribution -> {
            assertThat(distribution.versions()).isNotNull();
            assertThat(distribution.synonyms()).isNotNull();
        });
    }

    @Test
    void distributionsWithExplicitAndScopeOptions() {
        assertWithoutVersionsAndSynonyms(call(CLIENT.distributions()
                .notIncludeVersions()
                .notIncludeSynonyms()
                .discoveryScopeId(KnownDiscoveryScope.PUBLIC)));

        assertWithoutVersionsAndSynonyms(call(CLIENT.distributions()
                .includeVersions(false)
                .includeSynonyms(false)
                .discoveryScopeId(List.of(KnownDiscoveryScope.PUBLIC))));
    }

    private static ApiResponse<Distribution> call(DistributionsBuilder builder) {
        return builder.call().await().atMost(TIMEOUT);
    }

    private static void assertWithoutVersionsAndSynonyms(ApiResponse<Distribution> response) {
        assertValid(response);
        assertThat(response.result()).allSatisfy(distribution -> {
            assertThat(distribution.versions()).isNullOrEmpty();
            assertThat(distribution.synonyms()).isNullOrEmpty();
        });
    }

    private static void assertValid(ApiResponse<Distribution> response) {
        assertThat(response).isNotNull();
        assertThat(response.message()).isNotNull();
        assertThat(response.result())
                .hasSizeGreaterThanOrEqualTo(30)
                .allSatisfy(distribution -> {
                    assertThat(distribution.name()).isNotBlank();
                    assertThat(distribution.apiParameter()).isNotBlank();
                    assertThat(distribution.vendor()).isNotBlank();
                    assertThat(distribution.maintained()).isNotNull();
                    assertThat(distribution.available()).isNotNull();
                    assertThat(distribution.buildOfOpenjdk()).isNotNull();
                    assertThat(distribution.buildOfGraalvm()).isNotNull();
                    assertThat(distribution.officialUri()).isNotBlank();
                });
    }
}
