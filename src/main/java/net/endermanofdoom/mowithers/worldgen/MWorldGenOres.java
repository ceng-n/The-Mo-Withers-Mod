package net.endermanofdoom.mowithers.worldgen;

import java.util.Random;
import net.endermanofdoom.mowithers.registry.MBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class MWorldGenOres implements IWorldGenerator 
{
	@Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
    {
        if (world.provider.getDimension() == 1) 
        {
        	generateEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if (world.provider.getDimension() == -1) 
        {
        	generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if (world.provider.getDimension() == 0)
        {
            generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }
    
    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    	generateOre(MBlocks.ATROPHIC_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 6, 12, random.nextInt(2) + 1, 1, BlockMatcher.forBlock(Blocks.STONE));
    }
    
    private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    	generateOre(MBlocks.ATROPHIC_ORE_NETHER.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 4, 16, random.nextInt(2) + 1, 1, BlockMatcher.forBlock(Blocks.NETHERRACK));
    }
    
    private void generateEnd(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {

    }
    
    private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances, BlockMatcher blockMatcher) 
    {
        int deltaY = maxY - minY;
        
        for (int i = 0; i < chances; i++) 
        {
            BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
            
            WorldGenMinable generator = new WorldGenMinable(ore, size, blockMatcher);
            generator.generate(world, random, pos);
        }
    }
}