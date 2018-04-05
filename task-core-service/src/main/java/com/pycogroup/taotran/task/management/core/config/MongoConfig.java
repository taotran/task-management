package com.pycogroup.taotran.task.management.core.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.util.Arrays;
import java.util.Collection;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    private static final String DB_NAME = "mongodb.db.name";
    private static final String DB_PORT = "mongodb.db.port";
    private static final String DB_HOST = "mongodb.client.host";
    private static final String SCAN_PACKAGE = "com.pycogroup.taotran";

    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return env.getProperty(DB_NAME);
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(env.getProperty(DB_HOST), env.getProperty(DB_PORT, Integer.class));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Collection<String> getMappingBasePackages() {
        return Arrays.asList(SCAN_PACKAGE);
    }
}
