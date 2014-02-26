package homework;

import geom.Point;
import geom.Segment;

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
 * Date: 26.02.14
 * Time: 2:11
 * To change this template use File | Settings | File Templates.
 */
public class Homework5_SegmentsIntersection extends JFrame {

    private static Homework5_SegmentsIntersection mainFrame = new Homework5_SegmentsIntersection();

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

    private Homework5_SegmentsIntersection() {
        Toolkit t = Toolkit.getDefaultToolkit();
        DEFAULT_POSITION_X = t.getScreenSize().width / 4;
        DEFAULT_POSITION_Y = t.getScreenSize().height / 4;
        DEFAULT_WIDTH = t.getScreenSize().width / 2;
        DEFAULT_HEIGHT = t.getScreenSize().height / 2;

        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(DEFAULT_POSITION_X, DEFAULT_POSITION_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setTitle("Вычислительная геометрия. Задание 5. Определение наличия пересекающихся отрезков");

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
    boolean isSecondPointInSegment = false;


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
                    if (!mainFrame.isSecondPointInSegment) {
                        points.add(new Point(e.getX(), e.getY()));
                        mainFrame.isSecondPointInSegment = true;
                    } else {
                        points.add(new Point(e.getX(), e.getY()));
                        if (points.get(points.size() - 2).getX() < points.get(points.size() - 1).getX()) {
                            points.get(points.size() - 2).setIsLeft(true);
                            points.get(points.size() - 1).setIsLeft(false);
                            Segment segment = new Segment(points.get(points.size() - 2), points.get(points.size() - 1));
                            points.get(points.size() - 2).setSegment(segment);
                            points.get(points.size() - 1).setSegment(segment);
                            mainFrame.drawLine(points.get(points.size() - 2), points.get(points.size() - 1));
                            mainFrame.label1.update(mainFrame.label1.getGraphics());
                            mainFrame.isSecondPointInSegment = false;
                            mainFrame.resultTextArea.setText("");
                        } else if (points.get(points.size() - 2).getX() > points.get(points.size() - 1).getX()) {
                            points.get(points.size() - 2).setIsLeft(false);
                            points.get(points.size() - 1).setIsLeft(true);
                            Segment segment = new Segment(points.get(points.size() - 2), points.get(points.size() - 1));
                            points.get(points.size() - 2).setSegment(segment);
                            points.get(points.size() - 1).setSegment(segment);
                            mainFrame.drawLine(points.get(points.size() - 2), points.get(points.size() - 1));
                            mainFrame.label1.update(mainFrame.label1.getGraphics());
                            mainFrame.isSecondPointInSegment = false;
                            mainFrame.resultTextArea.setText("");
                        } else {
                            mainFrame.resultTextArea.setText("Отрезок\nне может\nбыть вер-\nтикальным!");
                            points.remove(points.size() - 1);
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mainFrame.graphics.setColor(Color.BLACK);
                mainFrame.graphics.setPaintMode();
                mainFrame.graphics.drawOval(points.get(points.size() - 1).getX() - 2,
                        points.get(points.size() - 1).getY() - 2, 2, 2);
                mainFrame.label1.repaint();
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
                if (points.size() % 2 == 0) {
                    mainFrame.createButton.setEnabled(false);
                    mainFrame.clearButton.setEnabled(false);

                    if (isCrossed(points)) {
                        mainFrame.resultTextArea.setText("YES!");
                    } else {
                        mainFrame.resultTextArea.setText("NO!");
                    }

                    mainFrame.createButton.setEnabled(true);
                    mainFrame.clearButton.setEnabled(true);
                } else {
                    mainFrame.resultTextArea.setText("Точек\nдолжно\nбыть\nкратно 2!");
                }
            }
        });
    }

    /*public static boolean isCrossed(ArrayList<Point> xArray) {
        ArrayList<Segment> allSegments = new ArrayList<Segment>();

        sortByX(xArray);

        for (Point p : xArray) {
            if (p.getIsLeft()) {
                insert(allSegments, p.getSegment());
                if ((above(allSegments, p.getSegment()) != null && above(allSegments, p.getSegment()).segmentIntersect
                        (p.getSegment())) || (below(allSegments, p.getSegment()) != null && below(allSegments,
                        p.getSegment()).segmentIntersect(p.getSegment()))) {
                    return true;
                }
            } else {
                if (above(allSegments, p.getSegment()) != null &&
                        below(allSegments, p.getSegment()) != null &&
                        above(allSegments, p.getSegment()).segmentIntersect(below(allSegments, p.getSegment()))) {
                    return true;
                }
                delete(allSegments, p.getSegment());
            }
        }
        return false;
    }  */

