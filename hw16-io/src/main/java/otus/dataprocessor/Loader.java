package otus.dataprocessor;

import otus.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
