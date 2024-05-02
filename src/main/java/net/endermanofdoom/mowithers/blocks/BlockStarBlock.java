package net.endermanofdoom.mowithers.blocks;

import net.endermanofdoom.mowithers.MoWithers;
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
    public BlockStarBlock()
    {
        super(Material.IRON);
        setHardness(9.0F);
        setResistance(6000001.0F);
        setCreativeTab(MoWithers.MO_TAB);
        setLightLevel(1F);
		setHarvestLevel("pickaxe", 3);
		setSoundType(SoundType.METAL);
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
