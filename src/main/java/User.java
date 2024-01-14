import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class User extends JFrame implements MouseMotionListener, ActionListener {
    private int x;
    private int y;
    private boolean isReleased = true;
    private boolean isSaved = false;
    private boolean colorIsAdd = false;
    private Color lineColor = Color.black;

    private final ArrayList<ArrayList<LinePoint>> points = new ArrayList<>();
    private final ArrayList<Color> colors = new ArrayList<>();

    private final static TxtDataBase txtDataBase = new TxtDataBase();
    User() {
        points.add(new ArrayList<>());

        CreateLayout();
        CreateButtons();
    }

    private void CreateLayout() {
        setSize(700, 700);
        setBackground(Color.WHITE);
        setForeground(Color.WHITE);
        setLayout(null);
        setVisible(true);
        setBackground(Color.WHITE);
        addMouseMotionListener(this);
    }

    //-------------------------------------BUTTONS--------------------------------------
    private void CreateButtons() {
        CreateButton(630, 10, Color.RED, "RED", "");
        CreateButton(630, 60, Color.GREEN, "GREEN", "");
        CreateButton(630, 110, Color.BLACK, "BLACK", "");
        CreateButton(630, 160, Color.BLUE, "BLUE", "");
        CreateButton(630, 210, Color.YELLOW, "YELLOW", "");
        CreateButton(630, 260, Color.LIGHT_GRAY, "CHOOSE_COLOR", "...");

        CreateButton(10, 10, Color.WHITE, "CLEAR", "C");
        CreateButton(60, 10, Color.WHITE, "READ", "R");
        CreateButton(110, 10, Color.WHITE, "SAVE", "S");
    }

    private void CreateButton(int x, int y, Color color, String actionListener, String buttonText) {
        JButton button = new JButton(buttonText);
        button.setBounds(x, y, 50, 50);
        button.setBackground(color);
        button.addActionListener(this);
        button.setActionCommand(actionListener);
        add(button);

    }

    //---------------------------------DRAWER---------------------------------------
    private Color chooseColor() {
        return JColorChooser.showDialog(this, "Choose color", lineColor);
    }

    private void draw(MouseEvent mouseEvent) {
        Graphics g = getGraphics();
        g.setColor(lineColor);
        g.drawLine(x, y, mouseEvent.getX(), mouseEvent.getY());
        x = mouseEvent.getX();
        y = mouseEvent.getY();

        points.getLast().add(new LinePoint(x, y));
    }


    private void readPointsFromJSon() {
        ArrayList<ArrayList<LinePoint>> linePoints = Utils.readFromJson(txtDataBase.read());

        if (isSaved) {
            Graphics g = getGraphics();

            for (int i = 0; i < linePoints.size() - 1; i++) {
                ArrayList<LinePoint> points = linePoints.get(i);
                g.setColor(colors.get(i));

                for (LinePoint firstPoint : points) {
                    int x1 = firstPoint.x();
                    int y1 = firstPoint.y();

                    if (points.indexOf(firstPoint) + 1 < points.size()) {
                        LinePoint secondPoint = points.get(points.indexOf(firstPoint) + 1);

                        int x2 = secondPoint.x();
                        int y2 = secondPoint.y();

                        g.drawLine(x1, y1, x2, y2);
                    }
                }
            }

        } else {
            System.out.println("Nothing saved yet!");
        }

        isSaved = false;
    }

    private void savePointsToJSon() {
        txtDataBase.save(Utils.saveToJson(points));
        isSaved = true;
        repaint();
    }

    private void clearScreen() {
        points.clear();
        repaint();
    }


    //--------------------------ACTION LISTENER---------------------------
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        colorIsAdd = false;

        if (isReleased) {
            y = mouseEvent.getY();
            x = mouseEvent.getX();
            isReleased = false;
        }
        draw(mouseEvent);
    }


    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        ArrayList<LinePoint> lastPointsArray = points.getLast();

        if (!lastPointsArray.isEmpty()) {
            points.add(new ArrayList<>());

            if (!colorIsAdd) {
                colors.add(lineColor);
                colorIsAdd = true;
            }
        }

        isReleased = true;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "RED" -> lineColor = Color.RED;
            case "GREEN" -> lineColor = Color.GREEN;
            case "BLACK" -> lineColor = Color.BLACK;
            case "BLUE" -> lineColor = Color.BLUE;
            case "YELLOW" -> lineColor = Color.YELLOW;
            case "CHOOSE_COLOR" -> lineColor = chooseColor();
            case "CLEAR" -> clearScreen();
            case "READ" -> readPointsFromJSon();
            case "SAVE" -> savePointsToJSon();
        }
    }
}
