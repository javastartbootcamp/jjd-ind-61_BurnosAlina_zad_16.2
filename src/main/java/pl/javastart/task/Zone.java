package pl.javastart.task;

import java.time.ZoneId;

public class Zone {

    private ZoneId zoneId;
    private String name;

    public Zone(ZoneId id, String name) {
        this.zoneId = id;
        this.name = name;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public String getName() {
        return name;
    }
}
