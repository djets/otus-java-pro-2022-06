package otus.dataprocessor;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import jakarta.json.Json;
import jakarta.json.JsonStructure;
import otus.model.Measurement;

import java.lang.reflect.Type;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        List<Measurement> data;
//        JsonReader jsonReader;
        try (var jsonReader = Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            JsonStructure jsonFromTheFile = jsonReader.read();
            System.out.println("\n json from the file:");
            System.out.println(jsonFromTheFile);
            System.out.println("property:" + jsonFromTheFile.getValue("/firstName"));
            Type REVIEW_TYPE = new TypeToken<List<Measurement>>() {
            }.getType();
            Gson gson = new Gson();

            data = gson.fromJson((JsonReader) jsonReader, REVIEW_TYPE);

        }
        return data;
    }
}
