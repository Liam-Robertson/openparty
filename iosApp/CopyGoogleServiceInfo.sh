#!/bin/sh
#File: iosApp/CopyGoogleServiceInfo.sh
if [ "${CONFIGURATION}" = "Debug" ]; then
    cp "${SRCROOT}/GoogleService-Info-Debug.plist" "${BUILT_PRODUCTS_DIR}/${PRODUCT_NAME}.app/GoogleService-Info.plist"
else
    cp "${SRCROOT}/GoogleService-Info-Release.plist" "${BUILT_PRODUCTS_DIR}/${PRODUCT_NAME}.app/GoogleService-Info.plist"
fi
