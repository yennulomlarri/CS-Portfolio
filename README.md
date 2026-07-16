## Create WeatherApp README:

```powershell
# Navigate to the WeatherApp folder
cd WeatherApp

# Create README.md file
@"
# ☀️ WeatherApp

A JavaFX-based weather application that fetches real-time weather data and maintains search history.

## 📋 Features

- **Real-time Weather Data**: Fetches current weather conditions for any city
- **Temperature Units**: Toggle between Celsius (°C) and Fahrenheit (°F)
- **Wind Speed Units**: Switch between km/h and mph
- **Search History**: Automatically saves and displays previous searches
- **User-Friendly Interface**: Clean and intuitive JavaFX UI
- **Error Handling**: Graceful error handling with user alerts

## 🛠️ Technologies Used

- **Java 17+** - Core programming language
- **JavaFX** - Desktop application framework
- **Maven** - Dependency management and build tool
- **OpenWeatherMap API** - Weather data provider

## 📁 Project Structure

```
WeatherApp/
├── pom.xml                          # Maven configuration
├── weather_history.txt              # Search history storage
└── src/
    └── main/
        └── java/
            └── com/
                └── weatherapp/
                    ├── Main.java                    # Application entry point
                    ├── controller/
                    │   └── WeatherController.java   # Main controller
                    ├── model/
                    │   └── WeatherData.java         # Weather data model
                    └── service/
                        └── HistoryManager.java      # History management
```

## 🚀 How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yennulomlarri/CS-Portfolio.git
   cd CS-Portfolio/WeatherApp
   ```

2. **Build the project**:
   ```bash
   mvn clean compile
   ```

3. **Run the application**:
   ```bash
   mvn javafx:run
   ```

4. **Alternatively, package and run**:
   ```bash
   mvn package
   java -jar target/WeatherApp.jar
   ```

## 🔧 Configuration

### API Key Setup
The application uses the OpenWeatherMap API. To run it:

1. Get a free API key from [OpenWeatherMap](https://openweathermap.org/api)
2. Add your API key in `WeatherController.java`:
   ```java
   private static final String API_KEY = "YOUR_API_KEY_HERE";
   ```

## 📖 Usage

1. **Enter a city name** in the search field
2. **Press Enter** or click the search button
3. **View weather data** including:
   - Temperature
   - Weather conditions
   - Humidity
   - Wind speed
4. **Toggle units** using the radio buttons:
   - Celsius/Fahrenheit for temperature
   - km/h/mph for wind speed
5. **View search history** in the history panel

## 📸 Screenshots

> *Screenshots coming soon*

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is part of the CS-Portfolio and is created for educational purposes.

## 👨‍💻 Author

**Mateiyendou Kombat** - [GitHub](https://github.com/yennulomlarri)

## 🙏 Acknowledgments

- OpenWeatherMap for providing the weather API
- JavaFX community for the excellent framework

---
*Built with ❤️ as part of the CS-Portfolio*
"@ | Out-File -FilePath README.md -Encoding utf8
```

## Add and commit the README:

```powershell
# Add the README
git add README.md

# Commit
git commit -m "docs: Add WeatherApp README documentation"

# Push to GitHub
git push origin main
```

---

## Quick Version (Minimal README):

If you prefer a shorter version:

```powershell
cd WeatherApp

@"
# WeatherApp

JavaFX weather application with real-time data and search history.

## Features
- Real-time weather data
- Celsius/Fahrenheit toggle
- km/h/mph wind speed toggle
- Search history tracking

## Run
\`\`\`bash
mvn javafx: run
\`\`\`

## Technologies
- Java 17+
- JavaFX
- Maven
- OpenWeatherMap API

## Author
Mateiyendou Kombat
"@ | Out-File -FilePath README.md -Encoding utf8

git add README.md
git commit -m "docs: Add WeatherApp README"
git push origin main
