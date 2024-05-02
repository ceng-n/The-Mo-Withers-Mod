package net.endermanofdoom.mowithers.registry;

import net.endermanofdoom.mowithers.entity.monster.*;
import net.endermanofdoom.mowithers.entity.wither.*;
import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class MEntity
{
	private static int id = 0;
	
	public static void registerEntities()
	{
		   createEntityWithEgg(EntityCultist.class, "cultist", 0x1e1c1a, 0xbd8b72);

		   createEntityWithEgg(EntityWitherPink.class, "wither.pink", 0xFFC0CB, 0xFF69B4);
		   createEntityWithEgg(EntityWitherAlly.class, "wither.ally", 0x101010, 0x3d5b7f);
		   createEntityWithEgg(EntityWitherFire.class, "wither.fire", 0x8a0a0a, 0xa22d2d);
		   createEntityWithEgg(EntityWitherEarth.class, "wither.earth", 0x513f32, 0x7ba499);
		   createEntityWithEgg(EntityWitherWater.class, "wither.water", 0x0f0f74, 0x3b93ff);
		   createEntityWithEgg(EntityWitherAir.class, "wither.air", 0xffffff, 0xffffff);
		   createEntityWithEgg(EntityWitherIce.class, "wither.ice", 0x7592c5, 0x5cdeff);
		   createEntityWithEgg(EntityWitherDust.class, "wither.dust", 0x666b57, 0xDCC5AF);
		   createEntityWithEgg(EntityWitherSteam.class, "wither.steam", 0xffffff, 0xd7d7d7);
		   createEntityWithEgg(EntityWitherMagma.class, "wither.magma", 0xeaab43, 0xcd4509);
		   createEntityWithEgg(EntityWitherLightning.class, "wither.lightning", 0x686529, 0xfef340);
		   createEntityWithEgg(EntityWitherCreeper.class, "wither.creeper", 3232308, 0);
		   createEntityWithEgg(EntityWitherZombie.class, "wither.zombie", 44975, 7969893);
		   createEntityWithEgg(EntityWitherEnder.class, "wither.ender", 1447446, 9725844);
		   createEntityWithEgg(EntityWitherHare.class, "wither.hare", 0xf0f0f0, 0xcc0f0f);
		   createEntityWithEgg(EntityWitherSand.class, "wither.sand", 0xdacfa3, 0xedebcb);
		   createEntityWithEgg(EntityWitherGravel.class, "wither.gravel", 0x817f7f, 0x645b5b);
		   createEntityWithEgg(EntityWitherGlass.class, "wither.glass", 0x8bc1cd, 0xd0eae9);
		   createEntityWithEgg(EntityWitherStone.class, "wither.stone", 0x7f7f7f, 0x686868);
		   createEntityWithEgg(EntityWitherCoal.class, "wither.coal", 0x151515, 0x050505);
		   createEntityWithEgg(EntityWitherLapis.class, "wither.lapis", 0x20509c, 0x1b3588);
		   createEntityWithEgg(EntityWitherIron.class, "wither.iron", 0xe6e6e6, 0xc1c1c1);
		   createEntityWithEgg(EntityWitherGold.class, "wither.gold", 0xfee048, 0xfffd90);
		   createEntityWithEgg(EntityWitherDiamond.class, "wither.diamond", 0x4bede6, 0xd5fff6);
		   createEntityWithEgg(EntityWitherEmerald.class, "wither.emerald", 0x17dd62, 0x1aae35);
		   createEntityWithEgg(EntityWitherTNT.class, "wither.tnt", 0xdb2f1a, 0x8e8e8e);
		   createEntityWithEgg(EntityWitherObsidian.class, "wither.obsidian", 0x100c1c, 0x3b2754);
		   createEntityWithEgg(EntityWitherPattyDay.class, "wither.patty", 0x009900, 0xfeff99);
		   createEntityWithEgg(EntityWitherAvatar.class, "wither.avatar", 0x000000, 0xffffff);
		   createEntityWithEgg(EntityWitherVoid.class, "wither.void", 0, 0);
		   createEntityWithEgg(EntityWitherDragon.class, "wither.dragon", 0, 0x350037);
		   createEntityWithEgg(EntityWitherBedrock.class, "wither.bedrock", 0x575757, 0x333333);
		   createEntityWithEgg(EntityWitherCommand.class, "wither.command", 0xc77e4f, 0xb3b0b9);
	}

	public static void createEntityWithEgg(Class<? extends Entity> entityClass, String entityName, int primary, int secondary)
	{
		createEntity(entityClass, entityName);
		EntityRegistry.registerEgg(new ResourceLocation(MoWithers.MODID, entityName), primary, secondary);
	}
	
	public static void createEntity(Class<? extends Entity> entityClass, String entityName)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(MoWithers.MODID, entityName), entityClass, entityName, ++id, MoWithers.instance, 2048, 1, true);
	}
}
