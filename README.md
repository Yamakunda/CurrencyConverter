# Currency Conversion App

## Overview
This application is built using Kotlin on Android Studio. As this is my first project on Android Studio, there might be some limitations and room for improvement.

## Features
- A user-friendly interface with buttons, input fields, and text views.
- Fetches exchange rate data from an API using Retrofit.
- Converts currency based on user input.
- Handles common test cases like empty input and invalid data.

## Prerequisites
- Android Studio installed.
- Basic knowledge of Kotlin and Android development.
- Internet connection for API calls.

## Steps to Build the Application
1. **Create a Kotlin Project**: 
   - Start a new project in Android Studio with Kotlin as the primary language.

2. **Add API Calling Libraries**:
   - Add dependencies for Retrofit and other required libraries to your `build.gradle` file.
   - Add the Internet permission to the `AndroidManifest.xml` file:
     ```xml
     <uses-permission android:name="android.permission.INTERNET"/>
     ```

3. **Design the UI for `MainActivity`**:
   - Add UI components such as Buttons, EditText fields, and TextViews using XML.

4. **Build the API Handling Class**:
   - Create a class to handle API calls and process the results using Retrofit.
   - The API is taken from "openexchangerates.org"

5. **Develop Application Logic**:
   - Assign UI elements in the `MainActivity`.
   - Implement functions for:
     - API data fetching and processing.
     - Handling empty input cases.
     - Performing currency conversion logic.

6. **Test and Debug**:
   - Validate the application by testing various scenarios such as empty input, incorrect data, and successful conversions.
   
## Challenges
- Free exchange rate APIs often provide conversion rates only from USD to other currencies.  
- To enable conversions between multiple currencies, the rates are calculated indirectly using the USD rate as a reference.  
- This approach may introduce small errors in the conversion process.

## Potential Improvements
Since this is the initial attempt at building an Android application, future enhancements could include:
- Implementing a progress indicator during API calls.
- Enhancing the UI with better styling and animations.
