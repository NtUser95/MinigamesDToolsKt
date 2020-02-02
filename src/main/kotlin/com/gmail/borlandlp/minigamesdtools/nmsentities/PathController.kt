package com.gmail.borlandlp.minigamesdtools.nmsentities

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.IMoveControllableEntity

class PathController(
    val entity: IMoveControllableEntity,
    val paths: Array<PathProvider>,
    val isRepeating: Boolean
) {
    private var curIndex = 0
    var isFinished = false
        private set
    var failCounter = 0

    val currentPath: PathProvider
        get() = paths[curIndex]

    @Throws(Exception::class)
    fun update() {
        if (isFinished) {
            return
        } else if (curIndex >= paths.size) {
            throw Exception("index out of bounds!")
        }
        if (paths[curIndex].pathEnding()) { //if the entity has reached the path point
// next path
            if (curIndex + 1 >= paths.size) {
                if (isRepeating) {
                    curIndex = 0
                } else {
                    isFinished = true
                    return
                }
            } else {
                curIndex++
            }
            val nextPath = currentPath
            if (isRepeating && nextPath.pathEnding()) {
                nextPath.reloadPath()
            }
            failCounter = 0
            entity.setPath(nextPath)
        } else if (!entity.isFollowsThisPath(currentPath)) { // if entity switched path
            if (++failCounter > 5) {
                Debug.print(
                    Debug.LEVEL.NOTICE,
                    "teleport entity"
                )
                failCounter = 0
                entity.teleport(currentPath)
                entity.setPath(currentPath)
            }
        }
    }

    init {
        entity.setPath(currentPath)
    }
}