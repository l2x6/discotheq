/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.smallrye.mutiny.Uni;
import org.l2x6.discotheq.api.DiscoApi;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoPackage;

public class PackagesJresBuilder extends PackagesBuilder<PackagesJresBuilder> {

    public PackagesJresBuilder(DiscoApi api) {
        super(api);
    }

    @Override
    protected PackagesJresBuilder self() {
        return this;
    }

    public Uni<ApiResponse<DiscoPackage>> call() {
        return api.packagesJres(
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
                tckTested,
                aqavitCertified,
                match);
    }
}
