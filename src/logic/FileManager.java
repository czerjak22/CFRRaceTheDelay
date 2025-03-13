package logic;

import java.io.*;
import java.util.Properties;
import java.util.stream.Collectors;

public class FileManager {
    //ez egy statik class akar lenni ..nincs szukseg initializalni a cuccokat
    //loadolja a filokat egy hash map szerusegbe

    public static int SETTINGS_PROP = 1;
    public static int DATA_PROP = 2;
    public static int USER_PROP = 3;

    private static Properties settingProp = new Properties();
    private static Properties dataProp = new Properties();
    private static Properties userProp = new Properties();

    static {
        try (InputStream settingStream = FileManager.class.getResourceAsStream("/settings.czn");
             InputStream dataStream = FileManager.class.getResourceAsStream("/szovegek.czn");
             InputStream userScoreStream = FileManager.class.getResourceAsStream("/userScores.czn");) {
            //prop initializalasa a fielbol;
            settingProp.load(settingStream);
            dataProp.load(dataStream);
            userProp.load(userScoreStream);
        } catch (IOException e) {
            System.out.println("Can not read properties file");
            throw new RuntimeException(e);//ilyenkor nem is akarom hogy a programom lefusson
        }
    }

    private static void updateFile(int k) {
        try {
            switch (k) {
                case 1:
                    OutputStream setting = new FileOutputStream("./src/settings.czn");
                    settingProp.store(setting, null);
                    setting.close();

                    break;
                case 2:
                    OutputStream data = new FileOutputStream("./src/szovegek.czn");
                    dataProp.store(data, null);
                    data.close();
                    break;
                case 3:
                    OutputStream user = new FileOutputStream("./src/userScores.czn");
                    userProp.store(user, null);
                    user.close();
                    break;
            }
        } catch (IOException e) {
            System.out.println("Can not read properties file");
        }
    }

    public static int getKeysNum(int k) {
        switch (k) {
            case 1:
                return settingProp.size();

            case 2:
                return dataProp.size();

            case 3:
                return userProp.size();
        }
        return -1;
    }

    public static void setData(int from, String key, String val) {
        switch (from) {
            case 1:
                settingProp.setProperty(key, val);
                updateFile(SETTINGS_PROP);
                break;
            case 2:
                dataProp.setProperty(key, val);
                updateFile(DATA_PROP);
                break;
            case 3:
                userProp.setProperty(key, val);
                updateFile(USER_PROP);
                break;

            default:
                System.out.println("Error: the given FROM does not exist!");
                break;
        }
    }

    public static String getData(int from, String key, String defval) {
        String tmp = null;
        switch (from) {
            case 1:
                tmp = settingProp.getProperty(key);
                break;
            case 2:
                tmp = dataProp.getProperty(key);
                break;
            case 3:
                tmp = userProp.getProperty(key);
                break;

            default:
                System.out.println(" The selected file does not exist! Only settings, data and scores");
                break;
        }
        if (tmp == null) {
            System.out.println("Key :" + key + " could not be found defaulting!");
            return defval;
        }
        return tmp;
    }

    private static void rewriteFile(int from, File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter bw = null;
            switch (from) {
                case 1:
                    bw = new BufferedWriter(new FileWriter("./src/settings.czn"));
                    break;
                case 2:
                    bw = new BufferedWriter(new FileWriter("./src/szovegek.czn"));
                    break;
                case 3:
                    bw = new BufferedWriter(new FileWriter("./src/userScores.czn"));
                    break;
                default:
                    return;
            }
            String tmp = br.lines().collect(Collectors.joining("\n"));
            bw.write(tmp);
            br.close();
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Read error!");
        }
    }

    public static void importData(int from, File file) {
        try {
            switch (from) {
                case 1:
                    settingProp.load(new FileInputStream(file));
                    rewriteFile(SETTINGS_PROP, file);
                    break;
                case 2:
                    dataProp.load(new FileInputStream(file));
                    rewriteFile(DATA_PROP, file);
                    break;
                case 3:
                    userProp.load(new FileInputStream(file));
                    rewriteFile(USER_PROP, file);
                    break;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException e) {
            System.out.println("Reading error" + e);
        }
    }

    public static void exportData(int from, File file) {
        try {
            switch (from) {
                case 1:
                    settingProp.store(new FileOutputStream(file), "");
                    break;
                case 2:
                    dataProp.store(new FileOutputStream(file), "");
                    break;
                case 3:
                    userProp.store(new FileOutputStream(file), "");
                    break;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException e) {
            System.out.println("Reading error" + e);
        }
    }
}

