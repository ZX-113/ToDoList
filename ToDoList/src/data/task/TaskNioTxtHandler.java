package data.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TaskNioTxtHandler {

    private static final String CONTENT_MARKER = "§"; // 特殊字符作为内容标记
    private static final String NEWLINE_PLACEHOLDER = "¶"; // 替代换行符的占位符

    public static void saveTask(Task task, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.notExists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        ArrayList<String> lines = new ArrayList<>();

        for (DayTask dayTask : task.getDayTasks()) {
            lines.add("===" + dayTask.getDate() + "===");

            ArrayList<TaskUnit> units = (ArrayList<TaskUnit>) dayTask.getTask_list();
            for (int i = 0; i < units.size(); i++) {
                TaskUnit unit = units.get(i);

                String content = (unit.getContent() == null) ? "" : unit.getContent();
                content = content.replace("\n", NEWLINE_PLACEHOLDER);
                String wrappedContent = CONTENT_MARKER + content + CONTENT_MARKER;

                String unitLine = String.join("|",
                        (unit.getTitle() == null ? "" : unit.getTitle()),
                        wrappedContent,
                        (unit.getDead_line() == null ? "" : unit.getDead_line().toString()),
                        String.valueOf(unit.isExtern()),
                        String.valueOf(unit.isFinished())
                );
                lines.add(unitLine);

                if (i != units.size() - 1) {
                    lines.add("---");
                }
            }
            lines.add("");
        }

        Files.write(path, lines);
    }

    public static Task loadTask(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.notExists(path)) {
            return new Task();
        }

        Task task = new Task();
        ArrayList<DayTask> dayTasks = new ArrayList<>();
        DayTask currentDayTask = null;
        ArrayList<TaskUnit> currentUnits = new ArrayList<>();

        try (Stream<String> stream = Files.lines(path)) {
            ArrayList<String> lines = new ArrayList<>(stream.toList());

            for (String line : lines) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    continue;
                }

                if (trimmedLine.startsWith("===") && trimmedLine.endsWith("===")) {
                    if (currentDayTask != null) {
                        currentDayTask.setTask_list(currentUnits);
                        dayTasks.add(currentDayTask);
                    }
                    String dateStr = trimmedLine.substring(3, trimmedLine.length() - 3);
                    currentDayTask = new DayTask(LocalDate.parse(dateStr));
                    currentUnits = new ArrayList<>();
                    continue;
                }

                if (trimmedLine.equals("---")) {
                    continue;
                }

                TaskUnit unit = parseTaskUnit(line);
                if (unit != null) {
                    currentUnits.add(unit);
                }
            }

            if (currentDayTask != null) {
                currentDayTask.setTask_list(currentUnits);
                dayTasks.add(currentDayTask);
            }
        }

        task.setDayTasks(dayTasks);
        return task;
    }

    private static TaskUnit parseTaskUnit(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 5) {
            return null; // 格式错误
        }

        TaskUnit unit = new TaskUnit();
        unit.setTitle(parts[0].isEmpty() ? null : parts[0]);

        String content = parts[1];
        if (content.startsWith(CONTENT_MARKER) && content.endsWith(CONTENT_MARKER)) {
            content = content.substring(1, content.length() - 1); // 移除首尾标记
            content = content.replace(NEWLINE_PLACEHOLDER, "\n"); // 恢复换行
            unit.setContent(content.isEmpty() ? null : content);
        } else {
            unit.setContent(null);
        }

        unit.setDead_line(parts[2].isEmpty() ? null : LocalDate.parse(parts[2]));
        unit.setExtern(Boolean.parseBoolean(parts[3]));
        unit.setFinished(Boolean.parseBoolean(parts[4]));

        return unit;
    }
}
