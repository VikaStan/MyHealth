# MyHealth

MyHealth is a health tracking application written in Kotlin using modern Android libraries and tools.

## Technologies

The project is developed using the following technologies:

- **Kotlin**: The main programming language.
- **Jetpack Compose**: Modern UI toolkit for Android.
- **Hilt**: Dependency injection library.
- **Room**: Database library.
- **Coroutines**: For asynchronous programming.
- **Material3**: To create a modern and user-friendly interface.
- **LiveData** and **Flow**: For reactive programming and data management.

## Main Screens

### User Profile
The profile screen contains essential information about the user's parameters, such as age, weight, height, and other important metrics.

### Diary
The diary displays information about three meals per day over a two-week period. Users can add, edit, and delete their meal entries.

### Statistics
The statistics screen shows necessary data for the filled days, allowing users to analyze their eating habits and progress.

## Installation

```bash
git clone https://github.com/your-username/MyHealth.git
cd MyHealth
```
После клонирования создайте файл `local.properties` в корне проекта со следующим содержимым:

```properties
sdk.dir=/путь/к/Android/Sdk
```

Файл не должен коммититься, он уже исключён в `.gitignore`.
## Screenshots
|||
|-|--------|
![5 экранов входа](https://github.com/user-attachments/assets/72348e2a-b6e6-4b73-8234-6057df1dc775)
![dashboard](https://github.com/user-attachments/assets/225f0c9b-dbbb-4f65-b287-a7aaef43e01a)
![экран дневника питания](https://github.com/user-attachments/assets/acf33deb-ede8-4208-8cde-f60daf8d68e1)
![экран аккаунта пользователя](https://github.com/user-attachments/assets/419da077-2fbd-4cb3-ba5d-a0fa8c0101b0)








