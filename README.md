## Create the main README.md:

```powershell
# Navigate to the root of CS-Portfolio
cd C:\Users\hp\IdeaProjects\WeatherApp

# Create comprehensive README
@"
# 💻 CS-Portfolio

Welcome to my Computer Science portfolio! This repository showcases various projects I've developed throughout my CS journey, demonstrating skills in Java, JavaFX, Maven, and software development principles.

## 📚 Table of Contents
- [About Me](#about-me)
- [Projects](#projects)
- [Technologies](#technologies)
- [Project Highlights](#project-highlights)
- [How to Run](#how-to-run)
- [Contact](#contact)

---

## 👨‍💻 About Me

**Mateiyendou Kombat**  
Computer Science Student | Software Developer

Passionate about building practical applications and solving real-world problems through code. This portfolio represents my growth and learning in various areas of computer science.

---

## 🗂️ Projects

### 🚀 Completed Projects

| # | Project | Description | Technologies |
|---|---------|-------------|--------------|
| 1 | [WeatherApp](WeatherApp/) | Real-time weather application with JavaFX UI, temperature/wind speed toggles, and search history | Java, JavaFX, Maven, OpenWeatherMap API |
| 2 | [Chat Application](Chat%20Application/) | Real-time chat system with client-server architecture | Java, Socket Programming |
| 3 | [EcommerceSystem](EcommerceSystem/) | E-commerce platform with customer, product, and order management | Java, OOP |
| 4 | [GenericLibraryCatalog](GenericLibraryCatalog/) | Library catalog system for managing books and resources | Java, Collections Framework |
| 5 | [LibraryManager](LibraryManager/) | Library management system with book tracking | Java, OOP |
| 6 | [QuizGame](QuizGame/) | Interactive quiz game application | Java |
| 7 | [SimpleClockApp](SimpleClockApp/) | Digital clock application with real-time display | Java, JavaFX |
| 8 | [StockDataAnalysis](StockDataAnalysis/) | Stock market data analysis tool | Java |
| 9 | [StudentRecordSystem](StudentRecordSystem/) | Student record management system | Java, OOP |
| 10 | [Unit1ProgrammingAssignment](Unit1ProgrammingAssignment/) | Programming assignment from Unit 1 | Java |
| 11 | [VehicleInformationSystem](VehicleInformationSystem/) | Vehicle information management system | Java, OOP |

---

## 🛠️ Technologies

### Languages & Frameworks
- **Java 17+** - Primary programming language
- **JavaFX** - Desktop application framework
- **Maven** - Build automation and dependency management

### Concepts & Tools
- Object-Oriented Programming (OOP)
- Socket Programming
- API Integration
- Git & GitHub
- Collections Framework
- File I/O Operations

---

## 🏆 Project Highlights

### 🌤️ WeatherApp
**Standout Features:**
- Real-time weather data fetching
- Celsius/Fahrenheit toggle
- km/h/mph wind speed toggle
- Search history tracking
- Clean JavaFX UI

**Technologies:** Java 17, JavaFX, Maven, OpenWeatherMap API

---

### 💬 Chat Application
**Standout Features:**
- Client-server architecture
- Real-time messaging
- Multi-client support

**Technologies:** Java, Socket Programming, Threads

---

### 🛒 EcommerceSystem
**Standout Features:**
- Customer management
- Product catalog
- Order processing
- OOP Design Patterns

**Technologies:** Java, OOP Principles

---

## 🚀 How to Run

### Prerequisites
- Java 17 or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- Maven 3.6+ ([Download](https://maven.apache.org/download.cgi))
- Git ([Download](https://git-scm.com/downloads))

### General Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yennulomlarri/CS-Portfolio.git
   cd CS-Portfolio
   ```

2. **Navigate to any project:**
   ```bash
   cd ProjectName
   ```

3. **Build with Maven:**
   ```bash
   mvn clean compile
   ```

4. **Run the application:**
   ```bash
   mvn javafx:run
   ```

### Running Specific Projects

**For JavaFX applications (WeatherApp):**
```bash
cd WeatherApp
mvn javafx:run
```

**For standard Java applications:**
```bash
cd ProjectName
javac src/*.java
java -cp src Main
```

---

## 📊 Repository Statistics

- **Total Projects:** 11
- **Lines of Code:** 1000+
- **Last Updated:** July 2026
- **Language:** Java 100%

---

## 🤝 Contributing

This is a personal portfolio repository, but feel free to:
- Fork the repository
- Star ⭐ if you like it
- Suggest improvements via Issues

---

## 📞 Contact

**Mateiyendou Kombat**
- GitHub: [@yennulomlarri](https://github.com/yennulomlarri)
- Email: [Your Email Here]

---

## 📝 License

This project is for educational purposes and portfolio demonstration.

---

## 🙏 Acknowledgments

- All professors and mentors who guided my learning journey
- Open source community for valuable resources
- OpenWeatherMap for the weather API

---

*Built with ❤️ as part of my Computer Science journey*

---
*Last Updated: July 2026*
"@ | Out-File -FilePath README.md -Encoding utf8
```

## Add and commit the updated README:

```powershell
# Add the README
git add README.md

# Commit
git commit -m "docs: Update main README with comprehensive project overview"

# Push to GitHub
git push origin main
```

---

## Quick Verification:

```powershell
# Check the status
git status

# See the README content
cat README.md
