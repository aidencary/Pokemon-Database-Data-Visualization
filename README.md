# ◓ Pokémon Visualizer

![PokeApp Logo](https://raw.githubusercontent.com/aidencary/Pokemon-Database-Data-Visualization/refs/heads/Part-2-Branch/DALL%C2%B7E%202025-03-16%2020.01.41%20-%20A%20high-quality%20digital%20illustration%20of%20a%20classic%20Pok%C3%A9%20Ball%20with%20the%20text%20'PokeApp'%20written%20across%20its%20center.%20The%20design%20should%20be%20sleek%2C%20modern%2C%20and%20.webp)

## 📌 Overview

**Pokémon Visualizer** is a Java-based application that reads Pokémon data from a CSV file and presents it in a **graphical user interface (GUI)**. The application allows users to **view, filter, sort, and analyze** Pokémon statistics. It also includes **color-coded type visualizations**, **statistical summaries**, and a **pie chart** of Pokémon type distribution.

This project demonstrates the use of:
- **Java Swing** for the GUI
- **Streams and Collections API** for data processing
- **JFreeChart** for chart visualization
- **Custom Renderers** for table styling

---

## 🛠 Features

### 🔹 **Data Handling & Display**
- Loads Pokémon data from `updatedPokemon.csv` using **Java Streams**.
- Stores each Pokémon as an immutable **record (`Pokemon.java`)**.
- Displays data in a **sortable JTable**.

### 🎨 **Custom Visualization**
- **Type-Based Coloring**: Colors Pokémon type cells based on their type (`TypeColorRenderer.java`).
- **Details Panel**: Displays detailed information when selecting a Pokémon (`DetailsPanel.java`).
- **Pie Chart**: Shows **type distribution** with corresponding type colors (`TypeChartPanel.java`).

### 📊 **Statistics & Sorting**
- **Stat Summary**: Calculates **minimum, average, and maximum** stats across all Pokémon (`StatsPanel.java`).
- **Sorting**:
  - Numerical columns (`HP`, `Attack`, etc.) are sorted **highest-to-lowest**.
  - String columns (`Name`, `Type 1`) are sorted **alphabetically**.
  - "None" values in **Type 2** are moved to the bottom.

### 🔍 **Filtering Options**
Users can filter Pokémon using:
- **Type** (e.g., "Fire", "Water").
- **Legendary Status** (Only show Legendaries).
- **Generation** (Select from **Gen 1 - 6**).

---

## 🔧 Installation & Setup

### 🖥 Prerequisites
- **Java 22** or later
- **JFreeChart Library** (`jfreechart-1.0.19.jar`, `jcommon-1.0.24.jar`)

### 📥 Installation Steps
1. Clone this repository:
   ```sh
   git clone https://github.com/your-repo/Pokemon-Visualizer.git
2. Run PokeApp.java

## Usage Instructions
- Running the Application
1. Console Output:
- Prints details of 1st Pokémon, 10th Pokémon, and total Pokémon count.

2. GUI Components:
- Table Panel: View Pokémon details with sorting and filtering.
- Stats Panel: Displays stats (min, max, avg) and type distribution.
- Type Chart Panel: Pie chart showing Pokémon types.

3. Interacting with the Table:
- Click on a Pokémon to view more details.
- Click a column header to sort Pokémon.
- Use filters (dropdowns and checkboxes) to refine results.

---
📝 **Author:** *Aiden Cary*  
📅 **Last Updated:** *March 2025*  
🤖 **AI Assistance:** Some parts of this project were developed with guidance from **ChatGPT**.

