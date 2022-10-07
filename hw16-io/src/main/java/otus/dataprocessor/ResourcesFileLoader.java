package otus.dataprocessor;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import otus.model.Measurement;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
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
        try (var inputStream = new BufferedInputStream(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
        var jsonR = new JsonReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        Type REVIEW_TYPE = new TypeToken<List<Measurement>>() {
        }.getType();
        Gson gson = new Gson();
        data = gson.fromJson(jsonR, REVIEW_TYPE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
