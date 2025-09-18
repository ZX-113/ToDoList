package data.corpus;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;


public class Corpus implements frame.DisplayCorpus{
    private ArrayList<String> corpus_list;

    public Corpus() {
        corpus_list = new ArrayList<>();
    }

    public void beforeAdd(String new_corpus){
        corpus_list.removeIf(cor -> cor.equals(new_corpus));
    }

    public void add(String new_corpus){
        beforeAdd(new_corpus);
        corpus_list.add(new_corpus);
    }

    public void remove(String new_corpus){
        beforeAdd(new_corpus);
    }

    public ArrayList<String> getCorpus_list() {
        return corpus_list;
    }

    public void setCorpus_list(ArrayList<String> corpus_list) {
        this.corpus_list = corpus_list;
    }

    public void add(AddCorpus getter){
        add(getter.getNewCorpus());
    }

    public String getRandomCorpus(){
        int random_index = ThreadLocalRandom.current().nextInt(0, corpus_list.size());
        return corpus_list.get(random_index);
    }

    @Override
    public String getRandomSentence() {
        return getRandomCorpus();
    }
}
