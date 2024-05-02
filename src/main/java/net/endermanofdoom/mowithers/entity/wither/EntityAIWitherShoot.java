package net.endermanofdoom.mowithers.entity.wither;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIWitherShoot extends EntityAIBase
{
    /** The entity the AI instance has been applied to */
    private final EntityLiving entityHost;
    /** The entity (as a RangedAttackMob) the AI instance has been applied to. */
    private final IRangedAttackMob rangedAttackEntityHost;
    private EntityLivingBase attackTarget;
    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackTime.
     */
    private int rangedAttackTime;

    public EntityAIWitherShoot(IRangedAttackMob attacker)
    {
        this.rangedAttackTime = -1;

        if (!(attacker instanceof EntityLivingBase))
        {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else
        {
            this.rangedAttackEntityHost = attacker;
            this.entityHost = (EntityLiving)attacker;
            this.setMutexBits(3);
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else
        {
            this.attackTarget = entitylivingbase;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.attackTarget = null;
        this.rangedAttackTime = -1;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);

        this.entityHost.getNavigator().clearPath();
        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, entityHost.getHorizontalFaceSpeed(), entityHost.getVerticalFaceSpeed());

        if (--this.rangedAttackTime == 0 && d0 <= (float)entityHost.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue())
        {
            float f = MathHelper.sqrt(d0) / (float)entityHost.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue();
            float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
            this.rangedAttackTime = (int)entityHost.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue();
        }
        else if (this.rangedAttackTime < 0)
        {
            this.rangedAttackTime = (int)entityHost.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue();
        }
    }
}