    public static boolean isCrossed(ArrayList<Point> xArray) {
        ArrayList<Segment> tLines = new ArrayList<Segment>();

        sortByX(xArray);

        for (Point p : xArray) {
            if (p.getIsLeft()) {
                insert(tLines, p.getSegment());
                mainFrame.drawB(p.getSegment(), Color.BLACK);
                mainFrame.label1.update(mainFrame.label1.getGraphics());

                //System.out.println(above(tLines, p.getSegment()));
                //System.out.println(p.getSegment());
                //System.out.println(below(tLines, p.getSegment()));

                if ((above(tLines, p.getSegment()) != null && above(tLines, p.getSegment()).segmentIntersect(p.getSegment())) ||
                        (below(tLines, p.getSegment()) != null && below(tLines, p.getSegment()).segmentIntersect(p.getSegment()))) {

                    if (above(tLines, p.getSegment()).segmentIntersect(p.getSegment())) {
                        mainFrame.drawB(above(tLines, p.getSegment()), Color.CYAN);
                        mainFrame.drawB(p.getSegment(), Color.CYAN);
                        mainFrame.label1.update(mainFrame.label1.getGraphics());
                    } else {
                        mainFrame.drawB(below(tLines, p.getSegment()), Color.CYAN);
                        mainFrame.drawB(p.getSegment(), Color.CYAN);
                        mainFrame.label1.update(mainFrame.label1.getGraphics());
                    }
                    return true;
                }
            }
            if (!p.getIsLeft()) {
                //System.out.println(above(tLines, p.getSegment()));
                //System.out.println(p.getSegment());
                //System.out.println(below(tLines, p.getSegment()));
                if (above(tLines, p.getSegment()) != null &&
                        below(tLines, p.getSegment()) != null &&
                        above(tLines, p.getSegment()).segmentIntersect(below(tLines, p.getSegment()))) {
                    mainFrame.drawB(above(tLines, p.getSegment()), Color.CYAN);
                    mainFrame.drawB(below(tLines, p.getSegment()), Color.CYAN);
                    mainFrame.label1.update(mainFrame.label1.getGraphics());
                    return true;
                }
                mainFrame.drawB(p.getSegment(), Color.GREEN);
                mainFrame.label1.update(mainFrame.label1.getGraphics());
                delete(tLines, p.getSegment());
            }
        }
        return false;
    }

    private static void sortByX(ArrayList<Point> xArray) {
        Collections.sort(xArray, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                if (p1.getX() > p2.getX()) {
                    return 1;
                } else if (p1.getX() < p2.getX()) {
                    return -1;
                } else if (p1.getY() < p2.getY()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    private static void insert(ArrayList<Segment> allSegments, Segment thisSegment) {
        allSegments.add(thisSegment);
    }

    private static Segment above(ArrayList<Segment> allSegments, Segment thisSegment) {
        int index = -1;
        for (Segment segment : allSegments) {
            if (segment == thisSegment) {
                index = allSegments.indexOf(thisSegment);
                break;
            }
        }
        if (index > 0) {
            return allSegments.get(index - 1);
        } else if (index == 0) {
            return null;
        }
        return null;
    }

    private static Segment below(ArrayList<Segment> allSegments, Segment thisSegment) {
        int index = -1;
        for (Segment segment : allSegments) {
            if (segment == thisSegment) {
                index = allSegments.indexOf(thisSegment);
                break;
            }
        }
        if (index < allSegments.size() - 1) {
            return allSegments.get(index + 1);
        } else if (index == allSegments.size() - 1) {
            return null;
        }
        return null;
    }

    private static void delete(ArrayList<Segment> allSegments, Segment thisSegment) {
        for (int i = 0; i < allSegments.size(); i++) {
            if (thisSegment == allSegments.get(i)) {
                allSegments.remove(thisSegment);
                break;
            }
        }
    }

    public void drawLine(Point point1, Point point2) {
        graphics.setColor(Color.RED);
        graphics.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }

    public synchronized void drawB(Segment l, Color c) {
        try {
            Thread.sleep(200);

        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        graphics.setColor(c);
        graphics.drawLine(l.getPoint1().getX(), l.getPoint1().getY(),
                l.getPoint2().getX(), l.getPoint2().getY());
    }
}