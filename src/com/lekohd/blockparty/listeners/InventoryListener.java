package com.lekohd.blockparty.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.lekohd.blockparty.Main;
import com.lekohd.blockparty.music.Vote;

public class InventoryListener implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getSlot() == e.getRawSlot()){
			Player p = (Player) e.getWhoClicked();
			if(Main.inLobbyPlayers.containsKey(p)){
				e.setCancelled(true);
				p.updateInventory();
				ItemStack item = e.getCurrentItem();
				if (item != null)
				{
					String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
					Vote.voteFor(name, Main.inLobbyPlayers.get(p));
					p.sendMessage("�3[BlockParty] �8You have voted for " + name);
					p.getInventory().remove(Material.FIREBALL);
					Vote.closeInv(p);
					
				}
			}
		}
	}

}