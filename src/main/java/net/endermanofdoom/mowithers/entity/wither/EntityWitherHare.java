package net.endermanofdoom.mowithers.entity.wither;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherHare extends EntityHostileWither
{
	public EntityWitherHare(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 80;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 150D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 20D;
	}

	public double getMobSpeed() 
	{
		return 0.95D;
	}
	
    public float getWitherScale()
    {
    	return super.getWitherScale() * 2F;
    }    
    
    public float getBaseWidth()
    {
    	return 0.9F;
    }

    public float getBaseHeight()
    {
    	return this.isCrouching() ? 1.0F : 3.25F;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_HARE[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_HARE[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_HARE[2];
    }
    
    protected double getFlyHeight()
    {
        return 1D;
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.DARK_RED;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.MOB;
    }
}
