package net.endermanofdoom.mowithers.entity.wither;

import javax.annotation.Nullable;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherCoal extends EntityHostileWither
{

	public EntityWitherCoal(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 9;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new EntityAIWitherTargeting<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(1F);
        skull.setType(22);
        skull.setPlacedBlockState(Blocks.COAL_BLOCK.getDefaultState());
        skull.setSkullTexture("wither/block/wither_coal");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12.0D);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.DARK_GRAY;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.BLOCK_ROCK;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 12D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 3D;
	}

	public double getMobSpeed() 
	{
		return 0.4D;
	}
	
    public float getWitherScale()
    {
    	return super.getWitherScale() * 1.75F;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_COAL[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_COAL[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_COAL[2];
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
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.COAL_BLOCK.getDefaultState()));
        }

        for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.COAL_BLOCK.getDefaultState()));
        }
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextInt(9) != 1 ? Items.COAL : Item.getItemFromBlock(Blocks.COAL_BLOCK);
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.1F;
    }
}
