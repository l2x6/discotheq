/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.api;

import java.util.List;

/**
 * foojay DiscoAPI
 * <p>
 * The foojay (Friends of OpenJDK) discovery api allows users to discover and query for Java packages (jre/jdk) from
 * different distributions.
 * </p>
 */
@jakarta.ws.rs.Path("/disco/v3.0")
@org.eclipse.microprofile.rest.client.inject.RegisterRestClient(configKey = "foojay_yml")
@jakarta.enterprise.context.ApplicationScoped
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public interface DefaultApi {

    /**
     * Return a list of major versions defined by the given parameters
     *
     * Return a list of major versions defined by the given parameters
     *
     * @param ea               Defines if versions in major version that are early access will be shown (optional
     *                         true/false, default false)
     * @param ga               Defines if versions in major version that are general availability will be shown (optional
     *                         true/false, default true)
     * @param maintained       Defines that only major versions will be shown that are actively be maintained. (optional
     *                         true/false, default false)
     * @param includeBuild     Includes build numbers if true (optional)
     * @param discoveryScopeId The scope of the package query (e.g. public, build_of_openjdk, build_of_graalvm,
     *                         directly_downloadable, not_directly_downloadable, free_to_use_in_production,
     *                         license_needed_for_production)
     * @param match            Defines how the discovery_socpe_ids should be matched (any will return all packages that
     *                         contain at least one of the discovery_scope_ids (default), all will only return packages that
     *                         contain all given discovery_scope_ids)
     * @param includeVersions  Shows all versions related to the given major version (e.g. major version 17 -\\> versions
     *                         17, 17.0.1, 17.0.2) (default is true)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/major_versions")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getAllMajorVersionsV3(

            @jakarta.ws.rs.QueryParam("ea") Boolean ea,

            @jakarta.ws.rs.QueryParam("ga") Boolean ga,

            @jakarta.ws.rs.QueryParam("maintained") Boolean maintained,

            @jakarta.ws.rs.QueryParam("include_build") Boolean includeBuild,

            @jakarta.ws.rs.QueryParam("discovery_scope_id") List<String> discoveryScopeId,

            @jakarta.ws.rs.QueryParam("match") String match,

            @jakarta.ws.rs.QueryParam("include_versions") Boolean includeVersions);

    /**
     * Returns all packages that are builds of GraalVM
     *
     * Returns all packages that are builds of GraalVM
     *
     * @param downloadable When set to true it will return all packages build of graalvm that are directly downloadable
     * @param includeEa    When set to true it will return all packages build of graalvm including early access builds
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/packages/all_builds_of_graalvm")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getAllPackagesGraalVMV3(

            @jakarta.ws.rs.QueryParam("downloadable") Boolean downloadable,

            @jakarta.ws.rs.QueryParam("include_ea") Boolean includeEa);

    /**
     * Returns all packages that are builds of OpenJDK
     *
     * Returns all packages that are builds of OpenJDK
     *
     * @param downloadable When set to true it will return all packages that are builds of openjdk that are directly
     *                     downloadable
     * @param includeEa    When set to true it will return all packages that are builds of openjdk including early access
     *                     builds
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/packages/all_builds_of_openjdk")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getAllPackagesOpenJDKV3(

            @jakarta.ws.rs.QueryParam("downloadable") Boolean downloadable,

            @jakarta.ws.rs.QueryParam("include_ea") Boolean includeEa);

    /**
     * Returns all packages defined the downloadable and include_ea parameter
     *
     * Returns all packages defined the downloadable and include_ea parameter
     *
     * @param downloadable When set to true it will return all packages that are directly downloadable
     * @param includeEa    When set to true it will return all packages including early access builds
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/packages/all")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getAllPackagesV3(

            @jakarta.ws.rs.QueryParam("downloadable") Boolean downloadable,

            @jakarta.ws.rs.QueryParam("include_ea") Boolean includeEa);

    /**
     * Returns the days since the last feature release (e.
     *
     * Returns the days since the last feature release (e.g. 21 GA) based on the current release cadence
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/days_since/release")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getDaysSinceLastRelease();

    /**
     * Returns the days since the last update (e.
     *
     * Returns the days since the last update (e.g. 17.0.9) based on the current release cadence
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/days_since/update")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getDaysSinceLastUpdate();

    /**
     * Returns detailled information about a given distribution
     *
     * Returns detailled information about a given distribution
     *
     * @param distroName
     * @param latestPerUpdate  If true the response will only contain the latest version of each update (e.g. in case of
     *                         17.0.1+1, 17.0.1+2 and 17.0.1+3 -\\> only 17.0.1+3 will be returned) (default is false)
     * @param discoveryScopeId The scope of the package query (e.g. public, build_of_openjdk, build_of_graalvm,
     *                         directly_downloadable, not_directly_downloadable, free_to_use_in_production,
     *                         license_needed_for_production)
     * @param match            Defines how the discovery_socpe_ids should be matched (any will return all packages that
     *                         contain at least one of the discovery_scope_ids (default), all will only return packages that
     *                         contain all given discovery_scope_ids)
     * @param includeVersions  If true the response will contain the list of supported versions of this distribution
     *                         (default is true)
     * @param includeSynonyms  If true the response will contain the list of synonyms used for this distribution (default is
     *                         false)
     * @param includeEa        If true the response will contain ea and ga versions, otherwise it will only contain ga
     *                         versions (default is true)
     * @param termOfSupport    A list of term of support strings like for example sts or lts that will be used to filter the
     *                         versions in case versions is set to true (default will return all types, STS, MTS and LTS)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/distributions/{distro_name}")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getDistributionV3(
            @jakarta.ws.rs.PathParam("distro_name") String distroName,

            @jakarta.ws.rs.QueryParam("latest_per_update") Boolean latestPerUpdate,

            @jakarta.ws.rs.QueryParam("discovery_scope_id") List<String> discoveryScopeId,

            @jakarta.ws.rs.QueryParam("match") String match,

            @jakarta.ws.rs.QueryParam("include_versions") Boolean includeVersions,

            @jakarta.ws.rs.QueryParam("include_synonyms") Boolean includeSynonyms,

            @jakarta.ws.rs.QueryParam("include_ea") Boolean includeEa,

            @jakarta.ws.rs.QueryParam("term_of_support") List<String> termOfSupport);

    /**
     * Returns a list of all distributions that support the given java version
     *
     * Returns a list of all distributions that support the given java version
     *
     * @param version          The version number to check the distributions for e.g. 1.8.0_265, 13.0.5.1, 15 etc.
     * @param discoveryScopeId
     * @param match
     * @param includeVersions  If true the response will contain the list of supported versions of this distribution
     *                         (default is true)
     * @param includeSynonyms  If true the response will contain the list of synonyms used for this distribution (default is
     *                         false)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/distributions/versions/{version}")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getDistributionsForGivenVersionV3(
            @jakarta.ws.rs.PathParam("version") String version,

            @jakarta.ws.rs.QueryParam("discovery_scope_id") List<String> discoveryScopeId,

            @jakarta.ws.rs.QueryParam("match") String match,

            @jakarta.ws.rs.QueryParam("include_versions") Boolean includeVersions,

            @jakarta.ws.rs.QueryParam("include_synonyms") Boolean includeSynonyms);

    /**
     * Returns a list of all supported distributions
     *
     * Returns a list of all supported distributions
     *
     * @param includeVersions  If true the response will contain the list of supported versions of this distribution
     *                         (default is true)
     * @param includeSynonyms  If true the response will contain the list of synonyms used for this distribution (default is
     *                         true)
     * @param discoveryScopeId Defines the scope that will be part of the response (e.g. build_of_openjdk)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/distributions")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getDistributionsV3(

            @jakarta.ws.rs.QueryParam("include_versions") Boolean includeVersions,

            @jakarta.ws.rs.QueryParam("include_synonyms") Boolean includeSynonyms,

            @jakarta.ws.rs.QueryParam("discovery_scope_id") List<String> discoveryScopeId);

    /**
     * Returns a list of packages that are of package_type JDK defined by the given parameters.
     *
     * Returns a list of packages that are of package_type JDK defined by the given parameters. The version parameter not
     * only supports different formats for version numbers (e.g. 11.9.0.1, 1.8.0_262, 15, 16-ea) but also ranges (e.g.
     * 15.0.1..\\<16). The ranges are defined as follows: VersionNumber1...VersionNumber2 =\\> includes VersionNumber1 and
     * VersionNumber2 VersionNumber1.. includes VersionNumber1 and excludes VersionNumber2 VersionNumber1\\>..VersionNUmber2
     * =\\> excludes VersionNumber1 and includes VersionNumber2 VersionNumber1\\>. excludes VersionNumber1 and
     * VersionNumber2
     *
     * @param version               The version number (e.g. 11.9.0.1, 1.8.0_262, 15, 16-ea). Ranges are also supported e.g.
     *                              15.0.1..\\<16
     * @param jdkVersion            The feature version of the used JDK (e.g. 11) which can differ in builds of GraalVM even
     *                              with the same version number
     * @param distro
     * @param distribution          The name of the distribution (aoj, aoj_openj9, corretto, dragonwell, graalvm_ce8,
     *                              graalvm_ce11, graalvm_ce16, graalvm_ce17, graalvm_ce19, graalvm_ce20, graalvm_community,
     *                              liberica, liberica_native, mandrel, microsoft, ojdk_build, openlogic, oracle,
     *                              oracle_open_jdk, redhat, sap_machine, semeru, temurin, trava, zulu)
     * @param architecture          The architecture (aarch32, aarch64, amd64, arm, arm32, arm64, mips, ppc, ppc64el,
     *                              ppc64le, ppc64, riscv64, s390, s390x, sparc, sparcv9, x64, x86-64, x86, i386, i486,
     *                              i586, i686, x86-32)
     * @param fpu
     * @param archiveType           The archive type (apk, cab, deb, dmg, exe, msi, pkg, rpm, tar, tar.gz, zip)
     * @param operatingSystem       The operating system (aix, alpine_linux, linux, linux_musl, macos, qnx, solaris,
     *                              windows)
     * @param libcType
     * @param libCType              The type of libc that is used (glibc, libc, musl, c_std_lib)
     * @param releaseStatus         The release status e.g. early access or general availability (ea, ga)
     * @param javafxBundled         True if the packages should contain JavaFX bundled
     * @param withJavafxIfAvailable If true and javafx_bundled not set it will return the package that comes with JavaFX
     *                              bundled
     * @param latest                The definition of the latest version e.g. the latest version overall or the latest
     *                              version per distribution (overall, per_distro, per_version, available, all_of_version)
     * @param signatureAvailable    True for packages that provide a signature uri
     * @param freeToUseInProduction True for packages that are not only allowed to be used in development and testing but
     *                              also in production
     * @param tckTested             True if the package is tck tested, otherwise it has the value unknown
     * @param aqavitCertified       True if the package is aqavit certified, otherwise it has the value unknown
     * @param match                 Defines if all or any scope should be matched
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/packages/jdks")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getJDKPackagesV3(

            @jakarta.ws.rs.QueryParam("version") String version,

            @jakarta.ws.rs.QueryParam("jdk_version") Integer jdkVersion,

            @jakarta.ws.rs.QueryParam("distro") List<String> distro,

            @jakarta.ws.rs.QueryParam("distribution") List<String> distribution,

            @jakarta.ws.rs.QueryParam("architecture") List<String> architecture,

            @jakarta.ws.rs.QueryParam("fpu") List<String> fpu,

            @jakarta.ws.rs.QueryParam("archive_type") List<String> archiveType,

            @jakarta.ws.rs.QueryParam("operating_system") List<String> operatingSystem,

            @jakarta.ws.rs.QueryParam("libc_type") List<String> libcType,

            @jakarta.ws.rs.QueryParam("lib_c_type") List<String> libCType,

            @jakarta.ws.rs.QueryParam("release_status") List<String> releaseStatus,

            @jakarta.ws.rs.QueryParam("javafx_bundled") Boolean javafxBundled,

            @jakarta.ws.rs.QueryParam("with_javafx_if_available") Boolean withJavafxIfAvailable,

            @jakarta.ws.rs.QueryParam("latest") String latest,

            @jakarta.ws.rs.QueryParam("signature_available") Boolean signatureAvailable,

            @jakarta.ws.rs.QueryParam("free_to_use_in_production") Boolean freeToUseInProduction,

            @jakarta.ws.rs.QueryParam("tck_tested") String tckTested,

            @jakarta.ws.rs.QueryParam("aqavit_certified") String aqavitCertified,

            @jakarta.ws.rs.QueryParam("match") String match);

    /**
     * Returns a list of packages that are of package_type JRE defined by the given parameters.
     *
     * Returns a list of packages that are of package_type JRE defined by the given parameters. The version parameter not
     * only supports different formats for version numbers (e.g. 11.9.0.1, 1.8.0_262, 15, 16-ea) but also ranges (e.g.
     * 15.0.1..\\<16). The ranges are defined as follows: VersionNumber1...VersionNumber2 =\\> includes VersionNumber1 and
     * VersionNumber2 VersionNumber1.. includes VersionNumber1 and excludes VersionNumber2 VersionNumber1\\>..VersionNUmber2
     * =\\> excludes VersionNumber1 and includes VersionNumber2 VersionNumber1\\>. excludes VersionNumber1 and
     * VersionNumber2
     *
     * @param version               The version number (e.g. 11.9.0.1, 1.8.0_262, 15, 16-ea). Ranges are also supported e.g.
     *                              15.0.1..\\<16
     * @param jdkVersion            The feature version of the used JDK (e.g. 11) which can differ in builds of GraalVM even
     *                              with the same version number
     * @param distro
     * @param distribution          The name of the distribution (aoj, aoj_openj9, corretto, dragonwell, graalvm_ce8,
     *                              graalvm_ce11, graalvm_ce16, graalvm_ce17, graalvm_ce19, graalvm_ce20, graalvm_community,
     *                              liberica, liberica_native, mandrel, microsoft, ojdk_build, openlogic, oracle,
     *                              oracle_open_jdk, redhat, sap_machine, semeru, temurin, trava, zulu)
     * @param architecture          The architecture (aarch32, aarch64, amd64, arm, arm32, arm64, mips, ppc, ppc64el,
     *                              ppc64le, ppc64, riscv64, s390, s390x, sparc, sparcv9, x64, x86-64, x86, i386, i486,
     *                              i586, i686, x86-32)
     * @param fpu
     * @param archiveType           The archive type (apk, cab, deb, dmg, exe, msi, pkg, rpm, tar, tar.gz, zip)
     * @param operatingSystem       The operating system (aix, alpine_linux, linux, linux_musl, macos, qnx, solaris,
     *                              windows)
     * @param libcType
     * @param libCType              The type of libc that is used (glibc, libc, musl, c_std_lib)
     * @param releaseStatus         The release status e.g. early access or general availability (ea, ga)
     * @param javafxBundled         True if the packages should contain JavaFX bundled
     * @param withJavafxIfAvailable If true and javafx_bundled not set it will return the package that comes with JavaFX
     *                              bundled
     * @param latest                The definition of the latest version e.g. the latest version overall or the latest
     *                              version per distribution (overall, per_distro, per_version, available, all_of_version)
     * @param signatureAvailable    True for packages that provide a signature uri
     * @param tckTested             True if the package is tck tested, otherwise it has the value unknown
     * @param aqavitCertified       True if the package is aqavit certified, otherwise it has the value unknown
     * @param match                 Defines if all or any scope should be matched
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/packages/jres")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getJREPackagesV3(

            @jakarta.ws.rs.QueryParam("version") String version,

            @jakarta.ws.rs.QueryParam("jdk_version") Integer jdkVersion,

            @jakarta.ws.rs.QueryParam("distro") List<String> distro,

            @jakarta.ws.rs.QueryParam("distribution") List<String> distribution,

            @jakarta.ws.rs.QueryParam("architecture") List<String> architecture,

            @jakarta.ws.rs.QueryParam("fpu") List<String> fpu,

            @jakarta.ws.rs.QueryParam("archive_type") List<String> archiveType,

            @jakarta.ws.rs.QueryParam("operating_system") List<String> operatingSystem,

            @jakarta.ws.rs.QueryParam("libc_type") List<String> libcType,

            @jakarta.ws.rs.QueryParam("lib_c_type") List<String> libCType,

            @jakarta.ws.rs.QueryParam("release_status") List<String> releaseStatus,

            @jakarta.ws.rs.QueryParam("javafx_bundled") Boolean javafxBundled,

            @jakarta.ws.rs.QueryParam("with_javafx_if_available") Boolean withJavafxIfAvailable,

            @jakarta.ws.rs.QueryParam("latest") String latest,

            @jakarta.ws.rs.QueryParam("signature_available") Boolean signatureAvailable,

            @jakarta.ws.rs.QueryParam("tck_tested") String tckTested,

            @jakarta.ws.rs.QueryParam("aqavit_certified") String aqavitCertified,

            @jakarta.ws.rs.QueryParam("match") String match);

    /**
     * Returns the latest ea and ga versions for the given distribution(s)
     *
     * Returns the latest ea and ga versions for the given distribution(s)
     *
     * @param distribution List of distributions you would like to get the latest ea and ga version for
     * @param includeEa    If true the response will contain ea and ga versions, otherwise it will only contain ga versions
     *                     (default is true)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/distributions/versions/latest")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getLatestVersionPerDistributionV3(

            @jakarta.ws.rs.QueryParam("distribution") List<String> distribution,

            @jakarta.ws.rs.QueryParam("include_ea") Boolean includeEa);

    /**
     * Returns information about the requested major version
     *
     * Returns information about the requested major version
     *
     * @param query           The query to get info about a major version (latest_ea, latest_ga, latest_sts, latest_mts,
     *                        latest_lts, useful)
     * @param includeBuild    Include build number if true
     * @param includeVersions Shows all versions related to the given major version (e.g. major version 17 -\\> versions 17,
     *                        17.0.1, 17.0.2) (default is true)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/major_versions/{query}")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getMajorVersionV3(
            @jakarta.ws.rs.PathParam("query") String query,

            @jakarta.ws.rs.QueryParam("include_build") Boolean includeBuild,

            @jakarta.ws.rs.QueryParam("include_versions") Boolean includeVersions);

    /**
     * Return a list of major versions defined by the given parameters
     *
     * Return a list of major versions defined by the given parameters
     *
     * @param releaseStatus         If not set it will return ea and ga releases, otherwise only the selected release status
     *                              (optional, default GA and EA)
     * @param releaseStatusVersions If not set it will return all versions incl. ea and ga versions, otherwise only the ones
     *                              defined (optional, default GA and EA)
     * @param maintained            Defines that only major versions will be shown that are actively be maintained.
     *                              (optional true/false, default false)
     * @param includeBuild          Includes build numbers if true (optional, default true)
     * @param includeVersions       Shows all versions related to the given major version (e.g. major version 17 -\\>
     *                              versions 17, 17.0.1, 17.0.2) (default is true)
     * @param ltsOnly               If true it will only return major versions that are long term stable releases (optional,
     *                              default false)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/major_versions1")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getMajorVersionsNew(

            @jakarta.ws.rs.QueryParam("release_status") String releaseStatus,

            @jakarta.ws.rs.QueryParam("release_status_versions") String releaseStatusVersions,

            @jakarta.ws.rs.QueryParam("maintained") Boolean maintained,

            @jakarta.ws.rs.QueryParam("include_build") Boolean includeBuild,

            @jakarta.ws.rs.QueryParam("include_versions") Boolean includeVersions,

            @jakarta.ws.rs.QueryParam("lts_only") Boolean ltsOnly);

    /**
     * Redirects to either the direct download link or the download site of the requested package defined by it is id
     *
     * Redirects to either the direct download link or the download site of the requested package defined by it is id
     *
     * @param pkgId The pkg_id with which one can get the download info.
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/ids/{pkg_id}/redirect")
    @jakarta.ws.rs.Produces({ "application/octet-stream" })
    public io.smallrye.mutiny.Uni<Object> getPackageRedirectV3(
            @jakarta.ws.rs.PathParam("pkg_id") String pkgId

    );

    /**
     * Returns information about a package defined by the given package id
     *
     * Returns information about a package defined by the given package id
     *
     * @param id The package id to get either the direct download uri for the file or the download site uri where you can
     *           download the file from
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/packages/{id}")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getPackageV3(
            @jakarta.ws.rs.PathParam("id") String id

    );

    /**
     * Returns a list of packages defined by the given parameters The version parameter not only supports different formats
     * for version numbers (e.
     *
     * Returns a list of packages defined by the given parameters The version parameter not only supports different formats
     * for version numbers (e.g. 11.9.0.1, 1.8.0_262, 15, 16-ea) but also ranges (e.g. 15.0.1..\\<16). The ranges are
     * defined as follows: VersionNumber1...VersionNumber2 =\\> includes VersionNumber1 and VersionNumber2 VersionNumber1..
     * includes VersionNumber1 and excludes VersionNumber2 VersionNumber1\\>..VersionNUmber2 =\\> excludes VersionNumber1
     * and includes VersionNumber2 VersionNumber1\\>. excludes VersionNumber1 and VersionNumber2
     *
     * @param version               The version number (e.g. 11.9.0.1, 1.8.0_262, 15, 16-ea). Ranges are also supported e.g.
     *                              15.0.1..\\<16
     * @param versionByDefinition   The version will be calculated from the given parameter (latest, latest_sts, latest_mts,
     *                              latest_lts)
     * @param jdkVersion            The feature version of the used jdk (e.g. 11) which is used to select builds of GraalVM
     *                              based on different jdks
     * @param distro
     * @param distribution          The name of the distribution (aoj, aoj_openj9, bisheng, corretto, dragonwell,
     *                              graalvm_ce8, graalvm_ce11, graalvm_ce16, graalvm_ce17, graalvm_ce19, graalvm_ce20,
     *                              graalvm_community, graalvm, jetbrains, kona, liberica, liberica_native, mandrel,
     *                              microsoft, ojdk_build, openlogic, oracle, oracle_open_jdk, redhat, sap_machine, semeru,
     *                              semeru_certified, temurin, trava, zulu)
     * @param architecture          The architecture (aarch32, aarch64, amd64, arm, arm32, arm64, mips, ppc, ppc64el,
     *                              ppc64le, ppc64, riscv64, s390, s390x, sparc, sparcv9, x64, x86-64, x86, i386, i486,
     *                              i586, i686, x86-32)
     * @param archiveType           The archive type (apk, cab, deb, dmg, exe, msi, pkg, rpm, tar, tar.gz, tgz, zip)
     * @param packageType           The pkg type (jdk, jre)
     * @param operatingSystem       The operating system (aix, alpine_linux, linux, linux_musl, macos, qnx, solaris,
     *                              windows)
     * @param libcType
     * @param libCType              The type of libc that is used (glibc, libc, musl, c_std_lib)
     * @param releaseStatus         The release status e.g. early access or general availability (ea, ga)
     * @param termOfSupport         The support term e.g. short term stable, midterm stable, long term stable (sts, mts,
     *                              lts)
     * @param bitness               The bitness (32, 64)
     * @param fpu                   Indicator for arm hard_float and soft_float packages. For all other packages this
     *                              parameter has the value unknown
     * @param javafxBundled         True if the packages should contain JavaFX bundled
     * @param withJavafxIfAvailable If true and javafx_bundled not set it will return the package that comes with JavaFX
     *                              bundled
     * @param directlyDownloadable  True if the package is directly downloadable via the api (packages that need the user to
     *                              click through pages or are commercial will be marked with false)
     * @param latest                The definition of the latest version e.g. the latest version overall or the latest
     *                              version per distribution (overall, per_distro, per_version, available, all_of_version)
     * @param feature               Features that are supported by some early access versions of Oracle OpenJDK (loom,
     *                              panama, lanai, valhalla)
     * @param signatureAvailable    True for packages that provide a signature uri
     * @param freeToUseInProduction True if the package is allowed to be used not only for development and testing but also
     *                              for production
     * @param tckTested             True if the package is tck tested, otherwise it has the value unknown
     * @param aqavitCertified       True if the package is aqavit certified, otherwise it has the value unknown
     * @param discoveryScopeId      The scope of the package query (e.g. public, build_of_openjdk, build_of_graalvm,
     *                              directly_downloadable, not_directly_downloadable, free_to_use_in_production,
     *                              license_needed_for_production, signature_available, signature_not_available)
     * @param match                 Defines how the discovery_socpe_ids should be matched (any will return all packages that
     *                              contain at least one of the discovery_scope_ids (default), all will only return packages
     *                              that contain all given discovery_scope_ids)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/packages")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getPackagesV3(

            @jakarta.ws.rs.QueryParam("version") String version,

            @jakarta.ws.rs.QueryParam("version_by_definition") String versionByDefinition,

            @jakarta.ws.rs.QueryParam("jdk_version") Integer jdkVersion,

            @jakarta.ws.rs.QueryParam("distro") List<String> distro,

            @jakarta.ws.rs.QueryParam("distribution") List<String> distribution,

            @jakarta.ws.rs.QueryParam("architecture") List<String> architecture,

            @jakarta.ws.rs.QueryParam("archive_type") List<String> archiveType,

            @jakarta.ws.rs.QueryParam("package_type") String packageType,

            @jakarta.ws.rs.QueryParam("operating_system") List<String> operatingSystem,

            @jakarta.ws.rs.QueryParam("libc_type") List<String> libcType,

            @jakarta.ws.rs.QueryParam("lib_c_type") List<String> libCType,

            @jakarta.ws.rs.QueryParam("release_status") List<String> releaseStatus,

            @jakarta.ws.rs.QueryParam("term_of_support") List<String> termOfSupport,

            @jakarta.ws.rs.QueryParam("bitness") Integer bitness,

            @jakarta.ws.rs.QueryParam("fpu") List<String> fpu,

            @jakarta.ws.rs.QueryParam("javafx_bundled") Boolean javafxBundled,

            @jakarta.ws.rs.QueryParam("with_javafx_if_available") Boolean withJavafxIfAvailable,

            @jakarta.ws.rs.QueryParam("directly_downloadable") Boolean directlyDownloadable,

            @jakarta.ws.rs.QueryParam("latest") String latest,

            @jakarta.ws.rs.QueryParam("feature") List<String> feature,

            @jakarta.ws.rs.QueryParam("signature_available") Boolean signatureAvailable,

            @jakarta.ws.rs.QueryParam("free_to_use_in_production") Boolean freeToUseInProduction,

            @jakarta.ws.rs.QueryParam("tck_tested") String tckTested,

            @jakarta.ws.rs.QueryParam("aqavit_certified") String aqavitCertified,

            @jakarta.ws.rs.QueryParam("discovery_scope_id") List<String> discoveryScopeId,

            @jakarta.ws.rs.QueryParam("match") String match);

    /**
     * Returns a list of parameters and their possible values which are used in the Disco API
     *
     * Returns a list of parameters and their possible values which are used in the Disco API
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/parameters")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getParametersV3();

    /**
     * Returns the remaining days to next feature release (e.
     *
     * Returns the remaining days to next feature release (e.g. 21 GA) based on the current release cadence
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/remaining_days/release")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getRemainingDaysToNextRelease();

    /**
     * Returns the remaining days to next update (e.
     *
     * Returns the remaining days to next update (e.g. 17.0.9) based on the current release cadence
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/remaining_days/update")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getRemainingDaysToNextUpdate();

    /**
     * Returns the specified major version including early access builds
     *
     * Returns the specified major version including early access builds
     *
     * @param majorVersion    A major version between 6 and the latest currently available (December 2020 -\\> 17)
     * @param includeBuild    Include build numbers if true
     * @param includeVersions Shows all versions related to the given major version (e.g. major version 17 -\\> versions 17,
     *                        17.0.1, 17.0.2) (default is true)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/major_versions/{major_version}/ea")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSpecificMajorVersionIncludingEaV3(
            @jakarta.ws.rs.PathParam("major_version") Integer majorVersion,

            @jakarta.ws.rs.QueryParam("include_build") Boolean includeBuild,

            @jakarta.ws.rs.QueryParam("include_versions") Boolean includeVersions);

    /**
     * Returns the specified major version excluding early access builds
     *
     * Returns the specified major version excluding early access builds
     *
     * @param majorVersion    A major version between 6 and the latest currently available (December 2020 -\\> 17)
     * @param includeBuild    Include build numbers if true
     * @param includeVersions Shows all versions related to the given major version (e.g. major version 17 -\\> versions 17,
     *                        17.0.1, 17.0.2) (default is true)
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/major_versions/{major_version}/ga")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSpecificMajorVersionV3(
            @jakarta.ws.rs.PathParam("major_version") Integer majorVersion,

            @jakarta.ws.rs.QueryParam("include_build") Boolean includeBuild,

            @jakarta.ws.rs.QueryParam("include_versions") Boolean includeVersions);

    /**
     * Returns the supported architectures with their bitness etc.
     *
     * Returns the supported architectures with their bitness etc.
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_architectures")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedArchitectures();

    /**
     * Returns the supported archive types
     *
     * Returns the supported archive types
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_archive_types")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedArchiveTypes();

    /**
     * Returns the supported features
     *
     * Returns the supported features
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_features")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedFeatures();

    /**
     * Returns the supported floating point types
     *
     * Returns the supported floating point types
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_fpus")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedFpus();

    /**
     * Returns the supported latest parameters
     *
     * Returns the supported latest parameters
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_latest_parameters")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedLatestParameters();

    /**
     * Returns the supported libc types
     *
     * Returns the supported libc types
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_lib_c_types")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedLibCTypes();

    /**
     * Returns the supported operating systems with their libc type
     *
     * Returns the supported operating systems with their libc type
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_operating_systems")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedOperatingSystems();

    /**
     * Returns the supported package types
     *
     * Returns the supported package types
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_package_types")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedPackageTypes();

    /**
     * Returns the supported release status
     *
     * Returns the supported release status
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_release_status")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedReleaseStatus();

    /**
     * Returns the supported terms of support
     *
     * Returns the supported terms of support
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/supported_terms_of_support")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getSupportedTermsOfSupport();

    /**
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/upcoming_releases")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getUpcomingReleases();

    /**
     * Returns a list of all vendors with their distributions
     *
     * Returns a list of all vendors with their distributions
     *
     * @param includeDistributions
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Path("/vendors")
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> getVendorsV3(

            @jakarta.ws.rs.QueryParam("include_distributions") Boolean includeDistributions);

    /**
     * Returns a list of endpoints of Disco API v3.
     *
     * Returns a list of endpoints of Disco API v3.0
     *
     */
    @jakarta.ws.rs.GET
    @jakarta.ws.rs.Produces({ "application/json" })
    public io.smallrye.mutiny.Uni<Object> v3();

}
