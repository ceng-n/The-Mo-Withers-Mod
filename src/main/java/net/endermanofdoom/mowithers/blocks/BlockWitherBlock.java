package net.endermanofdoom.mowithers.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockWitherBlock extends Block
{
    public BlockWitherBlock()
    {
        super(Material.IRON);
        setHardness(4.0F);
        setResistance(600.0F);
        setLightLevel(1F);
		setHarvestLevel("pickaxe", 4);
		setSoundType(SoundType.METAL);
    }
}
