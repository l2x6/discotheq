/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import io.smallrye.mutiny.Uni;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.l2x6.discotheq.api.DiscoApi;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoveryScope;
import org.l2x6.discotheq.api.model.Distribution;

public class DistributionsBuilder {
    private final DiscoApi api;
    private Boolean includeVersions;
    private Boolean includeSynonyms;
    private List<DiscoveryScope> discoveryScopeId;

    public DistributionsBuilder(DiscoApi api) {
        this.api = api;
    }

    public DistributionsBuilder includeVersions(Boolean includeVersions) {
        this.includeVersions = includeVersions;
        return this;
    }

    public DistributionsBuilder includeVersions() {
        this.includeVersions = true;
        return this;
    }

    public DistributionsBuilder notIncludeVersions() {
        this.includeVersions = false;
        return this;
    }

    public DistributionsBuilder includeSynonyms(Boolean includeSynonyms) {
        this.includeSynonyms = includeSynonyms;
        return this;
    }

    public DistributionsBuilder includeSynonyms() {
        this.includeSynonyms = true;
        return this;
    }

    public DistributionsBuilder notIncludeSynonyms() {
        this.includeSynonyms = false;
        return this;
    }

    public DistributionsBuilder discoveryScopeId(DiscoveryScope... discoveryScopeIds) {
        if (discoveryScopeIds != null && discoveryScopeIds.length > 0) {
            if (this.discoveryScopeId == null) {
                this.discoveryScopeId = new ArrayList<>(discoveryScopeIds.length);
            }
            for (DiscoveryScope id : discoveryScopeIds) {
                this.discoveryScopeId.add(id);
            }
        }
        return this;
    }

    public DistributionsBuilder discoveryScopeId(Collection<DiscoveryScope> discoveryScopeIds) {
        if (discoveryScopeIds != null && discoveryScopeIds.size() > 0) {
            if (this.discoveryScopeId == null) {
                this.discoveryScopeId = new ArrayList<>(discoveryScopeIds.size());
            }
            this.discoveryScopeId.addAll(discoveryScopeIds);
        }
        return this;
    }

    public Uni<ApiResponse<Distribution>> call() {
        return api.distributions(includeVersions, includeSynonyms, discoveryScopeId);
    }
}
