package com.weatherapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.weatherapp.controller.WeatherController;
import com.weatherapp.model.WeatherData;
import com.weatherapp.service.HistoryManager;

import java.time.LocalDateTime;

public class Main extends Application {
    private WeatherController controller;
    private HistoryManager historyManager;
    private WeatherData currentWeather;

    // UI Components
    private TextField cityInput;
    private Button searchButton;
    private Label cityLabel;
    private Label tempLabel;
    private Label humidityLabel;
    private Label windLabel;
    private Label conditionLabel;
    private ImageView weatherIcon;
    private ListView<String> historyList;
    private TextArea forecastArea;

    // Unit preferences
    private ToggleGroup tempGroup;
    private RadioButton celsiusRadio;
    private RadioButton fahrenheitRadio;
    private ToggleGroup windGroup;
    private RadioButton kmhRadio;
    private RadioButton mphRadio;
    private boolean useCelsius = true;
    private boolean useKmh = true;

    @Override
    public void start(Stage primaryStage) {
        controller = new WeatherController();
        historyManager = new HistoryManager();

        // Create main layout
        BorderPane root = new BorderPane();

        // Setup UI sections
        root.setTop(createTopBar());
        root.setCenter(createCenterDisplay());
        root.setRight(createSidebar());
        root.setBottom(createBottomControls());

        // Set default background based on time of day (no weather data yet)
        updateBackgroundByTime(root);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Weather Information App");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load saved history
        refreshHistoryList();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #f0f0f0;");

        Label title = new Label("Weather Information App");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(title, spacer);
        return topBar;
    }

    private VBox createCenterDisplay() {
        VBox centerBox = new VBox(15);
        centerBox.setPadding(new Insets(20));
        centerBox.setAlignment(Pos.CENTER);

        // Search row
        HBox searchRow = new HBox(10);
        searchRow.setAlignment(Pos.CENTER);

        cityInput = new TextField();
        cityInput.setPromptText("Enter city name (e.g., London, Tokyo, New York)");
        cityInput.setPrefWidth(300);
        cityInput.setOnAction(event -> searchWeather());

        searchButton = new Button("Get Weather");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        searchButton.setOnAction(event -> searchWeather());

        searchRow.getChildren().addAll(cityInput, searchButton);

        // Weather display area
        VBox weatherBox = new VBox(10);
        weatherBox.setAlignment(Pos.CENTER);
        weatherBox.setPadding(new Insets(20));
        weatherBox.setStyle("-fx-background-color: rgba(255,255,255,0.85); -fx-border-radius: 10; -fx-background-radius: 10;");

        cityLabel = new Label();
        cityLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        weatherIcon = new ImageView();
        weatherIcon.setFitWidth(100);
        weatherIcon.setFitHeight(100);

        tempLabel = new Label();
        tempLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

        conditionLabel = new Label();
        conditionLabel.setStyle("-fx-font-size: 18px; -fx-font-style: italic;");

        humidityLabel = new Label();
        humidityLabel.setStyle("-fx-font-size: 14px;");

        windLabel = new Label();
        windLabel.setStyle("-fx-font-size: 14px;");

        weatherBox.getChildren().addAll(cityLabel, weatherIcon, tempLabel,
                conditionLabel, humidityLabel, windLabel);

        // Forecast area
        Label forecastTitle = new Label("5-Day Forecast");
        forecastTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        forecastArea = new TextArea();
        forecastArea.setEditable(false);
        forecastArea.setPrefHeight(150);
        forecastArea.setWrapText(true);
        forecastArea.setStyle("-fx-background-color: rgba(255,255,255,0.85);");

        centerBox.getChildren().addAll(searchRow, weatherBox, forecastTitle, forecastArea);
        return centerBox;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(250);
        sidebar.setStyle("-fx-background-color: rgba(245,245,245,0.9); -fx-border-color: #ddd; -fx-border-width: 0 0 0 1;");

        Label historyTitle = new Label("Search History");
        historyTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        historyList = new ListView<>();
        historyList.setPrefHeight(300);
        historyList.setOnMouseClicked(event -> {
            String selected = historyList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String city = selected.split(" - ")[0];
                cityInput.setText(city);
                searchWeather();
            }
        });

        Button clearHistory = new Button("Clear History");
        clearHistory.setMaxWidth(Double.MAX_VALUE);
        clearHistory.setOnAction(event -> {
            historyManager.clearHistory();
            refreshHistoryList();
        });

