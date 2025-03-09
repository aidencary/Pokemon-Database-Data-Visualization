# PokeApp 

## Overview

**PokeApp** is a simple Java application that demonstrates loading Pokémon data from a CSV file, viewing it in a graphical user interface (GUI), and performing basic operations such as filtering data, displaying Pokémon types with custom colors, and more. It showcases effective use of Java Swing for GUI and utilizes modern Java features like `record` for efficient data representation.

The application is aimed at providing a lightweight introduction to graphical applications, data parsing, and some basic visualizations using Java.

---

## Features
1. **Loading Pokémon Data**:
   - Reads data from a `.csv` file (`pokemon.csv`) which includes details of Pokémon from Generations 1 to 6 including Mega Evolutions and alternate forms.
   - Each record represents a Pokémon with stats such as `HP`, `Attack`, `Defense`, etc.
   - The raw data is loaded into a Java `List` of `Pokemon` objects.

2. **Display of Pokémon Data in GUI**:
   - A Swing-based graphical interface (`PokemonGUI`) for viewing Pokémon data in a user-friendly tabular format.
   - Simple console output for key Pokémon stats and total count.

3. **Type-Specific Coloring**:
   - A custom renderer (`TypeColorRenderer`) highlights Pokémon types such as `Fire`, `Water`, `Grass`, etc., by applying type-specific colors in the GUI.

4. **Data Representation**:
   - Uses Java's `record` to store Pokémon information efficiently. Each Pokémon's attributes are encapsulated in a clean, immutable format.

---

## Prerequisites

1. **Java SDK**: Ensure that you have Java 22 or later installed.
2. **Swing**: A standard part of the Java SDK is used for the graphical user interface, so no extra libraries are required.
3. **CSV File**: The application depends on the `pokemon.csv` file located at `src/Data/`. Ensure the file is present and formatted correctly.

---

## Getting Started

1. Clone or download this repository to your local machine.
2. Ensure the Pokémon data file `pokemon.csv` is placed in the directory: `src/Data/`.
   - The CSV file format should follow the structure:  
     ```csv
     ID,Name,Type 1,Type 2,Total,HP,Attack,Defense,Sp. Atk,Sp. Def,Speed,Generation,Legendary
     1,Bulbasaur,Grass,Poison,318,45,49,49,65,65,45,1,False
     ...
     ```
3. Open the project in IntelliJ IDEA (or your preferred Java IDE) and build the project.

4. Run the `PokeApp` class:
   - Outputs Key Pokémon details to the console.
   - Launches a GUI to display Pokémon data.

---

## File Structure

- **`PokeApp` (Main Entry Point)**:
  - Reads the CSV file containing Pokémon data.
  - Creates a `List` of `Pokemon` objects and initializes the GUI.

- **`TypeColorRenderer`**:
  - A custom renderer for applying specific colors based on Pokémon types in the table.
  - Each type (e.g., `Fire`, `Water`, `Grass`) is mapped to a specific color.

- **`Pokemon`**:
  - A `record` representing a Pokémon with attributes such as ID, Name, Types, Stats, and Legendary status.
  - Includes a convenient constructor for creating objects from CSV rows.

---

## How It Works

1. **Data Loading**:
   - `PokeApp` uses Java's `Files` and `Stream` APIs to parse the Pokémon data from the `pokemon.csv` file.
   - Each line is converted into a `Pokemon` object using the constructor defined in the `Pokemon` record.

2. **Console Output**:
   - Prints the details of the first Pokémon, the 10th Pokémon, and the total count of loaded Pokémon. 

3. **GUI Initialization**:
   - The application leverages `SwingUtilities.invokeLater` to ensure thread safety while launching the GUI.  
