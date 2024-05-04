package net.endermanofdoom.mowithers.entity.wither;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import javax.annotation.Nullable;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherGravel extends EntityHostileWither
{

	public EntityWitherGravel(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 6;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
	
    public boolean canDestroyBlock(BlockPos blockIn)
    {
        return super.canDestroyBlock(blockIn) && world.getBlockState(blockIn).getBlock() != Blocks.GRAVEL;
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(2F);
        skull.setType(19);
        skull.setPlacedBlockState(Blocks.GRAVEL.getDefaultState());
        skull.setSkullTexture("wither/block/wither_gravel");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.GRAY;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.BLOCK_SOFT;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 5D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 2.5D;
	}

	public double getMobSpeed() 
	{
		return 0.45D;
	}
	
    public float getWitherScale()
    {
    	return super.getWitherScale() * 1.5F;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_GRAVEL[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_GRAVEL[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_GRAVEL[2];
    }
    
    protected double getFlyHeight()
    {
        return 8D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.GRAVEL.getDefaultState()));
        }

        for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.GRAVEL.getDefaultState()));
        }
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Items.FLINT : Item.getItemFromBlock(Blocks.GRAVEL);
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.25F;
    }
}
