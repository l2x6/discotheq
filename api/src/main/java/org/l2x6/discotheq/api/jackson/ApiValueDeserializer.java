/*
 * SPDX-FileCopyrightText: Copyright (c) 2026 Discotheq contributors as indicated by the @author tags
 *                                 SPDX-License-Identifier: Apache-2.0
 */
package org.l2x6.discotheq.api.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import java.io.IOException;
import java.util.Locale;
import org.l2x6.discotheq.api.model.Architecture;
import org.l2x6.discotheq.api.model.ArchiveType;
import org.l2x6.discotheq.api.model.Bitness;
import org.l2x6.discotheq.api.model.DiscoveryScope;
import org.l2x6.discotheq.api.model.Fpu;
import org.l2x6.discotheq.api.model.Latest;
import org.l2x6.discotheq.api.model.LibCType;
import org.l2x6.discotheq.api.model.OperatingSystem;
import org.l2x6.discotheq.api.model.PackageType;
import org.l2x6.discotheq.api.model.ReleaseStatus;
import org.l2x6.discotheq.api.model.TermOfSupport;

/** Deserializes API values represented either as supported-value objects or as API strings. */
public final class ApiValueDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {

    private final Class<?> valueType;

    public ApiValueDeserializer() {
        this(null);
    }

    private ApiValueDeserializer(Class<?> valueType) {
        this.valueType = valueType;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty property) {
        final JavaType contextualType = context.getContextualType();
        if (contextualType != null && isApiValueType(contextualType.getRawClass())) {
            return new ApiValueDeserializer(contextualType.getRawClass());
        }
        final JavaType propertyType = property == null ? contextualType : property.getType();
        final JavaType valueType = propertyType != null && propertyType.hasContentType()
                ? propertyType.getContentType()
                : propertyType;
        return new ApiValueDeserializer(valueType.getRawClass());
    }

