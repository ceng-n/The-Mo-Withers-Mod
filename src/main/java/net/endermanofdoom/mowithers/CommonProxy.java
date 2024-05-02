package net.endermanofdoom.mowithers;

import net.endermanofdoom.mowithers.registry.MBlocks;
import net.endermanofdoom.mowithers.registry.MEntity;
import net.endermanofdoom.mowithers.registry.MItems;
import net.endermanofdoom.mowithers.registry.MLoot;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy 
{
	public void preInit(FMLPreInitializationEvent e)
	{
		MSounds.registerSounds();
		MBlocks.registerBlocks();
		MItems.registerItems();
		MLoot.registerLoot();
	}
	
	public void init(FMLInitializationEvent e)
	{
		MEntity.registerEntities();
	}
	
	public void postInit(FMLPostInitializationEvent e) 
	{
		
	}
}
