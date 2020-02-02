package com.gmail.borlandlp.minigamesdtools.nmsentities.classes

import net.minecraft.server.v1_12_R1.*

class SkyControllerMove(entityInsentient: EntityInsentient) : ControllerMove(entityInsentient) {
    override fun a() {
        val var9: Float
        if (this.h == Operation.STRAFE) {
            val var1 =
                a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).value.toFloat()
            var var2 = e.toFloat() * var1
            var var3 = f
            var var4 = g
            var var5 = MathHelper.c(var3 * var3 + var4 * var4)
            if (var5 < 1.0f) {
                var5 = 1.0f
            }
            var5 = var2 / var5
            var3 *= var5
            var4 *= var5
            val var6 = MathHelper.sin(a.yaw * 0.017453292f)
            val var7 = MathHelper.cos(a.yaw * 0.017453292f)
            val var8 = var3 * var7 - var4 * var6
            var9 = var4 * var7 + var3 * var6
            val var10 = a.navigation
            if (var10 != null) {
                val var11 = var10.r()
                if (var11 != null && var11.a(
                        a.world,
                        MathHelper.floor(a.locX + var8.toDouble()),
                        MathHelper.floor(a.locY),
                        MathHelper.floor(a.locZ + var9.toDouble())
                    ) != PathType.WALKABLE
                ) {
                    f = 1.0f
                    g = 0.0f
                    var2 = var1
                }
            }
            a.k(var2)
            a.n(f)
            a.p(g)
            this.h = Operation.WAIT
        } else if (this.h == Operation.MOVE_TO) {
            this.h = Operation.WAIT
            val var12 = b - a.locX
            val var13 = d - a.locZ
            val var14 = c - a.locY
            val var15 = var12 * var12 + var14 * var14 + var13 * var13
            if (var15 < 1.0) { // stop walking here
                a.n(0.0f)
                return
            }
            var9 = (MathHelper.c(var13, var12) * 57.2957763671875).toFloat() - 90.0f
            a.yaw = this.a(a.yaw, var9, 90.0f)
            a.k((e * a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).value).toFloat())
            if (var14 > a.P.toDouble() && var12 * var12 + var13 * var13 < Math.max(
                    1.0f,
                    a.width
                ).toDouble()
            ) {
                a.controllerJump.a()
                this.h = Operation.JUMPING
            }
        } else if (this.h == Operation.JUMPING) {
            a.k((e * a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).value).toFloat())
            if (a.onGround) {
                this.h = Operation.WAIT
            }
        } else {
            a.n(0.0f)
        }
    }
}