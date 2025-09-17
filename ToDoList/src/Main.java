import data.corpus.*;
import data.task.Task;
import data.task.TaskNioTxtHandler;
import data.task.TaskUnit;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IOException {
        String corpus_file_name = "src/data/corpus/corpus_data.txt";
        Corpus corpus = CorpusNioTxtHandler.loadCorpus(corpus_file_name);
        CorpusNioTxtHandler.saveCorpus(corpus,corpus_file_name);
        corpus.add("我不中了。");
        corpus.add("坦克是没有后视镜的。");
        corpus.add("枪炮是不长眼的。");
        corpus.add("黑哥们的语言是不通的");
        corpus.add("我不中了。");
        CorpusNioTxtHandler.saveCorpus(corpus,corpus_file_name);

        String task_file_name = "src/data/task/task_data.txt";
        Task task = new Task();
        task = TaskNioTxtHandler.loadTask(task_file_name);
        task.initDayTask(LocalDate.of(2025, 9, 16),LocalDate.of(2025, 9, 30));
        //task.addTask(new TaskUnit("biaoti","line1",LocalDate.of(2025, 9, 16)),LocalDate.of(2025, 9, 16));
        TaskNioTxtHandler.saveTask(task,task_file_name);
    }
}