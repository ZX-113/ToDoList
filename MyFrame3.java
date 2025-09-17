import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import java.io.*;


// 自定义百分比布局管理器
class PercentageLayout implements LayoutManager2 {
    private double[] percentages;
    private int gap;

    public PercentageLayout(double[] percentages, int gap) {
        this.percentages = percentages.clone();
        this.gap = gap;
    }

    public PercentageLayout(double[] percentages) {
        this(percentages, 0);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {}

    @Override
    public void addLayoutComponent(String name, Component comp) {}

    @Override
    public void removeLayoutComponent(Component comp) {}

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int width = 0;
        int height = 0;

        for (Component comp : parent.getComponents()) {
            Dimension d = comp.getPreferredSize();
            width = Math.max(width, d.width);
            height += d.height;
        }

        Insets insets = parent.getInsets();
        return new Dimension(
                width + insets.left + insets.right,
                height + insets.top + insets.bottom + (parent.getComponentCount() - 1) * gap
        );
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        int width = 0;
        int height = 0;

        for (Component comp : parent.getComponents()) {
            Dimension d = comp.getMinimumSize();
            width = Math.max(width, d.width);
            height += d.height;
        }

        Insets insets = parent.getInsets();
        return new Dimension(
                width + insets.left + insets.right,
                height + insets.top + insets.bottom + (parent.getComponentCount() - 1) * gap
        );
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {}

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int width = parent.getWidth() - insets.left - insets.right;
        int height = parent.getHeight() - insets.top - insets.bottom;

        int y = insets.top;
        for (int i = 0; i < parent.getComponentCount(); i++) {
            Component comp = parent.getComponent(i);
            int compHeight = (int)(height * percentages[i]);
            comp.setBounds(insets.left, y, width, compHeight);
            y += compHeight + gap;
        }
    }
}

// 主程序
public class MyFrame3 extends JFrame {
    private Container container;
    private Calendar currentCalendar; // 用于获取当前日期
    private SimpleDateFormat monthYearFormat; // 用于格式化“年月”显示
    private JButton monthYearButton; // 显示当前年月的按钮（替换原标签）
    private JFrame frame; // 提升为成员变量，方便内部类访问
    private ArrayList<String> sentenceLibrary = new ArrayList<>();
    private Random random = new Random();
    // 句子库文件路径（当前目录下的sentences.txt）
    private static final String SENTENCE_FILE = "sentences.txt";



    public void CreateJFrame(String title) {
        // 程序启动时读取句子库文件
        loadSentencesFromFile();
        currentCalendar = Calendar.getInstance(); // 获取当前系统日期
        // 格式化规则：年（4位）+ “年” + 月（2位）+ “月”（如：2024年 05月）
        monthYearFormat = new SimpleDateFormat("yyyy年 MM月");

        frame = new JFrame(title);

        // 窗口关闭时保存句子库
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveSentencesToFile();
            }
        });
        this.container = frame.getContentPane();
        Color myCustomColor = new Color(235, 215, 185);
        Color buttomColor1 = new Color(220, 180, 140);
        Color buttomColor2 = new Color(200, 166, 158);
        // 设置百分比布局：顶部10%，日历33%，任务47%，按钮10%
        container.setLayout(new PercentageLayout(new double[]{0.05, 0.33, 0.57, 0.05}));

        // ======================================
        // 第一部分：顶部区域（拆分为左7右3的两个面板）
        // ======================================
        JPanel topRootPanel = new JPanel(new GridBagLayout()); // 顶部根面板（控制左右比例）
        topRootPanel.setBackground(myCustomColor);
        GridBagConstraints topGbc = new GridBagConstraints();
        topGbc.fill = GridBagConstraints.BOTH; // 让面板填充分配的空间
        topGbc.insets = new Insets(0, 0, 0, 0); // 取消面板间的间隙（如需间隙可加1-2像素）

        // 1. 左侧面板（占30%宽度）- 放【当前年月按钮】（替换原标题标签）
        JPanel topLeftPanel = new JPanel(new BorderLayout());
        topLeftPanel.setBackground(myCustomColor);
        topLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        // 初始化年月按钮：显示当前年月，样式与原标签一致
        monthYearButton = new JButton(monthYearFormat.format(currentCalendar.getTime()));
        monthYearButton.setFont(new Font("微软雅黑", Font.BOLD, 13)); // 保持原标题字体大小
        monthYearButton.setBackground(myCustomColor); // 按钮背景与面板同色，视觉统一
        monthYearButton.setBorderPainted(false); // 取消按钮默认边框（更像标签）
        monthYearButton.setFocusPainted(false); // 取消按钮选中时的焦点框

        // 给年月按钮添加点击事件（可选：如点击弹出日期选择器）
        monthYearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 示例：点击按钮弹出当前年月提示（可扩展为日期选择器）
                JOptionPane.showMessageDialog(frame,
                        "当前选中：" + monthYearButton.getText(),
                        "日期信息",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 将按钮加入左侧面板（居中显示，与原标签位置一致）
        topLeftPanel.add(monthYearButton, BorderLayout.CENTER);

        // 左侧面板布局参数（保持30%宽度）
        topGbc.gridx = 0;
        topGbc.gridy = 0;
        topGbc.weightx = 0.05;
        topGbc.weighty = 1.0;
        topRootPanel.add(topLeftPanel, topGbc);

        // 2. 右侧面板（占30%宽度）- 放“＋”按钮
        JPanel topRightPanel = new JPanel(new GridBagLayout());
        topRightPanel.setBackground(myCustomColor); // 与根面板同色
        topRightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        GridBagConstraints rightGbc = new GridBagConstraints();
        // 右侧按钮靠右对齐，留少量右内边距
        //rightGbc.anchor = GridBagConstraints.EAST;
        //rightGbc.insets = new Insets(0, 10, 0, 0); // 右内边距15像素，避免按钮贴边
        //rightGbc.weightx = 1.0; // 让按钮在右侧面板内自适应水平空间
        rightGbc.weighty = 1.0; // 让按钮垂直居中
        // 核心2：同时开启水平+垂直拉伸（BOTH），让组件填充满分配到的空间
        rightGbc.fill = GridBagConstraints.BOTH;
// 核心3：统一垂直对齐方式为“拉伸填充”（避免anchor导致组件垂直居中不拉伸）
        rightGbc.anchor = GridBagConstraints.CENTER;

        // 1. 新增文字显示框（JLabel，静态显示“未完成：3项”）
        JLabel textLabel = new JLabel(); // 文字内容可自定义
        textLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15)); // 设置字体大小
        // 关键1：设置文字框底色为白色（JLabel默认透明，需先开启不透明）
        textLabel.setOpaque(true); // 必须先设为不透明，背景色才会显示
        textLabel.setBackground(Color.WHITE); // 设置背景色为白色

        // 关键2：设置文字框左侧内边距15像素（文字距离自身左侧边框15像素）
        // BorderFactory.createEmptyBorder(上, 左, 下, 右)：创建空白边框（仅控制内边距，无实线）
        textLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), // 外层边框
                BorderFactory.createEmptyBorder(0, 5, 0, 5)      // 内层内边距
        ));
        //textLabel.setPreferredSize(new Dimension(0, 40));
