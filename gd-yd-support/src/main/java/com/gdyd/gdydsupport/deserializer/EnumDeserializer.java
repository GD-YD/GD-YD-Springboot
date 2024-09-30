package com.gdyd.gdydsupport.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;

public class EnumDeserializer extends StdDeserializer<Enum<?>> implements ContextualDeserializer {

    public EnumDeserializer() {
        this(null);
    }

    protected EnumDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Enum<?> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        final JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        final String text = jsonNode.asText();
        @SuppressWarnings("unchecked")
        final Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) this._valueClass;
        if (enumType == null) {
            return null;
        }
        return Arrays.stream(enumType.getEnumConstants())
                .filter(value -> value.name().equalsIgnoreCase(text))
                .findAny()
                .orElse(null);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        return new EnumDeserializer(property.getType().getRawClass());
    }
}
