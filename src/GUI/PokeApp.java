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
        String filePath = "src/Data/pokemon.csv";
        List<Pokemon> pokeList =  loadPokemon(filePath);
        if (pokeList.isEmpty()) {
            System.out.println("No Pokemon Found");
            return;
        }

        // Console Output
        System.out.println("1st Pokemon: " + pokeList.get(0));
        System.out.println("10th Pokemon: " + pokeList.get(9));
        System.out.println("Total Pokemon Including Megas and Alt Forms (From Gen 1 to Gen 6): " + pokeList.size() );




        // GUI
        SwingUtilities.invokeLater(() -> new PokemonGUI(pokeList));

    }

    private static List<Pokemon> loadPokemon(String filePath) {
        try {
            return Files.lines(Paths.get(filePath))
                    .skip(1) // Skip header
                    .map(line -> line.split(","))
                    .map(Pokemon::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