        sidebar.getChildren().addAll(historyTitle, historyList, clearHistory);
        return sidebar;
    }

    private HBox createBottomControls() {
        HBox bottomBar = new HBox(20);
        bottomBar.setPadding(new Insets(10));
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setStyle("-fx-background-color: rgba(224,224,224,0.9);");

        Label unitLabel = new Label("Units:");
        unitLabel.setStyle("-fx-font-weight: bold;");

        tempGroup = new ToggleGroup();
        celsiusRadio = new RadioButton("Celsius (°C)");
        celsiusRadio.setToggleGroup(tempGroup);
        celsiusRadio.setSelected(true);
        celsiusRadio.setOnAction(event -> { useCelsius = true; updateDisplay(); });

        fahrenheitRadio = new RadioButton("Fahrenheit (°F)");
        fahrenheitRadio.setToggleGroup(tempGroup);
        fahrenheitRadio.setOnAction(event -> { useCelsius = false; updateDisplay(); });

        Label windLabelText = new Label("Wind:");
        windLabelText.setStyle("-fx-font-weight: bold;");

        windGroup = new ToggleGroup();
        kmhRadio = new RadioButton("km/h");
        kmhRadio.setToggleGroup(windGroup);
        kmhRadio.setSelected(true);
        kmhRadio.setOnAction(event -> { useKmh = true; updateDisplay(); });

        mphRadio = new RadioButton("mph");
        mphRadio.setToggleGroup(windGroup);
        mphRadio.setOnAction(event -> { useKmh = false; updateDisplay(); });

        bottomBar.getChildren().addAll(unitLabel, celsiusRadio, fahrenheitRadio,
                new Separator(Orientation.VERTICAL),
                windLabelText, kmhRadio, mphRadio);
        return bottomBar;
    }

    private void searchWeather() {
        String city = cityInput.getText().trim();
        if (city.isEmpty()) {
            showAlert("Input Error", "Please enter a city name.");
            return;
        }

        // Show loading state
        searchButton.setDisable(true);
        cityLabel.setText("Loading weather data...");
        tempLabel.setText("");
        conditionLabel.setText("");

        // Run API call in background thread
        new Thread(() -> {
            try {
                WeatherData data = controller.getCurrentWeather(city);
                String forecast = controller.getForecast(city);

                Platform.runLater(() -> {
                    currentWeather = data;
                    updateDisplay();
                    forecastArea.setText(forecast);
                    historyManager.addHistoryEntry(city);
                    refreshHistoryList();
                    updateBackgroundByWeather((BorderPane) cityLabel.getParent().getParent().getParent(), data);
                    searchButton.setDisable(false);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showAlert("Error", "Failed to fetch weather: " + e.getMessage());
                    cityLabel.setText("Error loading weather");
                    searchButton.setDisable(false);
                });
            }
        }).start();
    }

    private void updateDisplay() {
        if (currentWeather == null) return;

        cityLabel.setText(currentWeather.getCity() + ", " + currentWeather.getCountry());

        // Temperature
        double temp = currentWeather.getTemperature();
        if (!useCelsius) {
            temp = temp * 9.0 / 5.0 + 32;
        }
        tempLabel.setText(String.format("%.1f°%s", temp, useCelsius ? "C" : "F"));

        // Wind speed
        double wind = currentWeather.getWindSpeed();
        if (!useKmh) {
            wind = wind * 0.621371;
        }
        windLabel.setText(String.format("Wind: %.1f %s", wind, useKmh ? "km/h" : "mph"));

        humidityLabel.setText(String.format("Humidity: %d%%", currentWeather.getHumidity()));
        conditionLabel.setText(currentWeather.getDescription().toUpperCase());

        // Load weather icon
        try {
            String iconUrl = "https://openweathermap.org/img/wn/" + currentWeather.getIconCode() + "@2x.png";
            Image icon = new Image(iconUrl, true);
            weatherIcon.setImage(icon);
        } catch (Exception e) {
            System.err.println("Could not load icon: " + e.getMessage());
        }
    }

    /**
     * Updates background based on weather condition (dynamic)
     */
    private void updateBackgroundByWeather(BorderPane root, WeatherData weather) {
        if (weather == null) {
            updateBackgroundByTime(root);
            return;
        }

        String condition = weather.getCondition().toLowerCase();
        String bgColor;

        // Set background based on weather condition
        switch (condition) {
            case "clear":
                bgColor = "linear-gradient(to bottom, #FFD700, #FFA500)"; // Sunny - Gold to Orange
                break;
            case "clouds":
                bgColor = "linear-gradient(to bottom, #B0C4DE, #708090)"; // Cloudy - Light Steel Blue to Slate Gray
                break;
            case "rain":
            case "drizzle":
                bgColor = "linear-gradient(to bottom, #4682B4, #2F4F4F)"; // Rainy - Steel Blue to Dark Slate Gray
                break;
            case "thunderstorm":
                bgColor = "linear-gradient(to bottom, #4B0082, #000000)"; // Storm - Indigo to Black
                break;
            case "snow":
                bgColor = "linear-gradient(to bottom, #E0FFFF, #B0E0E6)"; // Snow - Light Cyan to Powder Blue
                break;
            case "mist":
            case "fog":
                bgColor = "linear-gradient(to bottom, #D3D3D3, #A9A9A9)"; // Foggy - Light Gray to Dark Gray
                break;
            case "haze":
                bgColor = "linear-gradient(to bottom, #F5DEB3, #CD853F)"; // Hazy - Wheat to Peru
                break;
            default:
                bgColor = "linear-gradient(to bottom, #87CEEB, #00BFFF)"; // Default - Sky Blue
                break;
        }

        root.setStyle("-fx-background-color: " + bgColor + ";");
    }

    /**
     * Updates background based on time of day (fallback when no weather data)
     */
    private void updateBackgroundByTime(BorderPane root) {
        int hour = LocalDateTime.now().getHour();
        String bgColor;

        if (hour >= 6 && hour < 12) {
            bgColor = "linear-gradient(to bottom, #FFE5B4, #FFD700)"; // Morning
        } else if (hour >= 12 && hour < 17) {
            bgColor = "linear-gradient(to bottom, #87CEEB, #00BFFF)"; // Afternoon
        } else if (hour >= 17 && hour < 20) {
            bgColor = "linear-gradient(to bottom, #FFA07A, #FF6347)"; // Evening
        } else {
            bgColor = "linear-gradient(to bottom, #2C3E50, #1A1A2E)"; // Night
        }

        root.setStyle("-fx-background-color: " + bgColor + ";");
    }

    private void refreshHistoryList() {
        historyList.getItems().setAll(historyManager.getHistoryEntries());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}