//        // 关键2：水平方向填充分配的空间
//        rightGbc.fill = GridBagConstraints.HORIZONTAL;
//        // 关键3：水平权重1，确保占满按钮左侧所有剩余空间
//        rightGbc.weightx = 1;
        rightGbc.insets = new Insets(0, 0, 0, 0); // 文字框左侧留10px，右侧与随机按钮留5px
        rightGbc.weightx = 1; // 水平权重1，让文字框占满左侧剩余空间
        rightGbc.gridx = 0; // 文字框在第0列
        topRightPanel.add(textLabel, rightGbc);

        // 3. 核心新增：调用方法获取随机句子，并设置到文字框
        String randomSentence = getRandomSentence();
        textLabel.setText(randomSentence);

//        rightGbc.gridx = 0;
//        rightGbc.anchor = GridBagConstraints.WEST;
//        topRightPanel.add(textLabel, rightGbc);

        // 2. 新增：随机按钮（位于文字框右侧，+按钮左侧）
        JButton randomButton = new JButton("随机");
        randomButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        //randomButton.setPreferredSize(new Dimension(20, 40)); // 固定按钮大小
        rightGbc.insets = new Insets(0,0,0,0); // 与左右组件的间距
        //rightGbc.fill = GridBagConstraints.NONE; // 不拉伸
        rightGbc.weightx = 0; // 不分配额外宽度
        rightGbc.gridx = 1; // 第1列（文字框是第0列）
        //rightGbc.anchor = GridBagConstraints.CENTER;
        topRightPanel.add(randomButton, rightGbc);
        //randomButton.setBorderPainted(false); // 取消按钮默认边框（更像标签）
        randomButton.setFocusPainted(false); // 取消按钮选中时的焦点框
        randomButton.setBackground(buttomColor1);

//        rightGbc.gridx = 0;
//        rightGbc.anchor = GridBagConstraints.WEST;
//        topRightPanel.add(textLabel, rightGbc);
        //"+"按钮：点击添加新句子到句子库
        JButton addButton1 = new JButton("＋");
        addButton1.setFont(new Font("微软雅黑", Font.PLAIN, 18)); // 放大按钮字体，更醒目
        //addButton1.setPreferredSize(new Dimension(10, 40)); // 固定按钮大小（方形更美观）
        // 按钮布局约束：居右，水平权重1（让按钮靠最右侧）
        //******************
        rightGbc.insets = new Insets(0, 0, 0, 0);
        //rightGbc.fill = GridBagConstraints.NONE;
        //********************
        rightGbc.gridx = 2; // 第1列（右侧）
        rightGbc.anchor = GridBagConstraints.EAST; // 居右对齐
        rightGbc.weightx = 0; // 水平权重1（占据剩余空间，将按钮推到最右）
        topRightPanel.add(addButton1, rightGbc);
        addButton1.setBackground(buttomColor2); // 按钮背景与面板同色，视觉统一
        //addButton1.setBorderPainted(false); // 取消按钮默认边框（更像标签）
        addButton1.setFocusPainted(false); // 取消按钮选中时的焦点框

        // 关键：设置右侧权重为0.3（占30%宽度）
        topGbc.gridx = 1; // 右列
        topGbc.weightx = 0.95; // 宽度占比30%
        topGbc.fill = GridBagConstraints.BOTH; // 确保右侧面板自身能填充满分配到的空间
        topRootPanel.add(topRightPanel, topGbc);

        // 将顶部根面板加入整体布局
        container.add(topRootPanel);

        // 按钮1点击事件（保持不变）
