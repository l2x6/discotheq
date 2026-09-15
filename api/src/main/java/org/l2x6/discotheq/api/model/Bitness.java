/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Bitness {
    _32("32"),
    _64("64");

    private final String apiString;

    Bitness(String apiString) {
        this.apiString = apiString;
    }

    @JsonCreator
    public static Bitness fromApiString(String apiString) {
        if (apiString == null || apiString.isEmpty()) {
            return null;
        }
        return switch (apiString) {
        case "32" -> _32;
        case "64" -> _64;
        default -> throw new IllegalArgumentException("Unsupported bitness " + apiString);
        };
    }

    @JsonValue
    public String apiString() {
        return apiString;
    }

    @Override
    public String toString() {
        return apiString;
    }
}
