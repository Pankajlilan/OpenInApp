# OpenInApp
This project is a Kotlin-based Android application that utilizes the MVVM architecture pattern for development. It integrates with the specified API (InOpenApp API) to fetch data and display it in the application.

### 🌟 Features
- **Architecture Pattern**: Utilizes the MVVM (Model-View-ViewModel) clean architecture pattern for a structured development approach.
- **API Integration**: Communicates with the specified API using the provided bearer token.
- **Token Management**: Reads the bearer token from encrypted SharedPreferences during runtime for secure and authenticated API calls. Utilizes 256-bit encryption for enhanced security.
- **Navigation**: Implements Jetpack Navigation for seamless navigation between fragments within the application.
- **Networking Layer**: Incorporates a robust networking layer to handle both GET and POST API calls efficiently.


### 🎥 Demo
https://github.com/Pankajlilan/OpenInApp/assets/10745274/e3ee554e-3c59-435b-9cf0-ef4de25393a2

### 🎥 Screen Shots

| Dashboard   | Top Links |
|------------------- |--------------------------------|
| ![Screenshot_2024-01-31-14-07-12-57_326bb15694499c123ad5e3d1f275c42a](https://github.com/Pankajlilan/OpenInApp/assets/10745274/f8aada6c-4972-45df-ad8c-839720ec95c6)         |  ![Screenshot_2024-01-31-14-07-22-82_326bb15694499c123ad5e3d1f275c42a](https://github.com/Pankajlilan/OpenInApp/assets/10745274/ed7ee2ab-74a1-42b9-926d-4388206a91b7)   |

| Recent Links   | Bottom  Dashboard |
|------------------- |--------------------------------|
|    ![Screenshot_2024-01-31-14-07-27-60_326bb15694499c123ad5e3d1f275c42a](https://github.com/Pankajlilan/OpenInApp/assets/10745274/e562b6aa-816a-4dbe-9b9a-f58c99441fa8)     |  ![Screenshot_2024-01-31-14-07-31-91_326bb15694499c123ad5e3d1f275c42a](https://github.com/Pankajlilan/OpenInApp/assets/10745274/9935d006-9a4c-4014-85c7-77448a7e9373)    |


### 💻  Installing
1.  In Android Studio, go to File -> New -> Import project
2.  Follow the dialog wizard to choose the folder where you cloned the project and click on open.
3.  Android Studio imports the projects and builds it for you.

### 📃 Libraries used
* [AndroidX](https://developer.android.com/jetpack/androidx/) 
* [Retrofit 2](https://github.com/square/retrofit)
* [Gson](https://github.com/google/gson)
* [Navigation](https://developer.android.com/guide/navigation)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [OkHttp](https://github.com/square/okhttp)
