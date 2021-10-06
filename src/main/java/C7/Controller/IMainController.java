package C7.Controller;

import C7.Model.Tools.ITool;

/**
 * Interface for the primary controller.
 * @author Hugo Ekstrand
 */
public interface IMainController {

    /**
     * Sets the selected tool to a new, given one.
     * @param tool the tool which will be selected.
     */
    void setCurrentTool(ITool tool);

}
