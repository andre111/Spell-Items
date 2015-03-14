package me.andre111.items.item.spell;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import me.andre111.items.item.ItemSpell;
import me.andre111.items.lua.LUAHelper;
import me.andre111.items.utils.EntityHandler;
import me.andre111.items.volatileCode.SpellItemsPackets;

public class ItemEntityStack extends ItemSpell {
	@Override
	public Varargs invoke(Varargs args) {
		if(args.narg()>=2) {
			LuaValue entityUUID = LUAHelper.getInternalValue(args.arg(1));
			LuaValue stackM = args.arg(2);
			
			if(entityUUID.isuserdata(UUID.class) && stackM.isboolean()) {
				Entity ent = EntityHandler.getEntityFromUUID((UUID) entityUUID.touserdata(UUID.class));
				boolean stackMode = stackM.toboolean();

				if(stackMode==false && ent.getPassenger()!=null) {
					SpellItemsPackets.disabledExits.remove(ent.getPassenger().getUniqueId());
					ent.eject();
					return LuaValue.TRUE;
				} else if(stackMode==true) {
					if(args.narg()>=3) {
						LuaValue passengerUUID = LUAHelper.getInternalValue(args.arg(3));
						if(passengerUUID.isuserdata(UUID.class)) {
							Entity passenger = EntityHandler.getEntityFromUUID((UUID) passengerUUID.touserdata(UUID.class));
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
