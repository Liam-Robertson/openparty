//File: composeApp/src/commonMain/kotlin/com/openroots/app/features/startup/feature_screen_name_generation/domain/usecase/GenerateUniqueScreenNameUseCase.kt
package com.openroots.app.features.startup.feature_screen_name_generation.domain.usecase

import com.openroots.app.core.shared.domain.DomainResult
import com.openroots.app.core.shared.domain.error.AppError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class GenerateUniqueScreenNameUseCase(
    private val isScreenNameTakenUseCase: IsScreenNameTakenUseCase
) {
    private val adjectives = listOf(
        "Whimsical", "Velvet", "Radiant", "Silent", "Brilliant", "Misty", "Luminous", "Ethereal", "Serene", "Vivid",
        "Gleaming", "Sparkling", "Bold", "Gentle", "Playful", "Vibrant", "Majestic", "Dreamy", "Tranquil", "Fiery",
        "Frosty", "Enchanted", "Mellow", "Wild", "Fierce", "Cheerful", "Gilded", "Mystical", "Ancient", "Quiet",
        "Daring", "Glorious", "Fearless", "Dazzling", "Warm", "Shimmering", "Noble", "Rustic", "Energetic", "Flowing",
        "Polished", "Refined", "Eager", "Heavenly", "Bright", "Blissful", "Elegant", "Glowing", "Peaceful"
    )

    private val nouns = listOf(
        "Moonlit", "Forest", "Shadow", "Dancer", "River", "Garden", "Whisper", "Harbor", "Canyon", "Prairie",
        "Mountain", "Sapphire", "Storm", "Falcon", "Phoenix", "Star", "Meadow", "Horizon", "Ocean", "Lake",
        "Thunder", "Cloud", "Valley", "Flame", "Tiger", "Rider", "Wave", "Pearl", "Bear", "Wanderer",
        "Eagle", "Raven", "Lark", "Rose", "Sparrow", "Snowflake", "Willow", "Drift", "Aurora", "Sunflower",
        "Mist", "Frost", "Spirit", "Comet", "Galaxy", "Trail", "Voyager", "Pioneer", "Guardian", "Haven",
        "Sanctuary", "Vortex", "Tempest", "Beacon", "Shelter", "Hearth", "Echo", "Aspen", "Seeker", "Nomad",
        "Voyage", "Wildflower", "Cove", "Twilight", "Stream", "Blossom", "Glow", "Pathfinder", "Dream", "Quest",
        "Wisp", "Wing", "Cliff", "Starling", "Fern", "Cedar", "Crest", "Brook", "Crystal",
        "Stone", "Breeze", "Sky", "Trailblazer", "Shore", "Summit", "Cascade", "Dawn", "Evening", "Loom",
        "Pine", "Flare", "Hill", "Field", "Glade", "Raindrop", "Zephyr", "Harmony"
    )

    suspend operator fun invoke(): DomainResult<String> = withContext(Dispatchers.Default) {
        println("Screen name generation started")
        var attempts = 0
        while (attempts < 10) {
            val candidate = generateName()
            println("Generated screen name candidate: $candidate")
            when (val takenResult = isScreenNameTakenUseCase(candidate)) {
                is DomainResult.Success -> {
                    attempts++
                    println("Screen name '$candidate' is ${if (takenResult.data) "taken" else "available"}")
                    if (!takenResult.data) {
                        println("Screen name '$candidate' selected as unique")
                        return@withContext DomainResult.Success(candidate)
                    }
                }
                is DomainResult.Failure -> {
                    println("Failed to check if screen name is taken: ${takenResult.error}")
                    return@withContext DomainResult.Failure(AppError.ScreenNameGeneration.GenerateScreenName)
                }
            }
        }
        println("Failed to generate a unique screen name after $attempts attempts")
        DomainResult.Failure(AppError.ScreenNameGeneration.GenerateScreenName)
    }

    private fun generateName(): String {
        val adj = adjectives[Random.nextInt(adjectives.size)]
        val noun1 = nouns[Random.nextInt(nouns.size)]
        val noun2 = nouns[Random.nextInt(nouns.size)]
        return "$adj $noun1 $noun2"
    }
}
