package com.example.sparbuch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.swing.filechooser.FileSystemView;
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
        homePath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        homePath += "\\Sparbuch\\";

        try
        {
            File mainDir = new File(homePath);
            if (!mainDir.exists())
            {
                mainDir.mkdirs();
            }

            File mainFile = new File(homePath + dataFileName);
            if(!mainFile.exists())
            {
                mainFile.createNewFile();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void WriteFile(SparbuchData data)
    {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try
        {
            String json = ow.writeValueAsString(data);
            Files.writeString(Path.of(homePath + dataFileName), json);
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
            String json = Files.readString(Path.of(homePath + dataFileName));
            if(json.isEmpty())
            {
                return null;
            }
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
