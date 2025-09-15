import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame{

    public void CreateJFrame(String title){

        JFrame frame=new JFrame(title);
        Container container=frame.getContentPane();
        Color myCustomColor=new Color(200,166,158);
        container.setBackground(myCustomColor);

        JLabel jl=new JLabel("二叉的计划表");
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(jl);

        frame.setVisible(true);
        frame.setSize(500, 800);//调节窗口大小
        frame.setLocationRelativeTo(null);//让窗口居中显示
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String srgs[]){
        new MyFrame().CreateJFrame("二叉的计划表1");
    }
}