package org.l2x6.discotheq.api;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import org.l2x6.discotheq.api.builder.MajorVersionsBuilder;

import java.net.URI;

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

    public MajorVersionsBuilder majorVersions() {
        return new MajorVersionsBuilder(this.api);
    }
}
