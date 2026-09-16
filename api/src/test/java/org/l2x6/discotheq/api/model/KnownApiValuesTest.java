/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import org.junit.jupiter.api.Test;
import org.l2x6.discotheq.api.model.Architecture.KnownArchitecture;
import org.l2x6.discotheq.api.model.ArchiveType.KnownArchiveType;
import org.l2x6.discotheq.api.model.DiscoveryScope.KnownDiscoveryScope;
import org.l2x6.discotheq.api.model.Fpu.KnownFpu;
import org.l2x6.discotheq.api.model.Latest.KnownLatest;
import org.l2x6.discotheq.api.model.LibCType.KnownLibCType;
import org.l2x6.discotheq.api.model.OperatingSystem.KnownOperatingSystem;
import org.l2x6.discotheq.api.model.PackageType.KnownPackageType;
import org.l2x6.discotheq.api.model.ReleaseStatus.KnownReleaseStatus;
import org.l2x6.discotheq.api.model.TermOfSupport.KnownTermOfSupport;
import org.l2x6.discotheq.api.model.Vendor.KnownVendor;

import static org.assertj.core.api.Assertions.assertThat;

class KnownApiValuesTest {

    @Test
    void resolvesApiStringsIgnoringCase() {
        assertThat(KnownArchitecture.fromApiString("AARCH64")).isEqualTo(KnownArchitecture.AARCH64);
        assertThat(KnownArchiveType.fromApiString("TAR.GZ")).isEqualTo(KnownArchiveType.TAR_GZ);
        assertThat(KnownDiscoveryScope.fromApiString("PUBLIC")).isEqualTo(KnownDiscoveryScope.PUBLIC);
        assertThat(KnownFpu.fromApiString("HARD_FLOAT")).isEqualTo(KnownFpu.HARD_FLOAT);
        assertThat(KnownLatest.fromApiString("PER_DISTRO")).isEqualTo(KnownLatest.PER_DISTRIBUTION);
        assertThat(KnownLibCType.fromApiString("GLIBC")).isEqualTo(KnownLibCType.GLIBC);
        assertThat(KnownOperatingSystem.fromApiString("LINUX")).isEqualTo(KnownOperatingSystem.ALPINE_LINUX);
        assertThat(KnownPackageType.fromApiString("JDK")).isEqualTo(KnownPackageType.JDK);
        assertThat(KnownReleaseStatus.fromApiString("GA")).isEqualTo(KnownReleaseStatus.GA);
        assertThat(KnownTermOfSupport.fromApiString("LTS")).isEqualTo(KnownTermOfSupport.LTS);
        assertThat(KnownVendor.fromApiString("RED_HAT")).isEqualTo(KnownVendor.red_hat);
    }

    @Test
    void returnsNullForUnknownApiStrings() {
        assertThat(KnownArchitecture.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownArchiveType.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownDiscoveryScope.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownFpu.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownLatest.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownLibCType.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownOperatingSystem.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownPackageType.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownReleaseStatus.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownTermOfSupport.fromApiString("unknown-api-value")).isNull();
        assertThat(KnownVendor.fromApiString("unknown-api-value")).isNull();
    }
}
