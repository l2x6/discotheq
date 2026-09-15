/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import java.net.URI;
import org.l2x6.discotheq.api.builder.DistributionsBuilder;
import org.l2x6.discotheq.api.builder.MajorVersionsBuilder;
import org.l2x6.discotheq.api.builder.PackagesAllBuilder;
import org.l2x6.discotheq.api.builder.VendorsBuilder;

public class DiscoClient {

    public static DiscoClient of() {
        return of("https://api.foojay.io");
    }

    public static DiscoClient of(String baseUri) {
        return new DiscoClient(QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(baseUri))
                .build(DiscoApi.class));
    }

    public DiscoClient(DiscoApi api) {
        this.api = api;
    }

    private final DiscoApi api;

    public DiscoApi client() {
        return api;
    }

    public MajorVersionsBuilder majorVersions() {
        return new MajorVersionsBuilder(this.api);
    }

    public VendorsBuilder vendors() {
        return new VendorsBuilder(this.api);
    }

    public DistributionsBuilder distributions() {
        return new DistributionsBuilder(this.api);
    }

    public PackagesAllBuilder packagesAll() {
        return new PackagesAllBuilder(this.api);
    }
}
