package GUI;

import Data.Pokemon;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PokeApp {

    public static void main(String[] args) {
        String filePath = "src/Data/updatedPokemon.csv";

        // Creates a List of Pokemon
        List<Pokemon> pokeList =  loadPokemon(filePath);
        if (pokeList.isEmpty()) {
            System.out.println("No Pokémon Found");
            return;
        }

        // Console Output
        consoleOutput(pokeList);

        // GUI
        SwingUtilities.invokeLater(() -> new PokemonGUI(pokeList));

    }
    private static void consoleOutput(List<Pokemon> pokeList) {
        System.out.println("1st Pokémon: " + pokeList.get(0));
        System.out.println("10th Pokémon: " + pokeList.get(9));
        System.out.println("Total Pokémon Including Megas and Alt Forms (From Gen 1 to Gen 6): " + pokeList.size() );
    }


    private static List<Pokemon> loadPokemon(String filePath) {
        try {
            return Files.lines(Paths.get(filePath))
                    .skip(1) // Skip header
                    .map(line -> line.split(",")) // Split each line into an array of Strings using commas
                    .map(Pokemon::new) // Convert each String array into a Pokemon object
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
