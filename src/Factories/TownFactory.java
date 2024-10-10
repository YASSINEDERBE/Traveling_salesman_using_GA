package Factories;

import DataStructures.Town;
import java.util.List;

public class TownFactory
{
    public List<Town> GetTowns()
    {
        List<Town> towns = new List<>();

//        for(i = 0; i < TownHelper.TownPositions.Count; i++)
//        {
//            List<> townPosition = TownHelper.TownPositions[i];
//            towns.Add(new Town(townPosition, new Texture($"../../Resources/Town_{i+1}.png")));
//        }

        return towns;
    }
}
