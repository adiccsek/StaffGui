package hu.shiya.staffGui.classes;

import java.util.UUID;

public class BannedPlayer {
    private UUID uuid;
    private  String reason;
    private  UUID bannedBy;
    private  long duration;
    private  long timestamp;
    private  String ip;
    private  String name;

    public BannedPlayer (UUID uuid, String reason, UUID bannedBy, long duration, long timestamp, String ip, String name) {
        this.reason = reason;
        this.bannedBy = bannedBy;
        this.duration = duration;
        this.timestamp = timestamp;
        this.ip = ip;
        this.name = name;
        this.uuid = uuid;
    }
    public BannedPlayer() {}

    public String getReason() {
        return reason;
    }
    public UUID getBannedBy() {
        return bannedBy;
    }
    public long getDuration() {
        return duration;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public String getIp() {
        return ip;
    }
    public String getName() {
        return name;
    }
    public UUID getUuid() {
        return uuid;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public void setBannedBy(UUID bannedBy) {
        this.bannedBy = bannedBy;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
