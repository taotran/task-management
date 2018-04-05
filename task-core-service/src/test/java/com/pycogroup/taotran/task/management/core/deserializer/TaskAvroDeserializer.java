package com.pycogroup.taotran.task.management.core.deserializer;

import com.pycogroup.taotran.task.management.core.avroentity.Task;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Map;

public class TaskAvroDeserializer implements Deserializer<Task> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskAvroDeserializer.class);

    public TaskAvroDeserializer() {

    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Task deserialize(String topic, byte[] data) {
        try {
            Task result = null;
            if (data != null) {
                LOGGER.debug("data='{}'", DatatypeConverter.printHexBinary(data));

                DatumReader<GenericRecord> datumReader = new SpecificDatumReader<>(Task.getClassSchema());

                Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);

                result = (Task) datumReader.read(null, decoder);
                LOGGER.debug("deserialized data='{}'", result);
            }
            return result;
        } catch (Exception e) {
            throw new SerializationException("Cannot deserialize data '" + Arrays.toString(data) + "' from topic '" + topic + "'", e);
        }
    }

    @Override
    public void close() {

    }
}
