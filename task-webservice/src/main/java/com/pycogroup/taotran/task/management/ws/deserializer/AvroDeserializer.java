package com.pycogroup.taotran.task.management.ws.deserializer;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Map;

public class AvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvroDeserializer.class);

    protected final Class<T> targetType;

    public AvroDeserializer(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //No-implementation
    }

    @Override
    @SuppressWarnings({"all"})
    public T deserialize(String topic, byte[] data) {
        try {
            T result = null;
            if (data != null) {
                LOGGER.debug("data='{}'", DatatypeConverter.printHexBinary(data));

                DatumReader<GenericRecord> datumReader = new SpecificDatumReader<>(targetType.newInstance().getSchema());

                Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);

                result = (T) datumReader.read(null, decoder);
                LOGGER.debug("deserialized data='{}'", result);
            }
            return result;
        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize data '" + Arrays.toString(data) + "' from topic '" + topic + "'", e);
        }
    }

    @Override
    public void close() {
        //No-implementation
    }
}
