package data.task;

import java.time.LocalDate;

public class TaskUnit {
    private String title;//标题(单日内不可重复)
    private String content;//内容
    private LocalDate dead_line;
    private boolean extern;//是否为外部任务
    private boolean finished;//是否完成

    public boolean isExtern() {
        return extern;
    }

    public void setExtern(boolean extern) {
        this.extern = extern;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public TaskUnit() {
        this.finished = false;
        this.extern = false;
    }

    public TaskUnit(String title) {
        this.title = title;
        this.finished = false;
        this.extern = false;
    }

    public TaskUnit(String title, String content) {
        this.title = title;
        this.content = content;
        this.finished = false;
        this.extern = false;
    }

    public TaskUnit(String title, String content, LocalDate dead_line) {
        this.title = title;
        this.content = content;
        this.dead_line = dead_line;
        this.finished = false;
        this.extern = false;
    }

    public TaskUnit(String title, String content, LocalDate dead_line, boolean extern) {
        this.title = title;
        this.content = content;
        this.dead_line = dead_line;
        this.extern = extern;
        this.finished = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDead_line() {
        return dead_line;
    }

    public void setDead_line(LocalDate dead_line) {
        this.dead_line = dead_line;
    }
}
