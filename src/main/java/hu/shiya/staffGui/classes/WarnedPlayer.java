package hu.shiya.staffGui.classes;

import java.util.UUID;

public class WarnedPlayer {
        private  UUID uuid;
        private  String reason;
        private  UUID warnedBy;
        private  long timestamp;
        private  long duration;
        private  String name;

        public WarnedPlayer(final UUID uuid, final String reason, final UUID warnedBy, final long timestamp, final long duration, final String name ) {
            this.uuid = uuid;
            this.reason = reason;
            this.warnedBy = warnedBy;
            this.timestamp = timestamp;
            this.duration = duration;
            this.name = name;
        }
        public WarnedPlayer() {};

        public UUID getUuid() {
            return uuid;
        }
        public String getReason() {
            return reason;
        }
        public UUID getWarnedBy() {
            return warnedBy;
        }
        public long getTimestamp() {
            return timestamp;
        }
        public long getDuration() {
            return duration;
        }
        public String getName() {
            return name;
        }

        public void setUuid( final UUID uuid ) {
            this.uuid = uuid;
        }
        public void setReason( final String reason ) {
            this.reason = reason;
        }
        public void setWarnedBy( final UUID warnedBy ) {
            this.warnedBy = warnedBy;
        }
        public void setTimestamp( final long timestamp ) {
            this.timestamp = timestamp;
        }
        public void setDuration( final long duration ) {
            this.duration = duration;
        }
        public void setName( final String name ) {
            this.name = name;
        }

    }
