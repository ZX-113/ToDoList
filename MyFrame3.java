import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

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



    public void CreateJFrame(String title) {
        currentCalendar = Calendar.getInstance(); // 获取当前系统日期
        // 格式化规则：年（4位）+ “年” + 月（2位）+ “月”（如：2024年 05月）
        monthYearFormat = new SimpleDateFormat("yyyy年 MM月");

        frame = new JFrame(title);
        this.container = frame.getContentPane();
        Color myCustomColor = new Color(200, 166, 158);

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
        rightGbc.anchor = GridBagConstraints.EAST;
        rightGbc.insets = new Insets(0, 0, 0, 15); // 右内边距15像素，避免按钮贴边
        rightGbc.weightx = 1.0; // 让按钮在右侧面板内自适应水平空间
        rightGbc.weighty = 1.0; // 让按钮垂直居中

        JButton addButton1 = new JButton("＋");
        addButton1.setFont(new Font("微软雅黑", Font.PLAIN, 18)); // 放大按钮字体，更醒目
        addButton1.setPreferredSize(new Dimension(40, 40)); // 固定按钮大小（方形更美观）
        topRightPanel.add(addButton1, rightGbc);
        addButton1.setBackground(myCustomColor); // 按钮背景与面板同色，视觉统一
        //addButton1.setBorderPainted(false); // 取消按钮默认边框（更像标签）
        addButton1.setFocusPainted(false); // 取消按钮选中时的焦点框

        // 关键：设置右侧权重为0.3（占30%宽度）
        topGbc.gridx = 1; // 右列
        topGbc.weightx = 0.95; // 宽度占比30%
        topRootPanel.add(topRightPanel, topGbc);

        // 将顶部根面板加入整体布局
        container.add(topRootPanel);

        // 按钮1点击事件（保持不变）
        addButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "添加语料成功!");
            }
        });

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

        frame.setSize(500, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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