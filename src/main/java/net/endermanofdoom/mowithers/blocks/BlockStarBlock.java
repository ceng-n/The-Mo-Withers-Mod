package net.endermanofdoom.mowithers.blocks;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStarBlock extends Block
{
    public BlockStarBlock(MBlocks registry)
    {
        super(Material.IRON);
        setHardness(9.0F);
        setResistance(6000001.0F);
        setLightLevel(1F);
		setHarvestLevel("pickaxe", 3);
		setSoundType(SoundType.METAL);
		registry.addBlock("nether_star_block", this, MoWithers.MO_TAB);
    }

    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return 15728880;
    }

    public boolean canEntitySpawn(IBlockState state, Entity entityIn)
    {
        return !entityIn.isNonBoss();
    }
}
