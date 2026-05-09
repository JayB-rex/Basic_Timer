import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimerApp {

    static int timeLeft = 0;
    static int originalTime = 0;
    static Timer timer;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Timer App");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel timerLabel = new JLabel("00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 45));

        JPanel inputPanel = new JPanel();

        JTextField minutesField = new JTextField("0", 5);
        JTextField secondsField = new JTextField("10", 5);

        inputPanel.add(new JLabel("Minutes:"));
        inputPanel.add(minutesField);
        inputPanel.add(new JLabel("Seconds:"));
        inputPanel.add(secondsField);

        JPanel buttonPanel = new JPanel();

        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton resetButton = new JButton("Reset");

        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(timerLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                timeLeft--;

                timerLabel.setText(formatTime(timeLeft));

                if (timeLeft <= 0) {
                    timer.stop();
                    Toolkit.getDefaultToolkit().beep();

                    JOptionPane.showMessageDialog(
                        frame,
                        "Time's up!",
                        "Timer Finished",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (!timer.isRunning()) {

                    if (timeLeft == 0) {
                        try {
                            int minutes = Integer.parseInt(minutesField.getText());
                            int seconds = Integer.parseInt(secondsField.getText());

                            timeLeft = minutes * 60 + seconds;
                            originalTime = timeLeft;

                            if (timeLeft <= 0) {
                                JOptionPane.showMessageDialog(frame, "Enter a time greater than 0.");
                                return;
                            }

                            timerLabel.setText(formatTime(timeLeft));

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Please enter valid numbers.");
                            return;
                        }
                    }

                    timer.start();
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                timeLeft = originalTime;
                timerLabel.setText(formatTime(timeLeft));
            }
        });

        frame.setVisible(true);
    }

    public static String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}