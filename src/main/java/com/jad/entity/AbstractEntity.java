package com.jad.entity;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

abstract class AbstractEntity {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalTimeTypeAdapter())
            .create();

    public String toJson() {
        return AbstractEntity.gson.toJson(this);
    }

    public String toPrettyJson() {
        return AbstractEntity.gson.toJson(this);
    }

    private static class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public JsonElement serialize(final LocalDate date, final Type typeOfSrc,
                                     final JsonSerializationContext context) {
            return new JsonPrimitive(date.format(this.formatter));
        }

        @Override
        public LocalDate deserialize(final JsonElement json,
                                     final Type typeOfT,
                                     final JsonDeserializationContext context) throws JsonParseException {
            return LocalDate.parse(json.getAsString(), this.formatter);
        }
    }

    private static class LocalTimeTypeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        @Override
        public JsonElement serialize(final LocalTime time,
                                     final Type typeOfSrc,
                                     final JsonSerializationContext context) {
            return new JsonPrimitive(time.format(this.formatter));
        }

        @Override
        public LocalTime deserialize(final JsonElement json, final Type typeOfT,
                                     final JsonDeserializationContext context) throws JsonParseException {
            return LocalTime.parse(json.getAsString(), this.formatter);
        }


    }
}
