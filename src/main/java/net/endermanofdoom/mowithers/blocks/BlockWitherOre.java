package net.endermanofdoom.mowithers.blocks;

import java.util.Random;

import net.endermanofdoom.mowithers.registry.MItems;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockWitherOre extends BlockOre
{
    public BlockWitherOre(MapColor color)
    {
        super(color);
        setHardness(3.0F);
        setResistance(100.0F);
		setHarvestLevel("pickaxe", 3);
    }
    
    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return MItems.ATROPHIC_SHARD;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 1 + random.nextInt(2);
    }
    
    @Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
        {
            return MathHelper.getInt(rand, 5, 10);
        }
        return 0;
    }
}
