package com.lekohd.blockparty;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.lekohd.blockparty.commands.BlockParty;
import com.lekohd.blockparty.floor.LoadFloor;
import com.lekohd.blockparty.listeners.BlockPlaceListener;
import com.lekohd.blockparty.listeners.ChangeBlockListener;
import com.lekohd.blockparty.listeners.CommandListener;
import com.lekohd.blockparty.listeners.DamageListener;
import com.lekohd.blockparty.listeners.DisconnectListener;
import com.lekohd.blockparty.listeners.FeedListener;
import com.lekohd.blockparty.listeners.InteractListener;
import com.lekohd.blockparty.listeners.InventoryListener;
import com.lekohd.blockparty.listeners.MoveListener;
import com.lekohd.blockparty.listeners.SignListener;
import com.lekohd.blockparty.music.Songs;
import com.lekohd.blockparty.system.Config;



public class Main extends JavaPlugin {

	public static Player p;
	public static HashMap<Player, String> inGamePlayers = new HashMap<>();
	public static HashMap<Player, String> inLobbyPlayers = new HashMap<>();
	public static HashMap<Player, String> onFloorPlayers = new HashMap<>();
	public static HashMap<Player, Location> locs = new HashMap<>();
	public static HashMap<Player, GameMode> gm = new HashMap<>();
	public static HashMap<Player, ItemStack[]> inv = new HashMap<>();
	//public static HashSet<String> floors = new HashSet<>();
	public static HashMap<String, Config> getArena = new HashMap<>();
	public static ArrayList<LoadFloor> floors = new ArrayList<>();
	public static HashMap<String, ArrayList<LoadFloor>> getFloor = new HashMap<>();
	public static HashMap<String, Sign> signs = new HashMap<>();
	public static Main instance;
	public static int playerAmount = 0;
	public static int lessMinimum = 0;
	public static boolean abort = false;
	public static ArrayList<String> arenaNames = new ArrayList<>();
	
	// TODO hoverable text
	// prevent user from placing blocks
	// TODO disable pvp
	// falling blocks
	// TODO Note Block as countdown
	// Heath and Feed regeneration
	
	 public void onEnable()
	  {
		  instance = this;
		  loadConfig();
		  System.out.println("[BlockParty] Plugin by " + this.getDescription().getAuthors());
		  this.getCommand("blockparty").setExecutor(new BlockParty());
		  getServer().getPluginManager().registerEvents(new DisconnectListener(), this);
		  getServer().getPluginManager().registerEvents(new CommandListener(), this);
		  getServer().getPluginManager().registerEvents(new MoveListener(), this);
		  getServer().getPluginManager().registerEvents(new SignListener(), this);
		  getServer().getPluginManager().registerEvents(new InteractListener(), this);
		  getServer().getPluginManager().registerEvents(new FeedListener(), this);
		  getServer().getPluginManager().registerEvents(new ChangeBlockListener(), this);
		  getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
		  getServer().getPluginManager().registerEvents(new DamageListener(), this);
		  getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		  if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI"))
			{
			  	for(Player p : Bukkit.getOnlinePlayers())
			  		Songs.stop(p);
			}
	  }
	 public void onDisable()
	 {
		 System.out.println("[BlockParty] Plugin disabled!");
	 }
	  public static Main getInstance(){
		  return instance;
	  }
	  @SuppressWarnings("unchecked")
	public static void loadConfig(){
		  FileConfiguration cfg = instance.getConfig();
		  cfg.options().copyDefaults(true);
		  instance.saveConfig();
		  arenaNames = (ArrayList<String>) cfg.get("enabledArenas");
		  if(!(arenaNames == null))
		  {
			  if(!(arenaNames.isEmpty()))
			  {
				  for(String name : arenaNames){
					  Config conf = new Config(name);
					  conf.enable();
					  conf.loadCfg();
					  conf.loadGameSpawn();
					  conf.loadLobbySpawn();
					  conf.loadMinPlayers();
					  conf.loadMax();
					  conf.loadMin();
					  conf.load();
					  getArena.put(name, conf);
					  if(!(conf.getFloors()  == null)){
						  floors.clear();
						  for(String floor : conf.getFloors())
						  {
							  floors.add(new LoadFloor(floor));
							  getFloor.put(name, floors);
						  }	
				  		}
					  System.out.println("[BlockParty] Arena " + name + " loaded!");
				  }
			  }  
		  }
	  }
	  /*public static boolean lessThanMinimum(String arenaName){
		  if(Players.getPlayerAmountInLobby(arenaName) >= lessMinimum)
		  {
			  return false;
		  }
		  return true;
	  }
	  
	  public static void minCfgPlayers(String arenaName){
		  File arena = new File("plugins//BlockParty//Arenas//" + arenaName + ".yml");
			if(arena.exists())
			{
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(arena);
				try {
					cfg.load(arena);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lessMinimum = cfg.getInt("configuration.MinPlayers");
			}
	  }*/
}