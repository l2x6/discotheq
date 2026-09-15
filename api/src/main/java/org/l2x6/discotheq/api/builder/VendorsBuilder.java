/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.smallrye.mutiny.Uni;
import org.l2x6.discotheq.api.DiscoApi;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.Vendor;

public class VendorsBuilder {
    private final DiscoApi api;
    private Boolean includeDistributions;

    public VendorsBuilder(DiscoApi api) {
        this.api = api;
    }

    public VendorsBuilder includeDistributions(Boolean includeDistributions) {
        this.includeDistributions = includeDistributions;
        return this;
    }

    public VendorsBuilder includeDistributions() {
        this.includeDistributions = true;
        return this;
    }

    public VendorsBuilder notIncludeDistributions() {
        this.includeDistributions = false;
        return this;
    }

    public Uni<ApiResponse<Vendor>> call() {
        return api.vendors(includeDistributions);
    }
}
