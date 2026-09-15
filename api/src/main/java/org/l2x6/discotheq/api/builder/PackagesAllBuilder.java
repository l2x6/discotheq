/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.smallrye.mutiny.Uni;
import org.l2x6.discotheq.api.DiscoApi;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoPackage;

public class PackagesAllBuilder {
    private final DiscoApi api;
    private Boolean downloadable;
    private Boolean includeEa;

    public PackagesAllBuilder(DiscoApi api) {
        this.api = api;
    }

    public PackagesAllBuilder downloadable(Boolean downloadable) {
        this.downloadable = downloadable;
        return this;
    }

    public PackagesAllBuilder downloadable() {
        this.downloadable = true;
        return this;
    }

    public PackagesAllBuilder notDownloadable() {
        this.downloadable = false;
        return this;
    }

    public PackagesAllBuilder includeEa(Boolean includeEa) {
        this.includeEa = includeEa;
        return this;
    }

    public PackagesAllBuilder includeEa() {
        this.includeEa = true;
        return this;
    }

    public PackagesAllBuilder notIncludeEa() {
        this.includeEa = false;
        return this;
    }

    public Uni<ApiResponse<DiscoPackage>> call() {
        return api.packagesAll(downloadable, includeEa);
    }
}
