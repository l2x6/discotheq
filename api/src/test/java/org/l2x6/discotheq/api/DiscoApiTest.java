/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.l2x6.discotheq.api.model.ApiResponse;
import org.l2x6.discotheq.api.model.Architecture;
import org.l2x6.discotheq.api.model.DaysSinceRelease;
import org.l2x6.discotheq.api.model.DaysSinceUpdate;
import org.l2x6.discotheq.api.model.DiscoEndpoint;
import org.l2x6.discotheq.api.model.DiscoPackage;
import org.l2x6.discotheq.api.model.DiscoParameters;
import org.l2x6.discotheq.api.model.Distribution;
import org.l2x6.discotheq.api.model.DistributionParameters;
import org.l2x6.discotheq.api.model.Feature;
import org.l2x6.discotheq.api.model.IdParameters;
import org.l2x6.discotheq.api.model.LatestDistributionVersion;
import org.l2x6.discotheq.api.model.MajorVersion;
import org.l2x6.discotheq.api.model.MajorVersionParameters;
import org.l2x6.discotheq.api.model.OperatingSystem;
import org.l2x6.discotheq.api.model.PackageLinks;
import org.l2x6.discotheq.api.model.PackageParameters;
import org.l2x6.discotheq.api.model.RemainingDaysToRelease;
import org.l2x6.discotheq.api.model.RemainingDaysToUpdate;
import org.l2x6.discotheq.api.model.SupportedValue;
import org.l2x6.discotheq.api.model.UpcomingRelease;
import org.l2x6.discotheq.api.model.Vendor;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class DiscoApiTest {

    private static final String API_RESOURCE_ROOT = "/disco/v3.0";
    private static final String PACKAGE_ID = "89e929798c69bb09512d7f655469018f";

    private static final DiscoPackage ZULU_JDK = new DiscoPackage(
            PACKAGE_ID, "dmg", "zulu", 26, "26.0.2.1+1", "26.32.203", 26, true, "ga", "sts", "macos",
            "libc", "x64", "unknown", "jdk", true, true,
            "zulu26.32.203-ca-fx-jdk26.0.2.1-macosx_x64.dmg",
            new PackageLinks("https://api.foojay.io/disco/v3.0/ids/" + PACKAGE_ID,
                    "https://api.foojay.io/disco/v3.0/ids/" + PACKAGE_ID + "/redirect"),
            true, "yes",
            "https://cdn.azul.com/zulu/pdf/cert.zulu26.32.203-ca-fx-jdk26.0.2.1-macosx_x64.dmg.pdf",
            "unknown", "", 338_567_802L, List.of());

    private static final DiscoPackage CORRETTO_JDK = new DiscoPackage(
            "475391b85d3045ee4bb5f4bbac2318f4", "deb", "corretto", 8, "8.0.222+10", "8.222.10", 8,
            false, "ga", "lts", "linux", "glibc", "arm64", "unknown", "jdk", true, true,
            "java-1.8.0-amazon-corretto-jdk_8.222.10-2_arm64.deb",
            new PackageLinks("https://api.foojay.io/disco/v3.0/ids/475391b85d3045ee4bb5f4bbac2318f4",
                    "https://api.foojay.io/disco/v3.0/ids/475391b85d3045ee4bb5f4bbac2318f4/redirect"),
            true, "unknown", "", "unknown", "", 100_014_032L, List.of());

    private static final DiscoPackage GRAALVM_JDK = new DiscoPackage(
            "1da4c6f7907e2b64bf188d67b2f4dadd", "zip", "graalvm", 17, "17.0.7", "17.0.7", 17,
            false, "ga", "lts", "windows", "c_std_lib", "x64", "unknown", "jdk", false, true,
            "graalvm-jdk-17.0.7_windows-x64_bin.zip",
            new PackageLinks("https://api.foojay.io/disco/v3.0/ids/1da4c6f7907e2b64bf188d67b2f4dadd",
                    "https://api.foojay.io/disco/v3.0/ids/1da4c6f7907e2b64bf188d67b2f4dadd/redirect"),
            true, "unknown", "", "unknown", "", 320_428_700L, List.of());

    private static final DiscoPackage ZULU_JRE = new DiscoPackage(
            "b9ab5073dfa6c3b74327ead131f76130", "zip", "zulu", 26, "26.0.2.1+1", "26.32.203", 26,
            true, "ga", "sts", "windows", "c_std_lib", "x64", "unknown", "jre", false, true,
            "zulu26.32.203-ca-jre26.0.2.1-win_x64.zip",
            new PackageLinks("https://api.foojay.io/disco/v3.0/ids/b9ab5073dfa6c3b74327ead131f76130",
                    "https://api.foojay.io/disco/v3.0/ids/b9ab5073dfa6c3b74327ead131f76130/redirect"),
            true, "yes", "https://cdn.azul.com/zulu/pdf/cert.zulu26.32.203-ca-jre26.0.2.1-win_x64.zip.pdf",
            "unknown", "", 60_113_638L, List.of());

    private static final DiscoParameters PARAMETERS = new DiscoParameters(
            new PackageParameters(
                    "aarch64,aarch32,arm64,arm32,arm,armhf,armel,mips,mipsel,ppc64le,ppc64,ppc,riscv64,s390x,sparcv9,sparc,x64,x32,i386,i386,i386,x86_64,x86,amd64,ia64",
                    "apk,bin,cab,deb,dmg,msi,pkg,rpm,src_tar,tar.gz,tar.xz,tap.zip,tar,tgz,tar.z,zip,exe",
                    "32,64", "true,false",
                    "aoj,aoj_openj9,bisheng,corretto,debian,dragonwell,eliya,gluon_graalvm,graalvm_ce8,graalvm_ce11,graalvm_ce16,graalvm_ce17,graalvm_ce19,graalvm_ce20,graalvm_community,graalvm,jetbrains,kona,liberica,liberica_native,mandrel,microsoft,ojdk_build,openlogic,oracle_open_jdk,oracle,redhat,sap_machine,semeru,semeru_certified,temurin,trava,zulu,zulu_prime",
                    "loom,panama,lanai,valhalla,kona_fiber,crac", "hard_float,soft_float,unknown", "true,false",
                    "true,false", "all_of_version,overall,per_distro,per_version,available",
                    "glibc,musl,libc,c_std_lib", "29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,31,30",
                    "linux,free_bsd,macos,windows,solaris,qnx,aix", "jdk,jre", "ga,ea", "sts,mts,lts",
                    "true,false", "md5,sha1,sha256,sha224,sha384,sha512,sha3_256", "feature.interim.update.patch"),
            new MajorVersionParameters("true,false", "true,false"),
            new DistributionParameters("public,build_of_openjdk,directly_downloadable,not_directly_downloadable"),
            new IdParameters("test"));

    @RegisterExtension
    static final WireMockExtension WIRE_MOCK = new WireMockExtension(
            WireMockExtension.newInstance()
                    .options(wireMockConfig().dynamicPort())
                    .resetOnEachTest(false)) {
        @Override
        protected void onBeforeAll(WireMockRuntimeInfo wireMockRuntimeInfo) {
            stubJsonResponses(this);
            this.stubFor(get(urlPathEqualTo(API_RESOURCE_ROOT + "/ids/" + PACKAGE_ID + "/redirect"))
                    .willReturn(aResponse().withStatus(301)
                            .withHeader("Location", "https://cdn.azul.com/zulu/bin/"
                                    + "zulu26.32.203-ca-fx-jdk26.0.2.1-macosx_x64.dmg")));
        }
    };

    @Test
    void majorVersions() {
        final MajorVersion first = first(client().majorVersions(null, null, null, null, null, null, null)
                .await().indefinitely(), "", 21, DiscoApiTest::isValidMajorVersion);
        assertMajorVersion(first, 26, "STS", true, false, "ga", 12, "26.0.2.1+1", "26",
                "5b5b91120a81de58c540defb46dd1198304142fa15adceecd7ceb23eaf183414");
    }

    @Test
    void packagesAllBuildsOfGraalvm() {
        assertThat(first(client().packagesAllBuildsOfGraalvm(null, null).await().indefinitely(), "", 1_171,
                DiscoApiTest::isValidPackage)).isEqualTo(GRAALVM_JDK);
    }

    @Test
    void packagesAllBuildsOfOpenjdk() {
        assertThat(first(client().packagesAllBuildsOfOpenjdk(null, null).await().indefinitely(), "", 1_141,
                DiscoApiTest::isValidPackage)).isEqualTo(CORRETTO_JDK);
    }

    @Test
    void packagesAll() {
        assertThat(first(client().packagesAll(null, null).await().indefinitely(), "", 1_145,
                DiscoApiTest::isValidPackage)).isEqualTo(CORRETTO_JDK);
    }

    @Test
    void daysSinceRelease() {
        assertThat(first(client().daysSinceRelease().await().indefinitely(),
                "Next release in 176 days at 17.03.2026", 1,
                value -> value != null && value.daysSinceLastRelease() >= 0 && hasText(value.dateSinceLastRelease())))
                .isEqualTo(new DaysSinceRelease(176, "17.03.2026"));
    }

    @Test
    void daysSinceUpdate() {
        assertThat(first(client().daysSinceUpdate().await().indefinitely(),
                "Next update in 50 days at 21.07.2026", 1,
                value -> value != null && value.daysSinceLastUpdate() >= 0 && hasText(value.dateSinceLastUpdate())))
                .isEqualTo(new DaysSinceUpdate(50, "21.07.2026"));
    }

    @Test
    void distributionsDistroName() {
        final Distribution first = first(client().distributionsDistroName("temurin", null, null, null, null, null, null, null)
                .await().indefinitely(), "", 1, DiscoApiTest::isValidDistribution);
        assertDistribution(first, "Temurin", "temurin", "eclipse_foundation", true, true, true, false,
                "https://adoptium.net/temurin/releases", null, null, null, null,
                922, "28-ea+14", "8.0.302+8",
                "5122b2c16b6d5c248199874e552a27059cdacbd872621203f6ed5e58af5c86f6");
    }

    @Test
    void distributionsVersionsVersion() {
        final Distribution first = first(client().distributionsVersionsVersion("21", null, null, null, null)
                .await().indefinitely(), "", 15, DiscoApiTest::isValidDistribution);
        assertDistribution(first, "Zulu", "zulu", "azul", true, true, true, false,
                "https://www.azul.com/downloads/?package=jdk", null, null, null, null,
                772, "27-ea+32", "6.0.42",
                "ec00bbc5f04cd7a7f792c64d78e6d1df7db4aabd37fef99614bb3206e69005db");
    }

    @Test
    void distributions() {
        final Distribution first = first(client().distributions(null, null, null).await().indefinitely(), "", 31,
                DiscoApiTest::isValidDistribution);
        assertDistribution(first, "Zulu", "zulu", "azul", true, true, true, false,
                "https://www.azul.com/downloads/?package=jdk", 12, "zulu", "Zulu Core",
                "831d59bfdb8efbb558a6422be9b021f395502bf4fd49b59490134c8429c6d7b4",
                772, "27-ea+32", "6.0.42",
                "ec00bbc5f04cd7a7f792c64d78e6d1df7db4aabd37fef99614bb3206e69005db");
    }

    @Test
    void packagesJdks() {
        assertThat(first(client().packagesJdks(
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null).await().indefinitely(), "33583 package(s) found", 1_141,
                DiscoApiTest::isValidPackage)).isEqualTo(ZULU_JDK);
    }

    @Test
    void packagesJres() {
        assertThat(first(client().packagesJres(
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null).await().indefinitely(), "19314 package(s) found", 1_136,
                DiscoApiTest::isValidPackage)).isEqualTo(ZULU_JRE);
    }

    @Test
    void distributionsVersionsLatest() {
        assertThat(first(client().distributionsVersionsLatest(null, null).await().indefinitely(), "", 31,
                DiscoApiTest::isValidLatestDistributionVersion))
                .isEqualTo(new LatestDistributionVersion("Eliya JDK", "eliya", "25.0.3", ""));
    }

    @Test
    void majorVersionsQuery() {
        final MajorVersion first = first(client().majorVersionsQuery("latest_ga", null, null).await().indefinitely(),
                "", 1, DiscoApiTest::isValidMajorVersion);
        assertMajorVersion(first, 26, "STS", true, false, "ga", 12, "26.0.2.1+1", "26",
                "5b5b91120a81de58c540defb46dd1198304142fa15adceecd7ceb23eaf183414");
    }

    @Test
    void majorVersions1() {
        final MajorVersion first = first(client().majorVersions1(null, null, null, null, null, null)
                .await().indefinitely(), "", 26, DiscoApiTest::isValidMajorVersion);
        assertMajorVersion(first, 31, "STS", false, true, "ea", 0, null, null,
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
    }

    @Test
    void idsPkgIdRedirect() {
        try (Response response = client().idsPkgIdRedirect(PACKAGE_ID).await().indefinitely()) {
            assertThat(response.getStatus()).isEqualTo(301);
            assertThat(response.getHeaderString("Location")).isEqualTo(
                    "https://cdn.azul.com/zulu/bin/zulu26.32.203-ca-fx-jdk26.0.2.1-macosx_x64.dmg");
        }
    }

    @Test
    void packagesId() {
        assertThat(first(client().packagesId(PACKAGE_ID).await().indefinitely(), "", 1,
                DiscoApiTest::isValidPackage)).isEqualTo(ZULU_JDK);
    }

    @Test
    void packages() {
        assertThat(first(client().packages(
                null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null)
                .await().indefinitely(), "", 1_145, DiscoApiTest::isValidPackage)).isEqualTo(CORRETTO_JDK);
    }

    @Test
    void parameters() {
        assertThat(first(client().parameters().await().indefinitely(),
                "url parameter for different endpoints", 1, value -> value != null)).isEqualTo(PARAMETERS);
    }

    @Test
    void remainingDaysRelease() {
        assertThat(first(client().remainingDaysRelease().await().indefinitely(),
                "Next release in 6 days at 15.09.2026", 1,
                value -> value != null && value.daysToNextRelease() >= 0 && hasText(value.dateOfNextRelease())))
                .isEqualTo(new RemainingDaysToRelease(6, "15.09.2026"));
    }

    @Test
    void remainingDaysUpdate() {
        assertThat(first(client().remainingDaysUpdate().await().indefinitely(),
                "Next update in 41 days at 20.10.2026", 1,
                value -> value != null && value.daysToNextUpdate() >= 0 && hasText(value.dateOfNextUpdate())))
                .isEqualTo(new RemainingDaysToUpdate(41, "20.10.2026"));
    }

    @Test
    void majorVersionsMajorVersionEa() {
        final MajorVersion first = first(client().majorVersionsMajorVersionEa(21, null, null)
                .await().indefinitely(), "", 1, DiscoApiTest::isValidMajorVersion);
        assertMajorVersion(first, 21, "LTS", true, false, "ga", 292, "21.0.13-ea+5", "21-ea",
                "95ff73048042a47cd3430be2e5904303881ead0a8261733150356a84088ea6e5");
    }

    @Test
    void majorVersionsMajorVersionGa() {
        final MajorVersion first = first(client().majorVersionsMajorVersionGa(21, null, null)
                .await().indefinitely(), "", 1, DiscoApiTest::isValidMajorVersion);
        assertMajorVersion(first, 21, "LTS", true, false, "ga", 152, "21.0.12.1+2", "21",
                "aba61be4e89e01b2c16bc20ebf0c7e4d592d7fa550cc45d31b9b2b14c3e1b93c");
    }

    @Test
    void supportedArchitectures() {
        assertThat(first(client().supportedArchitectures().await().indefinitely(), "Supported architectures", 27,
                DiscoApiTest::isValidArchitecture))
                .isEqualTo(new Architecture("AARCH64", "AARCH64", "aarch64", "64"));
    }

    @Test
    void supportedArchiveTypes() {
        assertThat(first(client().supportedArchiveTypes().await().indefinitely(), "Supported archive types", 19,
                DiscoApiTest::isValidSupportedValue))
                .isEqualTo(new SupportedValue("APK", "apk", "apk"));
    }

    @Test
    void supportedFeatures() {
        assertThat(first(client().supportedFeatures().await().indefinitely(), "Supported features", 6,
                DiscoApiTest::isValidFeature))
                .isEqualTo(new Feature("LOOM", "Loom", "loom"));
    }

    @Test
    void supportedFpus() {
        assertThat(first(client().supportedFpus().await().indefinitely(), "Supported floating point types", 5,
                DiscoApiTest::isValidSupportedValue))
                .isEqualTo(new SupportedValue("HARD_FLOAT", "hardfloat", "hard_float"));
    }

    @Test
    void supportedLatestParameters() {
        assertThat(first(client().supportedLatestParameters().await().indefinitely(),
                "Supported latest parameters", 7, DiscoApiTest::isValidSupportedValue))
                .isEqualTo(new SupportedValue("ALL_OF_VERSION", "all of version", "all_of_version"));
    }

    @Test
    void supportedLibCTypes() {
        assertThat(first(client().supportedLibCTypes().await().indefinitely(), "Supported libc types", 6,
                DiscoApiTest::isValidSupportedValue))
                .isEqualTo(new SupportedValue("GLIBC", "glibc", "glibc"));
    }

    @Test
    void supportedOperatingSystems() {
        assertThat(first(client().supportedOperatingSystems().await().indefinitely(),
                "Supported operating systems", 11, DiscoApiTest::isValidOperatingSystem))
                .isEqualTo(new OperatingSystem("ALPINE_LINUX", "Alpine Linux", "linux", "musl"));
    }

    @Test
    void supportedPackageTypes() {
        assertThat(first(client().supportedPackageTypes().await().indefinitely(), "Supported package types", 4,
                DiscoApiTest::isValidSupportedValue))
                .isEqualTo(new SupportedValue("JDK", "JDK", "jdk"));
    }

    @Test
    void supportedReleaseStatus() {
        assertThat(first(client().supportedReleaseStatus().await().indefinitely(), "Supported release status", 4,
                DiscoApiTest::isValidSupportedValue))
                .isEqualTo(new SupportedValue("GA", "General Access", "ga"));
    }

    @Test
    void supportedTermsOfSupport() {
        assertThat(first(client().supportedTermsOfSupport().await().indefinitely(),
                "Supported terms of support", 5, DiscoApiTest::isValidSupportedValue))
                .isEqualTo(new SupportedValue("STS", "short term stable", "sts"));
    }

    @Test
    void upcomingReleases() {
        assertThat(first(client().upcomingReleases().await().indefinitely(), "", 1,
                DiscoApiTest::isValidUpcomingRelease))
                .isEqualTo(new UpcomingRelease("2026-09-15", "27.0.0", 19, "2026-10-20", "", 54));
    }

    @Test
    void vendors() {
        assertThat(first(client().vendors(null).await().indefinitely(), "", 18,
                DiscoApiTest::isValidVendor))
                .isEqualTo(new Vendor("AdoptOpenJDK", "adopt_open_jdk", null));
    }

    @Test
    void v3() {
        assertThat(first(client().v3().await().indefinitely(), "Endpoints v3.0", 5,
                value -> value != null && hasText(value.uri())))
                .isEqualTo(new DiscoEndpoint("https://api.foojay.io/disco/v3.0/parameters"));
    }

    private static org.l2x6.discotheq.api.api.DiscoApi client() {
        return QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(WIRE_MOCK.baseUrl()))
                .followRedirects(false)
                .build(org.l2x6.discotheq.api.api.DiscoApi.class);
    }

    private static <T> T first(ApiResponse<T> response, String expectedMessage, int expectedSize,
            Predicate<T> validity) {
        assertThat(response.message()).isEqualTo(expectedMessage);
        assertThat(response.result()).hasSize(expectedSize);
        assertThat(response.result().subList(1, response.result().size())).allMatch(validity);
        return response.result().get(0);
    }

    private static void assertMajorVersion(MajorVersion actual, int majorVersion, String termOfSupport,
            boolean maintained, boolean earlyAccessOnly, String releaseStatus, int versionsSize,
            String firstVersion, String lastVersion, String versionsHash) {
        assertThat(actual.majorVersion()).isEqualTo(majorVersion);
        assertThat(actual.termOfSupport()).isEqualTo(termOfSupport);
        assertThat(actual.maintained()).isEqualTo(maintained);
        assertThat(actual.earlyAccessOnly()).isEqualTo(earlyAccessOnly);
        assertThat(actual.releaseStatus()).isEqualTo(releaseStatus);
        assertStringList(actual.versions(), versionsSize, firstVersion, lastVersion, versionsHash);
    }

    private static void assertDistribution(Distribution actual, String name, String apiParameter, String vendor,
            boolean maintained, boolean available, boolean buildOfOpenjdk, boolean buildOfGraalvm, String officialUri,
            Integer synonymsSize, String firstSynonym, String lastSynonym, String synonymsHash,
            int versionsSize, String firstVersion, String lastVersion, String versionsHash) {
        assertThat(actual.name()).isEqualTo(name);
        assertThat(actual.apiParameter()).isEqualTo(apiParameter);
        assertThat(actual.vendor()).isEqualTo(vendor);
        assertThat(actual.maintained()).isEqualTo(maintained);
        assertThat(actual.available()).isEqualTo(available);
        assertThat(actual.buildOfOpenjdk()).isEqualTo(buildOfOpenjdk);
        assertThat(actual.buildOfGraalvm()).isEqualTo(buildOfGraalvm);
        assertThat(actual.officialUri()).isEqualTo(officialUri);
        if (synonymsSize == null) {
            assertThat(actual.synonyms()).isNull();
        } else {
            assertStringList(actual.synonyms(), synonymsSize, firstSynonym, lastSynonym, synonymsHash);
        }
        assertStringList(actual.versions(), versionsSize, firstVersion, lastVersion, versionsHash);
    }

    private static void assertStringList(List<String> actual, int expectedSize, String expectedFirst,
            String expectedLast, String expectedHash) {
        assertThat(actual).hasSize(expectedSize);
        if (expectedSize > 0) {
            assertThat(actual.get(0)).isEqualTo(expectedFirst);
            assertThat(actual.get(actual.size() - 1)).isEqualTo(expectedLast);
        }
        assertThat(sha256(actual)).isEqualTo(expectedHash);
    }

    private static String sha256(List<String> values) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (int i = 0; i < values.size(); i++) {
                if (i > 0) {
                    digest.update((byte) 0);
                }
                digest.update(values.get(i).getBytes(StandardCharsets.UTF_8));
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 must be available", e);
        }
    }

    private static boolean isValidMajorVersion(MajorVersion value) {
        return value != null && value.majorVersion() > 0 && hasText(value.termOfSupport())
                && value.maintained() != null && value.earlyAccessOnly() != null && hasText(value.releaseStatus())
                && value.versions() != null && value.versions().stream().allMatch(DiscoApiTest::hasText);
    }

    private static boolean isValidDistribution(Distribution value) {
        return value != null && hasText(value.name()) && hasText(value.apiParameter()) && hasText(value.vendor())
                && value.maintained() != null && value.available() != null && value.buildOfOpenjdk() != null
                && value.buildOfGraalvm() != null && hasText(value.officialUri())
                && (value.synonyms() == null || value.synonyms().stream().allMatch(DiscoApiTest::hasText))
                && value.versions() != null && value.versions().stream().allMatch(DiscoApiTest::hasText);
    }

    private static boolean isValidPackage(DiscoPackage value) {
        return value != null && hasText(value.id()) && hasText(value.archiveType()) && hasText(value.distribution())
                && value.majorVersion() > 0 && hasText(value.javaVersion()) && hasText(value.distributionVersion())
                && value.jdkVersion() > 0 && value.latestBuildAvailable() != null && hasText(value.releaseStatus())
                && hasText(value.termOfSupport()) && hasText(value.operatingSystem()) && hasText(value.libCType())
                && hasText(value.architecture()) && hasText(value.fpu()) && hasText(value.packageType())
                && value.javafxBundled() != null && value.directlyDownloadable() != null && hasText(value.filename())
                && value.links() != null && hasText(value.links().pkgInfoUri())
                && hasText(value.links().pkgDownloadRedirect()) && value.freeUseInProduction() != null
                && hasText(value.tckTested()) && value.tckCertUri() != null && hasText(value.aqavitCertified())
                && value.aqavitCertUri() != null && value.size() >= -1 && value.feature() != null;
    }

    private static boolean isValidLatestDistributionVersion(LatestDistributionVersion value) {
        return value != null && hasText(value.uiString()) && hasText(value.apiString())
                && value.latestGa() != null && value.latestEa() != null;
    }

    private static boolean isValidArchitecture(Architecture value) {
        return value != null && hasText(value.name()) && value.uiString() != null
                && value.apiString() != null && value.bitness() != null;
    }

    private static boolean isValidSupportedValue(SupportedValue value) {
        return value != null && hasText(value.name()) && value.uiString() != null && value.apiString() != null;
    }

    private static boolean isValidFeature(Feature value) {
        return value != null && hasText(value.name()) && hasText(value.uiString()) && hasText(value.apiString());
    }

    private static boolean isValidOperatingSystem(OperatingSystem value) {
        return value != null && hasText(value.name()) && value.uiString() != null
                && value.apiString() != null && value.libCType() != null;
    }

    private static boolean isValidUpcomingRelease(UpcomingRelease value) {
        return value != null && hasText(value.dateOfNextRelease()) && hasText(value.versionOfNextRelease())
                && value.daysUntilNextRelease() >= 0 && hasText(value.dateOfNextUpdate())
                && value.versionsOfNextUpdate() != null && value.daysUntilNextUpdate() >= 0;
    }

    private static boolean isValidVendor(Vendor value) {
        return value != null && hasText(value.uiString()) && hasText(value.apiString())
                && (value.distributions() == null
                        || value.distributions().stream().allMatch(DiscoApiTest::hasText));
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private static void stubJsonResponses(WireMockExtension wireMock) {
        try {
            final Path resourceRoot = Path.of("src/test/resources/" + API_RESOURCE_ROOT);
            try (var paths = Files.walk(resourceRoot)) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".json"))
                        .forEach(path -> stubJsonResponse(wireMock, resourceRoot, path));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void stubJsonResponse(WireMockExtension wireMock, Path resourceRoot, Path jsonFile) {
        final String relativePath = resourceRoot.relativize(jsonFile).toString().replace(File.separatorChar, '/');
        final String relativeEndpointPath = relativePath.substring(0, relativePath.length() - 5);
        final String endpointPath = relativeEndpointPath.equals("index")
                ? API_RESOURCE_ROOT
                : API_RESOURCE_ROOT + "/" + relativeEndpointPath;
        try {
            wireMock.stubFor(get(urlPathEqualTo(endpointPath))
                    .willReturn(okJson(Files.readString(jsonFile))));
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read JSON response " + jsonFile, e);
        }
    }
}
