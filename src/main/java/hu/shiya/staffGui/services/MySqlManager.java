package hu.shiya.staffGui.services;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.classes.BannedPlayer;
import hu.shiya.staffGui.classes.MutedPlayer;
import hu.shiya.staffGui.classes.WarnedPlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class MySqlManager implements StorageProvider{
    private Connection connection;
    private final StaffGui plugin;
    public MySqlManager( final StaffGui plugin ) {
        this.plugin = plugin;
    }
    @Override
    public void connectStorage() {
        try {
            String host = plugin.getConfig().getString("db.host");
            String database = plugin.getConfig().getString("db.database");
            String user = plugin.getConfig().getString("db.user");
            String password = plugin.getConfig().getString("db.password");

            String url = "jdbc:mysql://" + host + "/" + database;
            connection = DriverManager.getConnection(url, user, password);
            String message = "Mysql connection successful!";
            plugin.getLogger().info( message );


            if (connection != null || !connection.isClosed()) {
                String sql = "CREATE TABLE IF NOT EXISTS warnings ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "player_uuid VARCHAR(36) NOT NULL, "
                        + "name VARCHAR(64), "
                        + "reason TEXT NOT NULL, "
                        + "warned_by VARCHAR(36) NOT NULL, "
                        + "timestamp VARCHAR(120) NOT NULL, "
                        + "duration BIGINT DEFAULT 0"
                        + ");";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.executeUpdate();

                sql = "CREATE TABLE IF NOT EXISTS mutes ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "ip VARCHAR(45), "
                        + "name VARCHAR(64), "
                        + "reason TEXT NOT NULL, "
                        + "muted_by VARCHAR(36) NOT NULL, "
                        + "timestamp VARCHAR(120) NOT NULL, "
                        + "duration BIGINT NOT NULL"
                        + ");";
                ps = connection.prepareStatement(sql);
                ps.executeUpdate();

                sql = "CREATE TABLE IF NOT EXISTS bans ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "ip VARCHAR(45), "
                        + "name VARCHAR(64), "
                        + "reason TEXT NOT NULL, "
                        + "banned_by VARCHAR(36) NOT NULL, "
                        + "timestamp VARCHAR(120) NOT NULL, "
                        + "duration BIGINT NOT NULL"
                        + ");";
                ps = connection.prepareStatement(sql);
                ps.executeUpdate();

                message = "The tables have been created.";
                plugin.getLogger().info(message);

            }

        } catch (Exception e) {
            plugin.getLogger().severe("There was an error connecting to the MySql Database.");
        }
    }

    @Override
    public void disconnectStorage() {
        try {
            if (connection == null || connection.isClosed()) {
              String message = "The connection was already closed.";
              plugin.getLogger().info( message );
            } else {
                connection.close();
                plugin.getLogger().info( "The connection was closed." );
            }
        } catch (Exception e) {
            plugin.getLogger().severe("There was an error disconnecting from MySql Database.");
        }
    }

    @Override
    public void saveWarningAsync(WarnedPlayer warnedPlayer) {
        try {
            if (connection == null || connection.isClosed()) return;
            String sql = "INSERT INTO warnings (player_uuid, name, reason, warned_by, timestamp, duration) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, warnedPlayer.getUuid().toString());
            ps.setString(2, warnedPlayer.getName());
            ps.setString(3, warnedPlayer.getReason());
            ps.setString(4, warnedPlayer.getWarnedBy().toString());
            ps.setLong(5, warnedPlayer.getTimestamp());
            ps.setLong(6, warnedPlayer.getDuration());
            ps.executeUpdate();

            String message = "Successfully saved the warning.";
            plugin.getLogger().info(message);
        } catch (Exception e) {
            plugin.getLogger().severe("There was an error saving the warned player.");
        }
    }

    @Override
    public WarnedPlayer loadWarningAsync(UUID uuid) {
        try {
            if (connection == null || connection.isClosed()) return null;
            String sql = "SELECT * FROM warnings WHERE player_uuid = ? AND (timestamp + duration) > ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uuid.toString());
            ps.setLong(2, System.currentTimeMillis());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                WarnedPlayer warnedPlayer = new WarnedPlayer();
                warnedPlayer.setUuid(uuid);
                warnedPlayer.setName(rs.getString("name"));
                warnedPlayer.setReason(rs.getString("reason"));
                warnedPlayer.setWarnedBy(UUID.fromString(rs.getString("warned_by")));
                warnedPlayer.setTimestamp(rs.getLong("timestamp"));
                warnedPlayer.setDuration(rs.getLong("duration"));

                String messsage = "Successfully loaded the warning.";
                plugin.getLogger().info(messsage);
                return warnedPlayer;
            }
        } catch (Exception e) {
            plugin.getLogger().severe("There was an error loading the warned player.");
            return null;
        }
        return null;
    }

    @Override
    public void saveMuteAsync(MutedPlayer mutedPlayer) {
        try {
            if (connection == null || connection.isClosed()) return;
            String sql = "INSERT INTO mutes (ip, name, reason, muted_by timestamp, duration) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mutedPlayer.getIp());
            ps.setString(2, mutedPlayer.getName());
            ps.setString(3, mutedPlayer.getReason());
            ps.setString(4, mutedPlayer.getMutedBy().toString());
            ps.setLong(5, mutedPlayer.getTimestamp());
            ps.setLong(6, mutedPlayer.getDuration());
            ps.executeUpdate();

            String message = "Successfully saved the mute.";
            plugin.getLogger().info(message);

        } catch (Exception e) {
            plugin.getLogger().severe("There was an error saving the mute player.");
        }
    }

    @Override
    public MutedPlayer loadMuteAsync(UUID uuid) {
        try {
            if (connection == null || connection.isClosed()) return null;
            String sql = "SELECT * FROM mutes WHERE player_uuid = ? AND (timestamp + duration) > ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uuid.toString());
            ps.setLong(2, System.currentTimeMillis());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MutedPlayer mutedPlayer = new MutedPlayer();
                mutedPlayer.setUuid(mutedPlayer.getUuid());
                mutedPlayer.setName(rs.getString("name"));
                mutedPlayer.setReason(rs.getString("reason"));
                mutedPlayer.setMutedBy(UUID.fromString(rs.getString("muted_by")));
                mutedPlayer.setTimestamp(rs.getLong("timestamp"));
                mutedPlayer.setDuration(rs.getLong("duration"));
                mutedPlayer.setIp(rs.getString("ip"));

                String message = "Successfully saved the mute.";
                plugin.getLogger().info(message);

                return mutedPlayer;
            }
        } catch (Exception e) {
            plugin.getLogger().severe("There was an error loading the warned player.");
            return null;
        }
        return null;
    }

    @Override
    public void saveBanAsync(BannedPlayer bannedPlayer) {
        try {
            if (connection == null || connection.isClosed()) return;
            String sql = "INSERT INTO bans (ip, name, reason, banned_by timestamp, duration) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bannedPlayer.getIp());
            ps.setString(2, bannedPlayer.getName());
            ps.setString(3, bannedPlayer.getReason());
            ps.setString(4, bannedPlayer.getBannedBy().toString());
            ps.setLong(5, bannedPlayer.getTimestamp());
            ps.setLong(6, bannedPlayer.getDuration());
            ps.executeUpdate();

            String message = "Successfully saved the ban.";
            plugin.getLogger().info(message);
        } catch (Exception e) {
            plugin.getLogger().severe("There was an error saving the banned player.");
        }
    }

    @Override
    public BannedPlayer loadBanAsync(UUID uuid) {
        try {
            if (connection == null || connection.isClosed()) return null;
            String sql = "SELECT * FROM bans WHERE player_uuid = ? AND (timestamp + duration) > ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uuid.toString());
            ps.setLong(2, System.currentTimeMillis());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BannedPlayer bannedPlayer = new BannedPlayer();
                bannedPlayer.setUuid(uuid);
                bannedPlayer.setName(rs.getString("name"));
                bannedPlayer.setReason(rs.getString("reason"));
                bannedPlayer.setBannedBy(UUID.fromString(rs.getString("banned_by")));
                bannedPlayer.setTimestamp(rs.getLong("timestamp"));
                bannedPlayer.setDuration(rs.getLong("duration"));
                bannedPlayer.setIp(rs.getString("ip"));

                String message = "Successfully saved the ban.";
                plugin.getLogger().info(message);

                return bannedPlayer;
            }
        } catch (Exception e) {
            plugin.getLogger().severe("There was an error loading the banned player.");
            return null;
        }
        return null;
    }
}
