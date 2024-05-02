package net.endermanofdoom.mowithers.items;

import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWitherBonemeal extends Item
{
    public ItemWitherBonemeal()
    {
    	setCreativeTab(MoWithers.MO_TAB);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            if (applyBonemeal(itemstack, worldIn, pos, player, hand))
            {
                if (!worldIn.isRemote)
                {
                    worldIn.playEvent(2005, pos, 0);
                }

                return EnumActionResult.SUCCESS;
            }

            return EnumActionResult.PASS;
        }
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target)
    {
        if (worldIn instanceof net.minecraft.world.WorldServer)
            return applyBonemeal(stack, worldIn, target, net.minecraftforge.common.util.FakePlayerFactory.getMinecraft((net.minecraft.world.WorldServer)worldIn), null);
        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player, @javax.annotation.Nullable EnumHand hand)
    {
        IBlockState iblockstate = worldIn.getBlockState(target);

        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, target, iblockstate, stack, hand);
        if (hook != 0) return hook > 0;

        if (iblockstate.getBlock() instanceof IGrowable)
        {
            IGrowable igrowable = (IGrowable)iblockstate.getBlock();

            if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote))
            {
                if (!worldIn.isRemote)
                {
                    igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
                    spawnBonemealParticles(worldIn, target, 15);
                    stack.shrink(1);
                }

                return true;
            }
        }
        
        if (iblockstate.getBlock() instanceof BlockCactus)
        {
        	BlockCactus igrowable = (BlockCactus)iblockstate.getBlock();
            BlockPos blockpos = target.up();

            if (worldIn.isAirBlock(blockpos))
            {
                int i;

                for (i = 1; worldIn.getBlockState(target.down(i)).getBlock() == igrowable; ++i)
                {
                    ;
                }

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, iblockstate, true))
                {
                    if (!worldIn.isRemote && i < 6)
                    {
                        worldIn.setBlockState(blockpos, igrowable.getDefaultState());
                        worldIn.setBlockState(target, iblockstate, 4);
                        iblockstate.neighborChanged(worldIn, blockpos, igrowable, blockpos);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, blockpos, iblockstate, worldIn.getBlockState(blockpos));
                        spawnBonemealParticles(worldIn, target, 15);
                        stack.shrink(1);
                    }
                    
                    return true;
                }
            }
        }
        
        if (iblockstate.getBlock() instanceof BlockReed)
        {
        	BlockReed igrowable = (BlockReed)iblockstate.getBlock();
            BlockPos blockpos = target.up();

            if (worldIn.isAirBlock(blockpos))
            {
                int i;

                for (i = 1; worldIn.getBlockState(target.down(i)).getBlock() == igrowable; ++i)
                {
                    ;
                }

                if (!worldIn.isRemote && i < 6)
                {
                    worldIn.setBlockState(blockpos, igrowable.getDefaultState());
                    worldIn.setBlockState(target, iblockstate, 4);
                    iblockstate.neighborChanged(worldIn, blockpos, igrowable, blockpos);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, blockpos, iblockstate, worldIn.getBlockState(blockpos));
                    spawnBonemealParticles(worldIn, target, 15);
                    stack.shrink(1);
                }
                
                return true;
            }
        }
        
        if (iblockstate.getBlock() instanceof BlockCocoa)
        {
            int i = ((Integer)iblockstate.getValue(BlockCocoa.AGE)).intValue();

            if (i < 2)
            {
                worldIn.setBlockState(target, iblockstate.withProperty(BlockCocoa.AGE, Integer.valueOf(i + 1)), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, target, iblockstate, worldIn.getBlockState(target));
                spawnBonemealParticles(worldIn, target, 15);
                stack.shrink(1);
                return true;
            }
        }
        
        if (iblockstate.getBlock() instanceof BlockNetherWart)
        {
            int i = ((Integer)iblockstate.getValue(BlockNetherWart.AGE)).intValue();

            if (i < 2)
            {
                worldIn.setBlockState(target, iblockstate.withProperty(BlockNetherWart.AGE, Integer.valueOf(i + 1)), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, target, iblockstate, worldIn.getBlockState(target));
                spawnBonemealParticles(worldIn, target, 15);
                stack.shrink(1);
                return true;
            }
        }
        
        if (iblockstate.getBlock() instanceof BlockSand)
        {
        	BlockSand igrowable = (BlockSand)iblockstate.getBlock();
            BlockPos blockpos = target.up();

            if (worldIn.isAirBlock(blockpos))
            {
                if (!worldIn.isRemote)
                {
                    worldIn.setBlockState(blockpos, Blocks.DEADBUSH.getDefaultState());
                    iblockstate.neighborChanged(worldIn, blockpos, igrowable, blockpos);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, blockpos, iblockstate, worldIn.getBlockState(blockpos));
                    spawnBonemealParticles(worldIn, target, 15);
                    stack.shrink(1);
                }
                
                return true;
            }
        }
        
        if (iblockstate.getBlock() instanceof BlockDirt)
        {
        	BlockDirt igrowable = (BlockDirt)iblockstate.getBlock();

        	if (!worldIn.isRemote && worldIn.getBlockState(target) == igrowable.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT))
        	{
        		worldIn.setBlockState(target, Blocks.GRASS.getDefaultState());
                spawnBonemealParticles(worldIn, target, 15);
        	}
            
            return true;
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount)
    {
        if (amount == 0)
        {
            amount = 15;
        }

        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getMaterial() != Material.AIR)
        {
            for (int i = 0; i < amount; ++i)
            {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + itemRand.nextFloat()), (double)pos.getY() + (double)itemRand.nextFloat() * iblockstate.getBoundingBox(worldIn, pos).maxY, (double)((float)pos.getZ() + itemRand.nextFloat()), d0, d1, d2);
            }
        }
        else
        {
            for (int i1 = 0; i1 < amount; ++i1)
            {
                double d0 = itemRand.nextGaussian() * 0.02D;
                double d1 = itemRand.nextGaussian() * 0.02D;
                double d2 = itemRand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + itemRand.nextFloat()), (double)pos.getY() + (double)itemRand.nextFloat() * 1.0f, (double)((float)pos.getZ() + itemRand.nextFloat()), d0, d1, d2, new int[0]);
            }
        }
    }
}