package openfoodfacts.github.scrachx.openfood.network.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import openfoodfacts.github.scrachx.openfood.models.LabelResponse;
import openfoodfacts.github.scrachx.openfood.models.LabelsWrapper;

/**
 * Created by Lobster on 03.03.18.
 */

public class LabelsWrapperDeserializer implements JsonDeserializer<LabelsWrapper> {

    private static final String NAMES_KEY = "name";

    @Override
    public LabelsWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<LabelResponse> labels = new ArrayList<>();
        JsonObject labelsWrapperJson = json.getAsJsonObject();

        for (Map.Entry<String, JsonElement> label : labelsWrapperJson.entrySet()) {
            JsonElement namesJsonElement = label.getValue().getAsJsonObject().get(NAMES_KEY);
            if (namesJsonElement != null) {
                JsonObject namesJson = namesJsonElement.getAsJsonObject();
                Map<String, String> names = new HashMap<String, String>();  /* Entry<Language Code, Product Name> */
                for (Map.Entry<String, JsonElement> name : namesJson.entrySet()) {
                    String strName = name.getValue().toString();
                    names.put(name.getKey(), strName.substring(1, strName.length() - 1)); /* Substring removes needless quotes */
                }

                labels.add(new LabelResponse(label.getKey(), names));
            }
        }

        LabelsWrapper labelsWrapper = new LabelsWrapper();
        labelsWrapper.setLabels(labels);

        return labelsWrapper;
    }
}
