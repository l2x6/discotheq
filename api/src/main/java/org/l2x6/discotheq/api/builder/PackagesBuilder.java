/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.l2x6.discotheq.api.DiscoApi;
import org.l2x6.discotheq.api.model.Architecture;
import org.l2x6.discotheq.api.model.ArchiveType;
import org.l2x6.discotheq.api.model.Fpu;
import org.l2x6.discotheq.api.model.Latest;
import org.l2x6.discotheq.api.model.LibCType;
import org.l2x6.discotheq.api.model.Match;
import org.l2x6.discotheq.api.model.OperatingSystem;
import org.l2x6.discotheq.api.model.ReleaseStatus;

abstract class PackagesBuilder<B extends PackagesBuilder<B>> {
    protected final DiscoApi api;
    protected String version;
    protected Integer jdkVersion;
    protected List<String> distro;
    protected List<String> distribution;
    protected List<Architecture> architecture;
    protected List<Fpu> fpu;
    protected List<ArchiveType> archiveType;
    protected List<OperatingSystem> operatingSystem;
    protected List<LibCType> libcType;
    protected List<LibCType> libCType;
    protected List<ReleaseStatus> releaseStatus;
    protected Boolean javafxBundled;
    protected Boolean withJavafxIfAvailable;
    protected Latest latest;
    protected Boolean signatureAvailable;
    protected String tckTested;
    protected String aqavitCertified;
    protected Match match;

    protected PackagesBuilder(DiscoApi api) {
        this.api = api;
    }

    protected abstract B self();

    public B version(String version) {
        this.version = version;
        return self();
    }

    public B jdkVersion(Integer jdkVersion) {
        this.jdkVersion = jdkVersion;
        return self();
    }

    public B distro(String... distro) {
        this.distro = add(this.distro, distro);
        return self();
    }

    public B distro(Collection<String> distro) {
        this.distro = add(this.distro, distro);
        return self();
    }

    public B distribution(String... distribution) {
        this.distribution = add(this.distribution, distribution);
        return self();
    }

    public B distribution(Collection<String> distribution) {
        this.distribution = add(this.distribution, distribution);
        return self();
    }

    public B architecture(Architecture... architecture) {
        this.architecture = add(this.architecture, architecture);
        return self();
    }

    public B architecture(Collection<Architecture> architecture) {
        this.architecture = add(this.architecture, architecture);
        return self();
    }

    public B fpu(Fpu... fpu) {
        this.fpu = add(this.fpu, fpu);
        return self();
    }

    public B fpu(Collection<Fpu> fpu) {
        this.fpu = add(this.fpu, fpu);
        return self();
    }

    public B archiveType(ArchiveType... archiveType) {
        this.archiveType = add(this.archiveType, archiveType);
        return self();
    }

    public B archiveType(Collection<ArchiveType> archiveType) {
        this.archiveType = add(this.archiveType, archiveType);
        return self();
    }

    public B operatingSystem(OperatingSystem... operatingSystem) {
        this.operatingSystem = add(this.operatingSystem, operatingSystem);
        return self();
    }

    public B operatingSystem(Collection<OperatingSystem> operatingSystem) {
        this.operatingSystem = add(this.operatingSystem, operatingSystem);
        return self();
    }

    public B libcType(LibCType... libcType) {
        this.libcType = add(this.libcType, libcType);
        return self();
    }

    public B libcType(Collection<LibCType> libcType) {
        this.libcType = add(this.libcType, libcType);
        return self();
    }

    public B libCType(LibCType... libCType) {
        this.libCType = add(this.libCType, libCType);
        return self();
    }

    public B libCType(Collection<LibCType> libCType) {
        this.libCType = add(this.libCType, libCType);
        return self();
    }

    public B releaseStatus(ReleaseStatus... releaseStatus) {
        this.releaseStatus = add(this.releaseStatus, releaseStatus);
        return self();
    }

    public B releaseStatus(Collection<ReleaseStatus> releaseStatus) {
        this.releaseStatus = add(this.releaseStatus, releaseStatus);
        return self();
    }

    public B javafxBundled(Boolean javafxBundled) {
        this.javafxBundled = javafxBundled;
        return self();
    }

    public B javafxBundled() {
        this.javafxBundled = true;
        return self();
    }

    public B notJavafxBundled() {
        this.javafxBundled = false;
        return self();
    }

    public B withJavafxIfAvailable(Boolean withJavafxIfAvailable) {
        this.withJavafxIfAvailable = withJavafxIfAvailable;
        return self();
    }

    public B withJavafxIfAvailable() {
        this.withJavafxIfAvailable = true;
        return self();
    }

    public B notWithJavafxIfAvailable() {
        this.withJavafxIfAvailable = false;
        return self();
    }

    public B latest(Latest latest) {
        this.latest = latest;
        return self();
    }

    public B signatureAvailable(Boolean signatureAvailable) {
        this.signatureAvailable = signatureAvailable;
        return self();
    }

    public B signatureAvailable() {
        this.signatureAvailable = true;
        return self();
    }

    public B notSignatureAvailable() {
        this.signatureAvailable = false;
        return self();
    }

    public B tckTested(String tckTested) {
        this.tckTested = tckTested;
        return self();
    }

    public B aqavitCertified(String aqavitCertified) {
        this.aqavitCertified = aqavitCertified;
        return self();
    }

    public B matchAll() {
        this.match = Match.ALL;
        return self();
    }

    public B matchAny() {
        this.match = Match.ANY;
        return self();
    }

    private static <T> List<T> add(List<T> target, T[] values) {
        if (values != null && values.length > 0) {
            if (target == null) {
                target = new ArrayList<>(values.length);
            }
            Collections.addAll(target, values);
        }
        return target;
    }

    private static <T> List<T> add(List<T> target, Collection<T> values) {
        if (values != null && !values.isEmpty()) {
            if (target == null) {
                target = new ArrayList<>(values.size());
            }
            target.addAll(values);
        }
        return target;
    }
}
