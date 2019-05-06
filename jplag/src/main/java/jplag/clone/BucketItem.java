package jplag.clone;

public class BucketItem {

    private Contract contract;
    private Double similarity;

    public Contract getContract() {
        return contract;
    }


    public Double getSimilarity() {
        return similarity;
    }

    public BucketItem(Contract contract, Double similarity) {
        this.contract = contract;
        this.similarity = similarity;
    }
}
