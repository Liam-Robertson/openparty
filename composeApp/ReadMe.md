How to build release ios app:
- Run this: 
  - ./gradlew linkReleaseFrameworkIosArm64
- Go to xcode, go to Product, click Archive


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