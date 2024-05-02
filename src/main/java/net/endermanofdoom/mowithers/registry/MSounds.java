package net.endermanofdoom.mowithers.registry;

import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.GameData;

public class MSounds {
	public static SoundEvent ENTITY_WITHER_FALL;
	public static SoundEvent[] ENTITY_WITHER_PINK;
	public static SoundEvent[] ENTITY_WITHER_ALLY;
	public static SoundEvent[] ENTITY_WITHER_FIRE;
	public static SoundEvent[] ENTITY_WITHER_WATER;
	public static SoundEvent[] ENTITY_WITHER_EARTH;
	public static SoundEvent[] ENTITY_WITHER_AIR;
	public static SoundEvent[] ENTITY_WITHER_ICE;
	public static SoundEvent[] ENTITY_WITHER_LIGHTNING;
	public static SoundEvent[] ENTITY_WITHER_DUST;
	public static SoundEvent[] ENTITY_WITHER_STEAM;
	public static SoundEvent[] ENTITY_WITHER_MAGMA;
	public static SoundEvent[] ENTITY_WITHER_SAND;
	public static SoundEvent[] ENTITY_WITHER_GRAVEL;
	public static SoundEvent[] ENTITY_WITHER_GLASS;
	public static SoundEvent[] ENTITY_WITHER_STONE;
	public static SoundEvent[] ENTITY_WITHER_COAL;
	public static SoundEvent[] ENTITY_WITHER_LAPIS;
	public static SoundEvent[] ENTITY_WITHER_IRON;
	public static SoundEvent[] ENTITY_WITHER_GOLD;
	public static SoundEvent[] ENTITY_WITHER_DIAMOND;
	public static SoundEvent[] ENTITY_WITHER_EMERALD;
	public static SoundEvent[] ENTITY_WITHER_TNT;
	public static SoundEvent[] ENTITY_WITHER_OBSIDIAN;
	public static SoundEvent[] ENTITY_WITHER_HARE;
	public static SoundEvent[] ENTITY_WITHER_PATTY;
	public static SoundEvent[] ENTITY_WITHER_AVATAR;
	public static SoundEvent[] ENTITY_WITHER_VOID;
	public static SoundEvent[] ENTITY_WITHER_DRAGON;
	public static SoundEvent[] ENTITY_WITHER_BEDROCK;
	
	public static void registerSounds()
	{
		ENTITY_WITHER_FALL = registerSound("entity.wither.fall");
		ENTITY_WITHER_PINK = registerAHDSound("pink");
		ENTITY_WITHER_ALLY = registerAHDSound("friendly");
		ENTITY_WITHER_FIRE = registerAHDSound("fire");
		ENTITY_WITHER_WATER = registerAHDSound("water");
		ENTITY_WITHER_EARTH = registerAHDSound("earth");
		ENTITY_WITHER_AIR = registerAHDSound("air");
		ENTITY_WITHER_ICE = registerAHDSound("ice");
		ENTITY_WITHER_LIGHTNING = registerAHDSound("lightning");
		ENTITY_WITHER_DUST = registerAHDSound("dust");
		ENTITY_WITHER_STEAM = registerAHDSound("steam");
		ENTITY_WITHER_MAGMA = registerAHDSound("lava");
		ENTITY_WITHER_SAND = registerAHDSound("sand");
		ENTITY_WITHER_GRAVEL = registerAHDSound("gravel");
		ENTITY_WITHER_GLASS = registerAHDSound("glass");
		ENTITY_WITHER_STONE = registerAHDSound("stone");
		ENTITY_WITHER_COAL = registerAHDSound("coal");
		ENTITY_WITHER_LAPIS = registerAHDSound("lapis");
		ENTITY_WITHER_IRON = registerAHDSound("iron");
		ENTITY_WITHER_GOLD = registerAHDSound("gold");
		ENTITY_WITHER_DIAMOND = registerAHDSound("diamond");
		ENTITY_WITHER_EMERALD = registerAHDSound("emerald");
		ENTITY_WITHER_TNT = registerAHDSound("tnt");
		ENTITY_WITHER_OBSIDIAN = registerAHDSound("obsidian");
		ENTITY_WITHER_HARE = registerAHDSound("hare");
		ENTITY_WITHER_PATTY = registerAHDSound("patty");
		ENTITY_WITHER_AVATAR = registerAHDSound("avatar");
		ENTITY_WITHER_VOID = registerAHDSound("void");
		ENTITY_WITHER_DRAGON = registerAHDSound("dragon");
		ENTITY_WITHER_BEDROCK = registerAHDSound("bedrock");
	}
	
	public static SoundEvent[] registerAHDSound(String soundName)
	{
		return new SoundEvent[] { registerSound("entity.wither.ambient." + soundName), registerSound("entity.wither.hurt." + soundName), registerSound("entity.wither.death." + soundName) };
	}
	
	public static SoundEvent registerSound(String soundName)
	{
		ResourceLocation soundID = new ResourceLocation(MoWithers.MODID, soundName);
		return (SoundEvent)GameData.register_impl(new SoundEvent(soundID).setRegistryName(soundID));
	}
}
