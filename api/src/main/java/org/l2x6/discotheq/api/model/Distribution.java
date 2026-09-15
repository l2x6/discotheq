/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import org.l2x6.discotheq.api.model.Vendor.KnownVendor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Distribution(
        String name,
        String apiParameter,
        String vendor,
        Boolean maintained,
        Boolean available,
        Boolean buildOfOpenjdk,
        Boolean buildOfGraalvm,
        String officialUri,
        List<String> synonyms,
        List<String> versions) {

    public enum KnownDistribution {
        zulu(
                KnownVendor.azul),
        trava(
                KnownVendor.community),
        temurin(
                KnownVendor.eclipse_foundation),
        semeru_certified(
                KnownVendor.ibm),
        semeru(
                KnownVendor.ibm),
        sap_machine(
                KnownVendor.sap),
        redhat(
                KnownVendor.red_hat),
        oracle_open_jdk(
                KnownVendor.oracle),
        oracle(
                KnownVendor.oracle),
        openlogic(
                KnownVendor.open_logic),
        ojdk_build(
                KnownVendor.community),
        microsoft(
                KnownVendor.microsoft),
        mandrel(
                KnownVendor.red_hat),
        liberica_native(
                KnownVendor.bell_soft),
        liberica(
                KnownVendor.bell_soft),
        kona(
                KnownVendor.tencent),
        jetbrains(
                KnownVendor.jetbrains),
        graalvm_community(
                KnownVendor.oracle),
        graalvm_ce8(
                KnownVendor.oracle),
        graalvm_ce19(
                KnownVendor.oracle),
        graalvm_ce17(
                KnownVendor.oracle),
        graalvm_ce16(
                KnownVendor.oracle),
        graalvm_ce11(
                KnownVendor.oracle),
        graalvm(
                KnownVendor.oracle),
        gluon_graalvm(
                KnownVendor.gluon),
        eliya(
                KnownVendor.asymm),
        dragonwell(
                KnownVendor.alibaba),
        corretto(
                KnownVendor.amazon),
        bisheng(
                KnownVendor.huawei),
        aoj_openj9(
                KnownVendor.adopt_open_jdk),
        aoj(
                KnownVendor.adopt_open_jdk);

        private final KnownVendor vendor;

        KnownDistribution(
                KnownVendor vendor) {
            this.vendor = vendor;
        }

        public KnownVendor vendor() {
            return vendor;
        }
    }
}
