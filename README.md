# ◓ Pokémon Visualizer

![PokeApp Logo](https://raw.githubusercontent.com/aidencary/Pokemon-Database-Data-Visualization/refs/heads/Part-2-Branch/pokeIcon.webp)

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
- Displays data in a sortable, interactive `JTable`.

### 🎨 **Custom Visualization**
- **Type-Based Coloring**: Colors Pokémon type cells in the table based on their type (`TypeColorRenderer.java`).
- **Details Panel**: Displays hidden stats like Pokédex number, base stat total, generation, and legendary status when a Pokémon is selected (`DetailsPanel.java`).
- **Pie Chart**: Dynamically shows type distribution with updated type colors after filters are applied (`TypeChartPanel.java`).
- **Pokémon Font Integration**: Loads a custom `.ttf` Pokémon-style font to style the StatsPanel and DetailsPanel.

### 📊 **Statistics & Sorting**
- **Stat Summary**:
  - Calculates minimum, average, and maximum stats.
  - Shows percentage of single-type vs dual-type Pokémon.
  - Stats reflect the **filtered Pokémon list**, not the entire dataset.
- **Sorting**:
  - Numeric columns (HP, Attack, etc.) sort high-to-low.
  - Name and Type columns sort alphabetically.
  - "None" values in Type 2 are pushed to the bottom of the table.

### 🔍 **Filtering Options**
- **Type** (e.g., Fire, Water, etc.)
- **Legendary Status** (toggle to show only legendary Pokémon)
- **Generation** (choose from Generation 1 to 6)
- Filters update **all panels**, including:
  - The Pokémon table
  - The statistics summary
  - The type distribution chart

---

## 🔧 Installation & Setup

### 🖥 Prerequisites
- Java 22 or later  
- JFreeChart Library  
  - `jfreechart-1.0.19.jar`  
  - `jcommon-1.0.24.jar`

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
- **GUI Panels**:
  - **Table Panel**: View, sort, and filter Pokémon
  - **Stats Panel**: Filter-aware stats and summaries
  - **Type Chart Panel**: Pie chart of filtered Pokémon types
  - **Details Panel**: Shows additional info for selected Pokémon

3. Interacting with the Table:
- Click a row to display details below the table
- Click column headers to sort
- Apply filters and view live updates in all panels
- Reset sorting with the "Reset Sort" button

---
📝 **Author:** *Aiden Cary*  
📅 **Last Updated:** *March 2025*  
🤖 **AI Assistance:** Some parts of this project were developed with guidance from **ChatGPT**.

