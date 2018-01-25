package com.walrusone.skywarsreloaded.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.managers.MatchManager;
import com.walrusone.skywarsreloaded.objects.GameMap;
import com.walrusone.skywarsreloaded.objects.PlayerStat;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerQuitListener implements Listener
{
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent a1) {
    	final String id = a1.getPlayer().getUniqueId().toString();
        final GameMap gameMap = MatchManager.get().getPlayerMap(a1.getPlayer());
        if (gameMap == null) {
            return;
        }
        
        MatchManager.get().playerLeave(a1.getPlayer(), DamageCause.CUSTOM, true, true);
        
   		if (PlayerStat.getPlayerStats(id) != null) {
   			new BukkitRunnable() {
				@Override
				public void run() {
					if (SkyWarsReloaded.get().getServer().getPlayer(UUID.fromString(id)) == null) {
			   			PlayerStat remove = PlayerStat.getPlayerStats(id);
			   			PlayerStat.getPlayers().remove(remove);
					}
				}
   				
   			}.runTaskLater(SkyWarsReloaded.get(), 40);
   		}
    }
}