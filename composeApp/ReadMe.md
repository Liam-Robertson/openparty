Notes:
- I'm using pods to manage firebase, but gradle to create my builds
- I'm using this build script to manage my dev and prod database config - custom - devVsProdConfig

IOS
----
How to build release ios app:
- Go to info.plist
- Search for "Bundle Version String" and update the version
- Do the same for "Bundle Version"
- Go to android studio, open terminal
- Run this: 
  - ./gradlew linkReleaseFrameworkIosArm64
- Go to xcode, go to Product, click Archive
- After the archive has finished building, it will automatically open the Organiser window
- Click the "Distribute App" button
- This is automatically linked to your account so it's automatically uploaded
- Once it's uploaded:
- Go to app store connect
- Scroll down to the "Build" section
- Replace the current build with your new build 

ANDROID
-------
How to upload to play store:
- Go to build.gradle.kts and update version number
- Click Build -> Generate signed app bundle
- It will be located in composeApp/release
- Go to the play store
- Go to Test and Release -> Closed Testing
- Manage Track
--------------------

IOS
----
How to run release build locally: 
- Go to Product -> Schema -> Edit Scheme
- Change the build configuration from debug to release
- Click close to save your changes

ANDROID
-------
How to run locally: 
- View -> Tool Windows -> Build Variants
- Change Build Variant from debug to release


----------------- 

Old:

How to build release ios app: 
- Go to info.plist
- Search for "Bundle Version String" and update the version
- Do the same for "Bundle Version"
- close xcode
- Go to android studio, open terminal
- cd iosApp
- pod install 
- Open iosApp/ folder in finder 
- Open iosApp.xcworkspace file in xcode 
- Test the app thoroughly
- In Xcode, go to "Product" in the top toolbar, then go to "Archive"
- After the archive has finished building, it will automatically open the Organiser window 
- Click the "Distribute App" button
- This is automatically linked to your account so it's automatically uploaded 