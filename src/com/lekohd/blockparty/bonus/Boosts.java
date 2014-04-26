package com.lekohd.blockparty.bonus;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.lekohd.blockparty.Main;

public class Boosts {
	
	public Block b;

	public void place(String arenaName){
		Random r = new Random();
		int x = r.nextInt(Main.getArena.get(arenaName).getFloorLength());
		int z = r.nextInt(Main.getArena.get(arenaName).getFloorWidth());
		int y = Main.getArena.get(arenaName).getLocMin().getBlockY()+1;
		World world = Main.getArena.get(arenaName).getWorld();
		b = world.getBlockAt(Main.getArena.get(arenaName).getLocMin().getBlockX() + x, y, Main.getArena.get(arenaName).getLocMin().getBlockZ() + z);
		b.setType(Material.BEACON);
	}
	
	public void remove(){
		if(b!=null)
		{
			b.setType(Material.AIR);
		}
	}
	
}