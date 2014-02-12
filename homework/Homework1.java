package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Lyange Dmitriy
 * Date: 04.02.14
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */
public class Homework1 extends JFrame {

    private static Homework1 mainFrame = new Homework1();

    private final int DEFAULT_POSITION_X; //координата X верхнего левого угла окна
    private final int DEFAULT_POSITION_Y; //координата Y верхнего левого угла окна
    private final int DEFAULT_WIDTH;      //ширина окна
    private final int DEFAULT_HEIGHT;     //высота окна

    //Компоненты
    //private JPanel gPanel1;

    private JLabel jLabel1;
    private JTextArea jText1;
    private JPanel gPanel2;

    private JTextField jTextFieldx00;
    private JTextField jTextFieldy00;
    private JTextField jTextFieldx10;
    private JTextField jTextFieldy10;

    private JTextField jTextFieldx01;
    private JTextField jTextFieldy01;
    private JTextField jTextFieldx11;
    private JTextField jTextFieldy11;

    private JTextField jTextField12;
    private JTextField jTextField22;

    private JButton jButton1;
    private JButton jButton2;


    //Дополнительные компоненты
    private BufferedImage image1;
    private ImageIcon iIcon1;
    private Graphics graphics;

    //Коструктор окна
    private Homework1() {
        Toolkit t = Toolkit.getDefaultToolkit();
        DEFAULT_POSITION_X = t.getScreenSize().width / 4;
        DEFAULT_POSITION_Y = t.getScreenSize().height / 4;
        DEFAULT_WIDTH = t.getScreenSize().width / 2;
        DEFAULT_HEIGHT = t.getScreenSize().height / 2;

        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(DEFAULT_POSITION_X, DEFAULT_POSITION_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setTitle("Вычгеом. Задание 1");

        //gPanel1 = new JPanel(new BorderLayout());
        jLabel1 = new JLabel();
        jLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1 = new JTextArea("ololo");
        jText1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1.setEditable(false);
        gPanel2 = new JPanel(new GridLayout(12, 1));


        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        setLayout(gridbag);
        c.weightx = 0.0;
        c.weighty = 1.0;
        gridbag.setConstraints(gPanel2, c);
        c.weightx = 0.0 ;
        gridbag.setConstraints(jText1, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        gridbag.setConstraints(jLabel1, c);

        add(gPanel2);
        add(jText1);
        add(jLabel1);

        jButton1 = new JButton();
        jButton1.setText("Clear");
        jTextFieldx00 = new JTextField("0");
        jTextFieldy00 = new JTextField("0");
        jTextFieldx10 = new JTextField("0");
        jTextFieldy10 = new JTextField("0");
        jTextFieldx01 = new JTextField("0");
        jTextFieldy01 = new JTextField("0");
        jTextFieldx11 = new JTextField("0");
        jTextFieldy11 = new JTextField("0");
        jTextField12 = new JTextField("1 отрезок");
        jTextField12.setEditable(false);
        jTextField22 = new JTextField("2 отрезок");
        jTextField22.setEditable(false);
        jButton2 = new JButton();
        jButton2.setText("Create");
        gPanel2.add(jButton1);
        gPanel2.add(jTextField12);
        gPanel2.add(jTextFieldx00);
        gPanel2.add(jTextFieldy00);
        gPanel2.add(jTextFieldx10);
        gPanel2.add(jTextFieldy10);
        gPanel2.add(jTextField22);
        gPanel2.add(jTextFieldx01);
        gPanel2.add(jTextFieldy01);
        gPanel2.add(jTextFieldx11);
        gPanel2.add(jTextFieldy11);
        gPanel2.add(jButton2);

        //this.setContentPane(this.gPanel1);
        this.setVisible(true);

        image1 = new BufferedImage(jLabel1.getWidth(), jLabel1.getHeight(), BufferedImage.TYPE_INT_RGB);
        graphics = image1.getGraphics();
        graphics.fillRect(0, 0, jLabel1.getWidth(), jLabel1.getHeight());
        graphics.setColor(Color.BLACK);

        iIcon1 = new ImageIcon();
        iIcon1.setImage(image1);
        jLabel1.setIcon(iIcon1);
    }

    public static void main(String[] args) {

    }
}
