package me.andre111.items.convert;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SpellConvertSpell {
	private int id;
	private String name;
	private ArrayList<String> arguments = new ArrayList<String>();
	private ArrayList<SpellConvertSpell> spells = new ArrayList<SpellConvertSpell>();
	
	public SpellConvertSpell(int ID, String n) {
		this.id = ID;
		this.name = n;
	}
	
	public void addArgument(String arg) {
		arguments.add(arg);
	}
	
	public void addSpell(SpellConvertSpell spell, int require) {
		if(require==id) {
			spells.add(spell);
		} else {
			for(SpellConvertSpell rspell : spells) {
				rspell.addSpell(spell, require);
			}
		}
	}
	
	public void save(BufferedWriter writer, String einrueck) throws IOException {
		String args = "";
		for(String arg : arguments) {
			if(!args.equals("")) args = args + ", ";
			args = args + arg;
		}
		
		if(spells.isEmpty()) {
			writer.write(einrueck+name+"("+args+")");
			writer.newLine();
		} else {
			writer.write(einrueck+"if("+name+"("+args+")) then");
			writer.newLine();
				for(SpellConvertSpell spell : spells) {
					spell.save(writer, einrueck+"   ");
				}
			writer.write(einrueck+"end");
			writer.newLine();
		}
	}
}
