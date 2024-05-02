package net.endermanofdoom.mowithers;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.endermanofdoom.mca.MinecraftAdventures;
import net.endermanofdoom.mowithers.events.MEvents;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Logger;

import com.google.common.base.Predicate;

@Mod(modid = MoWithers.MODID, name = MoWithers.MODNAME, version = MoWithers.VERSION)
public class MoWithers
{
	public static final String MODNAME = "Mo' Withers";
	public static final String MODID = "mowithers";
	public static final String VERSION = "0.42";
	public static final String CLIENT = "net.endermanofdoom.mowithers.ClientProxy";
	public static final String SERVER = "net.endermanofdoom.mowithers.CommonProxy";
	@SidedProxy(clientSide=CLIENT, serverSide=SERVER)
	public static CommonProxy proxy;
	@Mod.Instance
	public static MoWithers instance;
    private static Logger logger;
    public static final CreativeTabs MO_TAB = new CreativeTabs(MODID)
    {
      public ItemStack getTabIconItem()
      {
     	 return new ItemStack(Items.NETHER_STAR);
      }
    };
    public static final Predicate<Entity> NON_WITHER_OR_NETHER_MOB = new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity p_apply_1_)
        {
            return p_apply_1_ instanceof EntityLivingBase && !MinecraftAdventures.isWitherMob((EntityLivingBase)p_apply_1_) && !MinecraftAdventures.isNetherMob((EntityLivingBase)p_apply_1_) && ((EntityLivingBase)p_apply_1_).attackable();
        }
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        logger = e.getModLog();
		logger.info("Started The Mo' Withers Mod!");
		proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
		proxy.init(e);
    }
    
	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new MEvents());
		logger.info("Finished The Mo' Withers Mod!");
	}
}
