/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import io.quarkus.test.junit.QuarkusTest;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.l2x6.discotheq.api.api.DiscotheqApi;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.MajorVersion;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class DiscotheqApiTest {

    private static final String API_RESOURCE_ROOT = "/disco/v3.0";

    @RegisterExtension
    static final WireMockExtension WIRE_MOCK = new WireMockExtension(
            WireMockExtension.newInstance()
                    .options(wireMockConfig().dynamicPort())
                    .resetOnEachTest(false)) {
        @Override
        protected void onBeforeAll(WireMockRuntimeInfo wireMockRuntimeInfo) {
            stubJsonResponses(this);
        }
    };

    @Test
    void getAllMajorVersionsV3DeserializesResponse() {
        final URI uri = URI.create(WIRE_MOCK.baseUrl());
        final DiscotheqApi api = QuarkusRestClientBuilder.newBuilder()
                .baseUri(uri)
                .build(DiscotheqApi.class);

        final ApiResponse<MajorVersion> response = api.getAllMajorVersionsV3(
                null, null, null, null, null, null, null)
                .await()
                .indefinitely();

        assertThat(response.message()).isEmpty();
        assertThat(response.result()).isNotEmpty().allSatisfy(majorVersion -> {
            assertThat(majorVersion.majorVersion()).isPositive();
            assertThat(majorVersion.termOfSupport()).isNotBlank();
            assertThat(majorVersion.maintained()).isNotNull();
            assertThat(majorVersion.earlyAccessOnly()).isNotNull();
            assertThat(majorVersion.releaseStatus()).isNotBlank();
            assertThat(majorVersion.versions()).isNotEmpty().allSatisfy(version -> assertThat(version).isNotBlank());
        });
    }

    private static void stubJsonResponses(WireMockExtension wireMock) {
        try {
            final Path resourceRoot = Path.of("src/test/resources/" + API_RESOURCE_ROOT);
            try (var paths = Files.walk(resourceRoot)) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".json"))
                        .forEach(path -> stubJsonResponse(wireMock, resourceRoot, path));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void stubJsonResponse(WireMockExtension wireMock, Path resourceRoot, Path jsonFile) {
        final String relativePath = resourceRoot.relativize(jsonFile).toString().replace(File.separatorChar, '/');
        final String relativeEndpointPath = relativePath.substring(0, relativePath.length() - 5);
        final String endpointPath = relativeEndpointPath.equals("index")
                ? API_RESOURCE_ROOT
                : API_RESOURCE_ROOT + "/" + relativeEndpointPath;
        try {
            wireMock.stubFor(get(urlPathEqualTo(endpointPath))
                    .willReturn(okJson(Files.readString(jsonFile))));
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read JSON response " + jsonFile, e);
        }
    }
}
