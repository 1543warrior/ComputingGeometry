package homework;

import geom.Segment;
import geom.Point;

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
 * Date: 14.02.14
 * Time: 22:29
 * To change this template use File | Settings | File Templates.
 */
public class Homework4_ClosestPair extends JFrame {

    private static Homework4_ClosestPair mainFrame = new Homework4_ClosestPair();

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

    private Homework4_ClosestPair() {
        Toolkit t = Toolkit.getDefaultToolkit();
        DEFAULT_POSITION_X = t.getScreenSize().width / 4;
        DEFAULT_POSITION_Y = t.getScreenSize().height / 4;
        DEFAULT_WIDTH = t.getScreenSize().width / 2;
        DEFAULT_HEIGHT = t.getScreenSize().height / 2;

        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(DEFAULT_POSITION_X, DEFAULT_POSITION_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setTitle("Вычислительная геометрия. Задание 4. Ближайшие точки");

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


    boolean existAlready;
    ArrayList<Point> choice;


    public static void main(String[] args) {
        final ArrayList<Point> points = new ArrayList<Point>();

        mainFrame.label1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mainFrame.graphics.setColor(Color.BLACK);
                mainFrame.existAlready = false;

                for (Point point : points) {
                    if (new Point(e.getX(), e.getY()).equals(point)) {
                        mainFrame.existAlready = true;
                        break;
                    }
                }

                if (!mainFrame.existAlready) {
                    points.add(new Point(e.getX(), e.getY()));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (true) {
                    mainFrame.graphics.setColor(Color.BLACK);
                    mainFrame.graphics.setPaintMode();
                    mainFrame.graphics.drawOval(points.get(points.size() - 1).getX() - 2,
                            points.get(points.size() - 1).getY() - 2, 2, 2);
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
                points.clear();
                mainFrame.resultTextArea.setText("");
            }
        });

        mainFrame.createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (points.size() > 2) {
                    mainFrame.createButton.setEnabled(false);
                    mainFrame.clearButton.setEnabled(false);

                    ArrayList<Point> xPoints = new ArrayList<Point>(points);
                    Collections.sort(xPoints, new Comparator<Point>() {
                        @Override
                        public int compare(Point o1, Point o2) {
                            return Double.compare(o1.getX(), o2.getX());
                        }
                    });
                    ArrayList<Point> yPoints = new ArrayList<Point>(points);
                    Collections.sort(yPoints, new Comparator<Point>() {
                        @Override
                        public int compare(Point o1, Point o2) {
                            return Double.compare(o1.getY(), o2.getY());
                        }
                    });
                    ArrayList<Point> auxiliary = new ArrayList<Point>((points.size() * 3) / 2 + 1);        //
                    Segment shortest = mainFrame.closestPair(xPoints, yPoints, auxiliary, 0, points.size() - 1);
                    xPoints.clear();
                    yPoints.clear();
                    mainFrame.drawLine(shortest);
                    mainFrame.label1.update(mainFrame.label1.getGraphics());

                    mainFrame.createButton.setEnabled(true);
                    mainFrame.clearButton.setEnabled(true);
                } else {
                    mainFrame.resultTextArea.setText("Точек\nдолжно\nбыть как\nминимум 3!");
                }
            }
        });
    }

    public Segment closestPair(ArrayList<Point> xPoints, ArrayList<Point> yPoints, ArrayList<Point> auxiliary,
                               int leftBound, int rightBound) {
        if (leftBound >= rightBound) {
            return null;
        }
        if (leftBound + 1 == rightBound) {
            return new Segment(xPoints.get(leftBound), xPoints.get(rightBound));
        }

        int middle = leftBound + (rightBound - leftBound) / 2;
        Point middlePoint = xPoints.get(middle);

        Segment leftShortest = closestPair(xPoints, yPoints, auxiliary, leftBound, middle);
        Segment rightShortest = closestPair(xPoints, yPoints, auxiliary, middle + 1, rightBound);
        Segment shortest = null;
        if (leftShortest != null && rightShortest != null) {
            shortest = leftShortest.getLength() < rightShortest.getLength() ? leftShortest : rightShortest;
        } else {
            if (leftShortest != null) {
                shortest = leftShortest;
            }
            if (rightShortest != null) {
                shortest = rightShortest;
            }
        }
        if (shortest != null) {
            int currentIndex = 0;
            for (int i = leftBound; i <= rightBound; i ++) {
                if ((Math.abs(yPoints.get(i).getX() - middlePoint.getX())) < shortest.getLength()) {
                    auxiliary.add(currentIndex ++, yPoints.get(i));
                }
            }

            for (int i = 0; i < currentIndex; i ++) {
                for (int j = i + 1; (j < currentIndex) && (auxiliary.get(j).getY() - auxiliary.get(i).getY() <
                        shortest.getLength()); j ++) {
                    Segment currentSegment = new Segment(auxiliary.get(i), auxiliary.get(j));
                    if (currentSegment.getLength() < shortest.getLength()) {
                        shortest = currentSegment;
                    }
                }
            }
        }

        return shortest;
    }

    public void drawLine(Segment shortest) {
        graphics.setColor(Color.RED);
        graphics.drawLine(shortest.getPoint1().getX(), shortest.getPoint1().getY(),
                shortest.getPoint2().getX(), shortest.getPoint2().getY());
    }
}
