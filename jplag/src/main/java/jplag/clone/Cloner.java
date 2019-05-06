package jplag.clone;

import jplag.*;
import jplag.clone.util.Pair;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

import static jplag.solidity.SolidityTokenConstants.FILE_END;
import static jplag.solidity.SolidityTokenConstants.SEPARATOR_TOKEN;

public class Cloner {

    public static List<Contract> getContracts() throws ExitException, IOException {
        List<Contract> contracts = new ArrayList<>();
        File code_dir = new File(Folders.contractCodeDir);
        if (!code_dir.isDirectory()) {
            throw new jplag.ExitException("\"" + Folders.rootDir + "\"" + "is not a directory");
        }
        String[] list = null;
        try {
            list = code_dir.list();
        } catch (SecurityException e) {
            throw new jplag.ExitException("Unable to retrieve directory: " + Folders.rootDir + " Cause : " + e.toString());
        }
        assert list != null;

        for (int i = 0; i < list.length; i++) {
            File f = new File(code_dir, list[i]);
            if (!f.isDirectory()) {
                contracts.add(new Contract(code_dir, f.getName()));
            }
        }
        contracts.sort(new SortByTime());
        return contracts;
    }


    public static void main(String[] args) throws ExitException, IOException {
        var contracts = getContracts();
        var cluster = new Cluster(50.0);
        for (var contract :
                contracts) {
            cluster.add(contract);
        }
        cluster.save(Folders.clusterDir);
//
//        var cluster = Cluster.retrive(Folders.clusterDir);
        System.out.println();
    }
}
