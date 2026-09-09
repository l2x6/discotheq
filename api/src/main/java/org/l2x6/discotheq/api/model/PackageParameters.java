/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PackageParameters(
        String architecture,
        String archiveType,
        String bitness,
        String directlyDownloadable,
        String distro,
        String feature,
        String fpu,
        String javafxBundled,
        String withJavafxIfAvailable,
        String latest,
        @JsonProperty("lib_c_type") String libCType,
        String majorVersion,
        String operatingSystem,
        String packageType,
        String releaseStatus,
        String termOfSupport,
        String freeUseInProduction,
        String checksumType,
        String version) {
}
