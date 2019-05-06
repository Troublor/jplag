package jplag.clone;

import jplag.ExitException;

import javax.naming.directory.InvalidAttributesException;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bucket implements Iterable<BucketItem> {
    private Contract baseContract;
    private List<BucketItem> items;

    public Bucket(Contract baseContract) {
        this.baseContract = baseContract;
        this.items = new ArrayList<>();
    }

    public void add(Contract contract, Double similarity) {
        this.items.add(new BucketItem(contract, similarity));
    }

    public BucketItem get(int index) {
        return this.items.get(index);
    }

    public Contract getBaseContract() {
        return baseContract;
    }

    @Override
    public Iterator<BucketItem> iterator() {
        return items.iterator();
    }

    public void save(File dir) throws IOException {
        saveSourceCode(dir);
        saveBucketInfo(dir);
    }

    private void saveBucketInfo(File dir) throws IOException {
        File infoFile = new File(dir, "BucketInfo.dat");
        if (!infoFile.exists()) {
            if (!infoFile.createNewFile()) {
                throw new IOException(String.format("Could not create file %s/%s", dir.getName(), infoFile.getName()));
            }
        }
        FileWriter fw = new FileWriter(infoFile.getAbsoluteFile());
        fw.write(String.format("%s\n", baseContract.getAddress()));
        for (var item :
                this.items) {
            fw.write(String.format("%s=>%f\n", item.getContract().getAddress(), item.getSimilarity()));
        }
        fw.close();
    }

    private void saveSourceCode(File dir) throws IOException {
        if (!dir.exists()) {
            var r = dir.mkdirs();
            if (!r) {
                throw new IOException(String.format("Could not create dir %s", dir.getName()));
            }
        }
        File baseContractFile = new File(dir, baseContract.getName());
        if (baseContractFile.exists()) {
            if (!baseContractFile.delete()) {
                throw new IOException(String.format("Could not delete file %s/%s", dir.getName(),
                        baseContract.getName()));
            }
        }
        Files.copy(new File(baseContract.getDir(), baseContract.getName()).toPath(), baseContractFile.toPath());
        for (var item :
                items) {
            var contract = item.getContract();
            File contractFile = new File(dir, contract.getName());
            if (contractFile.exists()) {
                if (!contractFile.delete()) {
                    throw new IOException(String.format("Could not delete file %s/%s", dir.getName(),
                            contractFile.getName()));
                }
            }
            Files.copy(new File(contract.getDir(), contract.getName()).toPath(), contractFile.toPath());
        }
    }

    public static Bucket retrive(File dir) throws InvalidAttributesException, IOException, ExitException {
        File infoFile = new File(dir, "BucketInfo.dat");
        if (!infoFile.exists()) {
            throw new InvalidAttributesException();
        }
        var fr = new FileReader(infoFile);
        var br = new BufferedReader(fr);
        String line = br.readLine();
        var baseContract = new Contract(new File(Folders.contractCodeDir), line + ".txt");
        var bucket = new Bucket(baseContract);
        while ((line = br.readLine()) != null) {
            var info = line.split("=>");
            bucket.add(
                    new Contract(new File(Folders.contractCodeDir), info[0] + ".txt"),
                    Double.parseDouble(info[1])
            );
        }
        return bucket;
    }
}
