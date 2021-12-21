plugins {
    id("com.diffplug.spotless")
}


spotless {
    kotlin {
        ktfmt()
    }
}
