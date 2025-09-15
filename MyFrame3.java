import java.awt.*;
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



    public void CreateJFrame(String title) {
        JFrame frame = new JFrame(title);
        Container container = frame.getContentPane();
        Color myCustomColor = new Color(200, 166, 158);

        // 设置百分比布局：顶部10%，日历33%，任务47%，按钮10%
        container.setLayout(new PercentageLayout(new double[]{0.05, 0.33, 0.57, 0.05}));

        // 第一部分：顶部标题
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(myCustomColor);
        JLabel titleLabel = new JLabel("二叉的计划表", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        container.add(topPanel);

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
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(myCustomColor);
        JButton addButton = new JButton("添加计划");
        addButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        buttonPanel.add(addButton);
        container.add(buttonPanel);
//        // 第四部分：按钮部分
//        JPanel buttonPanel = new JPanel(new BorderLayout()); // 改为BorderLayout
//        buttonPanel.setBackground(myCustomColor);
//        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添加内边距
//
//// 创建一个面板来容纳按钮，并右对齐
//        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        buttonContainer.setOpaque(false); // 设置透明背景
//
//// 创建图标按钮
//        ImageIcon addIcon = createAddIcon(30, 30); // 创建自定义图标
//        JButton addButton = new JButton("添加计划", addIcon);
//        addButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
//        addButton.setBackground(new Color(70, 130, 180)); // 设置按钮背景色
//        addButton.setForeground(Color.WHITE); // 设置文字颜色
//        addButton.setFocusPainted(false); // 去除焦点边框
//        addButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // 设置按钮内边距
//        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 设置手型光标
//
//// 添加鼠标悬停效果
//        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                addButton.setBackground(new Color(100, 149, 237)); // 悬停时变亮
//            }
//
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                addButton.setBackground(new Color(70, 130, 180)); // 恢复原色
//            }
//        });
//
//        buttonContainer.add(addButton);
//        buttonPanel.add(buttonContainer, BorderLayout.EAST); // 将按钮容器放在右侧
//
//        container.add(buttonPanel);

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