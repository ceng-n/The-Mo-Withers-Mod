package net.endermanofdoom.mowithers.entity.wither;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;

public class EntityWitherPattyDay extends EntityHostileWither
{

	public EntityWitherPattyDay(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 600;
		this.bossInfo.setColor(BossInfo.Color.GREEN);
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new EntityAIWitherTargeting<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(2F);
        skull.setSkullTexture("wither/holiday/wither_patty");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.GREEN;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 600D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 90D;
	}

	public double getMobSpeed() 
	{
		return 0.5D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_PATTY[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_PATTY[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_PATTY[2];
    }
    
    protected double getFlyHeight()
    {
        return 8D;
    }

    public int getHeadCount()
    {
        return 4;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < getHeadCount(); ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
        }
    }
    
    protected double getHeadX(int p_82214_1_)
    {
        return p_82214_1_ >= 3 ? this.posX : super.getHeadX(p_82214_1_);
    }

    protected double getHeadY(int p_82208_1_)
    {
        return p_82208_1_ >= 3 ? this.posY + (this.getEyeHeight() + (this.getWitherScale() * 1.5F)) : super.getHeadY(p_82208_1_);
    }

    protected double getHeadZ(int p_82213_1_)
    {
        return p_82213_1_ >= 3 ? this.posZ : super.getHeadZ(p_82213_1_);
    }
}
