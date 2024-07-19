package com.cardinalfinance.test.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tools {
    @JsonProperty("tool")
    private List<Tool> tool;

    public List<Tool> getTool() {
        return tool;
    }

    public void setTool(List<Tool> tool) {
        this.tool = tool;
    }
}
