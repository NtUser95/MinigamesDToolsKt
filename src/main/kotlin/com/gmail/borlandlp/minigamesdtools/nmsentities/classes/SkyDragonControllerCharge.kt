package com.gmail.borlandlp.minigamesdtools.nmsentities.classes

import net.minecraft.server.v1_12_R1.*
import org.apache.logging.log4j.LogManager

/*
 * Spigot: net.minecraft.server.v1_12_R1.DragonControllerCharge
 * Vanilla: net.minecraft.entity.boss.dragon.phase.PhaseChargingPlayer
 * */
class SkyDragonControllerCharge(entityEnderDragon: EntityEnderDragon?) :
    AbstractDragonController(entityEnderDragon), PathProvider {
    private var c: Vec3D? = null
    private var d = 0
    private var pathEnding = false
    override fun pathEnding(): Boolean {
        return pathEnding
    }

    override fun reloadPath() {
        pathEnding = false
    }

    override val path: Vec3D?
        get() = g()

    override fun c() {
        if (g() == null) {
            b.warn("Aborting charge player as no target was set.")
            //this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
            pathEnding = true
        } else if (d > 0 && d++ >= 10) { //this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
            pathEnding = true
        } else {
            val var1 = c!!.c(a.locX, a.locY, a.locZ)
            if (var1 < 100.0 || var1 > 22500.0 || a.positionChanged || a.B) {
                ++d
            }
        }
    }

    override fun d() {
        c = null
        d = 0
    }

    fun a(var1: Vec3D?) {
        c = var1
    }

    override fun f(): Float {
        return 3.0f
    }

    override fun g(): Vec3D? {
        return c
    }

    override fun getControllerPhase(): DragonControllerPhase<DragonControllerCharge> {
        return DragonControllerPhase.i
    }

    companion object {
        private val b = LogManager.getLogger()
    }
}