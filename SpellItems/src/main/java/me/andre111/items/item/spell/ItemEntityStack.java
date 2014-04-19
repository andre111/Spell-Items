package me.andre111.items.item.spell;

import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.utils.PlayerHandler;
import me.andre111.items.volatileCode.SpellItemsPackets;

public class ItemEntityStack extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue entityUUID = args.arg(1);
			LuaValue stackM = args.arg(2);
			
			if(entityUUID.isstring() && stackM.isboolean()) {
				Entity ent = PlayerHandler.getEntityFromUUID(entityUUID.toString());
				boolean stackMode = stackM.toboolean();

				if(stackMode==false && ent.getPassenger()!=null) {
					SpellItemsPackets.disabledExits.remove(ent.getPassenger().getUniqueId());
					ent.eject();
					return LuaValue.TRUE;
				} else if(stackMode==true) {
					if(args.narg()>=3) {
						LuaValue passengerUUID = args.arg(3);
						if(passengerUUID.isstring()) {
							Entity passenger = PlayerHandler.getEntityFromUUID(passengerUUID.toString());
							if(passenger==null) return LuaValue.FALSE;
							int stackingMax = 1;
							if(args.narg()>=4 && args.arg(4).isint()) stackingMax = args.arg(4).toint();
							
							while(stackingMax>1) {
								if(ent!=null) {
									ent = ent.getPassenger();
								}
								stackingMax--;
							}
							
							if(ent!=null && ent.getPassenger()==null) {
								ent.setPassenger(passenger);
								SpellItemsPackets.disabledExits.add(passenger.getUniqueId());
								return LuaValue.TRUE;
							}
						}
					}
				}
			}
		}
		
		return LuaValue.FALSE;
	}
}
