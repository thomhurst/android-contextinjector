# Android Context Injector
## Inject Contexts in Fields without cluttering your onCreate !

## Install

[![](https://jitpack.io/v/thomhurst/android-contextinjector.svg)](https://jitpack.io/#thomhurst/android-contextinjector)

Add Jitpack to your repositories in your `build.gradle` file

```gradle
allprojects {
    repositories {
      // ...
      maven { url 'https://jitpack.io' }
    }
}
```

Add the below to your dependencies, again in your `gradle.build` file

```gradle
implementation 'com.github.thomhurst:android-contextinjector:{version}'
```

# Usage

Your Context can be used in Fields/Properties and so your onCreate method doesn't have to be cluttered!

## Context

```kotlin
val myField by ContextInjector(this) { context -> ... /* Use context from lambda */ }
```

#### Example

```kotlin
class MyActivity : Activity() {

    // WILL CRASH
    val unSafeSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    // Will not crash
    val safeSharedPreferences by ContextInjector(this) { context ->  PreferenceManager.getDefaultSharedPreferences(context) } 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val someSavedVariable = safeSharedPreferences.getString("key", null)
        // Etc
    }

}
```

## Activity

```kotlin
val myField by ActivityInjector(this) { activity -> ... /* Use activity from lambda */ }
```

#### Example

```kotlin
class MyActivity : Activity() {

    // WILL CRASH
    val unSafeAlert = AlertDialog.Builder(this)
                        .setTitle("Some Title")
                        .setMessage("Some Message")

    // Will not crash
    val safeAlert by ActivityInjector(this) { activity ->  
        AlertDialog.Builder(activity)
        .setTitle("Some Title")
        .setMessage("Some Message") 
        } 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        safeAlert.show()
        // Etc
    }

}
```

