package me.andre111.items.item.spell;

import me.andre111.items.SpellItems;
import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.PlayerHandler;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemLeap extends ItemSpell {
	/*private double forward = 40 / 10D;
	private double upward = 15 / 10D;
	private float power = 1;
	private boolean disableDamage = true;
	
	@SuppressWarnings("unused")
	private double range;*/
	
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=5) {
			LuaValue playerN = args.arg(1);
			LuaValue forwardN = args.arg(2);
			LuaValue upwardN = args.arg(3);
			LuaValue powerN = args.arg(4);
			LuaValue disableDamageN = args.arg(5);
			
			if(playerN.isstring() && forwardN.isnumber() && upwardN.isnumber() && powerN.isnumber() && disableDamageN.isboolean()) {
				Player player = PlayerHandler.getPlayerFromUUID(playerN.toString());
				
				
				if(player!=null) {
					double forward = forwardN.todouble();
					double upward = upwardN.todouble();
					float power = (float) powerN.todouble();
					boolean disableDamage = disableDamageN.toboolean();
					
					spellLeap(player, forward, upward, power, disableDamage);
					
					return RETURN_TRUE;
				}
			}
		} else {
			SpellItems.log("Missing Argument for "+getClass().getCanonicalName());
		}
		
		return RETURN_FALSE;
	}
	
	public static void spellLeap(Player player, double forward, double upward, float power, boolean diasableDamage) {
		Vector v = player.getLocation().getDirection();
        v.setY(0).normalize().multiply(forward*power).setY(upward*power);
        player.setVelocity(v);
        if(diasableDamage)
        	SpellItems.jumpingNormal.add(player);
	}
}
