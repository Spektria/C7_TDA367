package C7.Controller.Properties;

import C7.Util.ResourceIO;
import C7.Model.Tools.ToolProperties.IToolProperty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Used for fetching the name and description of a IToolProperty.
 * @author Hugo Ekstrand
 */
class PropertyDescription {

    private static final String fileName = "properties.dat";
    private static final Map<String, String> propertyNameToDescription = new HashMap<>();
    private static PropertyDescription instance;

    private PropertyDescription(){
        String[] lines = null;
        try{
            BufferedReader reader =
                    new BufferedReader(
                    new FileReader(
                            ResourceIO.getGlobalResource(fileName).getFile()));
            lines = reader.lines().toArray(String[]::new);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(String line : lines){
            if(line.contains("//") || line.isEmpty())
                continue;
            String[] splitedLine = line.split(":");
            propertyNameToDescription.put(splitedLine[0].toLowerCase(), splitedLine[1]);
        }
    }

    static PropertyDescription getInstance(){
        if(instance == null)
            instance = new PropertyDescription();
        return instance;
    }

    String getName(IToolProperty prop){
        return prop.getName();
    }

    String getDescription(IToolProperty prop){
        if(propertyNameToDescription.containsKey(prop.getName()))
            throw new RuntimeException("Missing property description for " + prop.getName());
        return propertyNameToDescription.get(prop.getName().toLowerCase());
    }
}
