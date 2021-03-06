/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2020 Austin "Scarsz" Shapiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package github.scarsz.discordsrv.api.events;

import github.scarsz.discordsrv.util.DiscordUtil;
import lombok.Getter;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

/**
 * <p>Called directly after an account pair is unlinked via DiscordSRV's {@link github.scarsz.discordsrv.objects.managers.AccountLinkManager}</p>
 */
public class AccountUnlinkedEvent extends Event {

    @Getter private final OfflinePlayer player;
    @Getter private final String discordId;
    @Getter private final User discordUser;

    public AccountUnlinkedEvent(String discordId, UUID playerUuid) {
        this.player = Bukkit.getOfflinePlayer(playerUuid);
        this.discordId = discordId;
        this.discordUser = DiscordUtil.getUserById(discordId);
    }

}
