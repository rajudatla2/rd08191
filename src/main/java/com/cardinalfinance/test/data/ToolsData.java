package com.cardinalfinance.test.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToolsData {
    @JsonProperty("tools")
    private Tools tools;

    public Tools getTools() {
        return tools;
    }

    public void setTools(Tools tools) {
        this.tools = tools;
    }
}
