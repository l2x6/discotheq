package org.l2x6.discotheq.api.builder;

import io.smallrye.mutiny.Uni;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.l2x6.discotheq.api.DiscoApi;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.DiscoveryScope;
import org.l2x6.discotheq.api.model.MajorVersion;
import org.l2x6.discotheq.api.model.Match;

public class MajorVersionsBuilder {
    private final DiscoApi api;
    private Boolean ea;

    private Boolean ga;

    private Boolean maintained;

    private Boolean includeBuild;

    private List<DiscoveryScope> discoveryScopeId;

    private Match match;

    private Boolean includeVersions;

    public MajorVersionsBuilder(DiscoApi api) {
        this.api = api;
    }

    public MajorVersionsBuilder ea(Boolean ea) {
        this.ea = ea;
        return this;
    }

    public MajorVersionsBuilder ea() {
        this.ea = true;
        return this;
    }

    public MajorVersionsBuilder notEa() {
        this.ea = false;
        return this;
    }

    public MajorVersionsBuilder ga(Boolean ga) {
        this.ga = ga;
        return this;
    }

    public MajorVersionsBuilder ga() {
        this.ga = true;
        return this;
    }

    public MajorVersionsBuilder notGa() {
        this.ga = false;
        return this;
    }

    public MajorVersionsBuilder maintained(Boolean maintained) {
        this.maintained = maintained;
        return this;
    }

    public MajorVersionsBuilder maintained() {
        this.maintained = true;
        return this;
    }

    public MajorVersionsBuilder notMaintained() {
        this.maintained = false;
        return this;
    }

    public MajorVersionsBuilder includeBuild(Boolean includeBuild) {
        this.includeBuild = includeBuild;
        return this;
    }

    public MajorVersionsBuilder includeBuild() {
        this.includeBuild = true;
        return this;
    }

    public MajorVersionsBuilder notIncludeBuild() {
        this.includeBuild = false;
        return this;
    }

    public MajorVersionsBuilder includeVersions(Boolean includeVersions) {
        this.includeVersions = includeVersions;
        return this;
    }

    public MajorVersionsBuilder includeVersions() {
        this.includeVersions = true;
        return this;
    }

    public MajorVersionsBuilder notIncludeVersions() {
        this.includeVersions = false;
        return this;
    }

    public MajorVersionsBuilder matchAll() {
        this.match = Match.ALL;
        return this;
    }

    public MajorVersionsBuilder matchAny() {
        this.match = Match.ANY;
        return this;
    }

    public MajorVersionsBuilder discoveryScopeId(DiscoveryScope... discoveryScopeIds) {
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

    public MajorVersionsBuilder discoveryScopeId(Collection<DiscoveryScope> discoveryScopeIds) {
        if (discoveryScopeIds != null && discoveryScopeIds.size() > 0) {
            if (this.discoveryScopeId == null) {
                this.discoveryScopeId = new ArrayList<>(discoveryScopeIds.size());
            }
            this.discoveryScopeId.addAll(discoveryScopeIds);
        }
        return this;
    }

    public Uni<ApiResponse<MajorVersion>> call() {
        return api.majorVersions(ea, ga, maintained, includeBuild, discoveryScopeId, match, includeVersions);
    }

}
