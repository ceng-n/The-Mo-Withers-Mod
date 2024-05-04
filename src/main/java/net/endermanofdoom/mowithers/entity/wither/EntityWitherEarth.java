package net.endermanofdoom.mowithers.entity.wither;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import javax.annotation.Nullable;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherEarth extends EntityHostileWither
{

	public EntityWitherEarth(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 8;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setType(2);
        skull.setRadius(this.world.getDifficulty().getDifficultyId() * 1.5F);
        skull.setDeflect(true);
        skull.setSkullSize(this.getWitherScale() * 0.325F);
        skull.setPlacedBlockState(invul ? Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT) : Blocks.DIRT.getDefaultState());
        skull.setSkullTexture("wither/element/wither_earth");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.GREEN;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.BLOCK_SOFT;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 4D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 2D;
	}

	public double getMobSpeed() 
	{
		return 0.4D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_EARTH[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_EARTH[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_EARTH[2];
    }
    
    public float getWitherScale()
    {
    	return super.getWitherScale() * 1.5F;
    }
    
    public void ignite()
    {
        this.setInvulTime(660);
        this.setHealth(this.getMaxHealth() / 3.0F);
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.DIRT.getDefaultState()));
        }

        for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.DIRT.getDefaultState()));
        }
    }
    
    public void onHealUpdate()
    {
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        Biome biome = this.world.getBiome(blockpos);
        
        if (biome.getTemperature(blockpos) > 0.4F && !this.isWet())
        	super.onHealUpdate();
        
        if (this.onGround)
        	super.onHealUpdate();
    }
	
    public boolean canDestroyBlock(BlockPos blockIn)
    {
        return super.canDestroyBlock(blockIn) && world.getBlockState(blockIn).getBlock() != Blocks.GRASS && world.getBlockState(blockIn).getBlock() != Blocks.DIRT && world.getBlockState(blockIn).getBlock() != Blocks.MYCELIUM && world.getBlockState(blockIn).getBlock() != Blocks.GRASS_PATH && world.getBlockState(blockIn).getBlock() != Blocks.GRAVEL && world.getBlockState(blockIn).getBlock() != Blocks.NETHERRACK && world.getBlockState(blockIn).getBlock() != Blocks.STONE && world.getBlockState(blockIn).getBlock() != Blocks.DIRT && world.getBlockState(blockIn).getBlock() != Blocks.COBBLESTONE && world.getBlockState(blockIn).getBlock() != Blocks.LEAVES;
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.blockBreakCounter <= 0 && getAttackTarget() != null && !this.isArmored())
            this.blockBreakCounter = 1;
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source == DamageSource.IN_WALL)
        {
          if (this.blockBreakCounter <= 0) {
            this.blockBreakCounter = 1;
          }
          return false;
        }
        else
        	return super.attackEntityFrom(source, amount);
    }
    
    protected int minDropNum(int lootingModifier)
    {
    	return 32 + lootingModifier;
    }
    
    protected int maxDropNum(int lootingModifier)
    {
    	return 64 + lootingModifier;
    }
    
    @Nullable
    protected Item getDropItem()
    {
    	switch (rand.nextInt(5))
    	{
    	case 1:
            return Items.POTATO;
    	case 2:
            return Items.CARROT;
    	case 3:
            return Items.WHEAT_SEEDS;
    	case 4:
            return Items.BEETROOT_SEEDS;
    	default:
            return Item.getItemFromBlock(Blocks.DIRT);
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return isWet() ? 5000000 : 15728880;
    }
    
	public int[] getBarColor() 
	{
		return new int[] {255, 255, 255, 0, this.isArmored() || this.isSuperBoss() ? 122 : 0, this.isArmored() || this.isSuperBoss() ? 255 : 0};
	}
}
