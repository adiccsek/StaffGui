package hu.shiya.staffGui.services;

import hu.shiya.staffGui.classes.BannedPlayer;
import hu.shiya.staffGui.classes.MutedPlayer;
import hu.shiya.staffGui.classes.WarnedPlayer;

import java.util.UUID;

public interface StorageProvider {
    void connectStorage();
    void disconnectStorage();

    void saveWarningAsync(WarnedPlayer warnedPlayer);
    WarnedPlayer loadWarningAsync(UUID uuid);

    void saveMuteAsync(MutedPlayer mutedPlayer);
    MutedPlayer loadMuteAsync(UUID uuid);

    void saveBanAsync(BannedPlayer bannedPlayer);
    BannedPlayer loadBanAsync(UUID uuid);

}
