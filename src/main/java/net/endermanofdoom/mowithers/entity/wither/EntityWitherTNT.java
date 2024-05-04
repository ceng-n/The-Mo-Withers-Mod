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
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherTNT extends EntityHostileWither
{

	public EntityWitherTNT(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 120;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
	
    public boolean canDestroyBlock(BlockPos blockIn)
    {
        return super.canDestroyBlock(blockIn) && world.getBlockState(blockIn).getBlock() != Blocks.TNT;
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(6F);
        skull.setType(21);
        skull.setSkullTexture("wither/block/wither_tnt");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12.0D);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.WHITE;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.BLOCK_SOFT;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 60D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 30D;
	}

	public double getMobSpeed() 
	{
		return 0.6D;
	}
	
    public float getWitherScale()
    {
    	return super.getWitherScale() * 2F;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_TNT[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_TNT[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_TNT[2];
    }
    
    protected double getFlyHeight()
    {
        return 12D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.TNT.getDefaultState()));
        }

        for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + this.getEyeHeight(), this.posZ, 0.0D, 0.0D, 0.0D);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.TNT.getDefaultState()));
        }
        
        if (this.ticksExisted % 2 == 0 && !this.world.isRemote && this.getRamTime() < -60)
        {
            for (int l = 0; l < 3; ++l)
            {
                double d1 = this.getHeadX(l);
                double d2 = this.getHeadY(l);
                double d3 = this.getHeadZ(l);
                EntityTNTPrimed tnt = new EntityTNTPrimed(world, d1, d2, d3, this);
                ++tnt.motionY;
                tnt.setFuse(60);
                this.world.spawnEntity(tnt);
            }
        }
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return Item.getItemFromBlock(Blocks.TNT);
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.25F;
    }
}
