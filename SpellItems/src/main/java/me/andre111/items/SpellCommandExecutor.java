package me.andre111.items;

import java.util.ArrayList;
import java.util.List;

import me.andre111.items.item.CustomItem;
import me.andre111.items.item.enchant.CustomEnchant;
import me.andre111.items.volatileCode.DeprecatedMethods;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpellCommandExecutor implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		//GIVE COMMAND
		if(command.getName().equalsIgnoreCase("siGive")) {
			if(!sender.hasPermission("spellitems.give")) {
				sender.sendMessage("You don't have the Permission to do that!");
				return false;
			}
			
			//get the player
			if(args.length>0) {
				Player player = DeprecatedMethods.getPlayerByName(args[0]);
				
				if(player!=null) {
					//recombine all other arguments
					String itemSt = "";
					int ii = 1;
					while(args.length>ii) {
						itemSt = itemSt + " " + args[ii];
						ii++;
					}
					
					//get the tem
					ItemStack it = ItemHandler.decodeItem(itemSt, player);
					if(it!=null) {
						player.getInventory().addItem(it);
						
						return true;
					} else {
						sender.sendMessage("Could not decode Itemstring: "+itemSt);
						return false;
					}
				} else {
					sender.sendMessage("Player "+args[0]+" not found!");
					return false;
				}
			} else {
				sender.sendMessage("Please specify a player to give the item to!");
				return false;
			}
		}
		//ENCHANT COMMAND
		if(command.getName().equalsIgnoreCase("siEnchant")) {
			if(!sender.hasPermission("spellitems.enchant")) {
				sender.sendMessage("You don't have the Permission to do that!");
				return false;
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be used by a Player!");
				return true;
			}
			Player player = (Player)sender;
			
			if(args.length>0) {
				CustomEnchant ce = SpellItems.enchantManager.getEnchantmentByName(args[0]);
				int level = 0;
				if(args.length>1) {
					try {
						level = Integer.parseInt(args[1]) - 1;
						if(level<0) {
							sender.sendMessage((level+1)+" is not a valid Level!");
							return false;
						}
					} catch(NumberFormatException e) {
						sender.sendMessage(args[1]+" is not a valid Level!");
						return false;
					}
				}
				if(ce!=null) {
					ItemStack it = player.getItemInHand();
					it = ce.enchantItem(it, level);
					player.setItemInHand(it);
					
					return true;
				} else {
					sender.sendMessage("Could not find Enchantment: "+args[0]);
					return false;
				}
			} else {
				sender.sendMessage("Please specify an enchantment!");
				return false;
			}
		}
		//MANA COMMAND
		if(command.getName().equalsIgnoreCase("siMana")) {
			if(!sender.hasPermission("spellitems.mana")) {
				sender.sendMessage("You don't have the Permission to do that!");
				return false;
			}
			
			//get the player
			if(args.length>0) {
				Player player = DeprecatedMethods.getPlayerByName(args[0]);
				
				if(player!=null) {
					//get the value
					if(args.length>2) {
						int value = 0;
						try {
							value = Integer.parseInt(args[2]);
						} catch (NumberFormatException e) {
							sender.sendMessage("Could not interpret "+args[2]+" as a number");
							return false;
						}
						
						if(args[1].equalsIgnoreCase("maxmana")) {
							ManaManager.setMaxMana(player.getUniqueId(), value, true);
						} else if(args[1].equalsIgnoreCase("regen")) {
							ManaManager.setManaRegen(player.getUniqueId(), value);
						} else {
							sender.sendMessage("Could not find variable: "+args[1]);
							sender.sendMessage("Please use maxmana or regen");
							return false;
						}
					} else {
						sender.sendMessage("Please specify a variable and the value!");
						return false;
					}
				} else {
					sender.sendMessage("Player "+args[0]+" not found!");
					return false;
				}
			} else {
				sender.sendMessage("Please specify a player!");
				return false;
			}
		}
		//REWARD COMMAND
		if(command.getName().equalsIgnoreCase("siReward")) {
			if(!sender.hasPermission("spellitems.reward")) {
				sender.sendMessage("You don't have the Permission to do that!");
				return false;
			}

			//get the player
			if(args.length>0) {
				Player player = DeprecatedMethods.getPlayerByName(args[0]);

				if(player!=null) {
					//get the value
					if(args.length>1) {
						int value = 0;
						try {
							value = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							if(args[1].equalsIgnoreCase("reset")) {
								RewardManager.resetRewardPoints(player);
								return true;
							} else {
								sender.sendMessage("Could not interpret "+args[1]+" as a number");
								return false;
							}
						}

						RewardManager.addRewardPoints(player, value);
						return true;
					} else {
						sender.sendMessage("Please specify the ammount of points to give!");
						return false;
					}
				} else {
					sender.sendMessage("Player "+args[0]+" not found!");
					return false;
				}
			} else {
				sender.sendMessage("Please specify a player!");
				return false;
			}
		}
		//HELP COMMAND
		if(command.getName().equalsIgnoreCase("siHelp")) {
			if(!sender.hasPermission("spellitems.help")) {
				sender.sendMessage("You don't have the Permission to do that!");
				return false;
			}
			
			if(args.length>0) {
				if(args[0].equalsIgnoreCase("siGive")) {
					if(sender.hasPermission("spellitems.give")) {
						sender.sendMessage("Give Items to Players using the SpellItems Syntax");
						sender.sendMessage("For info on the syntax please use /siHelp syntax");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("siEnchant")) {
					if(sender.hasPermission("spellitems.enchant")) {
						sender.sendMessage("Enchants the Item you are currently holding with a custom Enchantment");
						sender.sendMessage("This command can only be used as a Player");
						if(sender.hasPermission("spellitems.give")) {
							sender.sendMessage("But you can use /siGive to give allready enchanted Items");
						}
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("siMana")) {
					if(sender.hasPermission("spellitems.mana")) {
						sender.sendMessage("Set the Maximum Mana and Manaregeneration per second for a Player");
						sender.sendMessage("Setting Maximum Mana will also completly fill the Players Mana");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("siReward")) {
					if(sender.hasPermission("spellitems.reward")) {
						sender.sendMessage("Give Rewardpoints to a Player");
						sender.sendMessage("Use \"reset\" to reset the Players points");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("Syntax")) {
					sender.sendMessage("Itemsyntax: ");
					sender.sendMessage("<ItemName> <damagevalue> <min_count>:<max_count> <chance_to_get_item> <customEnchantmentName>:<level>,... <permission> <dataTag>");
					sender.sendMessage("Chance is X out of 100");
					sender.sendMessage("Enchantments: ");
					sender.sendMessage("internalname of a custom one");
					sender.sendMessage("-1 to completly ignore enchants(if you want to set a name)");
					sender.sendMessage("-10 to only get the glowing Effect without an enchantment");
					return true;
				}
			} else {
				sender.sendMessage("Please specify what you want more Info about!");
				return false;
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		List<String> completes = new ArrayList<String>();
		
		//GIVE COMMAND
		if(cmd.getName().equalsIgnoreCase("siGive")) {
			//1 = Player
			if(args.length==2) {
				if(args[1].equals("")) {
					completes.add("minecraft:");
					completes.add("spellitems:");
				} else if(args[1].toLowerCase().startsWith("minecraft:")) {
					completes.addAll(DeprecatedMethods.tabCompleteInternalName(args[1]));
				} else if(args[1].toLowerCase().startsWith("spellitems:")) {
					for(CustomItem item : SpellItems.itemManager.getItems()) {
						String name = "spellitems:" + item.getInternalName();
						if(name.toLowerCase().startsWith(args[1].toLowerCase())) {
							completes.add(name);
						}
					}
				} else if("minecraft:".startsWith(args[1].toLowerCase())) {
					completes.add("minecraft:");
				} else if("spellitems:".startsWith(args[1].toLowerCase())) {
					completes.add("spellitems:");
				}
			}
		}
		//ENCHANT COMMAND
		if(cmd.getName().equalsIgnoreCase("siEnchant")) {
			if(args.length==0) {
				for(CustomEnchant enchant : SpellItems.enchantManager.getEnchantments()) {
					completes.add(enchant.getInternalName());
				}
			} else if(args.length==1) {
				for(CustomEnchant enchant : SpellItems.enchantManager.getEnchantments()) {
					if(enchant.getInternalName().toLowerCase().startsWith(args[0].toLowerCase())) {
						completes.add(enchant.getInternalName());
					}
				}
			}
		}
		//MANA COMMAND
		if(cmd.getName().equalsIgnoreCase("siMana")) {
			
		}
		//REWARD COMMAND
		if(cmd.getName().equalsIgnoreCase("siReward")) {
			
		}
		//HELP COMMAND
		if(cmd.getName().equalsIgnoreCase("siHelp")) {
			if(args.length==0) {
				completes.addAll(SpellItems.instance.getDescription().getCommands().keySet());
				completes.add("syntax");
			} else if(args.length==1) {
				for(String st : SpellItems.instance.getDescription().getCommands().keySet()) {
					if(st.toLowerCase().startsWith(args[0].toLowerCase())) {
						completes.add(st);
					}
				}
				if("syntax".startsWith(args[0].toLowerCase())) {
					completes.add("syntax");
				}
			}
		}
		
		if(!completes.isEmpty()) {
			return completes;
		}
		return null;
	}
}
