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

    // Map of property name to tooltip description
    private static final Map<String, String> propertyNameToDescription = Map.ofEntries(

            // General tooltips for brush properties
            Map.entry("stroke size", "The size of this brush"),
            Map.entry("rotation", "The rotation of the stroke. E.g. a line could be rotated to PI/4"),
            Map.entry("y-scale", "The scale of the brush stroke in the y axis"),
            Map.entry("x-scale", "The scale of the brush stroke in the x axis"),
            Map.entry("stroke color", "The color of the stroke"),
            Map.entry("point frequency", "How many times the brush should draw per pixel"),

            // Fill bucket
            Map.entry("threshold", "If the color distance of a color c from the fill of bucket is less than this threshold then c will be changed to the buckets fill."),
            Map.entry("fill color", "The fill color of the bucket"),

            // Transform tool properties
            Map.entry("continuous rotation", "The rotation is continuously updated on the screen during the rotation action."),
            Map.entry("continuous scaling", "The scaling is continuously updated on the screen during the scaling action."),
            Map.entry("continuous translation", "The translation is continuously updated on the screen during the translation action."),
            Map.entry("relative to quadrant", "Is the scaling supposed to be relative to the layers quadrants or absolute?")
    );
    private static PropertyDescription instance;

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
