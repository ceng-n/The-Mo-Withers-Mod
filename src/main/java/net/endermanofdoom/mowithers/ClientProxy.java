package net.endermanofdoom.mowithers;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.endermanofdoom.mowithers.entity.monster.*;
import net.endermanofdoom.mowithers.entity.wither.*;
import net.endermanofdoom.mowithers.registry.MBlocks;
import net.endermanofdoom.mowithers.registry.MItems;
import net.endermanofdoom.mowithers.render.*;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		renderEntities();
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e)
	{
		MBlocks.registerRenders();
		MItems.registerRenders();
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
	
	public void renderEntities()
	{
		 RenderingRegistry.registerEntityRenderingHandler(EntityCultist.class, manager -> new RenderCultist(manager));

		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherPink.class, manager -> new RenderWitherPink(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherAlly.class, manager -> new RenderWitherAlly(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherFire.class, manager -> new RenderWitherFire(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherEarth.class, manager -> new RenderWitherEarth(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherWater.class, manager -> new RenderWitherWater(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherAir.class, manager -> new RenderWitherAir(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherIce.class, manager -> new RenderWitherIce(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherDust.class, manager -> new RenderWitherDust(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherSteam.class, manager -> new RenderWitherSteam(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherMagma.class, manager -> new RenderWitherMagma(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherLightning.class, manager -> new RenderWitherLightning(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherSand.class, manager -> new RenderWitherSand(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherGravel.class, manager -> new RenderWitherGravel(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherGlass.class, manager -> new RenderWitherGlass(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherCreeper.class, manager -> new RenderWitherCreeper(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherZombie.class, manager -> new RenderWitherZombie(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherEnder.class, manager -> new RenderWitherEnder(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherHare.class, manager -> new RenderWitherHare(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherStone.class, manager -> new RenderWitherStone(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherCoal.class, manager -> new RenderWitherCoal(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherLapis.class, manager -> new RenderWitherLapis(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherIron.class, manager -> new RenderWitherIron(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherGold.class, manager -> new RenderWitherGold(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherDiamond.class, manager -> new RenderWitherDiamond(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherEmerald.class, manager -> new RenderWitherEmerald(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherTNT.class, manager -> new RenderWitherTNT(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherObsidian.class, manager -> new RenderWitherObsidian(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherPattyDay.class, manager -> new RenderWitherPattyDay(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherAvatar.class, manager -> new RenderWitherAvatar(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherVoid.class, manager -> new RenderWitherVoid(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherDragon.class, manager -> new RenderWitherDragon(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherBedrock.class, manager -> new RenderWitherBedrock(manager));
		 RenderingRegistry.registerEntityRenderingHandler(EntityWitherCommand.class, manager -> new RenderWitherCommand(manager));
	}
}
