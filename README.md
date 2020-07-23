This is a in-development debug plugin, don't expect it to be stable so far, but please create issues if you encounter problems or need more features.

To install it, just declare the dependency:

debugImplementation 'com.github.glureau:MyResources:<FULL_COMMIT_HASH>@aar'

This library will add a new Activity to your app: 

    com.glureau.myresources.MyResourcesActivity

You can start this activity manually with adb

    adb shell am start <my_package_name>/com.glureau.myresources.MyResourcesActivity

Or with AndroidStudio you can create a new Run/Debug configuration and target this specific activity.

Or you can start the activity from your hidden debug menu ;)
