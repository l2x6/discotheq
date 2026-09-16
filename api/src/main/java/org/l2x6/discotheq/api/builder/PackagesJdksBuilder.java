/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.smallrye.mutiny.Uni;
import org.l2x6.discotheq.api.DiscoApi;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoPackage;

public class PackagesJdksBuilder extends PackagesBuilder<PackagesJdksBuilder> {
    private Boolean freeToUseInProduction;

    public PackagesJdksBuilder(DiscoApi api) {
        super(api);
    }

    @Override
    protected PackagesJdksBuilder self() {
        return this;
    }

    public PackagesJdksBuilder freeToUseInProduction(Boolean freeToUseInProduction) {
        this.freeToUseInProduction = freeToUseInProduction;
        return this;
    }

    public PackagesJdksBuilder freeToUseInProduction() {
        this.freeToUseInProduction = true;
        return this;
    }

    public PackagesJdksBuilder notFreeToUseInProduction() {
        this.freeToUseInProduction = false;
        return this;
    }

    public Uni<ApiResponse<DiscoPackage>> call() {
        return api.packagesJdks(
                version,
                jdkVersion,
                distro,
                distribution,
                architecture,
                fpu,
                archiveType,
                operatingSystem,
                libcType,
                libCType,
                releaseStatus,
                javafxBundled,
                withJavafxIfAvailable,
                latest,
                signatureAvailable,
                freeToUseInProduction,
                tckTested,
                aqavitCertified,
                match);
    }
}
