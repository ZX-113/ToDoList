package data.corpus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CorpusNioTxtHandler {

    // 保存Corpus到TXT文件
    public static void saveCorpus(Corpus corpus, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        // 创建父目录（如果不存在）
        if (Files.notExists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        // 直接写入所有字符串（每行一个元素）
        Files.write(path, corpus.getCorpus_list());
    }

    // 从TXT文件读取Corpus
    public static Corpus loadCorpus(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) {
            return new Corpus(); // 文件不存在时返回空的Corpus
        }

        // 读取所有行并转换为ArrayList<String>
        try (Stream<String> stream = Files.lines(path)) {
            ArrayList<String> corpusList = (ArrayList<String>) stream
                    .collect(Collectors.toList());

            Corpus corpus = new Corpus();
            corpus.setCorpus_list(corpusList);
            return corpus;
        }
    }
}
