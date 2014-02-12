package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Lyange Dmitriy
 * Date: 04.02.14
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */
public class Homework2 extends JFrame {

    private static Homework2 mainFrame = new Homework2();

    private final int DEFAULT_POSITION_X; //координата X верхнего левого угла окна
    private final int DEFAULT_POSITION_Y; //координата Y верхнего левого угла окна
    private final int DEFAULT_WIDTH;      //ширина окна
    private final int DEFAULT_HEIGHT;     //высота окна

    private JPanel firstPanel;

    private JButton createButton;
    private JButton clearButton;
    private JTextArea resultTextArea;

    private JLabel label1;

    private BufferedImage image1;
    private ImageIcon iIcon1;
    private Graphics graphics;

    private Homework2() {
        Toolkit t = Toolkit.getDefaultToolkit();
        DEFAULT_POSITION_X = t.getScreenSize().width / 4;
        DEFAULT_POSITION_Y = t.getScreenSize().height / 4;
        DEFAULT_WIDTH = t.getScreenSize().width / 2;
        DEFAULT_HEIGHT = t.getScreenSize().height / 2;

        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(DEFAULT_POSITION_X, DEFAULT_POSITION_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setTitle("Вычгеом. Задание 2");

        label1 = new JLabel();
        label1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        firstPanel = new JPanel(new BorderLayout());

        setLayout(new BorderLayout());
        add(firstPanel, BorderLayout.WEST);
        add(label1, BorderLayout.CENTER);

        resultTextArea = new JTextArea("");
        resultTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resultTextArea.setEditable(false);
        createButton = new JButton();
        createButton.setText("Create");
        clearButton = new JButton();
        clearButton.setText("Clear");
        firstPanel.add(createButton, BorderLayout.NORTH);
        firstPanel.add(resultTextArea, BorderLayout.CENTER);
        firstPanel.add(clearButton, BorderLayout.SOUTH);

        this.setVisible(true);

        image1 = new BufferedImage(label1.getWidth(), label1.getHeight(), BufferedImage.TYPE_INT_RGB);
        graphics = image1.getGraphics();
        graphics.fillRect(0, 0, label1.getWidth(), label1.getHeight());
        graphics.setColor(Color.BLACK);

        iIcon1 = new ImageIcon();
        iIcon1.setImage(image1);
        label1.setIcon(iIcon1);
    }

    ArrayList<geom.Point> points = new ArrayList<geom.Point>();
    boolean existAlready;

    public static void main(String[] args) {
        mainFrame.label1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mainFrame.graphics.setColor(Color.BLACK);
                mainFrame.existAlready = false;

                for (geom.Point point : mainFrame.points) {
                    if (new geom.Point(e.getX(), e.getY()).equals(point)) {
                        mainFrame.existAlready = true;
                        break;
                    }
                }

                if (!mainFrame.existAlready) {
                    mainFrame.points.add(new geom.Point(e.getX(), e.getY()));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (true) {
                    mainFrame.graphics.setColor(Color.BLACK);
                    mainFrame.graphics.setPaintMode();
                    mainFrame.graphics.drawOval(mainFrame.points.get(mainFrame.points.size() - 1).getX() - 2,
                            mainFrame.points.get(mainFrame.points.size() - 1).getY() - 2, 2, 2);
                    mainFrame.label1.repaint();
                    mainFrame.resultTextArea.setText("");
                }
            }
        });

        mainFrame.clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.graphics.setColor(Color.WHITE);
                mainFrame.graphics.fillRect(0, 0, mainFrame.label1.getWidth(), mainFrame.label1.getHeight());
                mainFrame.label1.repaint();
                mainFrame.points.clear();
                mainFrame.resultTextArea.setText("");
            }
        });

        mainFrame.createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame.points.size() > 2) {
                    mainFrame.createButton.setEnabled(false);
                    mainFrame.clearButton.setEnabled(false);
                    mainFrame.grahamScan();
                    mainFrame.createButton.setEnabled(true);
                    mainFrame.clearButton.setEnabled(true);
                } else {
                    mainFrame.resultTextArea.setText("Точек\nдолжно\nбыть как\nминимум 3!");
                }
            }
        });
    }

    ArrayList<geom.Point> choice = new ArrayList<geom.Point>();
    //ArrayList<geom.Point> pointsCopy = new ArrayList<geom.Point>();

    public void grahamScan() {
        choice = new ArrayList<geom.Point>();
        geom.Point startPoint;
        startPoint = points.get(startingPoint());
        points.remove(startingPoint());
        sort(startPoint);
        points.add(0, startPoint);
        graphics.setColor(Color.GREEN);
        for (int i = 1; i < points.size(); i ++) {
            drawLine(2, i);
            label1.update(label1.getGraphics());
        }
        geom.Point p0 = points.get(0);
        geom.Point p1 = points.get(1);
        choice.add(p0);
        choice.add(p1);
        repaintImage();
        graphics.setColor(Color.RED);
        for (int i = 2; i < points.size(); i++) {
            while (geom.Point.direction(choice.get(choice.size() - 2), choice.get(choice.size() - 1),
                    points.get(i)) == -1) {
                choice.remove(choice.size() - 1);
                repaintImage();
                graphics.setColor(Color.RED);
                drawLine(1, 0);
            }
            choice.add(points.get(i));
            drawLine(1, 0);
            label1.update(label1.getGraphics());
        }
        drawLine(3, 0);
    }

    public void sort(final geom.Point p) {
        Collections.sort(points, new Comparator<geom.Point>() {
            @Override
            public int compare(geom.Point p2, geom.Point p1) {
                return geom.Point.direction(p, p1, p2);
            }
        });
    }

    public void repaintImage() {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, label1.getWidth(), label1.getHeight());
        graphics.setColor(Color.BLACK);
        for (geom.Point point : points) {
            graphics.setPaintMode();
            graphics.drawOval(point.getX() - 2, point.getY() - 2, 2, 2);
            label1.repaint();
        }
    }

    public int startingPoint() {
        geom.Point min = new geom.Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        int resultIndex = 0;
        for (geom.Point point : points) {
            if (point.getY() <= min.getY()) {
                if (point.getY() < min.getY()) {
                    min = point;
                    resultIndex = points.indexOf(point);
                } else {
                    if (point.getX() < min.getX()) {
                        min = point;
                        resultIndex = points.indexOf(point);
                    }
                }
            }
        }
        return resultIndex;
    }

    public synchronized void drawLine(int type, final int index) {
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
            }
        });
        newThread.start();
        synchronized (newThread) {
            try {
                switch (type) {
                    case 1:
                        newThread.sleep(500);
                        for (int i = 1; i < choice.size(); i ++) {
                            graphics.drawLine(choice.get(i - 1).getX(), choice.get(i - 1).getY(),
                                    choice.get(i).getX(), choice.get(i).getY());
                        }
                        break;
                    case 2:
                        newThread.sleep(100);
                        graphics.drawLine(points.get(0).getX(), points.get(0).getY(),
                                points.get(index).getX(), points.get(index).getY());
                        break;
                    case 3:
                        newThread.sleep(500);
                        graphics.drawLine(choice.get(choice.size() - 1).getX(), choice.get(choice.size() - 1).getY(),
                                choice.get(0).getX(), choice.get(0).getY());
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
