package jplag.clone;

import jplag.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

public class Contract extends Submission {
    public static Language LANGUAGE;

    static {
        try {
            Constructor<?>[] languageConstructors = Class.forName("jplag.solidity.Language").getDeclaredConstructors();
            Constructor<?> cons = languageConstructors[1];
//            Object[] ob = { program };
            // All Language have to have a program as Constructor
            // Parameter
            // ->public Language(ProgramI prog)
            Language tmp = (Language) cons.newInstance();
            LANGUAGE = tmp;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Date createdTime;
    private String author;
    private String address;

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public File getDir() {
        return this.dir;
    }

    private boolean error;

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isError() {
        return error;
    }

    public Contract(File dir, String name) throws IOException, ExitException {
        super(name, dir, LANGUAGE);
        this.address = name.split("\\.")[0];

        var infoDir = new File(Folders.contractInfoDir);
        var inputStream = new FileInputStream(infoDir.getAbsolutePath() + File.separator + name);
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        var l1 = bufferedReader.readLine();
        var l2 = bufferedReader.readLine();
        this.author = l1.split(":")[1].strip();
        var time = Arrays.copyOfRange(l2.split(":"), 1, l2.split(":").length);
        this.createdTime = new Date(String.join(":", time));

        String[] files = {this.name};
        this.struct = this.language.parse(dir, files);
        if (this.language.errors()) {
            this.error = true;
            this.struct = null;
        } else {
            this.error = false;
        }
    }
}

class SortByTime implements Comparator<Contract> {
    @Override
    public int compare(Contract o1, Contract o2) {
        if (o1.getCreatedTime().before(o2.getCreatedTime())) {
            return -1;
        } else if (o1.getCreatedTime().after(o2.getCreatedTime())) {
            return 1;
        } else {
            return 0;
        }
    }
}