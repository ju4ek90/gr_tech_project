package api.tests.utils;

import api.tests.api.GRApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonOperations {

    private static final Logger logger = LoggerFactory.getLogger(GRApiClient.class);

    public static String getPeopleApiSchema(String schemaPath) {
        String peopleApiSchema = null;
        try {
            peopleApiSchema = new String(Files.readAllBytes(Paths.get(schemaPath)));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Can not get schema by path: {}", schemaPath);
        }
        return peopleApiSchema;
    }
}
