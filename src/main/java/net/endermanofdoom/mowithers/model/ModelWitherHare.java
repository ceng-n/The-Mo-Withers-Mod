package net.endermanofdoom.mowithers.model;

import net.endermanofdoom.mowithers.entity.wither.EntityWitherHare;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelWitherHare extends ModelBase 
{
	private final ModelRenderer body;
	private final ModelRenderer mainbody;
	private final ModelRenderer tail;
	private final ModelRenderer tailpart2;
	private final ModelRenderer tailpart3;
	private final ModelRenderer tailend;
	private final ModelRenderer leg1;
	private final ModelRenderer leg1joint;
	private final ModelRenderer leg2;
	private final ModelRenderer leg2joint;
	private final ModelRenderer neck1;
	private final ModelRenderer head1;
	private final ModelRenderer ear1;
	private final ModelRenderer ear2;
	private final ModelRenderer jaw1;
	private final ModelRenderer neck2;
	private final ModelRenderer head2;
	private final ModelRenderer ear3;
	private final ModelRenderer ear4;
	private final ModelRenderer jaw2;
	private final ModelRenderer neck3;
	private final ModelRenderer head3;
	private final ModelRenderer ear5;
	private final ModelRenderer ear6;
	private final ModelRenderer jaw3;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;
	private final ModelRenderer cube_r11;
	private final ModelRenderer cube_r12;
	private final ModelRenderer cube_r13;
	private final ModelRenderer cube_r14;
	private final ModelRenderer cube_r15;
	private final ModelRenderer cube_r16;
	private final ModelRenderer cube_r17;
	private final ModelRenderer cube_r18;
	private final ModelRenderer cube_r19;
	private final ModelRenderer cube_r20;
	private final ModelRenderer cube_r21;
	private final ModelRenderer cube_r22;
	private final ModelRenderer cube_r23;
	private final ModelRenderer cube_r24;
	private final ModelRenderer cube_r27;
	private final ModelRenderer cube_r28;
	private final ModelRenderer cube_r29;
	private final ModelRenderer cube_r30;
	private final ModelRenderer cube_r31;
	private final ModelRenderer cube_r32;
	private final ModelRenderer cube_r33;
	private final ModelRenderer cube_r36;
	private final ModelRenderer cube_r37;
	private final ModelRenderer cube_r38;
	private final ModelRenderer cube_r39;
	private final ModelRenderer cube_r40;
	private final ModelRenderer cube_r41;
	private final ModelRenderer cube_r42;
	private final ModelRenderer cube_r45;
	private final ModelRenderer cube_r46;
	private final ModelRenderer cube_r47;

	public ModelWitherHare(float p_i46302_1_) {
		textureWidth = 256;
		textureHeight = 256;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -31.0F, 4.0F);
		body.cubeList.add(new ModelBox(body, 92, 37, -12.0F, 12.0F, -5.0F, 24, 7, 10, p_i46302_1_, false));
		body.cubeList.add(new ModelBox(body, 0, 32, -16.0F, -4.0F, -7.0F, 32, 16, 14, p_i46302_1_, false));
		body.cubeList.add(new ModelBox(body, 0, 0, -17.0F, -11.0F, -8.0F, 34, 15, 16, p_i46302_1_, false));
		body.cubeList.add(new ModelBox(body, 28, 121, -23.0F, -9.0F, -9.0F, 7, 16, 13, p_i46302_1_, false));

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 38.0F, -4.0F);
		body.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 3.1416F, 0);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 28, 121, -23.0F, -47.0F, -9.0F, 7, 16, 13, p_i46302_1_, false));

		mainbody = new ModelRenderer(this);
		mainbody.setRotationPoint(0.0F, 18.0F, 1.0F);
		body.addChild(mainbody);
		

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -1.0F, 2.0F);
		mainbody.addChild(tail);
		setRotationAngle(tail, 0.6981F, 0.0F, 0);
		tail.cubeList.add(new ModelBox(tail, 152, 106, -2.0F, -2.0F, -1.0F, 4, 13, 4, p_i46302_1_ + 1.0F, false));

		tailpart2 = new ModelRenderer(this);
		tailpart2.setRotationPoint(0.0F, 11.0F, 1.0F);
		tail.addChild(tailpart2);
		setRotationAngle(tailpart2, 0.6981F, 0.0F, 0);
		tailpart2.cubeList.add(new ModelBox(tailpart2, 152, 104, -2.0F, -0.1623F, -1.9409F, 4, 17, 4, p_i46302_1_ + 1.0F, false));

		tailpart3 = new ModelRenderer(this);
		tailpart3.setRotationPoint(0.0F, 16.0F, 0);
		tailpart2.addChild(tailpart3);
		setRotationAngle(tailpart3, 0.6981F, 0.0F, 0);
		tailpart3.cubeList.add(new ModelBox(tailpart3, 151, 102, -2.0F, 0.5174F, -2.4341F, 4, 23, 4, p_i46302_1_ + 1.0F, false));

		tailend = new ModelRenderer(this);
		tailend.setRotationPoint(0.0F, 19.0F, 1.0F);
		tailpart3.addChild(tailend);
		setRotationAngle(tailend, 0.6981F, 0.0F, 0);
		

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(2.0F, -12.6175F, 3.6201F);
		tailend.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.3442F, -0.0594F, 0.7314F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 51, 13.0F, 14.2163F, -15.1633F, 0, 11, 11, p_i46302_1_, false));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 37, 40, 10.0F, 7.2163F, -25.1633F, 0, 5, 27, p_i46302_1_, false));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 37, 36, 10.0F, 13.2163F, -24.1633F, 0, 5, 27, p_i46302_1_, false));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 26, 62, 1.0F, 11.2163F, -15.1633F, 22, 0, 11, p_i46302_1_, false));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 26, 62, 1.0F, 15.2163F, -15.1633F, 22, 0, 11, p_i46302_1_, false));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 51, 10.0F, 15.2163F, -15.1633F, 0, 11, 11, p_i46302_1_, false));
		cube_r2.cubeList.add(new ModelBox(cube_r2, 128, 79, 6.0F, 7.2163F, -15.1633F, 11, 11, 11, p_i46302_1_, false));

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(2.0F, -12.6175F, 3.6201F);
		tailend.addChild(cube_r3);
		setRotationAngle(cube_r3, -2.7974F, -0.0594F, 0.7314F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 0, 51, 13.0F, -7.7837F, 3.8367F, 0, 11, 11, p_i46302_1_, false));

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(2.0F, -12.6175F, 3.6201F);
		tailend.addChild(cube_r4);
		setRotationAngle(cube_r4, -2.7974F, -0.0594F, 0.7314F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 0, 51, 10.0F, -9.7837F, 3.8367F, 0, 11, 11, p_i46302_1_, false));

		leg1 = new ModelRenderer(this);
		leg1.setRotationPoint(-13.0F, 28.0F, 0);
		body.addChild(leg1);
		setRotationAngle(leg1, -1.2654F, 0.0F, 0);
		

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(13.0F, -28.0F, 0);
		leg1.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.5236F, 0.0F, 0);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 147, 145, -16.0F, 21.0F, 11.0F, 7, 6, 7, p_i46302_1_, false));
		cube_r5.cubeList.add(new ModelBox(cube_r5, 81, 98, -16.0F, 40.0F, 11.0F, 7, 6, 7, p_i46302_1_, false));
		cube_r5.cubeList.add(new ModelBox(cube_r5, 150, 101, -15.0F, 23.0F, 12.0F, 5, 23, 5, p_i46302_1_, false));

		leg1joint = new ModelRenderer(this);
		leg1joint.setRotationPoint(0.0F, 19.0F, -10.0F);
		leg1.addChild(leg1joint);
		setRotationAngle(leg1joint, 2.4435F, 0.0F, 0);
		

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(17.3781F, -5.2085F, 5.3018F);
		leg1joint.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.3873F, -0.0665F, 0.1615F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 98, 142, -16.0F, -2.0F, -38.0F, 7, 28, 8, p_i46302_1_, false));

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(9.4694F, -8.7412F, 3.8384F);
		leg1joint.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.5206F, 0.05F, -0.121F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 101, 144, -15.0F, -15.0F, -36.0F, 5, 10, 6, p_i46302_1_, false));

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(9.4694F, -8.7412F, 3.8384F);
		leg1joint.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.3897F, 0.05F, -0.121F);
		cube_r8.cubeList.add(new ModelBox(cube_r8, 98, 141, -16.0F, -2.0F, -38.0F, 7, 28, 8, p_i46302_1_, false));

		cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(13.0F, -7.0173F, 4.5525F);
		leg1joint.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.5236F, 0.0F, 0);
		cube_r9.cubeList.add(new ModelBox(cube_r9, 100, 144, -14.0F, -16.0F, -36.0F, 5, 10, 6, p_i46302_1_, false));

		cube_r10 = new ModelRenderer(this);
		cube_r10.setRotationPoint(13.0F, -7.0173F, 4.5525F);
		leg1joint.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.5133F, -0.1084F, 0.1897F);
		cube_r10.cubeList.add(new ModelBox(cube_r10, 100, 144, -11.0F, -14.0F, -36.0F, 5, 10, 6, p_i46302_1_, false));

		cube_r11 = new ModelRenderer(this);
		cube_r11.setRotationPoint(13.0F, -7.0173F, 4.5525F);
		leg1joint.addChild(cube_r11);
		setRotationAngle(cube_r11, 0.3927F, 0.0F, 0);
		cube_r11.cubeList.add(new ModelBox(cube_r11, 98, 142, -16.0F, -2.0F, -38.0F, 7, 28, 8, p_i46302_1_, false));

		cube_r12 = new ModelRenderer(this);
		cube_r12.setRotationPoint(13.0F, -7.0173F, 4.5525F);
		leg1joint.addChild(cube_r12);
		setRotationAngle(cube_r12, -0.5236F, 0.0F, 0);
		cube_r12.cubeList.add(new ModelBox(cube_r12, 99, 142, -16.0F, 6.0F, -5.0F, 7, 34, 7, p_i46302_1_, false));

		leg2 = new ModelRenderer(this);
		leg2.setRotationPoint(13.0F, 28.0F, 0);
		body.addChild(leg2);
		setRotationAngle(leg2, -1.2654F, 0.0F, 0);
		

		cube_r13 = new ModelRenderer(this);
		cube_r13.setRotationPoint(13.0F, -28.0F, 0);
		leg2.addChild(cube_r13);
		setRotationAngle(cube_r13, -0.5236F, 0.0F, 0);
		cube_r13.cubeList.add(new ModelBox(cube_r13, 147, 145, -16.0F, 21.0F, 11.0F, 7, 6, 7, p_i46302_1_, false));
		cube_r13.cubeList.add(new ModelBox(cube_r13, 81, 98, -16.0F, 40.0F, 11.0F, 7, 6, 7, p_i46302_1_, false));
		cube_r13.cubeList.add(new ModelBox(cube_r13, 150, 101, -15.0F, 23.0F, 12.0F, 5, 23, 5, p_i46302_1_, false));

		leg2joint = new ModelRenderer(this);
		leg2joint.setRotationPoint(0.0F, 19.0F, -10.0F);
		leg2.addChild(leg2joint);
		setRotationAngle(leg2joint, 2.4435F, 0.0F, 0);
		

		cube_r14 = new ModelRenderer(this);
		cube_r14.setRotationPoint(17.3781F, -5.2085F, 5.3018F);
		leg2joint.addChild(cube_r14);
		setRotationAngle(cube_r14, 0.3873F, -0.0665F, 0.1615F);
		cube_r14.cubeList.add(new ModelBox(cube_r14, 98, 142, -16.0F, -2.0F, -38.0F, 7, 28, 8, p_i46302_1_, false));

		cube_r15 = new ModelRenderer(this);
		cube_r15.setRotationPoint(9.4694F, -8.7412F, 3.8384F);
		leg2joint.addChild(cube_r15);
		setRotationAngle(cube_r15, 0.5206F, 0.05F, -0.121F);
		cube_r15.cubeList.add(new ModelBox(cube_r15, 101, 144, -15.0F, -15.0F, -36.0F, 5, 10, 6, p_i46302_1_, false));

		cube_r16 = new ModelRenderer(this);
		cube_r16.setRotationPoint(9.4694F, -8.7412F, 3.8384F);
		leg2joint.addChild(cube_r16);
		setRotationAngle(cube_r16, 0.3897F, 0.05F, -0.121F);
		cube_r16.cubeList.add(new ModelBox(cube_r16, 98, 141, -16.0F, -2.0F, -38.0F, 7, 28, 8, p_i46302_1_, false));

		cube_r17 = new ModelRenderer(this);
		cube_r17.setRotationPoint(13.0F, -7.0173F, 4.5525F);
		leg2joint.addChild(cube_r17);
		setRotationAngle(cube_r17, 0.5236F, 0.0F, 0);
		cube_r17.cubeList.add(new ModelBox(cube_r17, 100, 144, -14.0F, -16.0F, -36.0F, 5, 10, 6, p_i46302_1_, false));

		cube_r18 = new ModelRenderer(this);
		cube_r18.setRotationPoint(13.0F, -7.0173F, 4.5525F);
		leg2joint.addChild(cube_r18);
		setRotationAngle(cube_r18, 0.5133F, -0.1084F, 0.1897F);
		cube_r18.cubeList.add(new ModelBox(cube_r18, 100, 144, -11.0F, -14.0F, -36.0F, 5, 10, 6, p_i46302_1_, false));

		cube_r19 = new ModelRenderer(this);
		cube_r19.setRotationPoint(13.0F, -7.0173F, 4.5525F);
		leg2joint.addChild(cube_r19);
		setRotationAngle(cube_r19, 0.3927F, 0.0F, 0);
		cube_r19.cubeList.add(new ModelBox(cube_r19, 98, 142, -16.0F, -2.0F, -38.0F, 7, 28, 8, p_i46302_1_, false));

		cube_r20 = new ModelRenderer(this);
		cube_r20.setRotationPoint(13.0F, -7.0173F, 4.5525F);
		leg2joint.addChild(cube_r20);
		setRotationAngle(cube_r20, -0.5236F, 0.0F, 0);
		cube_r20.cubeList.add(new ModelBox(cube_r20, 99, 142, -16.0F, 6.0F, -5.0F, 7, 34, 7, p_i46302_1_, false));

		neck1 = new ModelRenderer(this);
		neck1.setRotationPoint(0.0F, -11.0F, 0);
		body.addChild(neck1);
		neck1.cubeList.add(new ModelBox(neck1, 19, 6, -5.0F, -11.0F, -8.0F, 10, 12, 13, p_i46302_1_, false));
		neck1.cubeList.add(new ModelBox(neck1, 131, 54, -2.0F, -17.0F, -8.0F, 4, 6, 13, p_i46302_1_, false));

		cube_r21 = new ModelRenderer(this);
		cube_r21.setRotationPoint(-8.8339F, 10.1003F, 0);
		neck1.addChild(cube_r21);
		setRotationAngle(cube_r21, 0.0F, 0.0F, 0.2618F);
		cube_r21.cubeList.add(new ModelBox(cube_r21, 35, 0, -2.0F, -28.0F, -8.0F, 3, 20, 13, p_i46302_1_, false));

		cube_r22 = new ModelRenderer(this);
		cube_r22.setRotationPoint(8.8339F, 10.1003F, 0);
		neck1.addChild(cube_r22);
		setRotationAngle(cube_r22, 0.0F, 0.0F, -0.2618F);
		cube_r22.cubeList.add(new ModelBox(cube_r22, 39, 0, -1.0F, -28.0F, -8.0F, 3, 20, 13, p_i46302_1_, false));

		head1 = new ModelRenderer(this);
		head1.setRotationPoint(0.0F, -20.0F, 0);
		neck1.addChild(head1);
		setRotationAngle(head1, 0.1745F, 0.0F, 0);
		head1.cubeList.add(new ModelBox(head1, 0, 235, -6.0F, -6.0F, -8.0F, 13, 9, 12, p_i46302_1_, false));

		cube_r23 = new ModelRenderer(this);
		cube_r23.setRotationPoint(-6.1573F, 2.0F, 4.7692F);
		head1.addChild(cube_r23);
		setRotationAngle(cube_r23, 0.0F, -0.2618F, 0);
		cube_r23.cubeList.add(new ModelBox(cube_r23, 32, 94, -3.0F, -4.0F, -23.0F, 6, 6, 22, p_i46302_1_, false));

		cube_r24 = new ModelRenderer(this);
		cube_r24.setRotationPoint(10.9869F, 2.0F, 3.4751F);
		head1.addChild(cube_r24);
		setRotationAngle(cube_r24, 0.0F, 0.2618F, 0);
		cube_r24.cubeList.add(new ModelBox(cube_r24, 87, 189, -8.0F, -4.0F, -23.0F, 7, 6, 22, p_i46302_1_, false));

		ear1 = new ModelRenderer(this);
		ear1.setRotationPoint(0.0F, 2.0F, 0);
		head1.addChild(ear1);
		setRotationAngle(ear1, 0.3927F, 0.2182F, 0);
		ear1.cubeList.add(new ModelBox(ear1, 170, 197, 6.0F, -13.0F, 4.0F, 0, 14, 43, p_i46302_1_, false));

		ear2 = new ModelRenderer(this);
		ear2.setRotationPoint(0.0F, 2.0F, 0);
		head1.addChild(ear2);
		setRotationAngle(ear2, 0.48F, -0.2182F, 0);
		ear2.cubeList.add(new ModelBox(ear2, 170, 197, -5.0F, -13.0F, 4.0F, 0, 14, 43, p_i46302_1_, false));

		cube_r27 = new ModelRenderer(this);
		cube_r27.setRotationPoint(0.0F, 2.0F, 0);
		head1.addChild(cube_r27);
		setRotationAngle(cube_r27, 0.3054F, 0.0F, 0);
		cube_r27.cubeList.add(new ModelBox(cube_r27, 58, 231, -3.0F, -10.0F, -18.0F, 7, 6, 19, p_i46302_1_, false));

		jaw1 = new ModelRenderer(this);
		jaw1.setRotationPoint(0.0F, 5.0F, 0);
		head1.addChild(jaw1);
		jaw1.cubeList.add(new ModelBox(jaw1, 0, 62, -3.0F, -3.0F, -19.0F, 7, 5, 23, p_i46302_1_, false));

		cube_r28 = new ModelRenderer(this);
		cube_r28.setRotationPoint(-6.1573F, 2.0F, 4.7692F);
		jaw1.addChild(cube_r28);
		setRotationAngle(cube_r28, 0.0F, -0.2618F, 0);
		cube_r28.cubeList.add(new ModelBox(cube_r28, 92, 89, -4.0F, -5.0F, -23.0F, 7, 5, 22, p_i46302_1_, false));

		cube_r29 = new ModelRenderer(this);
		cube_r29.setRotationPoint(10.9869F, 2.0F, 3.4751F);
		jaw1.addChild(cube_r29);
		setRotationAngle(cube_r29, 0.0F, 0.2618F, 0);
		cube_r29.cubeList.add(new ModelBox(cube_r29, 60, 62, -8.0F, -5.0F, -23.0F, 8, 5, 22, p_i46302_1_, false));

		neck2 = new ModelRenderer(this);
		neck2.setRotationPoint(15.0F, -8.0F, 0);
		body.addChild(neck2);
		setRotationAngle(neck2, 0.0F, 0.0F, 0.5236F);
		neck2.cubeList.add(new ModelBox(neck2, 19, 6, -5.0F, -11.0F, -8.0F, 10, 12, 13, p_i46302_1_, false));
		neck2.cubeList.add(new ModelBox(neck2, 131, 54, -2.0F, -17.0F, -8.0F, 4, 6, 13, p_i46302_1_, false));

		cube_r30 = new ModelRenderer(this);
		cube_r30.setRotationPoint(-8.8339F, 10.1003F, 0);
		neck2.addChild(cube_r30);
		setRotationAngle(cube_r30, 0.0F, 0.0F, 0.2618F);
		cube_r30.cubeList.add(new ModelBox(cube_r30, 35, 0, -2.0F, -28.0F, -8.0F, 3, 20, 13, p_i46302_1_, false));

		cube_r31 = new ModelRenderer(this);
		cube_r31.setRotationPoint(8.8339F, 10.1003F, 0);
		neck2.addChild(cube_r31);
		setRotationAngle(cube_r31, 0.0F, 0.0F, -0.2618F);
		cube_r31.cubeList.add(new ModelBox(cube_r31, 39, 0, -1.0F, -28.0F, -8.0F, 3, 20, 13, p_i46302_1_, false));

		head2 = new ModelRenderer(this);
		head2.setRotationPoint(0.0F, -20.0F, 0);
		neck2.addChild(head2);
		setRotationAngle(head2, 0.1745F, 0.0F, 0);
		head2.cubeList.add(new ModelBox(head2, 0, 235, -6.0F, -6.0F, -8.0F, 13, 9, 12, p_i46302_1_, false));

		cube_r32 = new ModelRenderer(this);
		cube_r32.setRotationPoint(-6.1573F, 2.0F, 4.7692F);
		head2.addChild(cube_r32);
		setRotationAngle(cube_r32, 0.0F, -0.2618F, 0);
		cube_r32.cubeList.add(new ModelBox(cube_r32, 32, 94, -3.0F, -4.0F, -23.0F, 6, 6, 22, p_i46302_1_, false));

		cube_r33 = new ModelRenderer(this);
		cube_r33.setRotationPoint(10.9869F, 2.0F, 3.4751F);
		head2.addChild(cube_r33);
		setRotationAngle(cube_r33, 0.0F, 0.2618F, 0);
		cube_r33.cubeList.add(new ModelBox(cube_r33, 87, 189, -8.0F, -4.0F, -23.0F, 7, 6, 22, p_i46302_1_, false));

		ear3 = new ModelRenderer(this);
		ear3.setRotationPoint(0.0F, 2.0F, 0);
		head2.addChild(ear3);
		setRotationAngle(ear3, 0.3927F, 0.2182F, 0);
		ear3.cubeList.add(new ModelBox(ear3, 170, 197, 6.0F, -14.0F, 4.0F, 0, 14, 43, p_i46302_1_, false));

		ear4 = new ModelRenderer(this);
		ear4.setRotationPoint(0.0F, 2.0F, 0);
		head2.addChild(ear4);
		setRotationAngle(ear4, 0.48F, -0.2182F, 0);
		ear4.cubeList.add(new ModelBox(ear4, 170, 197, -5.0F, -13.0F, 4.0F, 0, 14, 43, p_i46302_1_, false));

		cube_r36 = new ModelRenderer(this);
		cube_r36.setRotationPoint(0.0F, 2.0F, 0);
		head2.addChild(cube_r36);
		setRotationAngle(cube_r36, 0.3054F, 0.0F, 0);
		cube_r36.cubeList.add(new ModelBox(cube_r36, 58, 231, -3.0F, -9.0F, -18.0F, 7, 6, 19, p_i46302_1_, false));

		jaw2 = new ModelRenderer(this);
		jaw2.setRotationPoint(0.0F, 5.0F, 0);
		head2.addChild(jaw2);
		jaw2.cubeList.add(new ModelBox(jaw2, 0, 62, -3.0F, -3.0F, -19.0F, 7, 5, 23, p_i46302_1_, false));

		cube_r37 = new ModelRenderer(this);
		cube_r37.setRotationPoint(-6.1573F, 2.0F, 4.7692F);
		jaw2.addChild(cube_r37);
		setRotationAngle(cube_r37, 0.0F, -0.2618F, 0);
		cube_r37.cubeList.add(new ModelBox(cube_r37, 92, 89, -4.0F, -5.0F, -23.0F, 7, 5, 22, p_i46302_1_, false));

		cube_r38 = new ModelRenderer(this);
		cube_r38.setRotationPoint(10.9869F, 2.0F, 3.4751F);
		jaw2.addChild(cube_r38);
		setRotationAngle(cube_r38, 0.0F, 0.2618F, 0);
		cube_r38.cubeList.add(new ModelBox(cube_r38, 60, 62, -8.0F, -5.0F, -23.0F, 8, 5, 22, p_i46302_1_, false));

		neck3 = new ModelRenderer(this);
		neck3.setRotationPoint(-15.0F, -8.0F, 0);
		body.addChild(neck3);
		setRotationAngle(neck3, 0.0F, 0.0F, -0.4363F);
		neck3.cubeList.add(new ModelBox(neck3, 19, 6, -5.0F, -11.0F, -8.0F, 10, 12, 13, p_i46302_1_, false));
		neck3.cubeList.add(new ModelBox(neck3, 131, 54, -2.0F, -17.0F, -8.0F, 4, 6, 13, p_i46302_1_, false));

		cube_r39 = new ModelRenderer(this);
		cube_r39.setRotationPoint(-8.8339F, 10.1003F, 0);
		neck3.addChild(cube_r39);
		setRotationAngle(cube_r39, 0.0F, 0.0F, 0.2618F);
		cube_r39.cubeList.add(new ModelBox(cube_r39, 35, 0, -2.0F, -28.0F, -8.0F, 3, 20, 13, p_i46302_1_, false));

		cube_r40 = new ModelRenderer(this);
		cube_r40.setRotationPoint(8.8339F, 10.1003F, 0);
		neck3.addChild(cube_r40);
		setRotationAngle(cube_r40, 0.0F, 0.0F, -0.2618F);
		cube_r40.cubeList.add(new ModelBox(cube_r40, 39, 0, -1.0F, -28.0F, -8.0F, 3, 20, 13, p_i46302_1_, false));

		head3 = new ModelRenderer(this);
		head3.setRotationPoint(0.0F, -20.0F, 0);
		neck3.addChild(head3);
		setRotationAngle(head3, 0.1745F, 0.0F, 0);
		head3.cubeList.add(new ModelBox(head3, 114, 0, -6.0F, -6.0F, -8.0F, 13, 9, 12, p_i46302_1_, false));

		cube_r41 = new ModelRenderer(this);
		cube_r41.setRotationPoint(-6.1573F, 2.0F, 4.7692F);
		head3.addChild(cube_r41);
		setRotationAngle(cube_r41, 0.0F, -0.2618F, 0);
		cube_r41.cubeList.add(new ModelBox(cube_r41, 32, 94, -3.0F, -4.0F, -23.0F, 6, 6, 22, p_i46302_1_, false));

		cube_r42 = new ModelRenderer(this);
		cube_r42.setRotationPoint(10.9869F, 2.0F, 3.4751F);
		head3.addChild(cube_r42);
		setRotationAngle(cube_r42, 0.0F, 0.2618F, 0);
		cube_r42.cubeList.add(new ModelBox(cube_r42, 78, 10, -8.0F, -4.0F, -23.0F, 7, 6, 22, p_i46302_1_, false));

		ear5 = new ModelRenderer(this);
		ear5.setRotationPoint(0.0F, 2.0F, 0);
		head3.addChild(ear5);
		setRotationAngle(ear5, 0.3927F, 0.2182F, 0);
		ear5.cubeList.add(new ModelBox(ear5, 186, -43, 6.0F, -14.0F, 4.0F, 0, 15, 43, p_i46302_1_, false));

		ear6 = new ModelRenderer(this);
		ear6.setRotationPoint(0.0F, 2.0F, 0);
		head3.addChild(ear6);
		setRotationAngle(ear6, 0.48F, -0.2182F, 0);
		ear6.cubeList.add(new ModelBox(ear6, 186, -43, -5.0F, -13.0F, 4.0F, 0, 15, 43, p_i46302_1_, false));

		cube_r45 = new ModelRenderer(this);
		cube_r45.setRotationPoint(0.0F, 2.0F, 0);
		head3.addChild(cube_r45);
		setRotationAngle(cube_r45, 0.3054F, 0.0F, 0);
		cube_r45.cubeList.add(new ModelBox(cube_r45, 98, 54, -3.0F, -9.0F, -18.0F, 7, 6, 19, p_i46302_1_, false));

		jaw3 = new ModelRenderer(this);
		jaw3.setRotationPoint(0.0F, 5.0F, 0);
		head3.addChild(jaw3);
		jaw3.cubeList.add(new ModelBox(jaw3, 0, 62, -3.0F, -3.0F, -19.0F, 7, 5, 23, p_i46302_1_, false));

		cube_r46 = new ModelRenderer(this);
		cube_r46.setRotationPoint(-6.1573F, 2.0F, 4.7692F);
		jaw3.addChild(cube_r46);
		setRotationAngle(cube_r46, 0.0F, -0.2618F, 0);
		cube_r46.cubeList.add(new ModelBox(cube_r46, 92, 89, -4.0F, -5.0F, -23.0F, 7, 5, 22, p_i46302_1_, false));

		cube_r47 = new ModelRenderer(this);
		cube_r47.setRotationPoint(10.9869F, 2.0F, 3.4751F);
		jaw3.addChild(cube_r47);
		setRotationAngle(cube_r47, 0.0F, 0.2618F, 0);
		cube_r47.cubeList.add(new ModelBox(cube_r47, 60, 62, -8.0F, -5.0F, -23.0F, 8, 5, 22, p_i46302_1_, false));
	}

	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) 
	{
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
		body.render(scale);
        GlStateManager.popMatrix();
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

    	EntityWitherHare wither = (EntityWitherHare)entityIn;
		MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
		MathHelper.sin(this.swingProgress * 3.1415927F);
        float f11 = wither.getStandingAnimationScale(ageInTicks - (float)wither.ticksExisted);
        f11 = f11 * f11;
		body.setRotationPoint(0.0F, -8.0F + (f11 * 60F), 4.0F + (f11 * 30F));
		setRotationAngle(body, f11 * 2.4F, 0.0F, 0);
		this.head1.rotateAngleY = (netHeadYaw / 57.295776F);
		this.head1.rotateAngleX = (headPitch / 57.295776F) - (f11 * 2.4F);
		this.head2.rotateAngleX -= (f11 * 1.4F);
		this.head3.rotateAngleX -= (f11 *1.4F);

		float f3 = MathHelper.cos(ageInTicks * 0.1F);
		leg1.setRotationPoint(-13.0F, 24.0F + (f3 * 2F) + (f11 * 6F), 0);
		leg2.setRotationPoint(13.0F, 24.0F + (f3 * 2F) + (f11 * 6F), 0);
		f3 = MathHelper.cos(ageInTicks * 0.1F - 1F);
		setRotationAngle(leg1, -1.6654F + (f3 * 0.125F) + (f11 * 4F), 0.0F, 0);
		setRotationAngle(leg2, -1.6654F + (f3 * 0.125F) + (f11 * 4F), 0.0F, 0);
		f3 = MathHelper.cos(ageInTicks * 0.1F - 2F);
		setRotationAngle(leg1joint, 2.4435F + (f3 * 0.125F) - (f11 * 4F), 0.0F, 0);
		setRotationAngle(leg2joint, 2.4435F + (f3 * 0.125F) - (f11 * 4F), 0.0F, 0);
		
		f3 = MathHelper.cos(ageInTicks * 0.1F);
		setRotationAngle(tail, ((0.065F + 0.05F * f3) * 3.1415927F) - (f11 * 0.5F), 0.0F, 0);
		f3 = MathHelper.cos(ageInTicks * 0.1F - 0.25F);
		setRotationAngle(tailpart2, ((0.265F + 0.05F * f3) * 3.1415927F) - (f11 * 0.75F), 0.0F, 0);
		f3 = MathHelper.cos(ageInTicks * 0.1F - 0.5F);
		setRotationAngle(tailpart3, ((0.265F + 0.05F * f3) * 3.1415927F) - (f11 * 0.75F), 0.0F, 0);
		f3 = MathHelper.cos(ageInTicks * 0.1F - 0.75F);
		setRotationAngle(tailend, ((0.265F + 0.05F * f3) * 3.1415927F) - (f11 * 0.5F), 0.0F, 0);
    }

    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
    	EntityWitherHare entitywither = (EntityWitherHare)entitylivingbaseIn;

        this.head2.rotateAngleY = (entitywither.getHeadYRotation(0) - entitylivingbaseIn.renderYawOffset) * 0.017453292F;
        this.head2.rotateAngleX = entitywither.getHeadXRotation(0) * 0.017453292F;
        this.head3.rotateAngleY = (entitywither.getHeadYRotation(1) - entitylivingbaseIn.renderYawOffset) * 0.017453292F;
        this.head3.rotateAngleX = entitywither.getHeadXRotation(1) * 0.017453292F;
    }
}