    @Override
    public Object deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.currentToken() == JsonToken.VALUE_STRING) {
            return fromApiString(parser.getText());
        }
        if (parser.currentToken() == JsonToken.START_OBJECT) {
            return fromObject(parser.readValueAsTree());
        }
        return context.handleUnexpectedToken(valueType, parser);
    }

    private Object fromApiString(String value) {
        if (valueType == Architecture.class) {
            final Architecture.KnownArchitecture known = Architecture.KnownArchitecture.fromApiString(value);
            if (known != null) {
                return known;
            }
            return new Architecture.ArchitectureRecord(value.toUpperCase(Locale.ROOT), value, value, null);
        }
        if (valueType == ArchiveType.class) {
            final ArchiveType.KnownArchiveType known = ArchiveType.KnownArchiveType.fromApiString(value);
            if (known != null) {
                return known;
            }
            return new ArchiveType.ArchiveTypeRecord(value.toUpperCase(Locale.ROOT), value, value);
        }
        if (valueType == DiscoveryScope.class) {
            final DiscoveryScope.KnownDiscoveryScope known = DiscoveryScope.KnownDiscoveryScope.fromApiString(value);
            if (known != null) {
                return known;
            }
            return new DiscoveryScope.DiscoveryScopeRecord(value);
        }
        if (valueType == Fpu.class) {
            final Fpu.KnownFpu known = Fpu.KnownFpu.fromApiString(value);
            if (known != null) {
                return known;
            }
            return new Fpu.FpuRecord(value.toUpperCase(Locale.ROOT), value, value);
        }
        if (valueType == Latest.class) {
            final Latest.KnownLatest known = Latest.KnownLatest.fromApiString(value);
            if (known != null) {
                return known;
            }
            return new Latest.LatestRecord(value.toUpperCase(Locale.ROOT), value, value);
        }
        if (valueType == LibCType.class) {
            final LibCType.KnownLibCType known = LibCType.KnownLibCType.fromApiString(value);
            if (known != null) {
                return known;
            }
            return new LibCType.LibCTypeRecord(value.toUpperCase(Locale.ROOT), value, value);
        }
        if (valueType == OperatingSystem.class) {
            return new OperatingSystem.OperatingSystemRecord(value.toUpperCase(Locale.ROOT), value, value, null);
        }
        if (valueType == PackageType.class) {
            final PackageType.KnownPackageType known = PackageType.KnownPackageType.fromApiString(value);
            if (known != null) {
                return known;
            }
            return new PackageType.PackageTypeRecord(value.toUpperCase(Locale.ROOT), value, value);
        }
        if (valueType == ReleaseStatus.class) {
            final ReleaseStatus.KnownReleaseStatus known = ReleaseStatus.KnownReleaseStatus.fromApiString(value);
            if (known != null) {
                return known;
            }
            return new ReleaseStatus.ReleaseStatusRecord(value.toUpperCase(Locale.ROOT), value, value);
        }
        if (valueType == TermOfSupport.class) {
            final TermOfSupport.KnownTermOfSupport known = TermOfSupport.KnownTermOfSupport.fromApiString(value);
            if (known != null) {
                return known;
            }
            return new TermOfSupport.TermOfSupportRecord(value.toUpperCase(Locale.ROOT), value, value);
        }
        throw new IllegalArgumentException("Unsupported API value type " + valueType);
    }

    private Object fromObject(JsonNode node) {
        if (valueType == Architecture.class) {
            return new Architecture.ArchitectureRecord(
                    text(node, "name"), text(node, "ui_string"), text(node, "api_string"),
                    Bitness.fromApiString(text(node, "bitness")));
        }
        if (valueType == ArchiveType.class) {
            return new ArchiveType.ArchiveTypeRecord(
                    text(node, "name"), text(node, "ui_string"), text(node, "api_string"));
        }
        if (valueType == DiscoveryScope.class) {
            return new DiscoveryScope.DiscoveryScopeRecord(text(node, "discovery_scope_id"));
        }
        if (valueType == Fpu.class) {
            return new Fpu.FpuRecord(text(node, "name"), text(node, "ui_string"), text(node, "api_string"));
        }
        if (valueType == Latest.class) {
            return new Latest.LatestRecord(text(node, "name"), text(node, "ui_string"), text(node, "api_string"));
        }
        if (valueType == LibCType.class) {
            return new LibCType.LibCTypeRecord(text(node, "name"), text(node, "ui_string"), text(node, "api_string"));
        }
        if (valueType == OperatingSystem.class) {
            return new OperatingSystem.OperatingSystemRecord(
                    text(node, "name"), text(node, "ui_string"), text(node, "api_string"),
                    (LibCType) fromApiString(LibCType.class, text(node, "lib_c_type")));
        }
        if (valueType == PackageType.class) {
            return new PackageType.PackageTypeRecord(text(node, "name"), text(node, "ui_string"), text(node, "api_string"));
        }
        if (valueType == ReleaseStatus.class) {
            return new ReleaseStatus.ReleaseStatusRecord(
                    text(node, "name"), text(node, "ui_string"), text(node, "api_string"));
        }
        if (valueType == TermOfSupport.class) {
            return new TermOfSupport.TermOfSupportRecord(
                    text(node, "name"), text(node, "ui_string"), text(node, "api_string"));
        }
        throw new IllegalArgumentException("Unsupported API value type " + valueType);
    }

    private static boolean isApiValueType(Class<?> type) {
        return type == Architecture.class || type == ArchiveType.class || type == DiscoveryScope.class
                || type == Fpu.class || type == Latest.class || type == LibCType.class || type == OperatingSystem.class
                || type == PackageType.class || type == ReleaseStatus.class || type == TermOfSupport.class;
    }

    private static Object fromApiString(Class<?> valueType, String value) {
        return new ApiValueDeserializer(valueType).fromApiString(value);
    }

    private static String text(JsonNode object, String field) {
        final JsonNode value = object.get(field);
        return value == null || value.isNull() ? null : value.asText();
    }
}
