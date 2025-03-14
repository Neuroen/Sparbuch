package com.example.sparbuch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager
{
    private String homePath;
    private final String dataFileName = "Sparbuch.json";

    public FileManager()
    {
        homePath = String.valueOf(Paths.get(System.getProperty("user.home"), "Documents"));
        homePath += "/Sparbuch/";

        try
        {
            if (!Files.exists(Path.of(homePath)))
            {
                File newDir = new File(homePath);
                newDir.mkdirs();

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(homePath);
    }

    public void WriteFile(SparbuchData data)
    {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try
        {
            String json = ow.writeValueAsString(data);
            Files.writeString(Path.of(homePath), json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public SparbuchData ReadFile()
    {
        try
        {
            String json = Files.readString(Path.of(homePath));
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, SparbuchData.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
