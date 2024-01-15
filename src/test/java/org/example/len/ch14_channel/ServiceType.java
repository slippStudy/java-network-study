package org.example.len.ch14_channel;

import org.assertj.core.util.Lists;

import java.util.HashMap;
import java.util.List;

enum ServiceType {

    SERVICE_A("SERVICE_A", Lists.list(DisplayType.DISPLAY_1, DisplayType.DISPLAY_2)),
    SERVICE_B("SERVICE_B", Lists.list(DisplayType.DISPLAY_3, DisplayType.DISPLAY_4));

    private final String name;
    private final List<DisplayType> displayTypes;

    private final static HashMap<String, List<DisplayType>> MAP;
    static {
        HashMap<String, List<DisplayType>> map = new HashMap<>();
        map.put("SERVICE_A", Lists.list(DisplayType.DISPLAY_1, DisplayType.DISPLAY_2));
        map.put("SERVICE_B", Lists.list(DisplayType.DISPLAY_3, DisplayType.DISPLAY_4));

        MAP = map;
    }

    static public List<DisplayType> get(String name) {
        return MAP.get(name);
    }

    ServiceType(String name, List<DisplayType> displayTypes) {
        this.name = name;
        this.displayTypes = displayTypes;
    }

    public String getName() {
        return name;
    }

    public List<DisplayType> getDisplayTypes() {
        return displayTypes;
    }



}
