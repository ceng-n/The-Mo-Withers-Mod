package net.endermanofdoom.mowithers.entity.wither;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIWitherTargeting<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
{
    public EntityAIWitherTargeting(EntityCreature entityIn, Class<T> classTarget, Predicate <? super T > targetSelector)
    {
        super(entityIn, classTarget, 0, false, false, targetSelector);
    }

	protected AxisAlignedBB getTargetableArea(double targetDistance)
    {
        return this.taskOwner.getEntityBoundingBox().grow(targetDistance);
    }
}