package com.gmail.borlandlp.minigamesdtools.conditions

class PlayerCheckResult(
    val result: CheckResult,
    val errId: List<String>
) {

    enum class CheckResult {
        ALLOWED, DENIED
    }

}