package otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import otus.model.Measurement;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileSerializer implements Serializer {

    public FileSerializer(String fileName) {
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.json"))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(Measurement.class, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
