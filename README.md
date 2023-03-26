# we-additions
Additions for the WorldEdit mod for FabricMC.

## Gradle Dependency
You can install `we-additions` via Gradle.

To use `we-additions` in your project, modify your `project.gradle`:
```groovy
repositories {
    mavenCentral()
    
    maven {
        url "https://repo.lclpnet.work/repository/internal"
    }
}

dependencies {
    implementation 'work.lclpnet.mods:we-additions:1.0.0'  // replace with your version
}
```
All available versions can be found [here](https://repo.lclpnet.work/#artifact/work.lclpnet.mods/we-additions).
