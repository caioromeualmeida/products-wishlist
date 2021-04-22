package com.example.products_wishlist.shared.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.PageImpl;

@JsonComponent
public class PageImplSerializer extends JsonSerializer<PageImpl<?>> {
    
    @Override
    public void serialize(PageImpl<?> page, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeObjectFieldStart("meta");
        jsonGenerator.writeNumberField("page_number", page.getNumber());
        jsonGenerator.writeNumberField("page_size", page.getSize());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeObjectField("content", page.getContent());

        jsonGenerator.writeEndObject();
    }
}
