package hu.shiya.staffGui.classes;

import java.util.UUID;

public class MutedPlayer {
    private UUID uuid;
    private  String reason;
    private  UUID mutedBy;
    private  long duration;
    private  long timestamp;
    private  String ip;
    private  String name;

    public MutedPlayer(final UUID uuid, final String reason, final UUID mutedBy, final long duration, final long timestamp, final String ip, final String name ) {
        this.uuid = uuid;
        this.reason = reason;
        this.mutedBy = mutedBy;
        this.duration = duration;
        this.timestamp = timestamp;
        this.ip = ip;
        this.name = name;
    }
    public MutedPlayer( ) {};

    public String getReason() {
        return reason;
    }
    public UUID getMutedBy() {
        return mutedBy;
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

    public void setReason( final String reason ) {
        this.reason = reason;
    }
    public void setMutedBy( final UUID mutedBy ) {
        this.mutedBy = mutedBy;
    }
    public void setDuration( final long duration ) {
        this.duration = duration;
    }
    public void setTimestamp( final long timestamp ) {
        this.timestamp = timestamp;
    }
    public void setIp( final String ip ) {
        this.ip = ip;
    }
    public void setName( final String name ) {
        this.name = name;
    }
    public void setUuid( final UUID uuid ) {
        this.uuid = uuid;
    }
}