//        addButton1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(frame, "添加语料成功!");
//            }
//        });
        randomButton.addActionListener(e -> {
            textLabel.setText(getRandomSentence());
        });
        // 添加句子事件
        addButton1.addActionListener(e -> {
            String newSentence = JOptionPane.showInputDialog(
                    frame,
                    "请输入要添加的句子：",
                    "添加新句子",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (newSentence != null && !newSentence.trim().isEmpty()) {
                sentenceLibrary.add(newSentence.trim());
                JOptionPane.showMessageDialog(
                        frame,
                        "已添加句子：\n" + newSentence,
                        "添加成功",
                        JOptionPane.INFORMATION_MESSAGE
                );
                textLabel.setText(newSentence);
                // 立即保存到文件
                saveSentencesToFile();
            } else if (newSentence != null) {
                JOptionPane.showMessageDialog(
                        frame,
                        "句子不能为空哦！",
                        "输入错误",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });
//        addButton2.addActionListener(e -> {
//            String newRandomSentence = getRandomSentence();
//            textLabel.setText(newRandomSentence); // 更新文字框内容
//        });

        // 第二部分：日历部分
        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(Color.WHITE);
        JLabel calendarLabel = new JLabel("日历区域", SwingConstants.CENTER);
        calendarLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        calendarPanel.add(calendarLabel, BorderLayout.CENTER);
        calendarPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        container.add(calendarPanel);



        // 第三部分：计划清单部分
        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBackground(Color.WHITE);
        JLabel taskLabel = new JLabel("计划清单区域", SwingConstants.CENTER);
        taskLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        taskPanel.add(taskLabel, BorderLayout.CENTER);
        taskPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        container.add(taskPanel);

        //第四部分：按钮部分
        // 按钮区域：使用GridBagLayout实现精确相对布局
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(myCustomColor);

        // 创建GridBagConstraints对象控制布局细节
        GridBagConstraints gbc = new GridBagConstraints();

        // 设置按钮位置参数（核心控制部分）
        gbc.gridx = 0;          // 列索引（0表示第一列）
        gbc.gridy = 0;          // 行索引（0表示第一行）
        gbc.weightx = 1.0;      // 水平权重（控制拉伸比例）
        gbc.weighty = 1.0;      // 垂直权重
        gbc.insets = new Insets(5, 5, 5, 20); // 边距（上、左、下、右）
        gbc.anchor = GridBagConstraints.EAST; // 锚点（靠右对齐）
        JButton addButton = new JButton("添加计划");
        addButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        //buttonPanel.add(addButton);
        buttonPanel.add(addButton, gbc);
        container.add(buttonPanel);
        // 给按钮添加点击事件监听器
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 按钮被点击时执行的操作
                JOptionPane.showMessageDialog(frame, "添加计划成功!");
            }
        });

        frame.setSize(550, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // 从文件加载句子库
    private void loadSentencesFromFile() {
        File file = new File(SENTENCE_FILE);
        // 如果文件不存在，使用默认句子并创建文件
        if (!file.exists()) {
            initDefaultSentences();
            saveSentencesToFile();
            return;
        }

        // 读取文件内容
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    sentenceLibrary.add(line.trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame,
                    "加载句子库失败：" + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            // 加载失败时使用默认句子
            initDefaultSentences();
        }
    }

    // 保存句子库到文件
    private void saveSentencesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SENTENCE_FILE))) {
            for (String sentence : sentenceLibrary) {
                bw.write(sentence);
                bw.newLine(); // 每个句子占一行
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame,
                    "保存句子库失败：" + e.getMessage(),
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // 初始化默认句子
    private void initDefaultSentences() {
        sentenceLibrary.addAll(Arrays.asList(
                "嘿哥们的芋圆是扑通的",
                "我是棋手，你只是棋子",
                "在少吃和不吃之间我选不少吃",
                "谁有不用的钱？我免费处理掉",
                "坦克是没有后视镜的",
                "枪炮是不长眼的",
                "是王不见王还是避我锋芒",
                "高斯想了一晚上的题，我高斯公式秒了"
        ));
    }
    // 获取随机句子的方法
    private String getRandomSentence() {
        if (sentenceLibrary.isEmpty()) {
            return "句子库为空，请添加句子";
        }
        int randomIndex = random.nextInt(sentenceLibrary.size());
        return sentenceLibrary.get(randomIndex);
    }


    public static void main(String args[]) {
        // 使用SwingUtilities确保线程安全
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame3().CreateJFrame("二叉的计划表");
            }
        });
    }
}