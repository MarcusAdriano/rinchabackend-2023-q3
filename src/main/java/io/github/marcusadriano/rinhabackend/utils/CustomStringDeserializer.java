package io.github.marcusadriano.rinhabackend.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CustomStringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(final JsonParser p, final DeserializationContext ctx) throws IOException {
        if (p.currentToken().isNumeric() || p.currentToken().isBoolean()) {
            throw ctx.wrongTokenException(p, String.class, JsonToken.VALUE_STRING, "Expected string value");
        }

        return p.getValueAsString().trim();
    }
}
