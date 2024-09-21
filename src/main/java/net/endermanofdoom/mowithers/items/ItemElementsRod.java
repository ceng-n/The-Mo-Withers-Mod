package net.endermanofdoom.mowithers.items;

import net.endermanofdoom.mca.MCA;
import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import net.endermanofdoom.mca.world.MCAWorldData;
import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class ItemElementsRod extends ItemSimpleFoiled
{
	public ItemElementsRod()
	{
		this.setMaxStackSize(1);
		setCreativeTab(MoWithers.MO_TAB);
	}

    /**
     * Return an item rarity from EnumRarity
     */
    @Deprecated // use Forge version
    public EnumRarity getRarity(ItemStack stack)
    {
        return MCA.LEGENDARY;
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
        if (target instanceof EntityBaseWither)
        {
        	EntityBaseWither wither = (EntityBaseWither)target;

            if (!wither.isRaidBoss() && (!wither.isSuperBoss() || playerIn.capabilities.isCreativeMode || MCAWorldData.progress.getBoolean("defeatedMoWithersSuperboss")))
            {
                wither.setRaidBoss(true);
                wither.setInvulTime(2200);
                wither.world.playBroadcastSound(1023, new BlockPos(wither), 0);
                wither.world.playBroadcastSound(1038, new BlockPos(wither), 0);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
