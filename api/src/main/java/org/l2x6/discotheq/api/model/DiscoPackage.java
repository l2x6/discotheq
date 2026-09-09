/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record DiscoPackage(
        String id,
        String archiveType,
        String distribution,
        Integer majorVersion,
        String javaVersion,
        String distributionVersion,
        Integer jdkVersion,
        Boolean latestBuildAvailable,
        String releaseStatus,
        String termOfSupport,
        String operatingSystem,
        @JsonProperty("lib_c_type") String libCType,
        String architecture,
        String fpu,
        String packageType,
        Boolean javafxBundled,
        Boolean directlyDownloadable,
        String filename,
        PackageLinks links,
        Boolean freeUseInProduction,
        String tckTested,
        String tckCertUri,
        String aqavitCertified,
        String aqavitCertUri,
        Long size,
        List<Feature> feature) {
}
